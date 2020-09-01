package com.example.texttospeech;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class SpeechToText extends AppCompatActivity {
    TextView textViewTTs;
    ImageView imageView;
    Switch aSwitch_stt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        textViewTTs = (TextView) findViewById(R.id.stt_Text);
        imageView = (ImageView) findViewById(R.id.voice);
        aSwitch_stt = (Switch) findViewById(R.id.switch_stt);

        aSwitch_stt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SpeechToText.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpeechInput();
            }
        });

    }

    public void getSpeechInput() {
        Intent u = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        u.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        u.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        u.putExtra(RecognizerIntent.EXTRA_PROMPT,"speak something");

        try {
            startActivityForResult(u,20);
        }
        catch (ActivityNotFoundException e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case 20: {
                if(resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    textViewTTs.setText(result.get(0));
                }
                break;
            }
        }
    }
}
