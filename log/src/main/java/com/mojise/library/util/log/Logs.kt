package com.mojise.library.util.log

import com.mojise.library.util.log.Logger.Level.*

object Logs {

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
    @JvmStatic fun v(format: String, vararg args: Any)              = Logger.log(VERBOSE, GlobalLogStrategy, format.format(*args))

    @JvmStatic fun d()                                              = Logger.log(DEBUG, GlobalLogStrategy, "")
    @JvmStatic fun d(message: Any?)                                 = Logger.log(DEBUG, GlobalLogStrategy, message)
    @JvmStatic fun d(format: String, vararg args: Any)              = Logger.log(DEBUG, GlobalLogStrategy, format.format(*args))

    @JvmStatic fun i()                                              = Logger.log(INFO, GlobalLogStrategy, "")
    @JvmStatic fun i(message: Any?)                                 = Logger.log(INFO, GlobalLogStrategy, message)
    @JvmStatic fun i(format: String, vararg args: Any)              = Logger.log(INFO, GlobalLogStrategy, format.format(*args))

    @JvmStatic fun w()                                              = Logger.log(WARN, GlobalLogStrategy, "")
    @JvmStatic fun w(message: Any?)                                 = Logger.log(WARN, GlobalLogStrategy, message)
    @JvmStatic fun w(format: String, vararg args: Any)              = Logger.log(WARN, GlobalLogStrategy, format.format(*args))

    @JvmStatic fun e()                                              = Logger.log(ERROR, GlobalLogStrategy, "")
    @JvmStatic fun e(message: Any?)                                 = Logger.log(ERROR, GlobalLogStrategy, message)
    @JvmStatic fun e(format: String, vararg args: Any)              = Logger.log(ERROR, GlobalLogStrategy, format.format(*args))

    @JvmStatic fun throwable(throwable: Throwable)                  = Logger.log(WARN, GlobalLogStrategy, message = "", throwable = throwable)
    @JvmStatic fun throwable(message: String, throwable: Throwable) = Logger.log(WARN, GlobalLogStrategy, message, throwable)

    object GlobalLogStrategyComposer {

        private var newStrategy: LogStrategy? = null

        @JvmStatic
        fun init() = apply {
            newStrategy = GlobalLogStrategy.copy()
        }

        @JvmStatic
        fun isVisible(enabled: Boolean) = apply {
            newStrategy = newStrategy!!.copy(isEnabled = enabled)
        }

        @JvmStatic
        fun setGlobalLogTag(tag: String) = apply {
            newStrategy = newStrategy!!.copy(tag = tag)
        }

        @JvmStatic
        fun setDefaultLogStyle(logType: LogStyle.Type) = apply {
            newStrategy = newStrategy!!.copy(defaultLogType = logType)
        }

        @JvmStatic
        fun setBoxLogStyle(boxLogStyle: LogStyle.Box) = apply {
            newStrategy = newStrategy!!.copy(boxLogStyle = boxLogStyle)
        }

        fun setSimpleLogStyle(simpleLogStyle: LogStyle.Simple) = apply {
            newStrategy = newStrategy!!.copy(simpleLogStyle = simpleLogStyle)
        }

        @JvmStatic
        fun apply() {
            GlobalLogStrategy = newStrategy!!
            newStrategy = null
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
        fun v(format: String, vararg args: Any)              = Logger.log(VERBOSE, strategy, format.format(*args))

        fun d()                                              = Logger.log(DEBUG, strategy, null)
        fun d(message: Any?)                                 = Logger.log(DEBUG, strategy, message)
        fun d(format: String, vararg args: Any)              = Logger.log(DEBUG, strategy, format.format(*args))

        fun i()                                              = Logger.log(INFO, strategy, null)
        fun i(message: Any?)                                 = Logger.log(INFO, strategy, message)
        fun i(format: String, vararg args: Any)              = Logger.log(INFO, strategy, format.format(*args))

        fun w()                                              = Logger.log(WARN, strategy, null)
        fun w(message: Any?)                                 = Logger.log(WARN, strategy, message)
        fun w(format: String, vararg args: Any)              = Logger.log(WARN, strategy, format.format(*args))

        fun e()                                              = Logger.log(ERROR, strategy, null)
        fun e(message: Any?)                                 = Logger.log(ERROR, strategy, message)
        fun e(format: String, vararg args: Any)              = Logger.log(ERROR, strategy, format.format(*args))

        fun throwable(throwable: Throwable)                  = Logger.log(WARN, strategy, message = "", throwable = throwable)
        fun throwable(message: String, throwable: Throwable) = Logger.log(WARN, strategy, message, throwable)
    }
}