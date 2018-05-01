package com.mersiyanov.dmitry.yadg.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mersiyanov.dmitry.yadg.R;

public class FullPictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_picture);

        Intent intent = getIntent();
        int position = intent.getIntExtra("pic_position", 0);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FullPicFragment fragment = FullPicFragment.newInstance(position);
        transaction.addToBackStack(null);
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();

    }
}
