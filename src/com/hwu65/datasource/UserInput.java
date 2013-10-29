package com.hwu65.datasource;

import java.io.Serializable;

public class UserInput implements Serializable {
    private int hrHealthyLower;
    private int hrHealthyUpper;
    private int sbpHealthyLower;
    private int sbpHealthyUpper;
    private int dbpHealthyLower;
    private int dbpHealthyUpper;
    
	public int getHrHealthyLower() {
		return hrHealthyLower;
	}
	
	public void setHrHealthyLower(int hrHealthyLower) {
		this.hrHealthyLower = hrHealthyLower;
	}
	public int getHrHealthyUpper() {
		return hrHealthyUpper;
	}
	public void setHrHealthyUpper(int hrHealthyUpper) {
		this.hrHealthyUpper = hrHealthyUpper;
	}
	public int getSbpHealthyLower() {
		return sbpHealthyLower;
	}
	public void setSbpHealthyLower(int sbpHealthyLower) {
		this.sbpHealthyLower = sbpHealthyLower;
	}
	public int getSbpHealthyUpper() {
		return sbpHealthyUpper;
	}
	public void setSbpHealthyUpper(int sbpHealthyUpper) {
		this.sbpHealthyUpper = sbpHealthyUpper;
	}
	public int getDbpHealthyLower() {
		return dbpHealthyLower;
	}
	public void setDbpHealthyLower(int dbpHealthyLower) {
		this.dbpHealthyLower = dbpHealthyLower;
	}
	public int getDbpHealthyUpper() {
		return dbpHealthyUpper;
	}
	public void setDbpHealthyUpper(int dbpHealthyUpper) {
		this.dbpHealthyUpper = dbpHealthyUpper;
	}
    
    
}
