package org.atsevdev.radioromania;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static org.atsevdev.radioromania.sample.SAMPLES;


public class MyServices extends Service {


    public SimpleExoPlayer player1;
    private PlayerNotificationManager playerNotificationManager;
    public boolean clickCheck = false;

    private boolean pausedoncall = false;
    private PhoneStateListener PSL;
    private TelephonyManager telephonyManager;


    //for binding service
    private IBinder mbinder = new localBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mbinder;
    }
    public void onCreate(){
        super.onCreate();
        final Context context = this;
        ////////////////////////////////////////////////////////////////////////////////
        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
                context, "Channel ID", R.string.app_name, 0,
                new PlayerNotificationManager.MediaDescriptionAdapter() {
                    @Override
                    public String getCurrentContentTitle(Player player1) {
                        return null;
                    }

                    @Nullable
                    @Override
                    public PendingIntent createCurrentContentIntent(Player player) {
                        Intent intent = new Intent(context, MainActivity.class);
                        return PendingIntent.getActivity(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    }

                    @Nullable
                    @Override
                    public String getCurrentContentText(Player player) {
                        return null;
                    }

                    @Nullable
                    @Override
                    public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
                        return sample.getBitmap(
                                context, SAMPLES[player.getCurrentWindowIndex()].bitmap
                        );
                    }
                }
        );
        playerNotificationManager.setNotificationListener(new PlayerNotificationManager.NotificationListener() {
            @Override
            public void onNotificationStarted(int notificationId, Notification notification) {
                startForeground(0, notification);
                playerNotificationManager.setColorized(true);
                playerNotificationManager.setColor(000255165000);



            }

            @Override
            public void onNotificationCancelled(int notificationId) {

                stopSelf();
                clickCheck = false;
            }
        });

        //DO NOT DELETE
        player1 = ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector());
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                context, Util.getUserAgent(context, "RadioRomania"));


    playerNotificationManager.setPlayer(player1);
    }





    public class localBinder extends Binder{
        public MyServices getService(){return MyServices.this;}
    }

    public int onStartCommand(Intent intent, int flats, int stardId){

        //phone state listener for incoming phone calls
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        PSL = new PhoneStateListener(){
            @Override
            public void onCallStateChanged (int state, String IncomingNum){
                switch(state){
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                    case TelephonyManager.CALL_STATE_RINGING:
                        if (player1 != null){
                            pausedoncall = true;
                            player1.setPlayWhenReady(false);
                        }
                        break;

                    case TelephonyManager.CALL_STATE_IDLE:
                        if (player1 != null){
                            if(pausedoncall){
                                pausedoncall = false;
                                player1.setPlayWhenReady(true);
                            }
                        }
                    break;
                }
            }
        };
        telephonyManager.listen(PSL, PhoneStateListener.LISTEN_CALL_STATE);

        startPlayback();

        return START_STICKY;
    }

    private class NoisyAudioStreamReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
                player1.setPlayWhenReady(false);
            }
        }
    }

    private IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
    private NoisyAudioStreamReceiver myNoisyAudioStreamReceiver = new NoisyAudioStreamReceiver();

    private void startPlayback() {
        registerReceiver(myNoisyAudioStreamReceiver, intentFilter);
    }

    public void stopPlayback() {
        unregisterReceiver(myNoisyAudioStreamReceiver);
    }













    @Override
    public void onDestroy(){

        stopPlayback();
        if(PSL != null){
            telephonyManager.listen(PSL, PhoneStateListener.LISTEN_NONE);
        }

        playerNotificationManager.setPlayer(null);
        player1.release();
        player1 = null;
    }




}
