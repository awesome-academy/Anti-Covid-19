package com.sunasterisk.anticovid_19.data.resource.remote.util

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class XMLParser {
    fun getDocument(xml: String?): Document? {
        val factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
        return try {
            val db: DocumentBuilder = factory.newDocumentBuilder()
            val inputSource = InputSource()
            inputSource.characterStream = StringReader(xml)
            inputSource.encoding = UNICODE
            db.parse(inputSource)
        } catch (e: ParserConfigurationException) {
            e.printStackTrace()
            null
        }
    }

    fun getValue(element: Element, name: String?): String? =
        getTextNodeValue(element.getElementsByTagName(name).item(0))

    private fun getTextNodeValue(element: Node?): String? {
        var child: Node?
        element?.let { node ->
            if (node.hasChildNodes()) {
                child = element.firstChild
                while (child != null) {
                    if (child?.nodeType == Node.TEXT_NODE) {
                        return child?.nodeValue
                    }
                    child = child?.nextSibling
                }
            }
        }
        return ""
    }

    companion object {
        private const val UNICODE = "UTF-8"
    }
}
