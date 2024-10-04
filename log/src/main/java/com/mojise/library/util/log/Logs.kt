package com.mojise.library.util.log

import com.mojise.library.util.log.Logger.Level.*

/**
 * ## 로그 출력을 담당하는 클래스
 *
 * ### 기본 사용법
 *
 * ```Kotlin
 * Logs.v("verbose log message")
 * Logs.d("debug log message")
 * Logs.i("info log message")
 * Logs.w("warn log message")
 * Logs.e("error log message")
 *
 * Logs.format("format log message :: text=%s, number=%d, ...", 100)
 *
 * try {
 *     val a = 1 / 0
 * } catch (e: Exception) {
 *     Logs.throwable(e)
 *     Logs.throwable("error log message", e)
 * }
 * ```
 *
 * ### 로그 출력 설정
 *
 * ```kotlin
 * Logs.boxStyle() // 박스 스타일 로그 출력 설정 (optional)
 *     .visibleForced() // 로그 출력 강제 설정 (optional)
 *     .withThreadInfo() // 스레드 정보 출력 설정 (optional)
 *     .withMethodStackTrace(5) // 메소드 스택 트레이스 출력 설정 (optional)
 *     .tag("MY_TAG") // 태그 설정 (optional)
 *     .d("box style log message")
 * ```
 *
 * ### JSON 출력
 * ```kotlin
 * Logs.json("""{ "key": "value" }""") // JSON 출력 (default indentWidth = 2)
 * Logs.json("""{ "key": "value" }""", 4) // JSON 출력 (indentWidth = 4)
 * Logs.json("json string with message", """{ "key": "value" }""") // JSON 출력 (with message)
 * ```
 */
object Logs {

    internal const val TAG = "Logs"

    private var GlobalLogStrategy: LogStrategy = LogStrategy.Default

    @JvmStatic
    fun boxStyle() = newLogStrategyComposer(logType = LogStyle.Type.BOX)

    @JvmStatic
    fun simpleStyle() = newLogStrategyComposer(logType = LogStyle.Type.SIMPLE)

    @JvmStatic
    fun visibleForced() = newLogStrategyComposer(isEnabled = true)

    @JvmStatic
    fun withThreadInfo() = newLogStrategyComposer(isShowThreadInfo = true)
    @JvmStatic
    fun withoutThreadInfo() = newLogStrategyComposer(isShowThreadInfo = false)

    @JvmStatic
    @JvmOverloads
    fun withMethodStackTrace(count: Int = Int.MAX_VALUE) =
        newLogStrategyComposer(isShowMethodStackTrace = true, showingMethodStackCount = count)
    @JvmStatic
    fun withoutMethodStackTrace() = newLogStrategyComposer(isShowMethodStackTrace = false)

    @JvmStatic
    fun tag(tag: String) = newLogStrategyComposer(tag = tag)

    private fun newLogStrategyComposer(
        strategy: LogStrategy = GlobalLogStrategy,
        isEnabled: Boolean = strategy.isEnabled,
        tag: String? = strategy.tag,
        logType: LogStyle.Type = strategy.defaultLogType,
        isShowThreadInfo: Boolean = when (logType) {
            LogStyle.Type.BOX -> strategy.boxLogStyle.isShowThreadInfo
            LogStyle.Type.SIMPLE -> strategy.simpleLogStyle.isShowThreadInfo
        },
        isShowMethodStackTrace: Boolean = when (logType) {
            LogStyle.Type.BOX -> strategy.boxLogStyle.isShowMethodStackTrace
            LogStyle.Type.SIMPLE -> strategy.simpleLogStyle.isShowMethodStackTrace
        },
        showingMethodStackCount: Int = when (logType) {
            LogStyle.Type.BOX -> strategy.boxLogStyle.showingMethodStackCount
            LogStyle.Type.SIMPLE -> strategy.simpleLogStyle.showingMethodStackCount
        },
    ): LogStrategyComposer = LogStrategyComposer(
        strategy.copy(
            isEnabled = isEnabled,
            tag = tag,
            defaultLogType = logType,
            boxLogStyle = when (logType) {
                LogStyle.Type.BOX -> strategy.boxLogStyle.copy(
                    isShowThreadInfo = isShowThreadInfo,
                    isShowMethodStackTrace = isShowMethodStackTrace,
                    showingMethodStackCount = showingMethodStackCount
                )
                LogStyle.Type.SIMPLE -> strategy.boxLogStyle
            },
            simpleLogStyle = when (logType) {
                LogStyle.Type.BOX -> strategy.simpleLogStyle
                LogStyle.Type.SIMPLE -> strategy.simpleLogStyle.copy(
                    isShowThreadInfo = isShowThreadInfo,
                    isShowMethodStackTrace = isShowMethodStackTrace,
                    showingMethodStackCount = showingMethodStackCount
                )
            },
        )
    )

