package com.mersiyanov.dmitry.yadg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.mersiyanov.dmitry.yadg.pojo.ResponseFileList;

public class FullPictureActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 5;

    private PagerAdapter mPagerAdapter;
    private ViewPager mPager;
    private String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_picture);


//
//        mPager = findViewById(R.id.pager);
//        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
//        mPager.setAdapter(mPagerAdapter);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("pic_url");
        int imagePosition = intent.getIntExtra("pic_position", 0);
        ResponseFileList.Item item = intent.getParcelableExtra("image");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FullPicFragment fragment = FullPicFragment.newInstance(imageUrl, item, imagePosition);
        transaction.addToBackStack(null);
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();




    }



    //    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
//        public ScreenSlidePagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            Intent intent = getIntent();
//            imageUrl = intent.getStringExtra("pic_url");
//            int pic_position = intent.getIntExtra("pic_position", 0);
//            Toast.makeText(getApplicationContext(), "Позиция адаптера = " + position, Toast.LENGTH_SHORT).show();
//            return FullPicFragment.newInstance(pic_position, imageUrl);
//        }
//
//        @Override
//        public int getCount() {
//            return MainActivity.itemList.size();
//        }
//    }
}
