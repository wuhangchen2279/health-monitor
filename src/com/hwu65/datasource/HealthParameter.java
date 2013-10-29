package com.hwu65.datasource;

import java.io.Serializable;

public class HealthParameter implements Serializable {
	private int hr;
    private int sbp;
    private int dbp;
    private int rt;
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
