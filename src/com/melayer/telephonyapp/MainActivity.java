package com.melayer.telephonyapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ShareCompat.IntentReader;
import android.telephony.CellLocation;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

	private BroadcastReceiver receiverSent = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {

			Toast.makeText(context, "Sent", Toast.LENGTH_LONG).show();
		}
	};
	
	private BroadcastReceiver receiverReached = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			Toast.makeText(context, "Reached", Toast.LENGTH_LONG).show();
		}
	}; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

		Log.i("####### IMEI #######", manager.getDeviceId());
		Log.i("####### COUNTRY CODE #######", manager.getNetworkCountryIso());
		Log.i("####### SIM SERIAL #######", manager.getSimSerialNumber());
		Log.i("####### SIM OPERATOR #######", manager.getSimOperator());
		Log.i("####### SUB ID #######", manager.getSubscriberId());

		CellLocation cellLoc = manager.getCellLocation();

		Intent intentSent = new Intent();
		intentSent.setAction("com.melayer.msg.sent");
		PendingIntent pendingSent = PendingIntent.getBroadcast(this, 1234,
				intentSent, 0);

		Intent intentDeliverd = new Intent();
		intentDeliverd.setAction("com.melayer.msg.reached");
		PendingIntent pendingReached = PendingIntent.getBroadcast(this, 5678,
				intentDeliverd, 0);
		SmsManager managerSms = SmsManager.getDefault();

		managerSms.sendTextMessage("", null, "From Telephony API", pendingSent,
				pendingReached);

		IntentFilter filterSent = new IntentFilter("com.melayer.msg.sent");
		registerReceiver(receiverSent, filterSent);
		
		IntentFilter filterReached = new IntentFilter("com.melayer.msg.reached");
		registerReceiver(receiverReached, filterReached);
	}
}
