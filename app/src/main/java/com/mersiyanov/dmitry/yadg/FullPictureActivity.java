package com.mersiyanov.dmitry.yadg;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class FullPictureActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_picture);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FullPicFragment fragment = new FullPicFragment();
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();

//        Intent intent = getIntent();
//        String imageUrl = intent.getStringExtra("pic_url");
//
//        imageView = findViewById(R.id.full_pic);
//
//        Picasso.get().load(imageUrl).into(imageView);
//
//        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
//        setSupportActionBar(mActionBarToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Test");

    }
}
