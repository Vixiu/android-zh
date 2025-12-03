package com.github.gotify.messages

import com.github.gotify.client.model.Message

internal object Extras {
    fun useMarkdown(message: Message): Boolean = useMarkdown(message.extras)

    fun useFlexmark(message: Message): Boolean = useFlexmark(message.extras)

    fun useMarkdown(extras: Map<String, Any>?): Boolean = hasContentType(extras, "text/markdown")

    fun useFlexmark(extras: Map<String, Any>?): Boolean =
        hasContentType(extras, "text/flexmark")

    private fun hasContentType(extras: Map<String, Any>?, expectedContentType: String): Boolean {
        val actualContentType = contentType(extras) ?: return false

        return actualContentType.substringBefore(';').trim().equals(expectedContentType, true)
    }

    private fun contentType(extras: Map<String, Any>?): String? {
        if (extras == null) {
            return null
        }

        val display: Any? = extras["client::display"]
        if (display !is Map<*, *>) {
            return null
        }

        val contentType = display["contentType"]
        if (contentType !is String) {
            return null
        }

        return contentType
    }

    fun <T> getNestedValue(clazz: Class<T>, extras: Map<String, Any>?, vararg keys: String): T? {
        var value: Any? = extras

        keys.forEach { key ->
            if (value == null) {
                return null
            }

            value = (value as Map<*, *>)[key]
        }

        if (!clazz.isInstance(value)) {
            return null
        }

        return clazz.cast(value)
    }
}
