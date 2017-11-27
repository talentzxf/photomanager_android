package org.vincentzhang.photomanagerandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(PhotoView.EXTRA_MESSAGE);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }
}
