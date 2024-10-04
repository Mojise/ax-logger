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

        // ax-logger 라이브러리 글로벌 설정
        Logs.GlobalLogStrategyComposer()
            .isEnabled(BuildConfig.DEBUG) // 로그 사용(표시) 여부 (default = false)
            //.setGlobalLogTag("MY_GLOBAL_TAG") // 전역 태그 설정
            .setDefaultLogStyle(LogStyle.Type.SIMPLE) // 기본 로그 스타일 설정 (BOX or SIMPLE, default = BOX)
            // BOX 로그 스타일 설정
            .setBoxLogStyle(
                LogStyle.Box.newBuilder()
                    .isShowThreadInfo(true) // 스레드 정보 표시 여부 (default = false)
                    .isShowMethodStackTrace(true) // 메소드 스택 트레이스 표시 여부 (default = false)
                    .showingMethodStackCount(Int.MAX_VALUE) // 표시할 메소드 스택 트레이스 개수 (default = Int.MAX_VALUE)
                    .build()
            )
            // SIMPLE 로그 스타일 설정
            .setSimpleLogStyle(
                LogStyle.Simple.newBuilder()
                    .isShowThreadInfo(false) // 스레드 정보 표시 여부 (default = false)
                    .isShowMethodStackTrace(false) // 메소드 스택 트레이스 표시 여부 (default = false)
                    .showingMethodStackCount(2) // 표시할 메소드 스택 트레이스 개수 (default = 2)
                    .build()
            )
            .apply()
    }
}