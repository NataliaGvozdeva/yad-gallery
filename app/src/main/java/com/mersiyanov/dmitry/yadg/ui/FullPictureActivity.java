package com.mersiyanov.dmitry.yadg.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mersiyanov.dmitry.yadg.R;
import com.mersiyanov.dmitry.yadg.pojo.Item;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class FullPictureActivity extends AppCompatActivity {

    private RealmList<Item> itemRealmList;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_picture);
        realm = Realm.getDefaultInstance();
        itemRealmList = new RealmList<>();

        getPicturesFromDb();

        Intent intent = getIntent();
        int position = intent.getIntExtra("pic_position", 0);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FullPicFragment fragment = FullPicFragment.newInstance(position, itemRealmList);
        transaction.addToBackStack(null);
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();

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
}
