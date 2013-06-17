package com.example.smartwareapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ControlActivity extends Activity {
	
	private Button btnpat1;
	private Button btnpat2;
	private Button btnpat3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		
		btnpat1 = (Button) findViewById(R.id.cbutton1);
		btnpat1.setOnClickListener(new Button.OnClickListener(){
    		public void onClick(View v)
    		{
    			byte[] send = new byte[1];
            	send[0] = '2';
                MainActivity.mChatService.write(send);
    		}
    	});
		
		btnpat2 = (Button) findViewById(R.id.cbutton2);
		btnpat2.setOnClickListener(new Button.OnClickListener(){
    		public void onClick(View v)
    		{
    			byte[] send = new byte[1];
            	send[0] = '3';
                MainActivity.mChatService.write(send);
    		}
    	});
		
		btnpat3 = (Button) findViewById(R.id.cbutton3)
				;
		btnpat3.setOnClickListener(new Button.OnClickListener(){
    		public void onClick(View v)
    		{
    			byte[] send = new byte[1];
            	send[0] = '4';
                MainActivity.mChatService.write(send);
    		}
    	});
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.control, menu);
		return true;
	}*/

}
