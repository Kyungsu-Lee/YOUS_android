package com.lmasi.lmasi.yous;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;

/**
 * Created by lmasi on 2017. 5. 8..
 */

public class SMSManager {

    private Context context;

    public SMSManager(Context context)
    {
        this.context = context;
    }

    public void sendSMS(String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED), 0);


        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {

                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        break;
                }
            }
        }, new IntentFilter(SENT));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }
}
