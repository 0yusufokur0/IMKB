package com.resurrection.imkb.ui.base.general

import android.annotation.SuppressLint
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
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
    private var activityName = ""
    private val fragmentNameList = mutableListOf<String>()

    init {
        XLog.init(LogLevel.ALL)
        // check old log file and delete it
    }

    fun d(message: String) {
        logList.add(message)
        XLog.d(LOG_TAG, message)
    }

    fun e(message: String) {
        logList.add(message)
        XLog.e(LOG_TAG, message)
    }

    fun i(message: String) {
        logList.add(message)
        XLog.i(LOG_TAG, message)
    }

    fun v(message: String) {
        logList.add(message)
        XLog.v(LOG_TAG, message)
    }

    fun w(message: String) {
        logList.add(message)
        XLog.w(LOG_TAG, message)
    }

    fun wtf(message: String) {
        logList.add(message)
        Log.wtf(LOG_TAG, message)
    }

    fun getLogList() = logList
    fun clearLogList() = logList.clear()


    // region Activity lifecycle
    fun activityOnCreate(activityName: String) {
        XLog.d(LIFECYCLE_TAG, "Activity Create : $activityName")
        this.activityName = activityName
    }

    fun activityOnStart(activityName: String) = XLog.d(LIFECYCLE_TAG, "Activity OnStart : $activityName")
    fun activityOnResume(activityName: String) = XLog.d(LIFECYCLE_TAG, "Activity OnResume : $activityName")
    fun activityOnPause(activityName: String) = XLog.d(LIFECYCLE_TAG, "Activity OnPause : $activityName")
    fun activityOnStop(activityName: String) = XLog.d(LIFECYCLE_TAG, "Activity OnStop : $activityName")
    fun activityOnDestroy(activityName: String) {
        XLog.d(LIFECYCLE_TAG, "Activity OnDestroy : $activityName")
    }

    fun activityOnRestart(activityName: String) = XLog.d(LIFECYCLE_TAG, "Activity OnRestart : $activityName")
    // endregion

    // region Fragment lifecycle
    fun fragmentOnCreate(fragmentName: String) = XLog.d(LIFECYCLE_TAG, "Fragment Create : $fragmentName")
    fun fragmentOnCreateView(fragmentName: String) {
        XLog.d(LIFECYCLE_TAG, "Fragment OnCreateView : $fragmentName")
        fragmentNameList.add(fragmentName)
    }

    fun fragmentOnStart(fragmentName: String) = XLog.d(LIFECYCLE_TAG, "Fragment OnStart : $fragmentName")
    fun fragmentOnResume(fragmentName: String) = XLog.d(LIFECYCLE_TAG, "Fragment OnResume : $fragmentName")
    fun fragmentOnPause(fragmentName: String) = XLog.d(LIFECYCLE_TAG, "Fragment OnPause : $fragmentName")
    fun fragmentOnStop(fragmentName: String) = XLog.d(LIFECYCLE_TAG, "Fragment OnStop : $fragmentName")
    fun fragmentOnDestroyView(fragmentName: String) {
        XLog.d(LIFECYCLE_TAG, "Fragment OnDestroyView : $fragmentName")
        writeLog(activityName)
        fragmentNameList.remove(fragmentName)
    }

    fun fragmentOnDestroy(fragmentName: String) = XLog.d(LIFECYCLE_TAG, "Fragment OnDestroy : $fragmentName")
    // endregion


    private fun writeLog(activityName: String) {
        lifecycleOwner?.let {

            lifecycleOwner!!.lifecycleScope.async(Dispatchers.IO) {
                var fragmentDir = ""

                fragmentNameList.forEach { fragmentDir += "$it/" }

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