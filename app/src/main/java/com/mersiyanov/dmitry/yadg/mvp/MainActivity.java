package com.mersiyanov.dmitry.yadg.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mersiyanov.dmitry.yadg.FullPictureActivity;
import com.mersiyanov.dmitry.yadg.PicturesAdapter;
import com.mersiyanov.dmitry.yadg.R;
import com.mersiyanov.dmitry.yadg.ResponseFileList;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PicturesContract.View {

    PicturesContract.Presenter presenter;

    static public List<ResponseFileList.Item> itemList;
    private RecyclerView rv_pics;
    private PicturesAdapter picturesAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter();
        presenter.attachView(this);
        rv_pics = findViewById(R.id.rv_images);
        progressBar = findViewById(R.id.image_load_prog);

        presenter.load();


    }

    @Override
    public void showLoading() {
        rv_pics.setAdapter(picturesAdapter);
        rv_pics.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Toast.makeText(MainActivity.this, R.string.load_error, Toast.LENGTH_LONG).show();

    }

    @Override
    public void showData(List<ResponseFileList.Item> localItemlist) {
        itemList = localItemlist;
        picturesAdapter = new PicturesAdapter(localItemlist, onPictureClickListener);
        rv_pics.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        rv_pics.setAdapter(picturesAdapter);
    }

    public void initUI() {

    }


    private final PicturesAdapter.OnPictureClickListener onPictureClickListener = new PicturesAdapter.OnPictureClickListener() {
        @Override
        public void onPictureClick(ResponseFileList.Item image) {
            Toast.makeText(MainActivity.this, image.getPath(), Toast.LENGTH_LONG).show();
            int itemIndex = itemList.indexOf(image);
            if(itemIndex == 0) {
                itemIndex = itemIndex + 1;
            }

            Intent intent = new Intent(MainActivity.this, FullPictureActivity.class);
            intent.putExtra("pic_url", image.getFile());
            intent.putExtra("pic_position", itemIndex);
            intent.putExtra("image", image);
            startActivity(intent);


        }
    };

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
