package com.hwu65.mobilehealthmonitor;


import com.hwu65.datasource.DataGenerator;
import com.hwu65.datasource.HealthParameter;
import com.hwu65.datasource.UserInput;
import com.hwu65.ruleinference.Rule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MonitorScreen extends Activity {
	private ProgressBar progressHr;
	private ProgressBar progressSbp;
	private ProgressBar progressDbp;
	private ProgressBar progressRt;
	private TextView hrTextView;
	private TextView sbpTextView;
	private TextView dbpTextView;
	private TextView rtTextView;
	private TextView healthSituationTextView;
	private int generateRate = 1000;
	private UserInput userInput;
	private DataGenerator dataGenerator;
	private Rule rule;
	private GraphData graphData;
	private SQLiteDatabase myDB;
	private String tableName;
	private final Context context = this;
	private Button hrButton;
	private Button sbpButton;
	private Button dbpButton;
	private Button rtButton;
	private Intent intent;
	private TextWatcher textWatcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.monitor_screen);
		this.initDatabase();
		userInput = (UserInput) getIntent().getSerializableExtra("userInput");
		this.progressHr = (ProgressBar) this.findViewById(R.id.hrPBar);
		this.progressSbp = (ProgressBar) this.findViewById(R.id.sbpPBar);
		this.progressDbp = (ProgressBar) this.findViewById(R.id.dbpPBar);
		this.progressRt = (ProgressBar) this.findViewById(R.id.rtPBar);
		this.hrTextView = (TextView) this.findViewById(R.id.hrTextView);
		this.sbpTextView = (TextView) this.findViewById(R.id.sbpTextView);
		this.dbpTextView = (TextView) this.findViewById(R.id.dbpTextView);
		this.rtTextView = (TextView) this.findViewById(R.id.rtTextView);
		this.healthSituationTextView = (TextView) this.findViewById(R.id.healthSituationTextView);
		textWatcher = new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if(healthSituationTextView.length()>=12){
					if(healthSituationTextView.getText().toString().substring(0, 12).equalsIgnoreCase("Hypertension")){
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
						alertDialogBuilder.setTitle("Hypertension Alert");
						alertDialogBuilder.setMessage("You are now experiencing Hypertension!!! Do you want to check nearby clinic?").setCancelable(false)
						.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close
								// current activity
								healthSituationTextView.addTextChangedListener(textWatcher);
								intent = new Intent(MonitorScreen.this, MyMapActivity.class); 
								startActivity(intent); 
							}
						})
						.setNegativeButton("No",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, just close
								// the dialog box and do nothing
								dialog.cancel();
								onResume();
							}
						});
						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
						progressHr.removeCallbacks(dataGenerator);
					}
					
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			
		};
		this.healthSituationTextView.addTextChangedListener(textWatcher);
		this.hrButton = (Button) this.findViewById(R.id.hrButton);
		this.sbpButton = (Button) this.findViewById(R.id.sbpButton);
		this.dbpButton = (Button) this.findViewById(R.id.dbpButton);
		this.rtButton = (Button) this.findViewById(R.id.rtButton);
		this.hrButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startGraphActivity(SimpleGraph.class, "Heart Rate");
			}
			
		});
		this.sbpButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startGraphActivity(SimpleGraph.class, "SBP");
			}
			
		});
		this.dbpButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startGraphActivity(SimpleGraph.class, "DBP");
			}
			
		});
		this.rtButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startGraphActivity(SimpleGraph.class, "Room Temp");
			}
			
		});
		this.progressHr.setMax(120);
		this.progressSbp.setMax(200);
		this.progressDbp.setMax(130);
		this.progressRt.setMax(50);
		dataGenerator = new DataGenerator();
		dataGenerator.setMonitorScreen(this);
		dataGenerator.setUserInput(userInput);
		rule = new Rule();
		graphData = new GraphData();
		rule.setUserInput(userInput);
		dataGenerator.addSubscriber(rule);
		dataGenerator.addSubscriber(graphData);
		this.progressHr.postDelayed(dataGenerator, generateRate);
	}

	private void startGraphActivity(Class<? extends Activity> activity, String content) {
		onResume();
		intent = new Intent(MonitorScreen.this, activity);
		intent.putExtra("content", content);
		intent.putExtra("userInput", userInput);
		startActivity(intent);
	}
	
	protected void initDatabase() {
		myDB = null;
		tableName = "generatedData";
		try {
			myDB = this.openOrCreateDatabase("mydbs.db", MODE_PRIVATE, null);
			myDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " (HR INTEGER, SBP INTEGER, DBP INTEGER, RT INTEGER);");
			myDB.delete(tableName, null, null);
		} catch(Exception e) {
			Log.e("error","error",e);
		} 
	}
	
	protected void closeDatabase() {
		myDB.close();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.healthSituationTextView.removeTextChangedListener(textWatcher);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.progressHr.postDelayed(dataGenerator, generateRate);
	}

	public void setThreadItem(Runnable r, HealthParameter h) {
		this.progressHr.postDelayed(r, generateRate);
		this.progressHr.setProgress(h.getHr());
		this.progressSbp.setProgress(h.getSbp());
		this.progressDbp.setProgress(h.getDbp());
		this.progressRt.setProgress(h.getRt());
		this.hrTextView.setText(Integer.toString(h.getHr()));
		this.sbpTextView.setText(Integer.toString(h.getSbp()));
		this.dbpTextView.setText(Integer.toString(h.getDbp()));
		this.rtTextView.setText(Integer.toString(h.getRt()));
		this.healthSituationTextView.setText(rule.getSituation());
		ContentValues values = new ContentValues();
		if(myDB.isOpen()) {
			values.put("HR", new Integer(h.getHr()));
			values.put("SBP", new Integer(h.getSbp()));
			values.put("DBP", new Integer(h.getDbp()));
			values.put("RT", new Integer(h.getRt()));
			
			myDB.insert(tableName, null, values);
		}
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("MyBroadcast");
		broadcastIntent.putExtra("graphData", graphData);
		this.sendBroadcast(broadcastIntent);		
	}

	public void backMenu(View view){
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra("userInput", userInput);
        startActivity(intent); 
        this.closeDatabase();
        finish();
	}


}
