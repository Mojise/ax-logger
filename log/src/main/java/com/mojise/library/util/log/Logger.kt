package com.mojise.library.util.log

import android.util.Log

internal object Logger {

    enum class Level {
        VERBOSE, DEBUG, INFO, WARN, ERROR
    }

    fun log(
        level: Level,
        strategy: LogStrategy,
        message: Any?,
        throwable: Throwable? = null,
    ) {
        if (strategy.isEnabled.not()) {
            return
        }

        val logTag = strategy.tag ?: LogMessageGenerator.generateTag()
        val logMessage = LogMessageGenerator.generateMessage(
            style = strategy.defaultLogStyle,
            message = message,
            throwable = throwable,
        )

        when (level) {
            Level.VERBOSE -> Log.v(logTag, logMessage)
            Level.DEBUG   -> Log.d(logTag, logMessage)
            Level.INFO    -> Log.i(logTag, logMessage)
            Level.WARN    -> Log.w(logTag, logMessage)
            Level.ERROR   -> Log.e(logTag, logMessage)
        }
    }
}