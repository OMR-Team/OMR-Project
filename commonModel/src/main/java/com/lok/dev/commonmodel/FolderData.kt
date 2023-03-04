package com.lok.dev.commonmodel

data class FolderData(
    val id: Int,
    val fileName: String,
    val color: String = "",
    var isSelect: Boolean = false
)

fun addFolderData(): List<FolderData> {
    return listOf(
        FolderData(1, "yellow", "#FFC85B", true),
        FolderData(2, "orange", "#FF9A5D"),
        FolderData(3, "lime", "#BDD33D"),
        FolderData(4, "green", "#52D187"),
        FolderData(5, "sky", "#4CC4E9"),
        FolderData(6, "blue", "#6893FE"),
        FolderData(7, "purple", "#AA81F5"),
        FolderData(8, "pink", "#F182C1"),
        FolderData(9, "white", "#DBDBEA"),
        FolderData(10, "black", "#585870"),
        FolderData(11, "teddybear"),
        FolderData(12, "coin",),
        FolderData(13, "fire"),
        FolderData(14, "hunpoint"),
        FolderData(15, "planet"),
        FolderData(16, "testtube"),
        FolderData(17, "notebook"),
        FolderData(18, "whiteheart"),
        FolderData(19, "blackheart"),
    )
}