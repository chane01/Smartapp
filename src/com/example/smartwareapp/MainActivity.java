package com.example.smartwareapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	
	// Debugging
    private static final String TAG = "BluetoothChat";
    private static final boolean D = true;
	
	// Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 2;
   //private static final int REQUEST_CONNECT_DEVICE_INSECURE = 1;
    
    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    
    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    public static BluetoothChatService mChatService = null;
    public static String temptext = "";
    
    
    //context
    private Button btnCon;
    private Button btnSenMode;
    private Button btnConMode;
    private ToggleButton tgBton;
    private Context context;
    private TextView txConstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        startActivity(new Intent(this, SplashActivity.class));
        
        context = this;
        // Get local Bluetooth adapter
        
    	//mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        /*// If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }*/
        
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        
        tgBton = (ToggleButton) findViewById(R.id.toggleButton1);
        if (!mBluetoothAdapter.isEnabled()) {
        	tgBton.setChecked(false);
        // Otherwise, setup the chat session
        } else {
            if (mChatService == null) 
            	setupChat();
            tgBton.setChecked(true);
        }
        
        txConstate = (TextView) findViewById(R.id.textView3);
        
        tgBton.setOnClickListener(new View.OnClickListener() 
    	{
    		//@Override
            public void onClick(View v) 
            {
                // Send a message using content of the edit text widget
                if(tgBton.isChecked()){
                	//Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	                //startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                	mBluetoothAdapter.enable();
                	if (mChatService == null) 
	                	setupChat();
                }
                else{
                	mChatService.stop();
                	mBluetoothAdapter.disable();
                }
            }
        });
        
        
        
        btnCon = (Button) findViewById(R.id.button1);
        btnCon.setOnClickListener(new Button.OnClickListener(){
    		public void onClick(View v)
    		{
    			if (!mBluetoothAdapter.isEnabled()) {
	            // Otherwise, setup the chat session
    				Toast.makeText(context, "블루투스 기능이 필요합니다.", Toast.LENGTH_SHORT).show();
	            } else {
	            	
	            	Intent serverIntent = new Intent(MainActivity.this,DeviceListActivity.class);
                    startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
	            }
    	        
    		}
    	});
        
        btnSenMode  = (Button) findViewById(R.id.button2);
        btnSenMode.setOnClickListener(new Button.OnClickListener(){
    		public void onClick(View v)
    		{
    			
    			byte[] send = new byte[1];
            	send[0] = '1';
                mChatService.write(send);
    			
    			
    			Intent tabIntent = new Intent(MainActivity.this, MainTabAct.class);
    			startActivity(tabIntent);
    		}
    	});
        
        btnConMode = (Button) findViewById(R.id.button3);
        btnConMode.setOnClickListener(new Button.OnClickListener(){
    		public void onClick(View v)
    		{
    			Intent tabIntent = new Intent(MainActivity.this, ControlActivity.class);
    			startActivity(tabIntent);
    		}
    	});
    }
    
    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        
       /* if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupChat();
        }*/
        
    }
    
    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
              // Start the Bluetooth chat services
              mChatService.start();
            }
        }
    }
    
    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);
    }
    
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BluetoothChatService.STATE_CONNECTED:
                	txConstate.setTextColor(Color.GREEN);
                	txConstate.setText("Connected");
                    //setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                    break;
                case BluetoothChatService.STATE_CONNECTING:
                    //setStatus(R.string.title_connecting);
                    break;
                case BluetoothChatService.STATE_LISTEN:
                case BluetoothChatService.STATE_NONE:
                	
                	txConstate.setTextColor(Color.RED);
                	txConstate.setText("Not Connected");
                    //setStatus(R.string.title_not_connected);
                    //togglebtn.setChecked(false);
                    break;
                }
                break;
            case MESSAGE_WRITE:
                //byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                //String writeMessage = new String(writeBuf);
                break;
            case MESSAGE_READ:
            	if(D) Log.e(TAG, "HEY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            	byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                
               
                //myLog.write("%s",readMessage);
                parseMsg(readMessage);
                	
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to "
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE_SECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                connectDevice(data, true);
            }
            break;
        /*case REQUEST_CONNECT_DEVICE_INSECURE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                connectDevice(data, false);
            }
            break;*/
        }
    }

    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/
    
    public void parseMsg(String msg)
    {
    	
    	if(D) Log.d(TAG, "parse Message");
    	
    	int index = msg.indexOf("temp : ");
    	int countIndex = msg.indexOf("MPUxA");
    	String temp = msg.substring(index+7, countIndex-2);
    	
    	temptext = temp;
    	
    }
    
}
