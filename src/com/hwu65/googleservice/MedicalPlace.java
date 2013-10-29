package com.hwu65.googleservice;

import com.google.android.gms.maps.model.LatLng;

public class MedicalPlace {
	private LatLng latlng;
	private String name;
	
	public MedicalPlace(LatLng latlng, String name) {
		this.latlng = latlng;
		this.name = name;
	}

	public LatLng getLatlng() {
		return latlng;
	}

	public void setLatlng(LatLng latlng) {
		this.latlng = latlng;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
