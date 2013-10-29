package com.hwu65.mobilehealthmonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hwu65.datasource.UserInput;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ListView menuList;
	protected List<HashMap<String,String>> menuArray;
	protected SimpleAdapter MyListAdapter;
	private final Context context = this;
	private UserInput userInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		userInput = (UserInput) intent.getSerializableExtra("userInput");
		menuList = (ListView) this.findViewById(R.id.menuList); 
		this.menuArray = new ArrayList<HashMap<String, String>>();
		String[] colHead = new String[] {"MNAME"};
        int[] dataCell = new int[]{R.id.menuName};
        HashMap<String,String> map1 = new HashMap<String,String>();
        map1.put("MNAME", "Data Entry"); 
        menuArray.add(map1);
        HashMap<String,String> map2 = new HashMap<String,String>();
        map2.put("MNAME", "Monitor Your Health");
        menuArray.add(map2);
        MyListAdapter = new 
        		SimpleAdapter(this,menuArray,R.layout.list_view,colHead,dataCell); 
        menuList.setAdapter(MyListAdapter);
        menuList.setOnItemClickListener(new OnItemClickListener(){
        	private void showError(String title, String errMsg) {
        		final Dialog dialog = new Dialog(context);
        		dialog.setContentView(R.layout.popup_window);
        		dialog.setTitle(title);
        		TextView text = (TextView) dialog.findViewById(R.id.text);
        		text.setText(errMsg);
        		ImageView image = (ImageView) dialog.findViewById(R.id.image);
        		image.setImageResource(R.drawable.ic_launcher);
        		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        		dialogButton.setOnClickListener(new OnClickListener() {
        			@Override
        			public void onClick(View v) {
        				dialog.dismiss();
        			}
        		});

        		dialog.show();
        	}
        	
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int pos,
					long id) {
				if(pos == 0) {
					if(userInput == null) {
						Intent intent = new Intent(context, DataEntry.class);
						startActivity(intent); 
						finish();
					} else {
						Intent intent = new Intent(context, DataEntry.class);
						intent.putExtra("userInput", userInput);
						startActivity(intent); 
						finish();
					}
				}
				if(pos == 1) {
					if(userInput == null) {
						this.showError("Error", "Please enter data for healthy situation first!");
					} else {
						Intent intent = new Intent(context, MonitorScreen.class);
						intent.putExtra("userInput", userInput);
	                    startActivity(intent); 
	                    finish();
					}
				}
			}
        	
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
}
