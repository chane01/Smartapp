package com.example.smartwareapp;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TabHost;
import android.widget.Toast;

public class MainTabAct extends TabActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintab);
        
        Resources res = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        
        intent = new Intent(MainTabAct.this, Tab_MapActivity.class);
        spec = tabHost.newTabSpec("지도").setIndicator("지도", res.getDrawable(R.drawable.ic_launcher)).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent(MainTabAct.this, Tab_SenActivity.class);
        spec = tabHost.newTabSpec("상태").setIndicator("상태", res.getDrawable(R.drawable.ic_launcher)).setContent(intent);
        tabHost.addTab(spec);
	}
	
}
