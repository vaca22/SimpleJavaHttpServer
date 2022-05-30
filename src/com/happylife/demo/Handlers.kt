package com.happylife.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class Handlers {
	public static class RootHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange he) throws IOException {
			String response = "<h1>Server start success if you see this message</h1>" + "<h1>Port: " + Main.port + "</h1>";
			he.sendResponseHeaders(200, response.length());
			OutputStream os = he.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}


	public static class EchoPostHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange he) throws IOException {

			//--------------url thing
			Map<String, String> parameters = new HashMap<String, String>();
			URI requestedUri = he.getRequestURI();
			String query = requestedUri.getRawQuery();
			parseQuery(query, parameters);
			String fuckx=parameters.get("filename");
			System.out.println(parameters);



			String fuckaaa= he.getRequestHeaders().get("filename").get(0);
			System.out.println(fuckaaa);


			InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			char[] fuck=new char[1000];
			while(br.read(fuck)>0){
				System.out.println("fuck");
			}
			System.out.println("fuckyou");

			// send response
			String response = "fuck";
			he.sendResponseHeaders(200, response.length());
			OutputStream os = he.getResponseBody();
			os.write(response.toString().getBytes());
			os.close();
		}
	}

	public static void parseQuery(String query, Map<String, String> parameters) throws UnsupportedEncodingException {
		if (query != null) {
			String pairs[] = query.split("[&]");
			for (String pair : pairs) {
				String param[] = pair.split("[=]");

				String key = null;
				String value = null;
				if (param.length > 0) {
					key = URLDecoder.decode(param[0], System.getProperty("file.encoding"));
				}
				if (param.length > 1) {
					value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));
				}
				parameters.put(key, value);
			}
		}
	}
}
