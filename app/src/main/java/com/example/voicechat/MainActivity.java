package com.example.voicechat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //to call voice command
        voiceautomation();
    }

    private void voiceautomation() {
        Intent voice=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());
        voice.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak Now Open Camera....");
        startActivityForResult(voice, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data !=null){
            ArrayList<String> arrayList=data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(arrayList.get(0).toString().equals("open camera")){
                Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(camera);
            }
        }
    }
}