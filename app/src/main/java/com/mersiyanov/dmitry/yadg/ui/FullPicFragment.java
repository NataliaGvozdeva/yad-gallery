package com.mersiyanov.dmitry.yadg.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mersiyanov.dmitry.yadg.R;
import com.mersiyanov.dmitry.yadg.YadApplication;
import com.mersiyanov.dmitry.yadg.pojo.Item;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.picasso.Picasso;

import io.realm.RealmList;

public class FullPicFragment extends Fragment {

    private RealmList<Item> itemRealmList;
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_pic, container, false);
        imageView = view.findViewById(R.id.full_pic);
        Picasso.get().load(itemRealmList.get(getArguments().getInt("position")).getFile()).into(imageView);
        return view;

    }

    public static FullPicFragment newInstance(int position, RealmList<Item> itemList) {
        FullPicFragment fragment = new FullPicFragment();
        fragment.itemRealmList = itemList;
        Bundle b = new Bundle();
        b.putInt("position", position);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = YadApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}


