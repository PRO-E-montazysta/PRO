package com.example.e_montazysta.ui.returnitem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_montazysta.data.model.ReleaseItem
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.data.model.mapToReleaseItem
import com.example.e_montazysta.data.repository.interfaces.IElementRepository
import com.example.e_montazysta.data.repository.interfaces.IReleaseRepository
import com.example.e_montazysta.data.repository.interfaces.IToolRepository
import com.example.e_montazysta.data.repository.interfaces.IWarehouseRepository
import com.example.e_montazysta.ui.warehouse.WarehouseFilterDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext


class ReturnCreateViewModel : ViewModel(), CoroutineScope, KoinComponent {

    private var job: Job? = null

    private val toolRepository: IToolRepository by inject()

    private val elementRepository: IElementRepository by inject()

    private val releaseRepository: IReleaseRepository by inject()

    val _itemsLiveData = MutableLiveData<List<ReleaseItem>>()
    val itemsLiveData: LiveData<List<ReleaseItem>> = _itemsLiveData

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    private val _warehousesLiveData = MutableLiveData<List<WarehouseFilterDAO>>()
    val warehouseLiveData: LiveData<List<WarehouseFilterDAO>> = _warehousesLiveData

    private val _selectedWarehouseLiveData = MutableLiveData<WarehouseFilterDAO?>()
    val selectedWarehouseLiveData: LiveData<WarehouseFilterDAO?> = _selectedWarehouseLiveData

    lateinit var stage: Stage

    fun addToolToReturn(code: String?) {
        job = launch {
            addToolToReturnAsync(code)
        }
    }

    private suspend fun addToolToReturnAsync(code: String?) {
        _isLoadingLiveData.postValue(true)
        val currentItems = _itemsLiveData.value ?: emptyList()
        val existingItem = currentItems.find { it.code == code }
        if (existingItem != null) {
            _messageLiveData.postValue("Narzędzie zostało już dodane do listy:\n$code")
        } else {
            val result = toolRepository.getToolByCode(code)
            when (result) {
                is Result.Success -> {
                    val tool = mapToReleaseItem(result.data)
                    val isReleased = stage.simpleToolReleases.any { it.toolId == tool.id }
                    if (isReleased) {
                        _itemsLiveData.value = currentItems + tool
                        if (selectedWarehouseLiveData.value == null) {
                            setWarehouse(
                                WarehouseFilterDAO(
                                    result.data.warehouse.id,
                                    "",
                                    result.data.warehouse.name,
                                    result.data.warehouse.openingHours,
                                    ""
                                )
                            )
                        }
                        _messageLiveData.postValue("Narzędzie dodane")
                    } else {
                        _messageLiveData.postValue("Brak narzędzia wśród wydanych!")
                    }
                }

                is Result.Error -> result.exception.message?.let { _messageLiveData.postValue(it) }
            }
        }
        _isLoadingLiveData.postValue(false)
    }

    fun addElementToReturn(code: String?) {
        job = launch {
            addElementToReturnAsync(code)
        }
    }

    //TODO Ogarnąć ten kod bo jest 2x to samo co w narzędziu
    private suspend fun addElementToReturnAsync(code: String?) {
        _isLoadingLiveData.postValue(true)
        val currentItems = _itemsLiveData.value ?: emptyList()
        val existingItem = currentItems.find { it.code == code }
        if (existingItem != null) {
            _messageLiveData.postValue("Element został już dodany do listy:\n$code")
        } else {
            val result = elementRepository.getElementByCode(code)
            when (result) {
                is Result.Success -> {
                    val element = mapToReleaseItem(result.data)
                    val isReleased =
                        stage.simpleElementReturnReleases.any { it.elementId == element.id }
                    if (isReleased) {
                        _itemsLiveData.value = currentItems + element
                        _messageLiveData.postValue("Element dodany")
                    } else {
                        _messageLiveData.postValue("Brak elementu wśród wydanych!")
                    }
                }

                is Result.Error -> {
                    result.exception.message?.let { _messageLiveData.postValue(it) }
                    _isLoadingLiveData.postValue(false)
                }
            }
            _isLoadingLiveData.postValue(false)
        }
    }


    suspend fun createReturn(items: List<ReleaseItem>, stageId: Int): Result<Any> {
        return createReturnAsync(items, stageId)
    }

    private suspend fun createReturnAsync(items: List<ReleaseItem>, stageId: Int): Result<Any> {
        _isLoadingLiveData.postValue(true)
        val result =
            releaseRepository.createReturn(items, stageId, selectedWarehouseLiveData.value?.id)
        when (result) {
            is Result.Success -> {
                _messageLiveData.postValue("Przedmioty zostały zwrócone.")
            }

            is Result.Error -> {
                result.exception.message?.let { _messageLiveData.postValue(it) }
                _isLoadingLiveData.postValue(false)
            }
        }
        _isLoadingLiveData.postValue(false)
        return result
    }

    fun getListOfWarehouse() {
        job = launch {
            withContext(Dispatchers.Default) { getListOfWarehouseAsync() }
        }
    }

    private suspend fun getListOfWarehouseAsync() {
        _isLoadingLiveData.postValue(true)
        val warehouseRepository: IWarehouseRepository by inject()
        val result = warehouseRepository.getListOfWarehouse()
        when (result) {
            is Result.Success -> _warehousesLiveData.postValue(result.data)
            is Result.Error -> {
                result.exception.message?.let { _messageLiveData.postValue(it) }
                Log.e("getListOfToolTypeAsync", Log.getStackTraceString(result.exception))
                _isLoadingLiveData.postValue(false)
            }
        }
        _isLoadingLiveData.postValue(false)
    }

    fun setWarehouse(selectedWarehouseFilter: WarehouseFilterDAO?) {
        _selectedWarehouseLiveData.postValue(selectedWarehouseFilter)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}