    @JvmStatic fun v()                                              = Logger.log(VERBOSE, GlobalLogStrategy, "")
    @JvmStatic fun v(message: Any?)                                 = Logger.log(VERBOSE, GlobalLogStrategy, message)

    @JvmStatic fun d()                                              = Logger.log(DEBUG, GlobalLogStrategy, "")
    @JvmStatic fun d(message: Any?)                                 = Logger.log(DEBUG, GlobalLogStrategy, message)

    @JvmStatic fun i()                                              = Logger.log(INFO, GlobalLogStrategy, "")
    @JvmStatic fun i(message: Any?)                                 = Logger.log(INFO, GlobalLogStrategy, message)

    @JvmStatic fun w()                                              = Logger.log(WARN, GlobalLogStrategy, "")
    @JvmStatic fun w(message: Any?)                                 = Logger.log(WARN, GlobalLogStrategy, message)

    @JvmStatic fun e()                                              = Logger.log(ERROR, GlobalLogStrategy, "")
    @JvmStatic fun e(message: Any?)                                 = Logger.log(ERROR, GlobalLogStrategy, message)

    @JvmStatic fun format(format: String, vararg args: Any)         = try { Logger.log(DEBUG, GlobalLogStrategy, format.format(*args)) } catch (t: Throwable) { throwable(t) }
    @JvmStatic fun json(jsonString: String)                         = Logger.logJsonString(DEBUG, GlobalLogStrategy, null, jsonString, LogsUtil.JSON_INDENT_WIDTH_DEFAULT)
    @JvmStatic fun json(jsonString: String, indentWidth: Int)       = Logger.logJsonString(DEBUG, GlobalLogStrategy, null, jsonString, indentWidth)
    @JvmStatic fun json(message: Any?, jsonString: String)          = Logger.logJsonString(DEBUG, GlobalLogStrategy, message.toString(), jsonString, LogsUtil.JSON_INDENT_WIDTH_DEFAULT)

    @JvmStatic fun throwable(throwable: Throwable)                  = Logger.log(WARN, GlobalLogStrategy, message = "", throwable = throwable)
    @JvmStatic fun throwable(message: String, throwable: Throwable) = Logger.log(WARN, GlobalLogStrategy, message, throwable)

    /**
     * ## ax-logger 라이브러리 글로벌 설정 클래스
     *
     * - [android.app.Application.onCreate] 에서 호출 권장.
     *
     * **Kotlin**
     *
     * ```Kotlin
     * // ax-logger 라이브러리 글로벌 설정
     * Logs.GlobalLogStrategyComposer()
     *     .isEnabled(BuildConfig.DEBUG) // 로그 사용(표시) 여부 (default = false)
     *     .setGlobalLogTag("MY_GLOBAL_TAG") // 전역 태그 설정
     *     .setDefaultLogStyle(LogStyle.Type.SIMPLE) // 기본 로그 스타일 설정 (BOX or SIMPLE, default = BOX)
     *     // BOX 로그 스타일 설정
     *     .setBoxLogStyle(
     *         LogStyle.Box.newBuilder()
     *             .isShowThreadInfo(true) // 스레드 정보 표시 여부 (default = false)
     *             .isShowMethodStackTrace(true) // 메소드 스택 트레이스 표시 여부 (default = false)
     *             .showingMethodStackCount(Int.MAX_VALUE) // 표시할 메소드 스택 트레이스 개수 (default = Int.MAX_VALUE)
     *             .build()
     *     )
     *     // SIMPLE 로그 스타일 설정
     *     .setSimpleLogStyle(
     *         LogStyle.Simple.newBuilder()
     *             .isShowThreadInfo(false) // 스레드 정보 표시 여부 (default = false)
     *             .isShowMethodStackTrace(false) // 메소드 스택 트레이스 표시 여부 (default = false)
     *             .showingMethodStackCount(2) // 표시할 메소드 스택 트레이스 개수 (default = 2)
     *             .build()
     *     )
     *     .apply()
     * ```
     *
     * **Java**
     *
     * ```Java
     * // ax-logger 라이브러리 글로벌 설정
     * new Logs.GlobalLogStrategyComposer()
     *         .isEnabled(BuildConfig.DEBUG) // 로그 사용(표시) 여부 (default = false)
     *         .setGlobalLogTag("MY_GLOBAL_TAG") // 전역 태그 설정
     *         .setDefaultLogStyle(LogStyle.Type.SIMPLE) // 기본 로그 스타일 설정 (BOX or SIMPLE, default = BOX)
     *         // BOX 로그 스타일 설정
     *         .setBoxLogStyle(
     *                 LogStyle.Box.newBuilder()
     *                         .isShowThreadInfo(true) // 스레드 정보 표시 여부 (default = true)
     *                         .isShowMethodStackTrace(true) // 메소드 스택 트레이스 표시 여부 (default = true)
     *                         .showingMethodStackCount(Integer.MAX_VALUE) // 표시할 메소드 스택 트레이스 개수 (default = Integer.MAX_VALUE)
     *                         .build()
     *         )
     *         // SIMPLE 로그 스타일 설정
     *         .setSimpleLogStyle(
     *                 LogStyle.Simple.newBuilder()
     *                         .isShowThreadInfo(false) // 스레드 정보 표시 여부 (default = false)
     *                         .isShowMethodStackTrace(false) // 메소드 스택 트레이스 표시 여부 (default = false)
     *                         .showingMethodStackCount(2) // 표시할 메소드 스택 트레이스 개수 (default = 2)
     *                         .build()
     *         )
     *         .apply();
     * ```
     */
    class GlobalLogStrategyComposer {

