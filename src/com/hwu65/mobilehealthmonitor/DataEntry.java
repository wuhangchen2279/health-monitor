package com.hwu65.mobilehealthmonitor;

import com.hwu65.datasource.UserInput;
import com.hwu65.mobilehealthmonitor.RangeSeekBar.OnRangeSeekBarChangeListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DataEntry extends Activity{
	private ViewGroup hrLayout;
	private ViewGroup sbpLayout;
	private ViewGroup dbpLayout;
	private TextView hrMin;
	private TextView hrMax;
	private TextView sbpMin;
	private TextView sbpMax;
	private TextView dbpMin;
	private TextView dbpMax;	
	final Context context = this;
	private UserInput userInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data_entry);
		hrLayout = (ViewGroup) this.findViewById(R.id.hrLayout);
	    sbpLayout = (ViewGroup) this.findViewById(R.id.sbpLayout);
	    dbpLayout = (ViewGroup) this.findViewById(R.id.dbpLayout);
	    hrMin = (TextView) this.findViewById(R.id.hrMin);
	    hrMax = (TextView) this.findViewById(R.id.hrMax);
	    sbpMin = (TextView) this.findViewById(R.id.sbpMin);
	    sbpMax = (TextView) this.findViewById(R.id.sbpMax);
	    dbpMin = (TextView) this.findViewById(R.id.dbpMin);
	    dbpMax = (TextView) this.findViewById(R.id.dbpMax);	 
		Intent intent = getIntent();
		UserInput u = (UserInput) intent.getSerializableExtra("userInput");
		if(u == null) {
			userInput = new UserInput();
			this.setSeekBar(40, 120, 40, 120, hrLayout, hrMin, hrMax);
		    this.setSeekBar(70, 200, 70, 200, sbpLayout, sbpMin, sbpMax);
		    this.setSeekBar(40, 130, 40, 130, dbpLayout, dbpMin, dbpMax);
		} else {
			userInput = u;
			hrMin.setText(Integer.toString(userInput.getHrHealthyLower()));
		    hrMax.setText(Integer.toString(userInput.getHrHealthyUpper()));
		    sbpMin.setText(Integer.toString(userInput.getSbpHealthyLower()));
		    sbpMax.setText(Integer.toString(userInput.getSbpHealthyUpper()));
		    dbpMin.setText(Integer.toString(userInput.getDbpHealthyLower()));
		    dbpMax.setText(Integer.toString(userInput.getDbpHealthyUpper()));
		    this.setSeekBar(userInput.getHrHealthyLower(), userInput.getHrHealthyUpper(), 40, 120, hrLayout, hrMin, hrMax);
		    this.setSeekBar(userInput.getSbpHealthyLower(), userInput.getSbpHealthyUpper(), 70, 200, sbpLayout, sbpMin, sbpMax);
		    this.setSeekBar(userInput.getDbpHealthyLower(), userInput.getDbpHealthyUpper(), 40, 130, dbpLayout, dbpMin, dbpMax);
		}				          
	    
	}
	
	private void setSeekBar(int selectedMinValue, int selectedMaxValue,int absoluteMinValue, int absoluteMaxValue, ViewGroup viewGroup, final TextView textMin, final TextView textMax) {
		RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(absoluteMinValue, absoluteMaxValue, this);
        seekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                    // handle changed range values
                    //Log.i("A", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
                	textMin.setText(minValue.toString());
                	textMax.setText(maxValue.toString());
                }
        });
        seekBar.setSelectedMinValue(selectedMinValue);
        seekBar.setSelectedMaxValue(selectedMaxValue);
        viewGroup.addView(seekBar);
	}
	
	public void backMenu(View view) {
		this.setUserInput();
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra("userInput", userInput);
        startActivity(intent); 
        finish();
	}
	
	public void proceedMonitor(View view) {
		this.setUserInput();
		Intent intent = new Intent(context, MonitorScreen.class);
		intent.putExtra("userInput", userInput);
        startActivity(intent); 
        finish();
	}
	
	private void setUserInput() {
		userInput.setHrHealthyLower(Integer.parseInt(hrMin.getText().toString()));
		userInput.setHrHealthyUpper(Integer.parseInt(hrMax.getText().toString()));
		userInput.setSbpHealthyLower(Integer.parseInt(sbpMin.getText().toString()));
		userInput.setSbpHealthyUpper(Integer.parseInt(sbpMax.getText().toString()));
		userInput.setDbpHealthyLower(Integer.parseInt(dbpMin.getText().toString()));
		userInput.setDbpHealthyUpper(Integer.parseInt(dbpMax.getText().toString()));
	}
}
