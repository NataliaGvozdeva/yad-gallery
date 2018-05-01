package com.mersiyanov.dmitry.yadg.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mersiyanov.dmitry.yadg.R;
import com.mersiyanov.dmitry.yadg.YadApplication;
import com.mersiyanov.dmitry.yadg.network.NetworkUtils;
import com.mersiyanov.dmitry.yadg.pojo.ResponseFileList;
import com.mersiyanov.dmitry.yadg.ui.AuthActivity;
import com.mersiyanov.dmitry.yadg.ui.FullPictureActivity;
import com.mersiyanov.dmitry.yadg.ui.PicturesAdapter;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements PicturesContract.View {

    @Inject PicturesContract.Presenter presenter;
    static public List<ResponseFileList.Item> itemList;
    private RecyclerView rv_pics;
    private PicturesAdapter picturesAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YadApplication.component.injects(this);

        setContentView(R.layout.activity_main);
        presenter.attachView(this);
        initUI();
        presenter.load();

    }

    @Override
    public void showLoading() {
        rv_pics.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        Toast.makeText(MainActivity.this, R.string.load_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showData(List<ResponseFileList.Item> localItemlist) {
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
        public void onPictureClick(ResponseFileList.Item image) {
            int itemIndex = itemList.indexOf(image);
            if(itemIndex == 0) { itemIndex = itemIndex + 1; }

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
            NetworkUtils.deleteAuthToken();
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
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
