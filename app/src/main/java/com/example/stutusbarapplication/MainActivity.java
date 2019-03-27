package com.example.stutusbarapplication;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private View mStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            addToolbarTransparent();
//        }

        printView((ViewGroup)getWindow().getDecorView());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void addToolbarTransparent() {
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (mStatusView == null) {
            mStatusView = new View(this);
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            int screenHeight = getStatusBarHeight();
            ViewGroup.LayoutParams layoutParams =  new ViewGroup.LayoutParams(screenWidth,screenHeight);

            mStatusView.setLayoutParams(layoutParams);
            mStatusView.requestLayout();

            ViewGroup systemContent = findViewById(android.R.id.content);
            Log.d(TAG, "addToolbarTransparent: systemContent / " + systemContent.getClass().getSimpleName());
            ViewGroup userContent = (ViewGroup) systemContent.getChildAt(0);
            Log.d(TAG, "addToolbarTransparent: userContent / " + userContent.getClass().getSimpleName());



            userContent.setFitsSystemWindows(true);
            systemContent.addView(mStatusView, 0);

            mStatusView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

    }

    private void printView(ViewGroup viewGroup) {
        Log.d(TAG, "printView: viewgroup:" + viewGroup.getClass().getSimpleName());

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childView = viewGroup.getChildAt(i);
            Log.d(TAG, "printView: childView:" + childView.getClass().getSimpleName());
        }

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof ViewGroup) {
                printView((ViewGroup) childView);
            }
        }
    }

    private int getStatusBarHeight() {
        int statusBarHeight = -1;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
