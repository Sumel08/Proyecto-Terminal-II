package com.lemus.vhrecorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        Bundle extras = new Bundle();
        extras.putString("name", ((TextView) findViewById(R.id.etName)).getText().toString());
    }

    public void beginRecord(View view) {
        String gender = "Man";
        String pcm = "8";
        int fs = 8000;
        Bundle extras = new Bundle();
        extras.putString("name", ((TextView) findViewById(R.id.etName)).getText().toString());
        extras.putString("environment", ((TextView) findViewById(R.id.etEnvironment)).getText().toString());

        if (((RadioButton)findViewById(R.id.rbWoman)).isChecked())
            gender = "Woman";
        if (((RadioButton)findViewById(R.id.rb16pcm)).isChecked())
            pcm = "16";
        if (((RadioButton)findViewById(R.id.rb11025)).isChecked())
            fs = 11025;
        if (((RadioButton)findViewById(R.id.rb22050)).isChecked())
            fs = 22050;
        if (((RadioButton)findViewById(R.id.rb44100)).isChecked())
            fs = 44100;

        extras.putString("gender", gender);

        extras.putString("pcm", pcm);
        extras.putInt("fs", fs);

        Intent intent = new Intent(DetailsActivity.this, RecordActivity.class);
        intent.putExtras(extras);
        startActivity(intent);

    }
}
