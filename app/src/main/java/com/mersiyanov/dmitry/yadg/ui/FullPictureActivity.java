package com.mersiyanov.dmitry.yadg.ui;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mersiyanov.dmitry.yadg.R;
import com.mersiyanov.dmitry.yadg.pojo.Item;

import java.util.Formatter;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class FullPictureActivity extends AppCompatActivity {

    private RealmList<Item> itemRealmList;
    private Realm realm;
    private Toolbar actionBarToolbar;
    private ViewPager viewPager;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_picture);
        realm = Realm.getDefaultInstance();
        itemRealmList = new RealmList<>();

        getPicturesFromDb();

        int position = getIntent().getIntExtra("pic_position", 0);
        initViewPager(position);
        initActionBar(position);

    }

    private void initViewPager(int position) {
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Formatter f = new Formatter();
                f.format("%d из %d", position + 1,  itemRealmList.size());
                getSupportActionBar().setTitle(f.toString());
            }

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @SuppressLint("RestrictedApi")
    private void initActionBar(int position) {

        actionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(actionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        Formatter f = new Formatter();
        f.format("%d из %d", position + 1,  itemRealmList.size());
        getSupportActionBar().setTitle(f.toString());

        actionBarToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

    }

    private void getPicturesFromDb() {
        RealmResults<Item> RealmResults = realm.where(Item.class).findAllAsync();
        itemRealmList.addAll(RealmResults);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FullPicFragment.newInstance(position, itemRealmList);
        }

        @Override
        public int getCount() {
            return itemRealmList.size();

        }
    }
}
