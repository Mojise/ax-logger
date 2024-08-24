package com.mojise.library.util.log

internal data class LogStrategy (
    val isEnabled: Boolean,
    val tag: String?,
    val logStyle: LogStyle,
) {
    companion object {
        val DEFAULT = LogStrategy(
            isEnabled = false,
            tag = null,
            logStyle = LogStyle.Box.DEFAULT,
        )
    }
}