package org.atsevdev.radioromania;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import org.atsevdev.radioromania.MyServices.*;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //initialising variables
    public final String DigiFm = "http://edge76.rdsnet.ro:84/digifm/digifm.mp3";
    public final String radio_zu = "http://live.romanticfm.ro:9123/radiozu.mp3";
    public final String Radio_popular = "http://mp3.radiopopular.ro:7777/;stream.mp3";
    public final String Eur_fm = "http://89.37.58.103:8000/europafm_mp3_64k";
    public final String radio_rom_acc = "http://stream2.srr.ro:8000/;stream/1";
    public final String PROFMurl = "http://edge126.rdsnet.ro:84/profm/profm.mp3";
    public final String radiobuchuresti = "http://89.238.227.6:8032/\n" +
            "Title1=Bucuresti FM\n" +
            "Length1=-1\n" +
            "Version=2";
    public final String impactfm = "http://89.39.189.159:8000/";
    public final String olteniaCraiovafm = "http://stream2.srr.ro:8370/;stream/1";
    public final String radiocluj = "http://89.238.227.6:8384/;stream/1";
    public final String vibefm = "http://89.37.58.102:8000/vibefm_mp3_64k";
    public final String smartfm = "http://live.smartfm.ro:9128/live";
    public final String virginradio = "http://astreaming.virginradio.ro:8000/virgin_mp3_64k";
    public final String romaniacultural = "http://stream2.srr.ro:8012/;stream/1";
    public final String romaniaresita = "http://stream2.srr.ro:8012/;stream/1";
    public final String radioromaniamuscial = "http://stream2.srr.ro:8022/;stream/1";
    public final String radiogaga = "http://rc.radiogaga.ro:8000/live";
    public final String radiorenasterea = "http://radiorenasterea.ro:8000/live";
    public final String dancefm = "http://stream.profm.ro:8032/dancefm.mp3";
    public final String radiotimisoara = "http://89.238.227.6:8350/;stream/1";
    public final String radioantentasatelor = "http://stream2.srr.ro:8042/;stream/1";
    public final String radioconstanta = "http://89.238.227.6:8332/;stream/1";
    public final String taraf = "http://192.99.98.244:8181/taraf";
    public final String radiointens = "http://51.254.103.133:8070/stream";
    public final String megahit = "http://188.165.162.208:8080/stream";
    //setting up exoplayer
    private PlayerView playerView;
    public SimpleExoPlayer player;
    //setting up binding service
    MyServices myServices;
    boolean mbound;

    ServiceConnection mconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mbound = true;
            localBinder binder = (localBinder) service;
            myServices = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mbound = false;
            myServices.unbindService(mconnection);

        }
    };

    //loading add
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent playService =  new Intent(this, MyServices.class);
        Util.startForegroundService(this, playService);
        playerView=findViewById(R.id.playerview);//playview id xml into private variable
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        MobileAds.initialize(this, "ca-app-pub-5272936325413669~3936405070");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        
    }

    @Override
    protected void onStart(){
         super.onStart();

        Intent i = new Intent(this, MyServices.class);
        bindService(i, mconnection,BIND_AUTO_CREATE);


        player = ExoPlayerFactory.newSimpleInstance(this,
                new DefaultTrackSelector());//instantiating new player instances
        playerView.setPlayer(player);//xml player to view

        //creating datasource
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "RadioRomania"));

        //extracting stream url
        final ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(PROFMurl));
        final ExtractorMediaSource mediaSource0 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(radiobuchuresti));
        final ExtractorMediaSource mediaSource1 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(impactfm));
        final ExtractorMediaSource mediaSource2 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(radio_rom_acc));
        final ExtractorMediaSource mediaSource3 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(Eur_fm));
        final ExtractorMediaSource mediaSource4 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(Radio_popular));
        final ExtractorMediaSource mediaSource5 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(radio_zu));
        final ExtractorMediaSource mediaSource6 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(DigiFm));
        final ExtractorMediaSource mediasource7 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(olteniaCraiovafm));
        final ExtractorMediaSource md8 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(radiocluj));
        final ExtractorMediaSource md9 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(vibefm));
        final ExtractorMediaSource md10 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(smartfm));
        final ExtractorMediaSource md11 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(virginradio));
        final ExtractorMediaSource md12 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(radioantentasatelor));
        final ExtractorMediaSource md13 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(romaniacultural));
        final ExtractorMediaSource md14 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(romaniaresita));
        final ExtractorMediaSource md15 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(radioromaniamuscial));
        final ExtractorMediaSource md16 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(radiogaga));
        final ExtractorMediaSource md18 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(radiorenasterea));
        final ExtractorMediaSource md19 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(dancefm));
        final ExtractorMediaSource md20 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(radiotimisoara));
        final ExtractorMediaSource md21 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(radioconstanta));
        final ExtractorMediaSource md22 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(taraf));
        final ExtractorMediaSource md23 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(radiointens));
        final ExtractorMediaSource md24 = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(megahit));





        //can be changed with a switch statement
       // instantiating buttons
        Button stop = (Button) findViewById(R.id.stopstream);
        Button bt = (Button) findViewById(R.id.profm);
        Button bt0 = (Button) findViewById(R.id.rad_buc);
        Button bt1 = (Button) findViewById(R.id.impactfm);
        Button bt2 = (Button) findViewById(R.id.radio_ro_acc);
        Button bt3 = (Button) findViewById(R.id.europafm);
        Button bt4 = (Button) findViewById(R.id.radpopular);
        Button bt5 = (Button) findViewById(R.id.Radiozuu);
        Button bt6 = (Button) findViewById(R.id.DigiFm);
        Button bt7 = (Button) findViewById(R.id.olteniacraiova);
        Button bt8 = (Button) findViewById(R.id.radiocluj);
        Button bt9 = (Button) findViewById(R.id.vibefm);
        Button bt10 = (Button) findViewById(R.id.smartfm);
        Button bt11 = (Button) findViewById(R.id.virginradio);
        Button bt12 = (Button) findViewById(R.id.radioantentasatelor);
        Button bt13 = (Button) findViewById(R.id.romaniacultural);
        Button bt14 = (Button) findViewById(R.id.romaniaresita);
        Button bt15 = (Button) findViewById(R.id.radioromaniamusical);
        Button bt16 = (Button) findViewById(R.id.radiogaga);
        Button bt18 = (Button) findViewById(R.id.radiorenasterea);
        Button bt19 = (Button) findViewById(R.id.dancefm);
        Button bt20 = (Button) findViewById(R.id.radiotimisoara);
        Button bt21 = (Button) findViewById(R.id.radioconstanta);
        Button bt22 = (Button) findViewById(R.id.taraf);
        Button bt23 = (Button) findViewById(R.id.radiointens);
        Button bt24 = (Button) findViewById(R.id.megahit);

        //for now playing text view
        final TextView tv = (TextView) findViewById(R.id.npl);


        //listener buttons
        //using onClick methods to choose radio station
        bt.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) { //ini profm

               SimpleExoPlayer player1;
               player1 = myServices.player1;
               player1.prepare(mediaSource);
               player1.setPlayWhenReady(true);
               tv.setText(R.string.pl);


           }

        });
        bt0.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                    SimpleExoPlayer player1;
                                       player1 = myServices.player1;
                                    player1.prepare(mediaSource0);
                                    player1.setPlayWhenReady(true);
                                       tv.setText(R.string.pl0);



                                   }
                               });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v){ //ini radio ro acc
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(mediaSource1);
                player1.setPlayWhenReady(true);
                tv.setText(R.string.pl1);



            }
        });
        bt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ //ini radio ro acc
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(mediaSource2);
                player1.setPlayWhenReady(true);
                tv.setText(R.string.pl2);




            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //ini profm
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(mediaSource3);
                player1.setPlayWhenReady(true);
                tv.setText(R.string.pl3);



            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(mediaSource4);
                player1.setPlayWhenReady(true);
                tv.setText(R.string.pl4);


            }
        });
        bt5.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(mediaSource5);
                player1.setPlayWhenReady(true);
                tv.setText(R.string.pl5);



            }
        });
        bt6.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(mediaSource6);
                player1.setPlayWhenReady(true);
                tv.setText(R.string.pl6);


            }
        });
        bt7.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(mediasource7);
                player1.setPlayWhenReady(true);
                tv.setText(R.string.rad_olt_cr);


            }
        });
        bt8.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(md8);
                player1.setPlayWhenReady(true);
                tv.setText(R.string.pl8);

            }
        });
        bt9.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(md9);
                player1.setPlayWhenReady(true);
                tv.setText(R.string.vibe_fm);


            }
        });
        bt10.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(md10);
                player1.setPlayWhenReady(true);
                tv.setText(R.string.smfm);


            }
        });
        bt11.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(md11);
                player1.setPlayWhenReady(true);
                tv.setText(R.string.vr);


            }
        });
        bt12.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(md12);
                player1.setPlayWhenReady(true);
                tv.setText(R.string.ras);

            }
        });
        bt13.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(md13);
                player1.setPlayWhenReady(true);
                tv.setText(getString(R.string.rrc));

            }
        });
        bt14.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(md14);
                player1.setPlayWhenReady(true);
                tv.setText(getString(R.string.resita));


            }
        });
        bt15.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(md15);
                player1.setPlayWhenReady(true);
                tv.setText(getString(R.string.rrm));


            }
        });
        bt16.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(md16);
                player1.setPlayWhenReady(true);
                tv.setText(getString(R.string.radgg));

            }
        });

        bt18.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(md18);
                player1.setPlayWhenReady(true);
                tv.setText(R.string.rrnstra);


            }
        });
        bt19.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(md19);
                player1.setPlayWhenReady(true);
                tv.setText(R.string.danfm);


            }
        });
        bt20.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(md20);
                player1.setPlayWhenReady(true);
                tv.setText(R.string.rtm);


            }
        });
        bt21.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(md21);
                player1.setPlayWhenReady(true);
                tv.setText(R.string.rconsta);



            }
        });
        bt22.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(md22);
                player1.setPlayWhenReady(true);
                tv.setText(getString(R.string.pl22));



            }
        });
        bt23.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(md23);
                player1.setPlayWhenReady(true);
                tv.setText(getString(R.string.pl23));



            }
        });
        bt24.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.prepare(md24);
                player1.setPlayWhenReady(true);
                tv.setText(getString(R.string.pl24));



            }
        });

        stop.setOnClickListener(new View.OnClickListener() {//ini radio popular
            @Override
            public void onClick(View v) {
                SimpleExoPlayer player1;
                player1 = myServices.player1;
                player1.setPlayWhenReady(false);
                tv.setText(R.string.nowplaying);



            }
        });



    }



    @Override
    protected void onStop(){
        super.onStop();
        unbindService(mconnection);


        playerView.setPlayer(null);
        player.release();
        player = null;


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent stopI = new Intent(this, MyServices.class);
        stopService(stopI);
    }
}
