package com.mersiyanov.dmitry.yadg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.mersiyanov.dmitry.yadg.network.RetroHelper;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    List<ResponseFileList.Item> itemList;
    RecyclerView rv_pics;
    PicturesAdapter picturesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RetroHelper.getApi().getImagesList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseFileList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ResponseFileList responseFileList) {
                        itemList = responseFileList.getItems();
                        System.out.println(itemList.get(1).getPath());

                        Toast.makeText(MainActivity.this, itemList.get(1).getPath(), Toast.LENGTH_LONG).show();

                        rv_pics = findViewById(R.id.rv_images);
                        picturesAdapter = new PicturesAdapter(itemList, onPictureClickListener);
                        rv_pics.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                        rv_pics.setAdapter(picturesAdapter);


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, R.string.load_error, Toast.LENGTH_LONG).show();
                        Log.e("onError", e.getLocalizedMessage());

                    }
                });
    }

    private final PicturesAdapter.OnPictureClickListener onPictureClickListener = new PicturesAdapter.OnPictureClickListener() {
        @Override
        public void onPictureClick(ResponseFileList.Item image) {
            Toast.makeText(MainActivity.this, image.getPath(), Toast.LENGTH_LONG).show();

        }
    };
}
