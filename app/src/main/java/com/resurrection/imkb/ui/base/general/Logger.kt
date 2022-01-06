package com.resurrection.imkb.ui.base.general

import android.annotation.SuppressLint
import android.os.Environment
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.resurrection.imkb.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class Logger {
    var lifecycleOwner: LifecycleOwner? = null
    private val logList = mutableListOf<String>()
    private val LOG_TAG = "AppLogger"
    private val LIFECYCLE_TAG = "LifecycleLogger"
    private lateinit var xLogger: Logger

    // private val fragmentNameList = mutableListOf<String>()
    private var rootPath = "" + Environment.getExternalStorageDirectory().absolutePath


    init {
        XLog.init(LogLevel.ALL)
        xLogger = XLog.tag(LOG_TAG).build()


        var dateTime = getDateTime()

        rootPath = createFolder(rootPath, "IMKB")
        rootPath = createFolder(rootPath, "Log")
        rootPath = createFolder(rootPath, getDateTime())


        // check old log file and delete it
    }

    // region log
    fun d(message: String) {
        logList.add(message)
        xLogger.d(LOG_TAG, message)
    }

    fun e(message: String) {
        logList.add(message)
        xLogger.e(LOG_TAG, message)
    }

    fun i(message: String) {
        logList.add(message)
        xLogger.i(LOG_TAG, message)
    }

    fun v(message: String) {
        logList.add(message)
        xLogger.v(LOG_TAG, message)
    }

    fun w(message: String) {
        logList.add(message)
        xLogger.w(LOG_TAG, message)
    }

    fun wtf(message: String) {
        logList.add(message)
        Log.wtf(LOG_TAG, message)
    }

    fun getLogList() = logList
    fun clearLogList() = logList.clear()
    // endregion

    // region Activity lifecycle
    fun activityOnCreate(activityName: String) {
        this.d("Activity Create : $activityName")
        createFolder(rootPath, activityName)
    }

    fun activityOnStart(activityName: String) = this.d("Activity OnStart : $activityName")
    fun activityOnResume(activityName: String) = this.d("Activity OnResume : $activityName")
    fun activityOnPause(activityName: String) = this.d("Activity OnPause : $activityName")
    fun activityOnStop(activityName: String) {
        this.d("Activity OnStop : $activityName")
        createFile("$rootPath/$activityName", "Log", logList)
        logList.clear()
    }

    fun activityOnDestroy(activityName: String) = this.d("Activity OnDestroy : $activityName")
    fun activityOnRestart(activityName: String) = this.d("Activity OnRestart : $activityName")

    // endregion

    // region Fragment lifecycle
    fun fragmentOnCreate(fragmentName: String) = logList.add("Fragment Create : $fragmentName")
    fun fragmentOnCreateView(fragmentName: String) =
        logList.add("Fragment OnCreateView : $fragmentName")

    fun fragmentOnStart(fragmentName: String) = this.d("Fragment OnStart : $fragmentName")
    fun fragmentOnResume(fragmentName: String) = this.d("Fragment OnResume : $fragmentName")
    fun fragmentOnPause(fragmentName: String) = this.d("Fragment OnPause : $fragmentName")
    fun fragmentOnStop(fragmentName: String) = this.d("Fragment OnStop : $fragmentName")
    fun fragmentOnDestroyView(fragmentName: String) =
        this.d("Fragment OnDestroyView : $fragmentName")

    fun fragmentOnDestroy(fragmentName: String) = this.d("Fragment OnDestroy : $fragmentName")
    // endregion

    private fun createFile(path: String, sFileName: String?, sBody: MutableList<String>) {
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


/*    private fun writeLog(activityName: String) {
        lifecycleOwner?.let {

            lifecycleOwner!!.lifecycleScope.async(Dispatchers.IO) {

                val file =
                    File("" + Environment.getDataDirectory() + "my_apppp" + getDateTime() + "$activityName/$fragmentDir/log.txt")
                BufferedWriter(file.outputStream().writer()).apply {
                    logList.forEach {
                        write(it)
                        newLine()
                    }
                    close()
                    logList.clear()
                }
            }
        }
    }*/

    fun createFolder(rootPath: String, folderName: String): String {
        val tempFile = File(rootPath, folderName)
        return if (!tempFile.exists()) {
            tempFile.mkdir()
            "$rootPath/$folderName"
        } else rootPath
    }


/*    fun save() {
        val text: String = "asdasdasd"
        var fos: FileOutputStream? = null
        try {

            val folderName = "myFolder/getmyasd/bash"
            val path: String = this.getExternalFilesDir(null)?.absolutePath+""

            File("$path/$folderName").mkdir()

            fos = FileOutputStream("$path/$folderName/cc.txt")

            fos.write(text.toByteArray())

            Toast.makeText(
                this, "Saved to $filesDir/$FILE_NAME",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }*/

    @SuppressLint("SimpleDateFormat")
    private fun getDateTime() = SimpleDateFormat("dd_mm_yyyy-hh_mm_ss").format(Date())


}