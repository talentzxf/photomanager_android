package org.vincentzhang.photomanagerandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class PhotoView extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private class WebViewOverrideUrl extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        createImageListView();
    }

    private void createImageListView() {

        DisplayImageOptions imageOptions = new DisplayImageOptions.Builder().cacheInMemory(true).
                cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        config.defaultDisplayImageOptions(imageOptions);

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());

        LinearLayout listLayout = (LinearLayout) findViewById(R.id.img_linearlayout);

        ImageView imgView = new ImageView(this);
        ImageLoader.getInstance().displayImage("http://10.86.48.54:8000/img/3/?width=300&height=300", imgView, new SimpleImageLoadingListener() {
        });

        ImageView imgView2 = new ImageView(this);
        ImageLoader.getInstance().displayImage("http://10.86.48.54:8000/img/1/?width=300&height=300", imgView2, new SimpleImageLoadingListener() {
        });

        listLayout.addView(imgView);
        listLayout.addView(imgView2);
    }


    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

}
