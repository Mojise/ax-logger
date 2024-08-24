package com.mojise.library.util.log.app

import android.app.Application
import com.mojise.library.util.log.Logs
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class LogsApplicationKotlin : Application() {

    override fun onCreate() {
        super.onCreate()

        Logger.addLogAdapter(AndroidLogAdapter())

        Logger.d("LogsApplicationKotlin onCreate")

        Logs.GlobalLogStrategyComposer
            .isVisible(true)
            //.simpleStyle() // or .simpleStyle()
            //.boxStyle()
            .simpleStyle()
            //.withThreadInfo()
            .withMethodStackTrace(5)
            //.withGlobalCustomTag("MyLog!!")
            .apply()
    }
}