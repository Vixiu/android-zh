package com.github.gotify.messages

import androidx.core.text.HtmlCompat
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.data.MutableDataSet

internal object FlexmarkRenderer {
    private val options = MutableDataSet().apply {
        set(Parser.EXTENSIONS, listOf(TablesExtension.create()))
    }

    private val parser: Parser = Parser.builder(options).build()
    private val renderer: HtmlRenderer = HtmlRenderer.builder(options).build()

    fun render(markdown: String): CharSequence {
        val document = parser.parse(markdown)
        val html = renderer.render(document)
        return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
