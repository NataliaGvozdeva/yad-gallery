package com.mersiyanov.dmitry.yadg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class FullPictureActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 5;

    private PagerAdapter mPagerAdapter;
    private ViewPager mPager;
    private String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_picture);

        Intent intent = getIntent();
        imageUrl = intent.getStringExtra("pic_url");

        mPager = findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FullPicFragment.newInstance(position, imageUrl);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
