package com.example.voicechat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ImageView openmic;
    TextToSpeech speech;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openmic=findViewById(R.id.btnopenmic);
        tv=findViewById(R.id.textView);

        speech=new TextToSpeech(getBaseContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
               if(i!= TextToSpeech.ERROR){
                   speech.setLanguage(Locale.getDefault());
                   //speech.speak("Hello There Welcome Back",TextToSpeech.QUEUE_FLUSH,null);

               }
            }
        });
        openmic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voiceautomation();
            }
        });
        //to call voice command

    }

    private void voiceautomation() {
        Intent voice=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());
        voice.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak Now Hello....");
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
            }else if(arrayList.get(0).toString().equals("hello")||arrayList.get(0).toString().equals("hey")){

                speech.speak("Hello There Welcome Back",TextToSpeech.QUEUE_FLUSH,null);
                tv.setText("Hello There Welcome Back");
            }else if(arrayList.get(0).toString().equals("how are you?")||arrayList.get(0).toString().equals("how are you")){

                speech.speak("I am Good what about you?",TextToSpeech.QUEUE_FLUSH,null);
                tv.setText("I am Good What about you?");
            }else if(arrayList.get(0).toString().equals("what can you do for me")||arrayList.get(0).toString().equals("What can you do for me")){

                speech.speak("I can open camera for you",TextToSpeech.QUEUE_FLUSH,null);
                tv.setText("I can open camera for you");
            }
        }
    }
}