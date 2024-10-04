package com.mojise.library.util.log

import android.os.Process

internal object LogMessageGenerator {

    private const val LOGS_LIBRARY_PACKAGE_NAME = "com.mojise.library.util.log"
    const val UNTIL_USER_METHOD_COUNT = 8
    const val HORIZONTAL_LINE_SEPARATOR_COUNT = 150

    fun generateTag(): String {
        return Thread.currentThread().stackTrace.getOrNull(UNTIL_USER_METHOD_COUNT - 2)?.fileName ?: "Unknown"
    }

    fun generateMessage(
        style: LogStyle,
        message: Any?,
        throwable: Throwable?,
    ): String = when (style) {
        is LogStyle.Box ->
            generateLogMessageByBoxStyle(style, message, throwable)
        is LogStyle.Simple ->
            generateLogMessageBySimpleStyle(style, message, throwable)
    }

    private fun generateLogMessageByBoxStyle(
        style: LogStyle,
        message: Any?,
        throwable: Throwable?,
    ): String = buildString {
        appendLine(            "┏━[Logger]" + "━".repeat(HORIZONTAL_LINE_SEPARATOR_COUNT))

        val logMessages = message.convertToStringList()
        if (logMessages.isNotEmpty()) {
            appendLine(        "┃                    │")
            logMessages.forEachIndexed { index, logMessage ->
                if (index == 0) {
                    appendLine("┃ Log Message        │ $logMessage")
                } else {
                    appendLine("┃                    │ $logMessage")
                }
            }
            appendLine(        "┃                    │")
        }
        if (style.isShowThreadInfo) {
            if (logMessages.isNotEmpty()) {
                appendLine(    "┠" + "┄".repeat(HORIZONTAL_LINE_SEPARATOR_COUNT))
            }
            appendLine(        "┃ Thread Info        │ ${buildThreadToString()}")
        }

        if (throwable == null) {
            if (style.isShowMethodStackTrace) {
                appendLine(        "┠" + "┄".repeat(HORIZONTAL_LINE_SEPARATOR_COUNT - 7))
                generateMethodStackTrace(style.showingMethodStackCount).forEachIndexed { index, methodStackElement ->
                    if (index == 0) {
                        appendLine("┃ Method Stack Trace │ $methodStackElement")
                    } else {
                        appendLine("┃                    │   - $methodStackElement")
                    }
                }
            }
        } else {
            appendLine(        "┠" + "┄".repeat(HORIZONTAL_LINE_SEPARATOR_COUNT))

            val stackTraceStringList = throwable.stackTraceToString()
                .replace("\t", "")
                .split("\n")
                .dropLast(1)
            stackTraceStringList.forEachIndexed { index, element ->
                if (index == 0) {
                    appendLine("┃ Throwable          │ $element")
                } else {
                    appendLine("┃                    │     $element")
                }
            }
        }
        appendLine(            "┗━" + "━".repeat(HORIZONTAL_LINE_SEPARATOR_COUNT + 13))
    }

    private fun generateLogMessageBySimpleStyle(
        style: LogStyle,
        message: Any?,
        throwable: Throwable?,
    ): String = buildString {
        if (throwable == null) {
            appendLine(getCurrentMethodSimpleInfo())

            message.convertToStringList().forEach {
                appendLine(it)
            }
            if (style.isShowThreadInfo || style.isShowMethodStackTrace) {
                appendLine()
            }
            if (style.isShowThreadInfo) {
                appendLine(buildThreadToString())
            }
            if (style.isShowMethodStackTrace) {
                generateMethodStackTrace(style.showingMethodStackCount).forEach { methodStackElement ->
                    appendLine("- $methodStackElement")
                }
            }
        } else {
            val logMessageList = message.convertToStringList()
            if (logMessageList.isNotEmpty()) {
                appendLine(getCurrentMethodSimpleInfo())

                logMessageList.forEach {
                    appendLine(it)
                }
                appendLine()
            }
            appendLine(buildThreadToString())
            appendLine()
            appendLine(throwable.stackTraceToString())
        }
    }

    private fun Any?.convertToStringList(): List<String> = this
        // Array 타입인 경우
        ?.contentDeepToStringIfArray()
        // 개행 문자가 포함된 경우
        ?.split("\n")
        // 1000자를 기준으로 나누어 리스트로 반환
        ?.flatMap { text ->
            if (text.length > 1000) {
                text.chunked(1000)
            } else {
                listOf(text)
            }
        }
        // 모든 문자열이 빈 문자열인 경우 빈 리스트 반환
        ?.let { if (it.all(String::isBlank)) emptyList() else it }
        ?: listOf("null")

    private fun Any.contentDeepToStringIfArray(): String = when (this) {
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
        methodStackCount: Int,
    ): List<String> = mutableListOf<String>().apply {
        Thread.currentThread()
            .stackTrace
            //.reversed()
            .drop(UNTIL_USER_METHOD_COUNT)
            .take(methodStackCount)
            .forEach { add(it.toString()) }
    }

    private fun getCurrentMethodSimpleInfo(): String {
        return Thread.currentThread()
            .stackTrace
            .getOrNull(UNTIL_USER_METHOD_COUNT)
            ?.let { element ->
                val simpleClassName = element.className.substringAfterLast('.')
                "[${element.fileName}:${element.lineNumber}] $simpleClassName::${element.methodName}()"
            } ?: "[Unknown]"
    }
}