package com.hwu65.mobilehealthmonitor;


import com.hwu65.datasource.UserInput;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class SimpleGraph extends Activity {
    private GraphViewSeries exampleSeries;
    private GraphView graphView;
    private double graphLastXValue = 0d;
    private GraphData graphData;
    private Intent intent;
    private UserInput userInput;
    private BroadcastReceiver myRecevier;
    private String content;
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.unregisterReceiver(this.myRecevier);
	}
    
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myRecevier = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				Bundle extras = intent.getExtras();
			     if (extras != null) {
				      if(extras.containsKey("graphData")){
				    	   graphData = (GraphData) extras.get("graphData");					    
					       if(graphView != null) {
					    	   graphLastXValue += 1d;					    	   
					    	   if(content.equals("Heart Rate")) {
					    		   exampleSeries.appendData(new GraphViewData(graphLastXValue, graphData.getHr()), true, 10);
					    	   } else if (content.equals("SBP")) {
					    		   exampleSeries.appendData(new GraphViewData(graphLastXValue, graphData.getSbp()), true, 10);
					    	   } else if (content.equals("DBP")) {
					    		   exampleSeries.appendData(new GraphViewData(graphLastXValue, graphData.getDbp()), true, 10);
					    	   } else {
					    		   exampleSeries.appendData(new GraphViewData(graphLastXValue, graphData.getRt()), true, 10);
					    	   }
					       } 
				      }
			     }
			}
			
		};
		this.registerReceiver(this.myRecevier, new IntentFilter("MyBroadcast"));
	}
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.graphs);
		userInput = (UserInput) this.getIntent().getSerializableExtra("userInput");
		exampleSeries = new GraphViewSeries(new GraphViewData[] {});
		content = getIntent().getStringExtra("content");
		graphView = new LineGraphView(
				this
				, content
		);
		((LineGraphView) graphView).setDrawBackground(true);
		graphView.addSeries(exampleSeries); // data
		graphView.setViewPort(1, 8);
		graphView.setScalable(true);
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		layout.addView(graphView);
		
	}

	
	
	public void backMonitor(View view) {
		intent = new Intent(this,MonitorScreen.class);
		intent.putExtra("userInput", userInput);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
		finish();
	}
	
	public void backMenu(View view) {
		intent = new Intent(this,MainActivity.class);
		intent.putExtra("userInput", userInput);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
		finish();
	}
}
