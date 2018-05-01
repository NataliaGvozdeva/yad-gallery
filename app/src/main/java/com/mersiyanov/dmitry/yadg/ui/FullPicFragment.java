package com.mersiyanov.dmitry.yadg.ui;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mersiyanov.dmitry.yadg.R;
import com.mersiyanov.dmitry.yadg.mvp.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.Formatter;

public class FullPicFragment extends Fragment {

    private ImageView imageView;
    private Toolbar mActionBarToolbar;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_full_pic, container, false);
    }

    public static FullPicFragment newInstance(int position) {
        FullPicFragment fragment = new FullPicFragment();
        Bundle b = new Bundle();
        b.putInt("position", position);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewPager = view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());
        mViewPager.setCurrentItem(getArguments().getInt("position"));
    }

    class SamplePagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.pager_item, container, false);

            imageView = view.findViewById(R.id.full_pic);
            mActionBarToolbar = view.findViewById(R.id.toolbar);

            Picasso.get().load(MainActivity.itemList.get(position).getFile()).into(imageView);
            initActionBar(position);

            container.addView(view);
            return view;
        }

        private void initActionBar(int position) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(mActionBarToolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            Formatter f = new Formatter();
            f.format("%d из %d", position + 1,  MainActivity.itemList.size());

            activity.getSupportActionBar().setTitle(f.toString());
            mActionBarToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        }

        @Override
        public int getCount() {
            return MainActivity.itemList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
    }
}


