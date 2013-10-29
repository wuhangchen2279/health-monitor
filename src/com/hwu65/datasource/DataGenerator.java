package com.hwu65.datasource;

import java.io.Serializable;
import java.util.Random;

import android.os.Parcel;
import android.os.Parcelable;

import com.hwu65.mobilehealthmonitor.MonitorScreen;

public class DataGenerator extends Publisher implements Runnable, Serializable  {
    private Random generator;
    private HealthParameter healthParameter;
    private int record;
    private UserInput userInput;
    private MonitorScreen monitorScreen;
    
    public DataGenerator() {
    	generator = new Random();
    }
    
    private HealthParameter setHealthParameter() {
        healthParameter = new HealthParameter();
        healthParameter.setHr(this.nextRecord(40, 120));
        healthParameter.setSbp(this.nextRecord(70, 200));
        healthParameter.setDbp(this.nextRecord(40, 130));
        healthParameter.setRt(this.nextRecord(0, 50));   
        return healthParameter;
    }
    
    private int nextRecord(int min, int max) {
        record = generator.nextInt(max - min) + min;
        return record;
    }
    
    private boolean validateDataRecord() {
        if((healthParameter.getHr() < 60 && healthParameter.getSbp() < 90 && healthParameter.getDbp() < 60)
                || (healthParameter.getHr() > 100 && healthParameter.getSbp() > 140 && healthParameter.getSbp() < 159 && healthParameter.getDbp() > 90 && healthParameter.getDbp() < 99) 
                || (healthParameter.getHr() > 100 && healthParameter.getSbp() > 160 && healthParameter.getSbp() < 179 && healthParameter.getDbp() > 100 && healthParameter.getDbp() < 109) 
                || (healthParameter.getHr() > 100 && healthParameter.getSbp() >= 180 && healthParameter.getDbp() >= 110) 
                || (healthParameter.getHr() > 100 && healthParameter.getRt() > 30 && healthParameter.getSbp() < this.userInput.getSbpHealthyLower() && healthParameter.getDbp() < this.userInput.getDbpHealthyLower())
                || (healthParameter.getHr() > this.userInput.getHrHealthyLower() && healthParameter.getHr() < this.userInput.getHrHealthyUpper() && healthParameter.getSbp() > this.userInput.getSbpHealthyLower() && healthParameter.getSbp() < this.userInput.getSbpHealthyUpper() && healthParameter.getDbp() > this.userInput.getDbpHealthyLower() && healthParameter.getDbp() < this.userInput.getDbpHealthyUpper())) {
            return true;
        } else {
            return false;
        }       
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
        this.setHealthParameter();
        if(this.validateDataRecord()) {
            super.notifySubscribers(healthParameter);
            monitorScreen.setThreadItem(this, healthParameter);
        } else {
        	this.run();
        }
	}
	
	public void setUserInput(UserInput userInput) {
        this.userInput = userInput;
    }

	public MonitorScreen getMonitorScreen() {
		return monitorScreen;
	}

	public void setMonitorScreen(MonitorScreen monitorScreen) {
		this.monitorScreen = monitorScreen;
	}
	

	public HealthParameter getHealthParameter() {
		return healthParameter;
	}

	public void setHealthParameter(HealthParameter healthParameter) {
		this.healthParameter = healthParameter;
	}

	public int getRecord() {
		return record;
	}

	public void setRecord(int record) {
		this.record = record;
	}

	public UserInput getUserInput() {
		return userInput;
	}
}
