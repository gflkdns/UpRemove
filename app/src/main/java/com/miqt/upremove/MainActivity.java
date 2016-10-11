package com.miqt.upremove;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.miqt.upremove.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    HorizontalScrollView hSrollview;
    LinearLayout layout;
    private float fristy;
    int[] imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hSrollview = (HorizontalScrollView) findViewById(R.id.hSrollview);
        layout = (LinearLayout) findViewById(R.id.layout);
        imgs = new int[]{
                R.drawable.pic_1,
                R.drawable.pic_2,
                R.drawable.pic_3,
                R.drawable.pic_4
        };
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        final int height = hSrollview.getHeight();
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 20;
        for (int i = 0; i < imgs.length; i++) {
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
            ImageView imageView = (ImageView) img.findViewById(R.id.img);
            ViewGroup.LayoutParams pm = imageView.getLayoutParams();
            pm.height = height;
            Bitmap bm = BitmapFactory.decodeResource(getResources(), imgs[i], option);
            imageView.setImageBitmap(bm);
            layout.addView(img);
        }
    }
}
