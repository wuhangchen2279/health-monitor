package com.hwu65.googleservice;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import android.os.StrictMode;

public class PlacesFetcher {
	private static final String API_KEY = "";
	private static final String RETURN_TYPE = "hospital";
	private static final String RETURN_RADIOUS = "5000";
	
	public static List<MedicalPlace> getMedicalPlaces(double lat, double lng){
	    StringBuilder stringBuilder = new StringBuilder();
	    List<MedicalPlace> medicalPlaces = new ArrayList<MedicalPlace>();

	    try {
	    	//https://maps.googleapis.com/maps/api/place/textsearch/json?query=hospital&types=hospital&location=-37.8769803,145.0449767&radius=5000&sensor=true&key=AIzaSyDwaMjhTN9BZgvmcg-hbSGJrMdZy9X6ZBs
	        //HttpPost httppost = new HttpPost("https://maps.googleapis.com/maps/api/place/radarsearch/json?location=" + lat + "," + lng +"&radius=" + RETURN_RADIOUS +"&types=" + RETURN_TYPE + "&sensor=false&key="+API_KEY);
	    	HttpPost httppost = new HttpPost("https://maps.googleapis.com/maps/api/place/textsearch/json?query=hospital&location=" + lat + "," + lng +"&radius=" + RETURN_RADIOUS +"&types=" + RETURN_TYPE + "&sensor=false&key="+API_KEY);
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
	    	JSONArray hospitalArray = (JSONArray) jsonObject.get("results");
	    	for(int i=0; i<hospitalArray.length(); i++) {
	    		JSONObject location = hospitalArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");
	    		String name = hospitalArray.getJSONObject(i).getString("name");
	    		medicalPlaces.add(new MedicalPlace(new LatLng(location.getDouble("lat"),location.getDouble("lng")), name));
	    	}
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return medicalPlaces;
	}
}
