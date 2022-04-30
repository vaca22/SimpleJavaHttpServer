package com.happylife.demo;


import com.sun.net.httpserver.*;
import com.sun.net.httpserver.spi.HttpServerProvider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;

public class HttpServerTest {
    public static void main(String[] args) throws IOException {
        /*运行服务器*/
        RunServer();
    }
    public static void RunServer() throws IOException {
        HttpServerProvider provider = HttpServerProvider.provider();
        HttpServer httpserver =provider.createHttpServer(new InetSocketAddress(8200), 100);//监听端口8200,能同时接受100个请求
        httpserver.createContext("/ais", new TestResponseHandler());
        httpserver.setExecutor(null);
        httpserver.start();
        System.out.println("启动服务器");
    }

    public static class TestResponseHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            InputStream ss = httpExchange.getRequestBody();
            String requestMethod = httpExchange.getRequestMethod();
            if (requestMethod.equalsIgnoreCase("POST")) {//客户端的请求是POST方法
                //设置服务端响应的编码格式，否则在客户端收到的可能是乱码
                Headers responseHeaders = httpExchange.getResponseHeaders();
                responseHeaders.set("Content-Type", "text/html;charset=utf-8");

                //在这里通过httpExchange获取客户端发送过来的消息
                URI url = httpExchange.getRequestURI();
                InputStream requestBody = httpExchange.getRequestBody();
                int count = 0;
                while (count == 0) {
                    count = requestBody.available();
                }
                byte[] b = new byte[count];
                int readCount = 0; // 已经成功读取的字节的个数
                while (readCount < count) {
                    readCount += requestBody.read(b, readCount, count - readCount);
                }
                System.out.println("请求数据"+ new String(b));

                String response = "应答数据：测试成功";
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes("UTF-8").length);
                OutputStream responseBody = httpExchange.getResponseBody();
                OutputStreamWriter writer = new OutputStreamWriter(responseBody, "UTF-8");
                /*写入返回的应答数据*/
                writer.write(response);
                writer.close();
                responseBody.close();
            }

        }
    }
}

