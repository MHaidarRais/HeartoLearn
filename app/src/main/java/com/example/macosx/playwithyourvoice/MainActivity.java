package com.example.macosx.playwithyourvoice;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech mTTS;
    private EditText mEditText;
    private ImageButton mbtnSpeak,btnGermany,btnFrance,btnItaly,btnEnglish;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbtnSpeak = findViewById(R.id.button_speak);
        btnGermany = findViewById(R.id.btnGermany);
        btnEnglish = findViewById(R.id.btnEnglish);
        btnFrance = findViewById(R.id.btnFrance);
        btnItaly = findViewById(R.id.btnItaly);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = mTTS.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS","Language Not Supported");
                    }else {
                        mbtnSpeak.setEnabled(true);
                    }
                }else {
                    Log.e("TTS","INITILIATIZATION FAILED");
                }
            }
        });

        btnGermany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTTS.setLanguage(Locale.GERMANY);
            }
        });
        btnItaly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mTTS.setLanguage(Locale.ITALY);
            }
        });
        btnFrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTTS.setLanguage(Locale.FRANCE);
            }
        });
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTTS.setLanguage(Locale.ENGLISH);
            }
        });


        mEditText = findViewById(R.id.edit_text);
        mSeekBarPitch = findViewById(R.id.seekbarpitch);
        mSeekBarSpeed = findViewById(R.id.seekbarspeed);


        mbtnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });

    }
    private void  speak(){
        String text = mEditText.getText().toString();
        float pitch  = (float)mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed  = (float)mSeekBarSpeed.getProgress() / 50;
        if (pitch < 0.1) speed= 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        mTTS.speak(text,TextToSpeech.QUEUE_FLUSH, null);


    }
    @Override
    protected void onDestroy() {
        if (mTTS != null){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }


}
