package com.resurrection.imkb.ui.base.util

import com.resurrection.imkb.ui.base.general.onlyTry
import java.io.File
import java.io.FileWriter


fun createFile(path: String, sFileName: String?, sBody: MutableList<String>) {
    onlyTry {
        val gpxfile = File(path, "$sFileName.txt")
        val writer = FileWriter(gpxfile)
        sBody.forEach {
            writer.append(it + "\n")
        }
        writer.flush()
        writer.close()
    }
}

fun createFolder(rootPath: String, folderName: String): String {
    val tempFile = File(rootPath, folderName)
    return if (!tempFile.exists()) {
        tempFile.mkdir()
        "$rootPath/$folderName"
    } else rootPath
}