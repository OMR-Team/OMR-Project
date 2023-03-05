package com.lok.dev.commonutil.preference

import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceUtil @Inject constructor(
    sharedPreferences: SharedPreferences
) : BasePreference(sharedPreferences) {

    companion object {
        private const val PROPERTY_FOLDER_STATE = "PROPERTY_FOLDER_STATE"
    }

    val folderState: Int
        get() = get(PROPERTY_FOLDER_STATE, 0)

    fun putFolderState(stateOrdinal: Int) {
        put(PROPERTY_FOLDER_STATE, stateOrdinal)
    }

}