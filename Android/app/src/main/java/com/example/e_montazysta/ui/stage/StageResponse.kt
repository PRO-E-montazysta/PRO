package com.example.e_montazysta.ui.stage

import com.example.e_montazysta.data.model.Comment
import com.example.e_montazysta.data.model.Release
import com.example.e_montazysta.data.model.Result
import com.example.e_montazysta.data.model.Stage
import com.example.e_montazysta.data.model.User
import com.example.e_montazysta.data.repository.interfaces.ICommentRepository
import com.example.e_montazysta.data.repository.interfaces.IReleaseRepository
import com.example.e_montazysta.data.repository.interfaces.IUserRepository
import com.squareup.moshi.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Date

data class StageDAO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "price")
    val price: Float,
    @Json(name = "plannedStartDate")
    val plannedStart: Date?,
    @Json(name = "plannedEndDate")
    val plannedEnd: Date?,
    @Json(name = "startDate")
    val startDate: Date?,
    @Json(name = "endDate")
    val endDate: Date?,
    @Json(name = "plannedDurationTime")
    val plannedDurationTime: Date?,
    @Json(name = "plannedFittersNumber")
    val plannedFittersNumber: Int,
    @Json(name = "minimumImagesNumber")
    val minimumImagesNumber: Int,
    @Json(name = "fitters")
    val fitters: List<Int>,
    @Json(name = "comments")
    val comments: List<Int>,
    @Json(name = "toolReleases")
    val toolReleases: List<Int>,
    @Json(name = "elementReturnReleases")
    val elementReturnReleases: List<Int>,
    @Json(name = "orderId")
    val orderId: Int,
    @Json(name = "listOfToolsPlannedNumber")
    val listOfToolsPlannedNumber: List<Int>,
    @Json(name = "listOfElementsPlannedNumber")
    val listOfElementsPlannedNumber: List<Int>
) : KoinComponent {
    private val userRepository: IUserRepository by inject()
    private val commentRepository: ICommentRepository by inject()
    private val releaseRepository: IReleaseRepository by inject()

    suspend fun mapToStage(): Stage {
        val fittersList: List<User?> = fitters.map {id -> getUserDetails(id)}
        val commentsList: List<Comment?> = comments.map {id -> getCommentDetails(id)}
        val releasesList: List<Release?> = toolReleases.map {id -> getReleaseDetails(id)}
        return Stage(id, name, status, price, plannedStart, plannedEnd, startDate, endDate, plannedDurationTime,
            plannedFittersNumber, minimumImagesNumber, fittersList, commentsList, releasesList,
            listOf(), orderId, listOfToolsPlannedNumber, listOfElementsPlannedNumber)
    }

    private suspend fun getUserDetails(userId: Int): User? {
        val result = userRepository.getUserDetails(userId)
        return when (result) {
            is Result.Success -> result.data
            is Result.Error -> null
        }
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