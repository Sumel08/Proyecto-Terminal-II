package com.lemus.oscar.voicehands;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lemus.oscar.voicehands.dictionary.VHDictionaryFragment;
import com.lemus.oscar.voicehands.text.VHTextFragment;
import com.lemus.oscar.voicehands.voice.RecognitionActivity;
import com.lemus.oscar.voicehands.voice.VHVoiceFragment;

import java.io.IOException;
import java.util.Locale;

public class VHMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    VHVoiceFragment.OnFragmentInteractionListener,
                    VHTextFragment.OnFragmentInteractionListener,
                    VHDictionaryFragment.OnFragmentInteractionListener{

    private TextToSpeech mTts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vhmain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((TextView)findViewById(R.id.voice_message)).setText(getResources().getText(R.string.recording));
                    ((ProgressBar)findViewById(R.id.progressBar)).setVisibility(View.VISIBLE);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((TextView)findViewById(R.id.voice_message)).setText(getResources().getText(R.string.pressRecord));
                    ((ProgressBar)findViewById(R.id.progressBar)).setVisibility(View.INVISIBLE);
                    startActivity(new Intent(VHMainActivity.this, RecognitionActivity.class));
                }
                return true;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /**
         * Text to Speech
         */

        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, 8);


        if (findViewById(R.id.mainFragment) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            VHVoiceFragment firstFragment = new VHVoiceFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainFragment, firstFragment).commit();
        }

        try {
            VHDataBaseHelper dataBaseHelper = new VHDataBaseHelper(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vhmain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_voice) {
            ((FloatingActionButton) findViewById(R.id.fab)).setVisibility(View.VISIBLE);
            VHVoiceFragment vhVoiceFragment = new VHVoiceFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainFragment, vhVoiceFragment);
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        } else if (id == R.id.nav_text) {
            ((FloatingActionButton) findViewById(R.id.fab)).setVisibility(View.INVISIBLE);
            VHTextFragment vhTextFragment = new VHTextFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainFragment, vhTextFragment);
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        } else if (id == R.id.nav_dictionary) {
            ((FloatingActionButton) findViewById(R.id.fab)).setVisibility(View.INVISIBLE);
            VHDictionaryFragment vhDictionaryFragment = new VHDictionaryFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainFragment, vhDictionaryFragment);
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        if (requestCode == 8) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                mTts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status == TextToSpeech.SUCCESS){
                            int result=mTts.setLanguage(new Locale("spa", "MEX"));
                            if(result==TextToSpeech.LANG_MISSING_DATA ||
                                    result==TextToSpeech.LANG_NOT_SUPPORTED){
                                Log.e("error", "This Language is not supported");
                            }
                            else{
                                ConvertTextToSpeech();
                            }
                        }
                        else
                            Log.e("error", "Initilization Failed!");
                    }
                });
            } else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(
                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }

    private void ConvertTextToSpeech() {
        // TODO Auto-generated method stub
        String text = "Hola mundo";
        if(text==null||"".equals(text))
        {
            text = "Content not available";
            mTts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }else
            mTts.speak(text+"is saved", TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
