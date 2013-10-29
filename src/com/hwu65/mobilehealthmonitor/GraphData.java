package com.hwu65.mobilehealthmonitor;

import java.io.Serializable;

import android.app.Activity;

import com.hwu65.datasource.HealthParameter;
import com.hwu65.datasource.Subscriber;

public class GraphData implements Subscriber, Serializable{
	private int hr;
    private int sbp;
    private int dbp;
    private int rt;
	
    public GraphData(){
    	
    }
	
	public GraphData(int hr, int sbp, int dbp, int rt) {
		this.hr = hr;
		this.sbp = sbp;
		this.dbp = dbp;
		this.rt = rt;
	}


	@Override
	public void update(HealthParameter healthParameter) {
		// TODO Auto-generated method stub
		this.setHr(healthParameter.getHr());
		this.setSbp(healthParameter.getSbp());
		this.setDbp(healthParameter.getDbp());
		this.setRt(healthParameter.getRt());
	}


	public int getHr() {
		return hr;
	}


	public void setHr(int hr) {
		this.hr = hr;
	}


	public int getSbp() {
		return sbp;
	}


	public void setSbp(int sbp) {
		this.sbp = sbp;
	}


	public int getDbp() {
		return dbp;
	}


	public void setDbp(int dbp) {
		this.dbp = dbp;
	}


	public int getRt() {
		return rt;
	}


	public void setRt(int rt) {
		this.rt = rt;
	}

}
