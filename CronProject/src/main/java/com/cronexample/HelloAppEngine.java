package com.cronexample;

import java.io.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.*;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(
	    name = "HelloAppEngine",
	    urlPatterns = {"/hello"}
	)

public class HelloAppEngine extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
      
	  String refreshToken = "dd51e2a9d25muTwhH10_XGYejeJvLj-QLhJnK-ikNrfIX";
	  String url1 = "https://developer.setmore.com/api/v1/o/oauth2/token?refreshToken=" + refreshToken;
	  
	  URL url = new URL(url1);
	  
	  URLConnection urlConn = url.openConnection();
		
	  String line, outputString = "";
	  BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
	     while ((line = reader.readLine()) != null) {
	         outputString += line;
	     }
	     System.out.println(outputString);
	     try {
			JSONObject data = new JSONObject(outputString);
			System.out.println(data);
			JSONObject token = data.getJSONObject("data");
			String access_token = token.getJSONObject("token").getString("access_token");
			System.out.println(access_token);
			HelloAppEngine h = new HelloAppEngine();
		    h.appointment(access_token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
  }
  
  public void appointment(String access_token) throws IOException, JSONException {
	  
	  String pattern = "yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'";
	  SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	  Date start = new Date();
	  String start_time = sdf.format(start).toString();
	  System.out.println(sdf.format(start));
	  
	  Date end = new Date();
	  Long min =end.getTime() + 1800000;
	  end.setTime(min);
	  String end_time = sdf.format(end).toString();
	  System.out.println(sdf.format(end));
	  
	  String url2 = "https://developer.setmore.com/api/v1/bookingapi/appointment/create";
	  URL url;
	  url = new URL(url2);
	  URLConnection urlConn1 = url.openConnection();
	  urlConn1.setDoOutput(true);
	  
	  urlConn1.setRequestProperty("Content-Type","application/json");
	  urlConn1.setRequestProperty("Authorization","Bearer "+access_token);
	  
	  JSONObject urlPram = new JSONObject();
	  urlPram.put("staff_key","r3b231493098786085");
	  urlPram.put("service_key","s07281488805917222");
	  urlPram.put("customer_key" , "c3a902efb680f33d6c050deffd69277a1257b3699");
	  urlPram.put("start_time",start_time);
	  urlPram.put("end_time",end_time);
		  String urlParameters = "staff_key="+"r3b231493098786085"
					+ "&service_key="+"s07281488805917222"
					+ "&customer_key" + "c3a902efb680f33d6c050deffd69277a1257b3699"
					+ "&start_time="+start_time
					+ "&end_time="+end_time;
		  
//	  JSONObject urlPram = new JSONObject(urlParameters);
	  System.out.println(urlPram);
	  OutputStreamWriter writers = new OutputStreamWriter(urlConn1.getOutputStream());
	  writers.write(urlPram.toString());
	  writers.close();
		

	  String responseJsonString="",responseString = "";
	  BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn1.getInputStream()));
	  while ((responseString = reader.readLine()) != null) 
	  {
		  responseJsonString += responseString;
	  }
	  System.out.println(responseJsonString);
  }
}