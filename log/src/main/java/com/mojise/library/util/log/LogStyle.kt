package com.mojise.library.util.log

import com.mojise.library.util.log.LogStyle.Box.Builder
import com.mojise.library.util.log.LogStyle.Box.Companion

sealed interface LogStyle {

    enum class Type {
        BOX, SIMPLE
    }

    val type: Type
    val isShowThreadInfo: Boolean
    val isShowMethodStackTrace: Boolean
    val showingMethodStackCount: Int

    data class Box(
        override val type: Type = Type.BOX,
        override val isShowThreadInfo: Boolean,
        override val isShowMethodStackTrace: Boolean,
        override val showingMethodStackCount: Int,
    ) : LogStyle {

        class Builder(private var boxLogStyle: Box) {

            fun isShowThreadInfo(isShowThreadInfo: Boolean) = apply {
                boxLogStyle = boxLogStyle.copy(isShowThreadInfo = isShowThreadInfo)
            }

            fun isShowMethodStackTrace(isShowMethodStackTrace: Boolean) = apply {
                boxLogStyle = boxLogStyle.copy(isShowMethodStackTrace = isShowMethodStackTrace)
            }

            fun showingMethodStackCount(showingMethodStackCount: Int) = apply {
                boxLogStyle = boxLogStyle.copy(showingMethodStackCount = showingMethodStackCount)
            }

            fun build() = boxLogStyle
        }

        companion object {

            val Default = Box(
                isShowThreadInfo = true,
                isShowMethodStackTrace = true,
                showingMethodStackCount = Int.MAX_VALUE,
            )

            @JvmStatic
            fun newBuilder() = Builder(Default)
        }
    }

    data class Simple(
        override val type: Type = Type.SIMPLE,
        override val isShowThreadInfo: Boolean,
        override val isShowMethodStackTrace: Boolean,
        override val showingMethodStackCount: Int,
    ) : LogStyle {

        class Builder(private var simpleLogStyle: Simple) {

            fun isShowThreadInfo(isShowThreadInfo: Boolean) = apply {
                simpleLogStyle = simpleLogStyle.copy(isShowThreadInfo = isShowThreadInfo)
            }

            fun isShowMethodStackTrace(isShowMethodStackTrace: Boolean) = apply {
                simpleLogStyle = simpleLogStyle.copy(isShowMethodStackTrace = isShowMethodStackTrace)
            }

            fun showingMethodStackCount(showingMethodStackCount: Int) = apply {
                simpleLogStyle = simpleLogStyle.copy(showingMethodStackCount = showingMethodStackCount)
            }

            fun build() = simpleLogStyle
        }

        companion object {

            val Default = Simple(
                isShowThreadInfo = false,
                isShowMethodStackTrace = false,
                showingMethodStackCount = 2,
            )

            @JvmStatic
            fun newBuilder() = Builder(Default)
        }
    }
}