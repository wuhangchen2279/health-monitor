package com.hwu65.mobilehealthmonitor;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hwu65.googleservice.GeocodingFetcher;
import com.hwu65.googleservice.MedicalPlace;
import com.hwu65.googleservice.PlacesFetcher;






public class MyMapActivity extends Activity  {
	
	private GoogleMap map;
	private double[] location;
	private List<MedicalPlace> medicalPlaces;
	private MarkerOptions redcrossMarker;
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_map);
		map = ((MapFragment)getFragmentManager().findFragmentById(R.id.the_map)).getMap();
		LoadMap loadMap = new LoadMap();
		loadMap.execute(new String[] {"Monash+University+Caulfield"});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_map, menu);
		return true;
	}
	
	private class LoadMap extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... addressKeyword) {
			// TODO Auto-generated method stub
			location = GeocodingFetcher.getLocation(addressKeyword[0]);
			medicalPlaces = PlacesFetcher.getMedicalPlaces(location[0], location[1]);
			redcrossMarker = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.redcross));
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			LatLng myCurrentLocation = new LatLng(location[0],location[1]);
			map.addMarker(new MarkerOptions().position(myCurrentLocation)
					 .title("Monash University"));
			for(int i=0; i<medicalPlaces.size(); i++) {
				map.addMarker(redcrossMarker.position(new LatLng(medicalPlaces.get(i).getLatlng().latitude, medicalPlaces.get(i).getLatlng().longitude)).title(medicalPlaces.get(i).getName()));
				
			}
			map.moveCamera(CameraUpdateFactory.newLatLng(myCurrentLocation));
			map.moveCamera(CameraUpdateFactory.zoomTo(13));
		}
	}	
}
