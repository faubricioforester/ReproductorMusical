package com.example.faubricio.reproductormusical;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    String lyrics = getFile("thechain");
    MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.TheChain);
    TextView lyricsTV = findViewById(R.id.textViewlyrics);
    TextView nameSong = findViewById(R.id.songtextView);
    boolean playing = false;

    public void playClicked(View view){
        Button playpauseButton = findViewById(R.id.playpausebutton);
        if (mediaPlayer.isPlaying()){
            playpauseButton.setBackgroundResource(android.R.drawable.ic_media_pause);
            mediaPlayer.pause();
            lyricsTV.getAnimation().cancel();
            lyricsTV.clearAnimation();
        } else{
            playpauseButton.setBackgroundResource(android.R.drawable.ic_media_play);
            mediaPlayer.start();
            lyricsTV.animate().translationYBy(-4000f).setDuration((mediaPlayer.getDuration()-mediaPlayer.getCurrentPosition())*1000);
        }





    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameSong.setText(mediaPlayer.toString());
        lyricsTV.setText(lyrics);

        SeekBar seekProg = findViewById(R.id.seekBarProgre);
        seekProg.setMax(mediaPlayer.getDuration());
        seekProg.setProgress(0);
        seekProg.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                                @Override
                                                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                                    if(mediaPlayer != null && b){
                                                        mediaPlayer.seekTo( i * 1000);
                                                    }
                                                }

                                                @Override
                                                public void onStartTrackingTouch(SeekBar seekBar) {

                                                }

                                                @Override
                                                public void onStopTrackingTouch(SeekBar seekBar) {

                                                }
                                            }

        );


    }

    private String getFile(String fileName) {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }

}
