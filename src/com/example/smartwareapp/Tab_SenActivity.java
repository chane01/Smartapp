package com.example.smartwareapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.TextView;

public class Tab_SenActivity extends Activity {
	
	private TextView text;
	private CountDownTimer timer;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tabstatus);
	    
	    text = (TextView) findViewById (R.id.ttv1);
	    
	    //타이머
	    timer = new CountDownTimer(10000*500, 500) {
	    	@Override
	    	public void onTick(long millisUntilFinished) {
	    		text.setText("온도 : " + MainActivity.temptext);
	    	}
	    	
	    	@Override
	    	public void onFinish(){
	    		;
	    	}
	    };
	    
	    timer.start();
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				//Toast.makeText(this, "Back키를 누르셨군요", Toast.LENGTH_SHORT).show();
				byte[] send = new byte[1];
            	send[0] = '0';
				MainActivity.mChatService.write(send);
				break;
    	}
    	return false;
	}

}
