package com.example.user.trysms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by User on 5/9/2017.
 */

public class SMSReceiver extends BroadcastReceiver {

    Bundle bundle;
    SmsMessage currentSMS;
    String sender,message;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            bundle = intent.getExtras();
            if(bundle!=null){
                Object[] pduObjects = (Object[]) bundle.get("pdus");
                if(pduObjects!=null){
                    for (Object object:pduObjects) {
                        currentSMS = buildIncomingMessage(object,bundle);
                        sender = currentSMS.getDisplayOriginatingAddress();
                        message = currentSMS.getDisplayMessageBody();
                        Toast.makeText(context, "Sender: "+sender+"\nMessage: "+message, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private SmsMessage buildIncomingMessage(Object object, Bundle bundle){
        SmsMessage incomingSMS;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            String format = bundle.getString("format");
            incomingSMS = SmsMessage.createFromPdu((byte[]) object,format);
        }else{
            incomingSMS = SmsMessage.createFromPdu((byte[]) object);
        }
        return incomingSMS;
    }
}
