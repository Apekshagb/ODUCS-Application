package com.example.oducs_mobile;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.oducs_mobile.Timer.TimerListener;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Reminder extends Activity implements AccelerometerListener{
	
	PendingIntent pendingIntent;
	AlarmManager alarmManager;
	BroadcastReceiver mReceiver;
	
	
	PowerManager powermanager = null;
	PowerManager.WakeLock wakelock = null;
	Ringtone ringtone = null;

	Button stopReminder;
	String reminder;
	
	String event_type,event_time,event_duration,event_days,event_note,shake_time,reminder_time;
	TextView eventType,eventTime,eventDuration,eventDays,eventNote;
	Document doc;
	Element rootElement;
	File folder;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		folder = getFilesDir();
		super.onCreate(savedInstanceState);
		
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
				 					WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
				 					WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
				 					WindowManager.LayoutParams.FLAG_FULLSCREEN |
				 					WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
				 					WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		
		
		setContentView(R.layout.set_reminder);
		
		Intent alarm=getIntent();
		event_type=alarm.getStringExtra("type");
		event_time=alarm.getStringExtra("time");
		event_duration=alarm.getStringExtra("duration");
		event_days=alarm.getStringExtra("days");
		event_note=alarm.getStringExtra("note");
		shake_time=alarm.getStringExtra("shake_time");
		reminder_time=alarm.getStringExtra("reminder_time"); 
		
		eventType=(TextView) findViewById(R.id.event_Type);
		eventTime=(TextView) findViewById(R.id.event_Time);
		eventDuration=(TextView) findViewById(R.id.event_Duration);
		eventDays=(TextView) findViewById(R.id.event_Days);
		eventNote=(TextView) findViewById(R.id.event_Note);
		
		
		eventType.setText(event_type);
		eventTime.setText(event_time);
		eventDuration.setText(event_duration);
		eventDays.setText(event_days);
		eventNote.setText(event_note);
		
		
		
		stopReminder = (Button) findViewById(R.id.stop);
		stopReminder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopAlarm();
				Intent stopReminder = new Intent("com.odu.mobileapp.EVENTS");
				
				if (v.getId() == R.id.stop) {
					// Log.d("parsiing", "xyz");
					startActivity(stopReminder);
				}
			}
		});
		
		startAlarm();
		

	}
		

	
    public void startAlarm(){
		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		
		 
		if(alert == null){
			Log.d("Ringtone","TYPE ALARM is not present checking for TYPE NOTIFICATION");
			
			alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			if(alert == null){
				Log.d("Ringtone","TYPE NOTIFICATION is not present checking for TYPE RINGTONE");
				
				alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
			}			
		}
		else{
			Log.d("Ringtone","TYPE ALARM is set");
		}
		
		//Get the wake lock to keep the phone awake till the timer expire
		powermanager = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
		//wakelock = powermanager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Wake tag");
		
		ringtone = RingtoneManager.getRingtone(getApplicationContext(), alert);
		//wakelock.acquire();
		ringtone.play();
		
	}
    
    public void stopAlarm(){
		if(ringtone != null && ringtone.isPlaying()){
			ringtone.stop();
			
			if(wakelock != null && wakelock.isHeld()){
				wakelock.release();
				wakelock = null;
			}
		}
	}
    
    public void onAccelerationChanged(float x, float y, float z) {
        // TODO Auto-generated method stub
         
    }
 
    public void onShake(float force) {
         Log.v("shake", "shake started");
        // Do your stuff here
       Timer.start(new TimerListener() {
		
		@Override
		public void timerStopped() {
			stopAlarm();
			
		}
	});
        
         
    }
 
    @Override
    public void onResume() {
            super.onResume();
            Toast.makeText(getBaseContext(), "onResume Accelerometer Started", 
                    Toast.LENGTH_SHORT).show();
             
            //Check device supported Accelerometer senssor or not
            if (AccelerometerManager.isSupported(this)) {
                 
                //Start Accelerometer Listening
                AccelerometerManager.startListening(this);
               
            }
    }
     
    @Override
    public void onStop() {
            super.onStop();
             
            //Check device supported Accelerometer senssor or not
            if (AccelerometerManager.isListening()) {
                 
                //Start Accelerometer Listening
                AccelerometerManager.stopListening();
                Timer.acceStoped(); 
                Toast.makeText(getBaseContext(), "onStop Accelerometer Stoped", 
                         Toast.LENGTH_SHORT).show();
                //stopAlarm();
            }
            
    }
     
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Sensor", "Service  distroy");
         
        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {
             
            //Start Accelerometer Listening
            AccelerometerManager.stopListening();
             
            Toast.makeText(getBaseContext(), "onDestroy Accelerometer Stoped", 
                   Toast.LENGTH_SHORT).show();
           // stopAlarm();
        }
             
    }



	@Override
	public void onAccStop() {
		Timer.acceStoped();
		
	}

}
