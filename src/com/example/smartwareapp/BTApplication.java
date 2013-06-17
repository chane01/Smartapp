package com.example.smartwareapp;

import android.app.Application;

public class BTApplication extends Application {
	
	private BluetoothChatService mChatService = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	public BluetoothChatService getBluetoothChatService() {
		return mChatService;
	}
	
	public void setBluetoothChatService(BluetoothChatService mbs) {
		this.mChatService = mbs;
	}

}
