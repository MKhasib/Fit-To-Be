package com.example.fittobe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity {
    private int[] layouts = {R.layout.first_welcome_slide, R.layout.second_welcome_slide, R.layout.third_welcome_slide, R.layout.fourth_welcome_slide, R.layout.fifth_welcome_slide};
    private MPagerAdapter mPagerAdapter;
    private ImageView[] dotsImageVIew;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.bottom_layout)
    LinearLayout mBottomLinearLayout;
    @BindBool(R.bool.isTablet)
    boolean isTablet;
    @BindView(R.id.next_button)
    Button nextButton;

    @OnClick(R.id.next_button)
    public void loadNextSlide() {
        int nextSlide = mViewPager.getCurrentItem() + 1;
        if (nextSlide < layouts.length) {
            mViewPager.setCurrentItem(nextSlide);
        } else {
            loadHome();
        }
    }

    @BindView(R.id.skip_button)
    Button skipButton;

    @OnClick(R.id.skip_button)
    public void loadHome() {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putBoolean(getString(R.string.app_shared_preference_key), Boolean.TRUE);
        edit.commit();
        startActivity(new Intent(this, StartUpActivity.class));
        finish();
    }

    String color;
    SharedPreferences mSharedPreferences;
    boolean previouslyStarted;

    @SuppressLint({"SourceLockedOrientationActivity", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        previouslyStarted = mSharedPreferences.getBoolean(getString(R.string.app_shared_preference_key), false);
        if (previouslyStarted) {
            loadHome();

        }

        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (isTablet == false) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        mPagerAdapter = new MPagerAdapter(layouts, this);
        mViewPager.setAdapter(mPagerAdapter);
        createDots(0);
        color = getResources().getString(R.color.color_slide_1);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("ResourceType")
            @Override
            public void onPageSelected(int position) {
                createDots(position);
                if (position == layouts.length - 1) {
                    nextButton.setText(R.string.start);
                    skipButton.setVisibility(View.INVISIBLE);
                } else {
                    nextButton.setText(R.string.next);
                    skipButton.setVisibility(View.VISIBLE);

                }
                switch (position) {
                    case 0:
                        color = getResources().getString(R.color.color_slide_1);
                        break;
                    case 1:
                        color = getResources().getString(R.color.color_slide_2);
                        break;
                    case 2:
                        color = getResources().getString(R.color.color_slide_3);
                        break;
                    case 3:
                        color = getResources().getString(R.color.color_slide_4);
                        break;
                    case 4:
                        color = getResources().getString(R.color.color_slide_5);
                        break;
                    default:
                        color = getResources().getString(R.color.color_slide_1);

                }
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void createDots(int position) {
        if (mBottomLinearLayout != null) {
            mBottomLinearLayout.removeAllViews();
            dotsImageVIew = new ImageView[layouts.length];
        }
        for (int i = 0; i < layouts.length; i++) {
            dotsImageVIew[i] = new ImageView(this);
            if (i == position) {
                dotsImageVIew[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dot));

            } else {
                dotsImageVIew[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.inactive_dot));

            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int leftRightMargin = 4;
            int topBottomMargin = 0;
            params.setMargins(leftRightMargin, topBottomMargin, leftRightMargin, topBottomMargin);
            mBottomLinearLayout.addView(dotsImageVIew[i], params);
        }

    }
}
