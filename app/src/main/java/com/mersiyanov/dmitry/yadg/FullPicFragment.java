package com.mersiyanov.dmitry.yadg;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class FullPicFragment extends Fragment {

    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_pic, container, false);
        imageView = view.findViewById(R.id.full_pic);

        Toolbar mActionBarToolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mActionBarToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Test");
        mActionBarToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        return view;
    }

    public static FullPicFragment newInstance(int position, String imageUrl) {

        FullPicFragment f = new FullPicFragment();
        Bundle b = new Bundle();
        b.putString("pictureUrl", imageUrl);
        b.putInt("pos", position);
        f.setArguments(b);

        return f;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Picasso.get().load(MainActivity.itemList.get(getArguments().getInt("pos")).getFile()).into(imageView);

    }
}


