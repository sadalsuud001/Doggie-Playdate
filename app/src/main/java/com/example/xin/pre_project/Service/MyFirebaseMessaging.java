package com.example.xin.pre_project.Service;

import android.content.Intent;
import android.util.Log;

import com.example.xin.pre_project.CustomerCall;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
//driverPart
public class MyFirebaseMessaging extends FirebaseMessagingService{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("EDMTDEV",remoteMessage.getNotification().getBody());
        //Because I will send the Firbase message with contain lat and lng from Rider app
        //So I need convert message to LatLng
        LatLng customer_location = new Gson().fromJson(remoteMessage.getNotification().getBody(),LatLng.class);
        Intent intent  = new Intent(getBaseContext(), CustomerCall.class);
        intent.putExtra("lat",customer_location.latitude);
        intent.putExtra("lng",customer_location.longitude);
        intent.putExtra("customer",remoteMessage.getNotification().getTitle());
        startActivity(intent);
    }
}
//part 9 16:33
