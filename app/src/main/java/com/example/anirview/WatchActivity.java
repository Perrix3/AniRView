package com.example.anirview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WatchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);




        // Button to go back to the main menu activity
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MainMenuActivity on click
                Intent intent = new Intent(WatchActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });

        TextView crunchy=findViewById(R.id.crunchyrollLink);
        crunchy.setOnClickListener(v->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.crunchyroll.com/"));
            startActivity(browserIntent);
        });

        TextView netflix=findViewById(R.id.netflixLink);
        netflix.setOnClickListener(v->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.netflix.com/"));
            startActivity(browserIntent);
        });

        TextView amazon=findViewById(R.id.amazonPrimeLink);
        amazon.setOnClickListener(v->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.disneyplus.com/"));
            startActivity(browserIntent);
        });

        TextView disney=findViewById(R.id.disneyPlusLink);
        disney.setOnClickListener(v->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.com/gp/video/storefront/"));
            startActivity(browserIntent);
        });

    }
}
