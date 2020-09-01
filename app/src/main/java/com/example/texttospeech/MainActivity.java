package com.example.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech textToSpeech;
    EditText editText;
    Button button;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.tts_editText);
        button = (Button) findViewById(R.id.btn_tts);
        aSwitch = (Switch) findViewById(R.id.switch_tts);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.getDefault());
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language Not Supported");
                    } else {
                        button.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization Failed");
                }
            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SpeechToText.class);
                startActivity(i);
                finish();
            }
        });

    }
        private void speak() {
            String text = editText.getText().toString();
            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null);
        }

        @Override
        protected void onDestroy() {
            if(textToSpeech != null)
            {
                textToSpeech.stop();
                textToSpeech.shutdown();
            }
            super.onDestroy();
        }




}