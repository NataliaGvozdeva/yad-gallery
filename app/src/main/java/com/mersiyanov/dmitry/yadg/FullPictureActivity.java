package com.mersiyanov.dmitry.yadg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class FullPictureActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_picture);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("pic_url");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FullPicFragment fragment = FullPicFragment.newInstance(imageUrl);
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();



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
