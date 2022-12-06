package com.newgbaxl.blastmaze;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.fragment.app.Fragment;

import com.newgbaxl.blastmaze.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    Bundle state;
    ProgressDialog progressDialog;
    MediaPlayer mediaPlayer;
    boolean hasPlayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        state = savedInstanceState;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());

        GlobalVars.globalMoney = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getInt("CurrencyAmnt",0);

        mediaPlayer = new MediaPlayer();
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpSelect(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onDestroy() {
        PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putInt("currencyAmnt",GlobalVars.globalMoney).apply();
        super.onDestroy();
    }

    /*public void startGameActivity(){
        AndroidLauncher app = (AndroidLauncher) getApplication();
        app.startGame(state);

    }*/

    public void helpSelect(View view) {
        if (!mediaPlayer.isPlaying()) {
            String serverURL = "https://cdns-preview-7.dzcdn.net/stream/c-711c687d62e81ac98900ee80b08dd023-2.mp3";
            new GetServerData().execute("http://www.virginmegastore.me/Library/Music/CD_001214/Tracks/Track1.mp3");
        } else {
            mediaPlayer.stop();
        }
    }


    class GetServerData extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Fetching data");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                String url = "https://p.scdn.co/mp3-preview/5580849e2f5390bfac8ec79236b5806db127b625?cid=f6a40776580943a7bc5173125a1e8832"; // your URL here
                //Uri uri = Uri.parse("spotify:track:4WNcduiCmDNfmTEz7JvmLv");
                mediaPlayer.setAudioAttributes(
                        new AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                );
                if(!hasPlayed) {
                    hasPlayed = true;
                    mediaPlayer.setDataSource(url);
                }
                mediaPlayer.prepare(); // might take long! (for buffering, etc)
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mediaPlayer;
        };

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            // Dismiss the progress dialog
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }


}