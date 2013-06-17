package com.example.smartwareapp;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.example.smartwareapp.MyLocation;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;

public class Tab_MapActivity extends FragmentActivity {
	
	private GoogleMap gmap;
	//private LocationManager locationManager;
	//private String provider;
	private double[] my_gps;
	private LatLng position;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tabblink);
	    
	    GooglePlayServicesUtil.isGooglePlayServicesAvailable(Tab_MapActivity.this);
	    
	    /*locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    
	    provider = locationManager.getBestProvider(new Criteria(), true);
	    
	    Location location = locationManager.getLastKnownLocation(provider);*/
	    
	    gmap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment1)).getMap();

    	gmap.setMyLocationEnabled(true);
	    
    	my_gps = MyLocation.getMyLocation(Tab_MapActivity.this);
	     
	    position = new LatLng(my_gps[0],my_gps[1]); // position값 설정
	     
	    gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,15));
	    
	    
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
