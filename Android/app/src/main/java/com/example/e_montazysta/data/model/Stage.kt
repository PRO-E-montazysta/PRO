package com.example.e_montazysta.data.model

import android.os.Parcel
import android.os.Parcelable
import com.example.e_montazysta.ui.release.ReleaseElementDAO
import com.example.e_montazysta.ui.release.ReleaseToolDAO
import com.example.e_montazysta.ui.stage.PlannedElementDAO
import com.example.e_montazysta.ui.stage.PlannedToolDAO
import com.example.e_montazysta.ui.stage.StageStatus
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
import java.util.Date

@Parcelize
data class Stage(
    val id: Int,
    val name: String,
    val status: StageStatus,
    val price: BigDecimal,
    val orderName: String,
    val plannedStart: Date?,
    val plannedEnd: Date?,
    val startDate: Date?,
    val endDate: Date?,
    val plannedDurationTime: Int,
    val plannedFittersNumber: Int,
    val minimumImagesNumber: Int,
    val fitters: List<User?>,
    val foreman: User?,
    val comments: List<Comment?>,
    val toolReleases: List<Release?>,
    val elementReturnReleases: List<Release>?,
    val orderId: Int,
    val listOfToolsPlannedNumber: List<PlannedToolDAO>,
    val listOfElementsPlannedNumber: List<PlannedElementDAO>,
    val simpleToolReleases: List<ReleaseToolDAO>,
    val simpleElementReturnReleases: List<ReleaseElementDAO>
) : Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(plannedFittersNumber)
        parcel.writeInt(minimumImagesNumber)
        parcel.writeInt(orderId)
    }

    override fun describeContents(): Int {
        return 0
    }
}
