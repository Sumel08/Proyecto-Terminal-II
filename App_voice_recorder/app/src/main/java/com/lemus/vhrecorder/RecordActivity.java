package com.lemus.vhrecorder;

import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class RecordActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private ProgressBar pbRecord;
    private TextView word2say;
    private ExtAudioRecorder extAudioRecorder;
    private int word;
    private String name, gender, environment, pcm;
    private int fs;
    private String[] words = {"Hola", "Buenos", "Buenas", "Días", "Tardes", "Noches", "A", "Qué", "Hora", "Abren", "Cierran", "Acepta", "Tarjeta",
            "De", "Información", "Estoy", "Buscando", "Cuánto", "Cuesta", "Me", "Llevo", "Esto", "Disculpe", "Dónde", "Está", "La", "Oficina",
            "Hospital", "Biblioteca", "Estación", "Autobuses", "Trenes", "Policía", "Hay", "Algún", "Cerca", "Cajero", "Banco", "Supermercado",
            "Farmacia", "Metro", "Crédito"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        name = getIntent().getExtras().getString("name");
        environment = getIntent().getExtras().getString("environment");
        gender = getIntent().getExtras().getString("gender");

        pcm = getIntent().getExtras().getString("pcm");
        fs = getIntent().getExtras().getInt("fs");

        ((TextView)findViewById(R.id.tvNameR)).setText(name);
        ((TextView)findViewById(R.id.tvEnvironmentR)).setText(environment);
        ((TextView)findViewById(R.id.tvGenderR)).setText(gender);

        ((TextView)findViewById(R.id.tvPCMR)).setText(getIntent().getExtras().getString("pcm"));
        ((TextView)findViewById(R.id.tvFSR)).setText(String.valueOf(getIntent().getExtras().getInt("fs")));

        word2say = (TextView) findViewById(R.id.tvWordRecord);
        pbRecord = (ProgressBar) findViewById(R.id.pbRecording);
        pbRecord.setVisibility(View.INVISIBLE);

        fab = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        word = 0;

        word2say.setText(words[word]);

        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        extAudioRecorder = ExtAudioRecorder.getInstance(false, getIntent().getExtras().getInt("fs"), getIntent().getExtras().getString("pcm"));
                        extAudioRecorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getWord() + "_" + pcm + "_" + fs +"_" + name + "_" + environment + "_" + gender + ".wav");
                        extAudioRecorder.prepare();
                        extAudioRecorder.start();
                        pbRecord.setVisibility(View.VISIBLE);
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        extAudioRecorder.stop();
                        pbRecord.setVisibility(View.INVISIBLE);
                        nextWord();
                        return true; // if you want to handle the touch event

                }
                return false;
            }
        });}

        private void nextWord() {

            word++;
            if (word == words.length)
                Toast.makeText(RecordActivity.this, "FIN", Toast.LENGTH_SHORT).show();
            else
                word2say.setText(words[word]);
        }

        private String getWord() {
            return words[word];
        }
}
