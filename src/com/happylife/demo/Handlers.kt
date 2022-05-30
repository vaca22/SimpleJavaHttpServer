package com.happylife.demo

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.URLDecoder

object Handlers {
    @Throws(UnsupportedEncodingException::class)
    fun parseQuery(query: String?, parameters: MutableMap<String?, String?>) {
        if (query != null) {
            val pairs = query.split("[&]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (pair in pairs) {
                val param = pair.split("[=]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                var key: String? = null
                var value: String? = null
                if (param.size > 0) {
                    key = URLDecoder.decode(param[0], System.getProperty("file.encoding"))
                }
                if (param.size > 1) {
                    value = URLDecoder.decode(param[1], System.getProperty("file.encoding"))
                }
                parameters[key] = value
            }
        }
    }

    class RootHandler : HttpHandler {
        @Throws(IOException::class)
        override fun handle(he: HttpExchange) {
            val response = "<h1>Server start success if you see this message</h1>" + "<h1>Port: " + Main.port + "</h1>"
            he.sendResponseHeaders(200, response.length.toLong())
            val os = he.responseBody
            os.write(response.toByteArray())
            os.close()
        }
    }

    class EchoPostHandler : HttpHandler {
        @Throws(IOException::class)
        override fun handle(he: HttpExchange) {

            //--------------url thing
            val parameters: MutableMap<String?, String?> = HashMap()
            val requestedUri = he.requestURI
            val query = requestedUri.rawQuery
            parseQuery(query, parameters)
            val fuckx = parameters["filename"]
            println(parameters)
            val fuckaaa = he.requestHeaders["filename"]!![0]
            println(fuckaaa)
            val isr = InputStreamReader(he.requestBody, "utf-8")
            val br = BufferedReader(isr)
            val fuck = CharArray(1000)
            while (br.read(fuck) > 0) {
                println("fuck")
            }
            println("fuckyou")

            // send response
            val response = "fuck"
            he.sendResponseHeaders(200, response.length.toLong())
            val os = he.responseBody
            os.write(response.toByteArray())
            os.close()
        }
    }
}