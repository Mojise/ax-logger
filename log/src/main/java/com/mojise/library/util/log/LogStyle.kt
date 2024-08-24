package com.mojise.library.util.log

internal sealed interface LogStyle {

    val isShowThreadInfo: Boolean
    val isShowMethodStackTrace: Boolean
    val methodStackTraceCount: Int

    data class Box(
        override val isShowThreadInfo: Boolean,
        override val isShowMethodStackTrace: Boolean,
        override val methodStackTraceCount: Int,
    ) : LogStyle {

        companion object {

            val DEFAULT = Box(
                isShowThreadInfo = false,
                isShowMethodStackTrace = false,
                methodStackTraceCount = Int.MAX_VALUE,
            )
        }
    }

    data class Simple(
        override val isShowThreadInfo: Boolean,
        override val isShowMethodStackTrace: Boolean,
        override val methodStackTraceCount: Int,
    ) : LogStyle {

        companion object {

            val DEFAULT = Simple(
                isShowThreadInfo = false,
                isShowMethodStackTrace = false,
                methodStackTraceCount = 2,
            )
        }
    }
}