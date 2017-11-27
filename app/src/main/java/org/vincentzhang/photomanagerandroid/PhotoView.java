package org.vincentzhang.photomanagerandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class PhotoView extends Activity {
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

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
//        layoutManager.setJustifyContent(JustifyContent.FLEX_END);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecyclerView.Adapter() {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ImageView view = new ImageView(parent.getContext());
                RecyclerView.ViewHolder vh = new RecyclerView.ViewHolder(view) {
                };
                return vh;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ImageLoader.getInstance().displayImage("http://10.86.48.54:8000/img/" + (291 + position) + "?height=80", (ImageView) holder.itemView, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
            }

            @Override
            public int getItemCount() {
                return 574 - 291 + 1;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = recyclerView.getChildAt(1);
                Rect scrollBounds = new Rect();
                recyclerView.getHitRect(scrollBounds);
                if (view.getLocalVisibleRect(scrollBounds)) {
                    Log.i("Scrolled", "View is shown." + view.getVisibility());
                } else {
                    Log.i("Scrolled", "View is not shown"+ view.getVisibility());
                }
            }
        });


    }

    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view) {
    }

}
