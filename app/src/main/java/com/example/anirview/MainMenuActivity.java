package com.example.anirview;

import static android.provider.Telephony.Mms.Part.FILENAME;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    private static MainMenuActivity instance;
    private LinearLayout userReviewsLayout;
    private static final String FILENAME = "reviews.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        instance=this;
        userReviewsLayout = findViewById(R.id.userReviewsLayout);
        loadReviews();
        // Button to go back to main activity
        ImageButton icon3 = findViewById(R.id.icon3);
        icon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MainActivity on click
                Intent intent = new Intent(MainMenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Button to go to WatchActivity
        ImageButton icon1 = findViewById(R.id.icon1);
        icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the WatchActivity on click
                Intent intent = new Intent(MainMenuActivity.this, WatchActivity.class);
                startActivity(intent);
            }
        });


        ImageButton iconCenter = findViewById(R.id.iconCenter);
        iconCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the WatchActivity on click
                Intent intent = new Intent(MainMenuActivity.this, AddReviewActivity.class);
                startActivity(intent);
            }
        });


        // Glide
        ImageView imageView = findViewById(R.id.reviewImage1);
        String imageUrl = "https://static.wikia.nocookie.net/wikiseriesjaponesas/images/f/fd/Tensei_shitara_Slime_Datta_Ken_%28Anime%29.jpg/revision/latest?cb=20181207131602&path-prefix=es";
        Glide.with(this)
                .load(imageUrl)
                .into(imageView);

        ImageView imageView2 = findViewById(R.id.reviewImage2);
        String imageUrl2 = "https://static.wikia.nocookie.net/typemoon/images/a/a1/Apocrypha_NL3.png/revision/latest/scale-to-width-down/250?cb=20180120020655&path-prefix=es";
        Glide.with(this)
                .load(imageUrl2)
                .into(imageView2);

        ImageView imageView3 = findViewById(R.id.reviewImage3);
        String imageUrl3 = "https://imgsrv.crunchyroll.com/cdn-cgi/image/fit=contain,format=auto,quality=85,width=480,height=720/catalog/crunchyroll/6533e54a54f7a69c806920607bc8238e.jpg";
        Glide.with(this)
                .load(imageUrl3)
                .into(imageView3);

        ImageView imageView4 = findViewById(R.id.userReviewImage1);
        String imageUrl4 = "https://imgsrv.crunchyroll.com/cdn-cgi/image/fit=contain,format=auto,quality=85,width=480,height=720/catalog/crunchyroll/e4e80c83c792d81c138e320874dbdffc.jpe";
        Glide.with(this)
                .load(imageUrl4)
                .into(imageView4);

        ImageView imageView5 = findViewById(R.id.userReviewImage2);
        String imageUrl5 = "https://www.justwatch.com/images/poster/112066193/s718/neon-genesis-evangelion.jpg";
        Glide.with(this)
                .load(imageUrl5)
                .into(imageView5);

        ImageView imageView6 = findViewById(R.id.userReviewImage3);
        String imageUrl6 = "https://upload.wikimedia.org/wikipedia/en/f/f0/Girls_und_Panzer_TV_key_visual.png";
        Glide.with(this)
                .load(imageUrl6)
                .into(imageView6);

        // Set up click listeners for each anime
        imageView.setOnClickListener(v -> {
            launchReviewActivity(
                    R.string.rSlime,
                    R.string.sSlime,
                    R.string.RSlime,
                    "8",
                    imageUrl
            );
        });

        imageView2.setOnClickListener(v -> {
            launchReviewActivity(
                    R.string.rFate,
                    R.string.sFate,
                    R.string.RFate,
                    "6",
                    imageUrl2
            );
        });

        imageView3.setOnClickListener(v -> {
            launchReviewActivity(
                    R.string.rNokotan,
                    R.string.sNokotan,
                    R.string.RNokotan,
                    "9",
                    imageUrl3
            );
        });

        imageView4.setOnClickListener(v -> {
            launchReviewActivity(
                    R.string.rJJK,
                    R.string.sJJK,
                    R.string.RJJK,
                    "8",
                    imageUrl4
            );
        });

        imageView5.setOnClickListener(v -> {
            launchReviewActivity(
                    R.string.rEva,
                    R.string.sNeon,
                    R.string.RNeon,
                    "9",
                    imageUrl5
            );
        });

        imageView6.setOnClickListener(v -> {
            launchReviewActivity(
                    R.string.rPanzer,
                    R.string.sPanzer,
                    R.string.RPanzer,
                    "5",
                    imageUrl6
            );
        });
    }


    private void loadReviews() {
        ArrayList<AddReviewActivity.Review> reviews = loadReviewsFromJSON();

        for (AddReviewActivity.Review review : reviews) {



            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    (int) getResources().getDimension(R.dimen.image_width),
                    (int) getResources().getDimension(R.dimen.image_height)
            );
            layoutParams.setMargins(0, 0, 8, 0); // Ensure consistent margin like static images
            imageView.setLayoutParams(layoutParams);
            Glide.with(this).load(review.getPosterUrl()).into(imageView);
            userReviewsLayout.addView(imageView);

            imageView.setOnClickListener(v -> {
                launchReviewActivity(
                        review.getTitle(),
                        review.getSubtitle(),
                        review.getReview(),
                        review.getScore(),
                        review.getPosterUrl()
                );
            });
        }
    }



    private ArrayList<AddReviewActivity.Review> loadReviewsFromJSON() {
        ArrayList<AddReviewActivity.Review> reviews = new ArrayList<>();

        try (FileInputStream fis = openFileInput(FILENAME)) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String json = new String(buffer);

            Gson gson = new Gson();
            Type reviewListType = new TypeToken<ArrayList<AddReviewActivity.Review>>(){}.getType();
            reviews = gson.fromJson(json, reviewListType);
        } catch (IOException e) {
            // No file found, return an empty list
            e.printStackTrace();
        }

        return reviews;
    }

    private void launchReviewActivity(String title, String subtitle, String reviewText, String rating, String imageUrl) {
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra("IS_JSON_REVIEW", true);
        intent.putExtra("TITLE", title);
        intent.putExtra("SUBTITLE", subtitle);
        intent.putExtra("REVIEW_TEXT", reviewText);
        intent.putExtra("RATING", rating);
        intent.putExtra("IMAGE_URL", imageUrl);
        startActivity(intent);
    }

    private void launchReviewActivity(int titleResId, int subtitleResId, int reviewResId, String rating, String imageUrl) {
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra("IS_JSON_REVIEW", false);
        intent.putExtra("TITLE_RES_ID", titleResId);
        intent.putExtra("SUBTITLE_RES_ID", subtitleResId);
        intent.putExtra("REVIEW_RES_ID", reviewResId);
        intent.putExtra("RATING", rating);
        intent.putExtra("IMAGE_URL", imageUrl);
        startActivity(intent);
    }

    public static void closeMainMenu() {
        if (instance != null) {
            instance.finish();
        }
    }



    }

