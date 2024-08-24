package com.mojise.library.util.log

import com.mojise.library.util.log.Logger.Level.*

object Logs {

    private var GlobalLogStrategy: LogStrategy = LogStrategy.DEFAULT

    @JvmStatic
    fun boxStyle() = LogStrategyComposer(
        strategy = GlobalLogStrategy.copy(logStyle = LogStyle.Box.DEFAULT)
    )

    @JvmStatic
    fun simpleStyle() = LogStrategyComposer(
        strategy = GlobalLogStrategy.copy(logStyle = LogStyle.Simple.DEFAULT)
    )

    @JvmStatic
    fun visibleAlways() = LogStrategyComposer(strategy = GlobalLogStrategy.copy(isEnabled = true))

    @JvmStatic
    fun withThreadInfo() = when (val logStyle = GlobalLogStrategy.logStyle) {
        is LogStyle.Box -> LogStrategyComposer(GlobalLogStrategy.copy(logStyle = logStyle.copy(isShowThreadInfo = true)))
        is LogStyle.Simple -> LogStrategyComposer(GlobalLogStrategy.copy(logStyle = logStyle.copy(isShowThreadInfo = true)))
    }

    @JvmStatic
    fun withMethodStackTrace(count: Int = Int.MAX_VALUE) = when (val logStyle = GlobalLogStrategy.logStyle) {
        is LogStyle.Box -> LogStrategyComposer(GlobalLogStrategy.copy(logStyle = logStyle.copy(isShowMethodStackTrace = true, methodStackTraceCount = count)))
        is LogStyle.Simple -> LogStrategyComposer(GlobalLogStrategy.copy(logStyle = logStyle.copy(isShowMethodStackTrace = true, methodStackTraceCount = count)))
    }

    @JvmStatic
    fun tag(tag: String) = LogStrategyComposer(GlobalLogStrategy)

    @JvmStatic
    @JvmOverloads
    fun v(message: Any? = "") = Logger.log(VERBOSE, GlobalLogStrategy, message)
    @JvmStatic
    fun v(format: String, vararg args: Any) = Logger.log(VERBOSE, GlobalLogStrategy, format.format(*args))

    @JvmStatic
    @JvmOverloads
    fun d(message: Any? = "") = Logger.log(DEBUG, GlobalLogStrategy, message)
    @JvmStatic
    fun d(format: String, vararg args: Any) = Logger.log(DEBUG, GlobalLogStrategy, format.format(*args))

    @JvmStatic
    @JvmOverloads
    fun i(message: Any? = "") = Logger.log(INFO, GlobalLogStrategy, message)
    @JvmStatic
    fun i(format: String, vararg args: Any) = Logger.log(INFO, GlobalLogStrategy, format.format(*args))

    @JvmStatic
    @JvmOverloads
    fun w(message: Any? = "") = Logger.log(WARN, GlobalLogStrategy, message)
    @JvmStatic
    fun w(format: String, vararg args: Any) = Logger.log(WARN, GlobalLogStrategy, format.format(*args))

    @JvmStatic
    @JvmOverloads
    fun e(message: Any? = "") = Logger.log(ERROR, GlobalLogStrategy, message)
    @JvmStatic
    fun e(format: String, vararg args: Any) = Logger.log(ERROR, GlobalLogStrategy, format.format(*args))

    object GlobalLogStrategyComposer {

        @JvmStatic
        fun isVisible(enabled: Boolean) = apply {
            GlobalLogStrategy = GlobalLogStrategy.copy(isEnabled = enabled)
        }

        @JvmStatic
        fun boxStyle() = apply {
            GlobalLogStrategy = GlobalLogStrategy.copy(logStyle = LogStyle.Box.DEFAULT)
        }

        @JvmStatic
        fun simpleStyle() = apply {
            GlobalLogStrategy = GlobalLogStrategy.copy(logStyle = LogStyle.Simple.DEFAULT)
        }

        @JvmStatic
        fun withThreadInfo() = apply {
            GlobalLogStrategy = when (val logStyle = GlobalLogStrategy.logStyle) {
                is LogStyle.Box -> GlobalLogStrategy.copy(logStyle = logStyle.copy(isShowThreadInfo = true))
                is LogStyle.Simple -> GlobalLogStrategy.copy(logStyle = logStyle.copy(isShowThreadInfo = true))
            }
        }

        @JvmStatic
        fun withMethodStackTrace(count: Int) = apply {
            GlobalLogStrategy = when (val logStyle = GlobalLogStrategy.logStyle) {
                is LogStyle.Box -> GlobalLogStrategy.copy(logStyle = logStyle.copy(isShowMethodStackTrace = true, methodStackTraceCount = count))
                is LogStyle.Simple -> GlobalLogStrategy.copy(logStyle = logStyle.copy(isShowMethodStackTrace = true, methodStackTraceCount = count))
            }
        }

        @JvmStatic
        fun withGlobalCustomTag(tag: String) = apply {
            GlobalLogStrategy = GlobalLogStrategy.copy(tag = tag)
        }

        @JvmStatic
        fun apply() {

        }
    }

    class LogStrategyComposer internal constructor(
        private var strategy: LogStrategy
    ) {
        fun visibleAlways() = apply {
            strategy = strategy.copy(isEnabled = true)
        }

        fun withThreadInfo() = apply {
            strategy = when (val logStyle = strategy.logStyle) {
                is LogStyle.Box -> strategy.copy(logStyle = logStyle.copy(isShowThreadInfo = true))
                is LogStyle.Simple -> strategy.copy(logStyle = logStyle.copy(isShowThreadInfo = true))
            }
        }

        fun withMethodStackTrace(count: Int) = apply {
            strategy = when (val logStyle = strategy.logStyle) {
                is LogStyle.Box -> strategy.copy(logStyle = logStyle.copy(isShowMethodStackTrace = true, methodStackTraceCount = count))
                is LogStyle.Simple -> strategy.copy(logStyle = logStyle.copy(isShowMethodStackTrace = true, methodStackTraceCount = count))
            }
        }

        fun tag(tag: String) = apply {
            strategy = strategy.copy(tag = tag)
        }

        @JvmOverloads
        fun v(message: Any? = "") = Logger.log(VERBOSE, strategy, message)
        fun v(format: String, vararg args: Any) = Logger.log(VERBOSE, strategy, format.format(*args))

        @JvmOverloads
        fun d(message: Any? = "") = Logger.log(DEBUG, strategy, message)
        fun d(format: String, vararg args: Any) = Logger.log(DEBUG, strategy, format.format(*args))

        @JvmOverloads
        fun i(message: Any? = "") = Logger.log(INFO, strategy, message)
        fun i(format: String, vararg args: Any) = Logger.log(INFO, strategy, format.format(*args))

        @JvmOverloads
        fun w(message: Any? = "") = Logger.log(WARN, strategy, message)
        fun w(format: String, vararg args: Any) = Logger.log(WARN, strategy, format.format(*args))

        @JvmOverloads
        fun e(message: Any? = "") = Logger.log(ERROR, strategy, message)
        fun e(format: String, vararg args: Any) = Logger.log(ERROR, strategy, format.format(*args))
    }
}