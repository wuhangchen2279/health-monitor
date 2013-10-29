package com.hwu65.ruleinference;

import com.hwu65.datasource.HealthParameter;
import com.hwu65.datasource.Subscriber;
import com.hwu65.datasource.UserInput;

public class Rule implements Subscriber {
	private int hr;
    private int sbp;
    private int dbp;
    private int rt;
    private UserInput userInput;
    
	@Override
	public void update(HealthParameter healthParameter) {
		// TODO Auto-generated method stub
		this.hr = healthParameter.getHr();
        this.sbp = healthParameter.getSbp();
        this.dbp = healthParameter.getDbp();
        this.rt = healthParameter.getRt();
	}
    
	public String getSituation() {
        String result = "";
        if(this.hr < 60 && this.sbp < 90 && this.dbp < 60) {
            result = "Hypotension"; 
        } else if (this.hr > 100 && this.sbp > 140 && this.sbp < 159 && this.dbp > 90 && this.dbp < 99 ) {
            result = "Hypertension Stage 1";
        } else if (this.hr >100 && this.sbp > 160 && this.sbp < 179 && this.dbp > 100 && this.dbp < 109) {
            result = "Hypertension Stage 2";
        } else if (this.hr > 100 && this.sbp >= 180 && this.dbp >= 110) {
            result = "Hypertension Stage 3";
        } else if (this.hr > 100 && this.rt > 30 && this.sbp < this.userInput.getSbpHealthyLower() && this.dbp < this.userInput.getDbpHealthyLower()) {
            result = "Heat Stroke";
        } else if (this.hr > this.userInput.getHrHealthyLower() && this.hr < this.userInput.getHrHealthyUpper() && this.sbp > this.userInput.getSbpHealthyLower() && this.sbp < this.userInput.getSbpHealthyUpper() && this.dbp > this.userInput.getDbpHealthyLower() && this.dbp < this.userInput.getDbpHealthyUpper()) {
            result = "Healthy";
        }
        return result;
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

	public UserInput getUserInput() {
		return userInput;
	}

	public void setUserInput(UserInput userInput) {
		this.userInput = userInput;
	}
	
	
}
