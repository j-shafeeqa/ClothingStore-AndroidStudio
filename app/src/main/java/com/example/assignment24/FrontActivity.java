package com.example.assignment24;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class FrontActivity extends AppCompatActivity {


        private VideoView videoView;

        //  Video URL
        String videoUrl = "https://firebasestorage.googleapis.com/v0/b/assignment-2-23128.appspot.com/o/FrontUI%20final.mp4?alt=media&token=2af2206a-b71f-4607-b6d9-3977fc4e0dff";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_front);



            // on below line we are initializing our variables.
            videoView = findViewById(R.id.idVideoView);

            // Uri object to refer the
            // resource from the videoUrl
            Uri uri = Uri.parse(videoUrl);

            // sets the resource from the
            // videoUrl to the videoView
            videoView.setVideoURI(uri);

            // creating object of
            // media controller class
            MediaController mediaController = new MediaController(this);

            // sets the anchor view
            // anchor view for the videoView
            mediaController.setAnchorView(videoView);

            // sets the media player to the videoView
            mediaController.setMediaPlayer(videoView);

            // sets the media controller to the videoView
            videoView.setMediaController(mediaController);

            // starts the video
            videoView.start();

            // Find and set click listener for the TextView
            TextView nextTextView = findViewById(R.id.textViewNext);
            nextTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create an Intent to start MainActivity
                    Intent intent = new Intent(FrontActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });


        }
}