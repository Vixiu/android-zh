package com.github.gotify.messages

import com.github.gotify.client.model.Message

internal object Extras {

    fun useHtml(message: Message): Boolean =
        useHtml(message.extras)

    fun useHtml(extras: Map<String, Any>?): Boolean =
        hasContentType(extras, "text/html")

    fun useMarkdown(message: Message): Boolean =
        useMarkdown(message.extras)

    fun useMarkdown(extras: Map<String, Any>?): Boolean =
        hasContentType(extras, "text/markdown")

    private fun hasContentType(
        extras: Map<String, Any>?,
        expectedContentType: String
    ): Boolean {
        val actual = contentType(extras) ?: return false
        return actual.substringBefore(';')
            .trim()
            .equals(expectedContentType, ignoreCase = true)
    }

    private fun contentType(extras: Map<String, Any>?): String? {
        val display = extras?.get("client::display") as? Map<*, *> ?: return null
        return display["contentType"] as? String
    }

    fun <T> getNestedValue(
        clazz: Class<T>,
        extras: Map<String, Any>?,
        vararg keys: String
    ): T? {
        var value: Any? = extras

        for (key in keys) {
            value = (value as? Map<*, *>)?.get(key) ?: return null
        }

        return if (clazz.isInstance(value)) clazz.cast(value) else null
    }
}