        private var newStrategy: LogStrategy = LogStrategy.Default

        fun isEnabled(enabled: Boolean) = apply {
            newStrategy = newStrategy.copy(isEnabled = enabled)
        }

        fun setGlobalLogTag(tag: String) = apply {
            newStrategy = newStrategy.copy(tag = tag)
        }

        fun setDefaultLogStyle(logType: LogStyle.Type) = apply {
            newStrategy = newStrategy.copy(defaultLogType = logType)
        }

        fun setBoxLogStyle(boxLogStyle: LogStyle.Box) = apply {
            newStrategy = newStrategy.copy(boxLogStyle = boxLogStyle)
        }

        fun setSimpleLogStyle(simpleLogStyle: LogStyle.Simple) = apply {
            newStrategy = newStrategy.copy(simpleLogStyle = simpleLogStyle)
        }

        fun apply() {
            GlobalLogStrategy = newStrategy
        }
    }

    class LogStrategyComposer internal constructor(
        private var strategy: LogStrategy
    ) {
        fun visibleForced() = newLogStrategyComposer(strategy = strategy, isEnabled = true)

        fun withThreadInfo() = newLogStrategyComposer(strategy = strategy, isShowThreadInfo = true)
        fun withoutThreadInfo() = newLogStrategyComposer(strategy = strategy, isShowThreadInfo = false)

        @JvmOverloads
        fun withMethodStackTrace(count: Int = Int.MAX_VALUE) = newLogStrategyComposer(strategy = strategy, isShowMethodStackTrace = true, showingMethodStackCount = count)
        fun withoutMethodStackTrace() = newLogStrategyComposer(strategy = strategy, isShowMethodStackTrace = false)

        fun tag(tag: String) = newLogStrategyComposer(strategy = strategy, tag = tag)

        fun v()                                              = Logger.log(VERBOSE, strategy, null)
        fun v(message: Any?)                                 = Logger.log(VERBOSE, strategy, message)

        fun d()                                              = Logger.log(DEBUG, strategy, null)
        fun d(message: Any?)                                 = Logger.log(DEBUG, strategy, message)

        fun i()                                              = Logger.log(INFO, strategy, null)
        fun i(message: Any?)                                 = Logger.log(INFO, strategy, message)

        fun w()                                              = Logger.log(WARN, strategy, null)
        fun w(message: Any?)                                 = Logger.log(WARN, strategy, message)

        fun e()                                              = Logger.log(ERROR, strategy, null)
        fun e(message: Any?)                                 = Logger.log(ERROR, strategy, message)

        fun format(format: String, vararg args: Any)         = try { Logger.log(DEBUG, strategy, format.format(*args)) } catch (t: Throwable) { Logs.throwable(t) }
        fun json(jsonString: String)                         = Logger.logJsonString(DEBUG, strategy, null, jsonString, LogsUtil.JSON_INDENT_WIDTH_DEFAULT)
        fun json(jsonString: String, indentWidth: Int)       = Logger.logJsonString(DEBUG, strategy, null, jsonString, indentWidth)
        fun json(message: Any?, jsonString: String)          = Logger.logJsonString(DEBUG, strategy, message.toString(), jsonString, LogsUtil.JSON_INDENT_WIDTH_DEFAULT)

        fun throwable(throwable: Throwable)                  = Logger.log(WARN, strategy, message = "", throwable = throwable)
        fun throwable(message: String, throwable: Throwable) = Logger.log(WARN, strategy, message, throwable)
    }
}