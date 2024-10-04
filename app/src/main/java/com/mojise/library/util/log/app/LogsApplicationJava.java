package com.mojise.library.util.log.app;

import android.app.Application;

import com.mojise.library.util.log.LogStyle;
import com.mojise.library.util.log.Logs;

public class LogsApplicationJava extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // ax-logger 라이브러리 글로벌 설정
        new Logs.GlobalLogStrategyComposer()
                .isEnabled(BuildConfig.DEBUG) // 로그 사용(표시) 여부 (default = false)
                .setGlobalLogTag("MY_GLOBAL_TAG") // 전역 태그 설정
                .setDefaultLogStyle(LogStyle.Type.SIMPLE) // 기본 로그 스타일 설정 (BOX or SIMPLE, default = BOX)
                // BOX 로그 스타일 설정
                .setBoxLogStyle(
                        LogStyle.Box.newBuilder()
                                .isShowThreadInfo(true) // 스레드 정보 표시 여부 (default = true)
                                .isShowMethodStackTrace(true) // 메소드 스택 트레이스 표시 여부 (default = true)
                                .showingMethodStackCount(Integer.MAX_VALUE) // 표시할 메소드 스택 트레이스 개수 (default = Integer.MAX_VALUE)
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
                .apply();
    }
}
