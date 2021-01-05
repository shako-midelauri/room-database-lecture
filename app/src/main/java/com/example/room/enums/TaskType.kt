package com.example.room.enums

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class TaskType: Parcelable {
    ADD_CONTACT,
    EDIT_CONTACT
}