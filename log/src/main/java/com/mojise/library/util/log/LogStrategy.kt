package com.mojise.library.util.log

data class LogStrategy (
    val isEnabled: Boolean,
    val tag: String?,
    val defaultLogType: LogStyle.Type,
    val boxLogStyle: LogStyle.Box,
    val simpleLogStyle: LogStyle.Simple,
) {
    val defaultLogStyle: LogStyle
        get() = when (defaultLogType) {
            LogStyle.Type.BOX -> boxLogStyle
            LogStyle.Type.SIMPLE -> simpleLogStyle
        }

    companion object {
        val Default = LogStrategy(
            isEnabled = false,
            tag = null,
            defaultLogType = LogStyle.Type.BOX,
            boxLogStyle = LogStyle.Box.Default,
            simpleLogStyle = LogStyle.Simple.Default,
        )
    }
}