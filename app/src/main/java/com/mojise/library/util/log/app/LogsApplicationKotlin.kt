package com.mojise.library.util.log.app

import android.app.Application
import com.mojise.library.util.log.LogStyle
import com.mojise.library.util.log.Logs
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class LogsApplicationKotlin : Application() {

    override fun onCreate() {
        super.onCreate()

        Logger.addLogAdapter(AndroidLogAdapter())

        Logger.d("LogsApplicationKotlin onCreate")

        Logs.GlobalLogStrategyComposer.init()
            .isVisible(BuildConfig.DEBUG)
//            .setGlobalLogTag("MyLog!!")
            .setDefaultLogStyle(LogStyle.Type.SIMPLE)
//            .setBoxLogStyle(
//                LogStyle.Box.newBuilder()
//                    .isShowThreadInfo(true)
//                    .isShowMethodStackTrace(true)
//                    .showingMethodStackCount(Int.MAX_VALUE)
//                    .build()
//            )
//            .setSimpleLogStyle(
//                LogStyle.Simple.newBuilder()
//                    .isShowThreadInfo(true)
//                    .isShowMethodStackTrace(true)
//                    .showingMethodStackCount(2)
//                    .build()
//            )
            .apply()
    }
}