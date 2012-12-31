package com.example.maps;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class Home extends ListActivity implements OnClickListener{

	String searches[];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.home);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.customtitle);
		//LinearLayout layout = (LinearLayout) getWindow().findViewById(R.id.title_complex);
		//layout.addView(new Button(this));
		//layout.addView(new Button(this));
		try {
			loadEntries();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setListAdapter(new ArrayAdapter<String>(Home.this, android.R.layout.simple_list_item_1, searches));
	}


	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.aboutUs:
			Intent i = new Intent("com.ABOUT");
			startActivity(i);
			break;
		case R.id.options:
			Intent p = new Intent("com.OPTIONS");
			startActivity(p);
			break;
		case R.id.exit:
			finish();
			break;
		}
		return false;
	}
	
	protected void loadEntries() throws IOException{
		FileOutputStream fos;
		String afterRead="";
		//checks if any files exist, if they don't, it creates one called "saved"
		if(fileList().length==0){
			fos = openFileOutput("saved", Context.MODE_PRIVATE);
		}else{
			//reads through saved and builds a string out of it
			FileInputStream fis = openFileInput("saved");
			int ch;
			StringBuffer fileContent = new StringBuffer("");
			byte[] buffer = new byte[1024];
			int length;
			while ((length = fis.read(buffer)) != -1) {
			    fileContent.append(new String(buffer));
			}
			afterRead=fileContent.toString();
			//reads through the built string for the number of new lines 
			Scanner sc=new Scanner(afterRead);
			int count=0;
			while(sc.hasNext()){
				if(sc.next().equals("/n")){
					count++;
				}
			}
			//builds searches array and adds the locations
			searches=new String[count];
			Scanner sc1=new Scanner(afterRead);
			int i=0;
			while(sc1.hasNext()){
				searches[i]=sc1.nextLine();
			}
		}
	}



	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String location = searches[position];
		try {
			Class ourClass = Class.forName("com.example.maps.MainActivity");
			Bundle basket=new Bundle();
			basket.putString("location", location);
			Intent ourIntent = new Intent(Home.this, ourClass);
			ourIntent.putExtras(basket);
			startActivity(ourIntent);
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}




	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}
