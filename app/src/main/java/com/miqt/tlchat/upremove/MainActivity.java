package com.miqt.tlchat.upremove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    HorizontalScrollView hSrollview;
    LinearLayout layout;
    private float fristy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hSrollview = (HorizontalScrollView) findViewById(R.id.hSrollview);
        layout = (LinearLayout) findViewById(R.id.layout);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        final int height = hSrollview.getHeight();
        for (int i = 0; i < 10; i++) {
            final ScrollView img = (ScrollView) getLayoutInflater().inflate(R.layout.up_item,
                    layout, false);
            img.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //记录初始y
                            fristy = event.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            img.onTouchEvent(event);
                            float rest = event.getRawY() - fristy;
                            if (rest < 0) {
                                img.setAlpha(1 - (Math.abs(rest * 4) / (height)));
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            //得到偏移量
                            float reset = event.getRawY() - fristy;
                            //删除或者归位
                            if (-reset < height / 5) {
                                //归位
                                img.smoothScrollTo(0, 0);
                                img.setAlpha(1);
                            } else {
                                //删除
                                layout.removeView(v);
                            }
                            break;
                    }
                    return true;
                }
            });
            ImageView imgs = (ImageView) img.findViewById(R.id.img);
            ViewGroup.LayoutParams pm = imgs.getLayoutParams();
            pm.height = height;
            imgs.setImageResource(R.mipmap.ic_launcher);
            layout.addView(img);
        }
    }
}
