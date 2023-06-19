package com.example.e_montazysta.ui.stage

import com.example.e_montazysta.data.model.Comment
import com.example.e_montazysta.data.model.Release
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.data.model.User
import com.example.e_montazysta.data.repository.interfaces.ICommentRepository
import com.example.e_montazysta.data.repository.interfaces.IReleaseRepository

import com.example.e_montazysta.ui.release.ReleaseElementDAO
import com.example.e_montazysta.ui.release.ReleaseToolDAO
import com.squareup.moshi.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.math.BigDecimal
import java.util.Date

data class StageDAO(
    val id: Int,
    val name: String,
    val status: StageStatus,
    val price: BigDecimal,
    val orderName: String,
    @Json(name = "plannedStartDate")
    val plannedStart: Date?,
    @Json(name = "plannedEndDate")
    val plannedEnd: Date?,
    val startDate: Date?,
    val foremanId: Int?,
    val endDate: Date?,
    val plannedDurationTime: Date?,
    val plannedFittersNumber: Int,
    val minimumImagesNumber: Int,
    val fitters: List<Int>,
    val comments: List<Int>,
    val toolReleases: List<Int>,
    val simpleToolReleases: List<ReleaseToolDAO>,
    val simpleElementReturnReleases: List<ReleaseElementDAO>,
    val orderId: Int,
    val listOfToolsPlannedNumber: List<Int>,
    val listOfElementsPlannedNumber: List<Int>
) : KoinComponent {
    private val commentRepository: ICommentRepository by inject()
    private val releaseRepository: IReleaseRepository by inject()

    suspend fun mapToStage(): Stage {
        val fittersList: List<User> = fitters.mapNotNull { id -> User.getUserDetails(id) }
        val foreman: User? = if (foremanId != null) User.getUserDetails(foremanId) else null
        val commentsList: List<Comment> = comments.mapNotNull { id -> getCommentDetails(id) }
        val releasesList: List<Release> = toolReleases.mapNotNull { id -> getReleaseDetails(id) }
        val listOfToolsPlannedNumber =
            listOfToolsPlannedNumber.mapNotNull { id -> PlannedToolDAO.getPlannedTool(id) }
        val listOfElementsPlannedNumber =
            listOfElementsPlannedNumber.mapNotNull { id -> PlannedElementDAO.getPlannedElement(id) }
        return Stage(
            id,
            name,
            status,
            price,
            orderName,
            plannedStart,
            plannedEnd,
            startDate,
            endDate,
            plannedDurationTime,
            plannedFittersNumber,
            minimumImagesNumber,
            fittersList,
            foreman,
            commentsList,
            releasesList,
            listOf(),
            orderId,
            listOfToolsPlannedNumber,
            listOfElementsPlannedNumber,
            simpleToolReleases,
            simpleElementReturnReleases
        )
    }

    fun mapToStageListItem(): StageListItem {
        return StageListItem(id, name, orderName, status, plannedStart, plannedEnd)
    }

    private suspend fun getCommentDetails(commentId: Int): Comment? {
        val result = commentRepository.getCommentDetails(commentId)
        return when (result) {
            is Result.Success -> result.data
            is Result.Error -> null
        }
    }

    private suspend fun getReleaseDetails(releaseId: Int): Release? {
        val result = releaseRepository.getReleaseDetail(releaseId)
        return when (result) {
            is Result.Success -> result.data
            is Result.Error -> null
        }
    }
}

enum class StageStatus(val value: String) {
    PLANNING("PLANOWANIE"),
    ADDING_FITTERS("DODAWANIE MONTAŻYSTÓW"),
    PICK_UP("WYDAWANIE"),
    REALESED("WYDANO"),
    ON_WORK("W TRAKCIE"),
    RETURN("ZWRACANIE"),
    RETURNED("ZWRÓCONO"),
    FINISHED("ZAKOŃCZONO")
}