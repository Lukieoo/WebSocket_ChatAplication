package com.anioncode.websocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Random;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;


public class MainActivity extends AppCompatActivity {

    ImageView JoinToChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        pulsator.start();

        Random generator = new Random();

        JoinToChat=(ImageView)findViewById(R.id.join);

        JoinToChat.setOnClickListener(v->{
            Intent intent=new Intent(MainActivity.this,ChatActivity.class);
            intent.putExtra("Name","Anonymous-"+generator.nextInt(9999));
            startActivity(intent);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            finish();
            pulsator.stop();
        });


    }

}