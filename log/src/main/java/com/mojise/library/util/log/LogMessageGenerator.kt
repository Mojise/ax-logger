package com.mojise.library.util.log

import android.os.Process

internal object LogMessageGenerator {

    private const val UNTIL_USER_METHOD_COUNT = 7

    fun generateTag(): String {
        return Thread.currentThread().stackTrace.getOrNull(UNTIL_USER_METHOD_COUNT - 2)?.fileName ?: "Unknown"
    }

    fun generateMessage(
        style: LogStyle,
        message: Any?,
    ): String = when (style) {
        is LogStyle.Box -> generateLogMessageBoxStyle(style, message)
        is LogStyle.Simple -> generateLogSimpleStyle(style, message)
    }

    private fun generateLogMessageBoxStyle(
        style: LogStyle,
        message: Any?,
    ): String = buildString {
        appendLine(            "┏━[Log Message]" + "━".repeat(150))
        appendLine(            "┃ ${message?.convertToString()}")

        if (style.isShowThreadInfo) {
            appendLine(        "┠─[Thread Info]" + "┄".repeat(150))
            appendLine(        "┃ ${buildThreadToString()}")
        }
        if (style.isShowMethodStackTrace) {
            appendLine(        "┠─[Method Stack Trace]" + "┄".repeat(150 - 7))
            generateMethodStackTrace(style.methodStackTraceCount).forEach { method ->
                appendLine(    "┃ $method")
            }
        }
        appendLine(            "┗━" + "━".repeat(150 + 13))
    }

    private fun generateLogMessageBoxStyle2(
        style: LogStyle,
        message: Any?,
    ): String = buildString {
        if (style.isShowThreadInfo) {
            appendLine(    "┏━[Thread Info]" + "━".repeat(150))
            appendLine(    "┃ ${buildThreadToString()}")
        }
        if (style.isShowMethodStackTrace) {
            if (style.isShowThreadInfo.not()) {
                appendLine("┏━[Method Stack Trace]" + "━".repeat(150 - 7))
            } else {
                appendLine("┠─[Method Stack Trace]" + "-".repeat(150 - 7))
            }
            generateMethodStackTrace(style.methodStackTraceCount).forEach {
                appendLine("┃ $it")
            }
        }
        if (style.isShowThreadInfo.not() && style.isShowMethodStackTrace.not()) {
            appendLine(    "┏━[Log Message]" + "━".repeat(150))
        } else {
            appendLine(    "┠─[Log Message]" + "-".repeat(150))
        }
        appendLine(        "┃ ${message?.convertToString()}")
        appendLine(        "┗━" + "━".repeat(150 + 13))
    }

    private fun generateLogSimpleStyle(
        style: LogStyle,
        message: Any?,
    ): String = buildString {
        appendLine(message?.convertToString())

        if (style.isShowMethodStackTrace && style.methodStackTraceCount > 0) {
            generateMethodStackTrace(style.methodStackTraceCount).forEach {
                appendLine(it)
            }
        }
    }

    private fun Any.convertToString(): String = when (this) {
        is Array<*> -> contentDeepToString()
        is BooleanArray -> contentToString()
        is ByteArray -> contentToString()
        is CharArray -> contentToString()
        is DoubleArray -> contentToString()
        is FloatArray -> contentToString()
        is IntArray -> contentToString()
        is LongArray -> contentToString()
        is ShortArray -> contentToString()
        else -> toString()
    }

    private fun buildThreadToString(): String = buildString {
        append("Process[pid=${Process.myPid()}] | ")

        val thread = Thread.currentThread()
        append("Thread[")
        append("id=${thread.id}")
        append(", name='${thread.name}'")
        append(", group='${thread.threadGroup?.name}'")
        append(", priority=${thread.priority}")
        append(", isDaemon=${thread.isDaemon}")
        append(", isAlive=${thread.isAlive}")
        append(", isInterrupted=${thread.isInterrupted}")
        append(", state=${thread.state}]")
    }

    private fun generateMethodStackTrace(
        methodCount: Int,
    ): List<String> = mutableListOf<String>().apply {
        Thread.currentThread()
            .stackTrace
//            .reversed()
            .drop(UNTIL_USER_METHOD_COUNT)
            .take(methodCount)
            .forEachIndexed { index, element ->
                val tab = "  ".repeat(index)
                // add("$tab- $element")
                add("    $element")
            }
    }
}