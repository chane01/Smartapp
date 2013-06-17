package com.example.smartwareapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

@SuppressLint("NewApi")
public class MyLocation {
	
	static public Criteria criteria;
	
	public static double[] getMyLocation(Context context){
		 
        double[] result = null;
        
        
        if(criteria==null){
            criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            criteria.setSpeedRequired(true); // �ӵ�
            criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);// ��Ȯ��
            criteria.setAltitudeRequired(false); // ��
 
            criteria.setBearingRequired(true);
            criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);// ..
 
            
 
        }
 
        LocationManager locationManager;
 
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
 
        //true=���� �̿밡���� ������ ����
 
        String provider = locationManager.getBestProvider(criteria, true);// "gps";
 
        if (provider == null)
            provider = "network";
 
        Location location = locationManager.getLastKnownLocation(provider);
 
        if(location==null){
            Intent intent = new Intent(context, Tab_MapActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            ((Activity) context).finish();
        }else{
            result = new double[]{
                location.getLatitude(), location.getLongitude(), location.getSpeed()
            };
        }
 
        return result;


	}
}
