package com.hwu65.googleservice;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.StrictMode;



public class GeocodingFetcher {
	public static double[] getLocation(String address){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
	    StringBuilder stringBuilder = new StringBuilder();
	    double[] location = new double[2];

	    try {

	        HttpPost httppost = new HttpPost("http://maps.googleapis.com/maps/api/geocode/json?address="+ address +"&sensor=false");
	        HttpClient client = new DefaultHttpClient();
	        org.apache.http.HttpResponse response;
	        stringBuilder = new StringBuilder();
	        response = client.execute(httppost);
	        HttpEntity entity = response.getEntity();

	        char[] buffer = new char[2048];
	        Reader reader = new InputStreamReader(entity.getContent(), "UTF-8");
	        while (true) {
	            int n = reader.read(buffer);
	            if (n < 0) {
	                break;
	            }
	            stringBuilder.append(buffer, 0, n);
	        }

	    } catch (ClientProtocolException e) {

	    } catch (IOException e) {

	    }

	    JSONObject jsonObject = new JSONObject();
	    try {
	        jsonObject = new JSONObject(stringBuilder.toString());
	    } catch (JSONException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

	    try {
	    	
	    	JSONObject j = ((JSONArray)jsonObject.get("results")).getJSONObject(0);
	    	JSONObject j1 = j.getJSONObject("geometry").getJSONObject("location");
	    	location[0] = j1.getDouble("lat");
	    	location[1] = j1.getDouble("lng");
	        //.getString("location");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return location;
	}
}
