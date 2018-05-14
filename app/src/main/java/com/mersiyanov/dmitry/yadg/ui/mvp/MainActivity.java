package com.mersiyanov.dmitry.yadg.ui.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mersiyanov.dmitry.yadg.R;
import com.mersiyanov.dmitry.yadg.YadApplication;
import com.mersiyanov.dmitry.yadg.pojo.Item;
import com.mersiyanov.dmitry.yadg.ui.AuthActivity;
import com.mersiyanov.dmitry.yadg.ui.FullPictureActivity;
import com.mersiyanov.dmitry.yadg.ui.PicturesAdapter;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements PicturesContract.View {

    @Inject PicturesContract.Presenter presenter;
    static public List<Item> itemList;
    private RecyclerView rv_pics;
    private PicturesAdapter picturesAdapter;
    private ProgressBar progressBar;
    private ImageView noInternetImg;
    private TextView noInternetTxt;
    private Button noInternetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YadApplication.component.injects(this);

        setContentView(R.layout.activity_main);
        presenter.attachView(this);
        initUI();
        presenter.getPictures(((YadApplication) getApplication()).getAuthToken());

    }

    @Override
    public void showLoading() {
        rv_pics.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        noInternetImg = findViewById(R.id.no_internet_img);
        noInternetTxt = findViewById(R.id.no_internet_txt);
        noInternetBtn = findViewById(R.id.no_internet_btn);

        progressBar.setVisibility(View.GONE);
        noInternetImg.setVisibility(View.VISIBLE);
        noInternetTxt.setVisibility(View.VISIBLE);
        noInternetBtn.setVisibility(View.VISIBLE);

        Toast.makeText(MainActivity.this, R.string.load_error, Toast.LENGTH_SHORT).show();

        noInternetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                noInternetImg.setVisibility(View.GONE);
                noInternetTxt.setVisibility(View.GONE);
                noInternetBtn.setVisibility(View.GONE);
                presenter.getPictures(((YadApplication) getApplication()).getAuthToken());
            }
        });
    }

    @Override
    public void showData(List<Item> localItemlist) {
        rv_pics.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        itemList = localItemlist;
        picturesAdapter = new PicturesAdapter(localItemlist, onPictureClickListener);
        rv_pics.setAdapter(picturesAdapter);

    }

    public void initUI() {
        rv_pics = findViewById(R.id.rv_images);
        progressBar = findViewById(R.id.image_load_prog);
        rv_pics.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
    }

    private final PicturesAdapter.OnPictureClickListener onPictureClickListener = new PicturesAdapter.OnPictureClickListener() {
        @Override
        public void onPictureClick(Item image) {
            int itemIndex = itemList.indexOf(image);

            Intent intent = new Intent(MainActivity.this, FullPictureActivity.class);
            intent.putExtra("pic_position", itemIndex);
            startActivity(intent);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == R.id.action_about) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.action_about)
                    .setMessage(R.string.about_text)
                    .setIcon(R.mipmap.gallery_icon)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
            return true;
        } else if (itemId == R.id.action_logout){
            ((YadApplication) getApplication()).deleteAuthToken();
            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            startActivity(intent);
            return  true;
        } else return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
