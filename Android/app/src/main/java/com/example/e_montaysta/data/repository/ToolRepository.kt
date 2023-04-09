package com.example.e_montaysta.data.repository

import com.example.e_montaysta.data.api.ServiceApi
import com.example.e_montaysta.data.model.Tool
import com.example.e_montaysta.data.repository.Interfaces.IToolRepository
import com.example.e_montaysta.helpers.Interfaces.ISharedPreferencesHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response
import retrofit2.http.Path

class ToolRepository(
    private val toolApi: ServiceApi
) : IToolRepository, KoinComponent {
    private val sharedPreferencesHelper: ISharedPreferencesHelper by inject()
    override suspend fun getDetails(@Path("id") id: Long): Response<Tool> {
        val token = sharedPreferencesHelper.get("lama").toString()
        return toolApi.getDetails(1, token)
    }

    override suspend fun getFilter(tool: Tool): Response<List<Tool>> {
        TODO("Not yet implemented")
    }

//    override suspend fun getTools(): Call<List<Tool>> {
//        val token = sharedPreferencesHelper.get("lama").toString()
//        val call = toolApi.getTools(token)
//
//        call.enqueue(object: Callback<List<Tool>>{
//
//            override fun onResponse(call: Call<List<Tool>>, response: Response<List<Tool>>) {
//                if (response.isSuccessful) {
//                    val toolList: List<Tool>? = response.body()
//                    Log.d("TAG", "Response = " + toolList)
//                }else{
//                    val rc = response.code()
//                    when(rc){
//                        400 -> {
//                            Log.e("Error 400", "Bad connection")
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<Tool>>, t: Throwable) {
//                Log.d("TAG","Response = " + t.toString());
//            }
//        })
//
//    }

    override suspend fun getTools(): Response<List<Tool>> {
        val token = sharedPreferencesHelper.get("lama").toString()
        return try {
            toolApi.getTools(token)
        } catch (e: Exception) {
            Response.error(1, null)
        }
    }

    override suspend fun update(tool: Tool) {
        TODO("Not yet implemented")
    }

    override suspend fun create(tool: Tool) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(tool: Tool) {
        TODO("Not yet implemented")
    }

}