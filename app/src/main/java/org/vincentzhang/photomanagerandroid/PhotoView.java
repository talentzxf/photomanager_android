package org.vincentzhang.photomanagerandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.IOException;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

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

        try {
            createImageListView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadImageAndRender(String url, ImageView view) {
        ImageLoader.getInstance().displayImage(url, view, new ImageLoadingListener() {
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

    public Drawable scaleImage(Drawable image, float scaleFactor) {

        if ((image == null) || !(image instanceof BitmapDrawable)) {
            return image;
        }

        Bitmap b = ((BitmapDrawable) image).getBitmap();

        int sizeX = Math.round(image.getIntrinsicWidth() * scaleFactor);
        int sizeY = Math.round(image.getIntrinsicHeight() * scaleFactor);

        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, sizeX, sizeY, false);

        image = new BitmapDrawable(getResources(), bitmapResized);

        return image;

    }

    private void createImageListView() throws IOException {

        GifDrawable gifFromResource = new GifDrawable(getResources(), R.drawable.small_imgloading);
        // Image: 4:3
        gifFromResource.setBounds(0, 0, 100, 75);

        DisplayImageOptions imageOptions = new DisplayImageOptions.Builder().cacheInMemory(true).
                cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).showImageOnLoading(gifFromResource).build();

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
        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setItemPrefetchEnabled(true);
//        layoutManager.setJustifyContent(JustifyContent.FLEX_END);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemViewCacheSize(20);

        final ArrayList<String> imgList = new ArrayList();
//        for (int i = 291; i <= 574; i++) {
//            imgList.add("http://10.86.48.54:8000/img/" + i + "?width=100&height=100");
//        }
        imgList.add("LoadingBar");

        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public int getItemViewType(int position) {
                String url = imgList.get(position);
                if (url.startsWith("http://")) {
                    return 1;
                }
                return 2;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == 1) {
                    ImageView view = new ImageView(parent.getContext());
                    view.setPadding(1,1,1,1);
                    RecyclerView.ViewHolder vh = new RecyclerView.ViewHolder(view) {
                    };
                    return vh;
                } else {
                    GifImageView view = new GifImageView(parent.getContext());
                    view.setImageResource(R.drawable.loading);
                    // view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                    RecyclerView.ViewHolder vh = new RecyclerView.ViewHolder(view) {
                    };
                    return vh;
                }
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                String url = imgList.get(position);
                if (url.startsWith("http://")) {
                    loadImageAndRender(url, (ImageView) holder.itemView);
                } else {

                }

            }

            @Override
            public int getItemCount() {
                return imgList.size();
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
                Log.i("Scrolled","Can scroll more:" + recyclerView.canScrollVertically(1) );
//                View view = recyclerView.getChildAt(1);
//                Rect scrollBounds = new Rect();
//                recyclerView.getHitRect(scrollBounds);
//                if (view.getLocalVisibleRect(scrollBounds)) {
//                    Log.i("Scrolled", "View is shown." + view.getVisibility());
//                } else {
//                    Log.i("Scrolled", "View is not shown" + view.getVisibility());
//                }
            }
        });


    }

    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view) {
    }

}
