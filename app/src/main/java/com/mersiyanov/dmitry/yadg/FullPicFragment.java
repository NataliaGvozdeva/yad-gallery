package com.mersiyanov.dmitry.yadg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class FullPicFragment extends Fragment {

    private ViewPager mViewPager;
    ImageView imageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blank, container, false);



        return v;
    }

    public static FullPicFragment newInstance(String pictureUrl) {

        FullPicFragment f = new FullPicFragment();
        Bundle b = new Bundle();
        b.putString("pictureUrl", pictureUrl);

        f.setArguments(b);

        return f;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mViewPager = view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());



    }

    class SamplePagerAdapter extends PagerAdapter {

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {

            return MainActivity.itemList.size();
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
         * same object as the {@link View} added to the {@link ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Item " + (position + 1);
        }

        /**
         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Inflate a new layout from our resources
            View view = getActivity().getLayoutInflater().inflate(R.layout.pager_item, container, false);
            // Add the newly created View to the ViewPager
            container.addView(view);

            // Retrieve a TextView from the inflated View, and update it's text

            imageView = view.findViewById(R.id.full_pic);
            Picasso.get().load(getArguments().getString("pictureUrl")).into(imageView);
            Picasso.get().load(MainActivity.itemList.get(position).getFile()).into(imageView);


            // Return the View
            return view;
        }



        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }

    }
}


