package com.resurrection.imkb.ui.base.general

import android.annotation.SuppressLint
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.elvishew.xlog.flattener.DefaultFlattener
import com.elvishew.xlog.formatter.border.DefaultBorderFormatter
import com.elvishew.xlog.formatter.message.json.DefaultJsonFormatter
import com.elvishew.xlog.formatter.message.throwable.DefaultThrowableFormatter
import com.elvishew.xlog.formatter.message.xml.DefaultXmlFormatter
import com.elvishew.xlog.formatter.stacktrace.DefaultStackTraceFormatter
import com.elvishew.xlog.formatter.thread.DefaultThreadFormatter
import com.elvishew.xlog.interceptor.BlacklistTagsFilterInterceptor
import com.elvishew.xlog.printer.AndroidPrinter
import com.elvishew.xlog.printer.ConsolePrinter
import com.elvishew.xlog.printer.Printer
import com.elvishew.xlog.printer.file.FilePrinter
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy
import com.elvishew.xlog.printer.file.clean.NeverCleanStrategy
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator
import com.elvishew.xlog.printer.file.writer.SimpleWriter
import com.resurrection.imkb.BuildConfig
import com.resurrection.imkb.ui.base.util.createFile
import com.resurrection.imkb.ui.base.util.createFolder
import java.text.SimpleDateFormat
import java.util.*


class Logger {
    var lifecycleOwner: LifecycleOwner? = null
    private val logList = mutableListOf<String>()
    private val LOG_TAG = "AppLogger"
    private lateinit var xLogger: Logger

    // private val fragmentNameList = mutableListOf<String>()
    private var rootPath = "" + Environment.getExternalStorageDirectory().absolutePath
    private var logPath = "" + Environment.getExternalStorageDirectory().absolutePath + "/IMKB/Logs"

    init {

        xLogInit()
        xLogger = XLog.tag(LOG_TAG).build()

        var dateTime = getDateTime()

        rootPath = createFolder(rootPath, "IMKB")
        rootPath = createFolder(rootPath, "Logs")
        rootPath = createFolder(rootPath, getDateTime())


        // TODO: get logPath size
        /*      val file = File(logPath)
              val size = file.length()
              if (size > 1000000) {
                  logPath = createFolder(rootPath, "Logs")
                  logPath = createFolder(logPath, getDateTime())
              }*/

    }

    private fun xLogInit() {
        val config = LogConfiguration.Builder()
            .logLevel(if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE)
            .tag(LOG_TAG)
            .enableThreadInfo()
            .enableStackTrace(2)
            .enableBorder()
            .jsonFormatter(DefaultJsonFormatter())
            .xmlFormatter(DefaultXmlFormatter())
            .throwableFormatter(DefaultThrowableFormatter())
            .threadFormatter(DefaultThreadFormatter())
            .stackTraceFormatter(DefaultStackTraceFormatter())
            .borderFormatter(DefaultBorderFormatter())
            .build()

        val androidPrinter: Printer = AndroidPrinter(true)

        val consolePrinter: Printer = ConsolePrinter()

        val filePrinter: Printer =
            FilePrinter.Builder("<path-to-logs-dir>")
                .fileNameGenerator(DateFileNameGenerator())
                .backupStrategy(NeverBackupStrategy())
                .cleanStrategy(NeverCleanStrategy())
                .flattener(DefaultFlattener())
                .writer(SimpleWriter())
                .build()

        XLog.init(config, androidPrinter, consolePrinter, filePrinter)
    }


    // region log
    fun d(message: String) {
        logList.add(message)
        xLogger.d(message)
    }

    fun e(message: String) {
        logList.add(message)
        xLogger.e(message)
    }

    fun i(message: String) {
        logList.add(message)
        xLogger.i(message)
    }

    fun v(message: String) {
        logList.add(message)
        xLogger.v(message)
    }

    fun w(message: String) {
        logList.add(message)
        xLogger.w(message)
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
    fun fragmentOnCreateView(fragmentName: String) = logList.add("Fragment OnCreateView : $fragmentName")
    fun fragmentOnStart(fragmentName: String) = this.d("Fragment OnStart : $fragmentName")
    fun fragmentOnResume(fragmentName: String) = this.d("Fragment OnResume : $fragmentName")
    fun fragmentOnPause(fragmentName: String) = this.d("Fragment OnPause : $fragmentName")
    fun fragmentOnStop(fragmentName: String) = this.d("Fragment OnStop : $fragmentName")
    fun fragmentOnDestroyView(fragmentName: String) = this.d("Fragment OnDestroyView : $fragmentName")
    fun fragmentOnDestroy(fragmentName: String) = this.d("Fragment OnDestroy : $fragmentName")
    // endregion

    @SuppressLint("SimpleDateFormat")
    private fun getDateTime() = SimpleDateFormat("dd_mm_yyyy-hh_mm_ss").format(Date())
}