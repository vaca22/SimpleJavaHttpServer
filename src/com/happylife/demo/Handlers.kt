package com.happylife.demo

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.io.*
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
            val fuckx = he.requestHeaders["filename"]!![0]
            if(File(fuckx).exists()){
                File(fuckx).delete()
            }
            println(fuckx)
            val isr =he.requestBody
            val fuck = ByteArray(16384)
            do {
                val fa=isr.read(fuck)
                if(fa==16384){
                    File(fuckx).appendBytes(fuck)
                }else{
                    File(fuckx).appendBytes(fuck.copyOfRange(0,fa))
                    break;
                }
            }while (true)
            println("fuckyou")


            val response = "ok"
            he.sendResponseHeaders(200, response.length.toLong())
            val os = he.responseBody
            os.write(response.toByteArray())
            os.close()
        }
    }
}