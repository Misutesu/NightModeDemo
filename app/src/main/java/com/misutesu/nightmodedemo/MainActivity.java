package com.misutesu.nightmodedemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private SharedPreferences mSharedPreferences;

    private LinearLayout mLayout;
    private Toolbar mToolbar;
    private TextView mNightText;
    private SwitchCompat mNightModeSwitch;
    private TextView mReCreateText;
    private TextView mStartActivityText;
    private TextView mAnimText;
    private AppCompatRadioButton mReCreateRadio;
    private AppCompatRadioButton mStartActivityRadio;
    private AppCompatRadioButton mAnimRadio;
    private AppCompatButton mBtn;
    private RecyclerView mRecyclerView;

    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = getSharedPreferences("theme", MODE_PRIVATE);
        if (mSharedPreferences.getBoolean("isNightMode", false)) {
            setTheme(R.style.NightTheme);
        }

        setContentView(R.layout.activity_main);
        initView();
        reductionView();
    }

    private void initView() {
        mLayout = (LinearLayout) findViewById(R.id.layout);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mNightText = (TextView) findViewById(R.id.night_mode_text);
        mNightModeSwitch = (SwitchCompat) findViewById(R.id.night_mode_switch);
        mReCreateText = (TextView) findViewById(R.id.re_create_text);
        mStartActivityText = (TextView) findViewById(R.id.start_activity_text);
        mAnimText = (TextView) findViewById(R.id.anim_text);
        mReCreateRadio = (AppCompatRadioButton) findViewById(R.id.re_create_radio);
        mStartActivityRadio = (AppCompatRadioButton) findViewById(R.id.start_activity_radio);
        mAnimRadio = (AppCompatRadioButton) findViewById(R.id.anim_radio);
        mBtn = (AppCompatButton) findViewById(R.id.btn);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        List<String> list = new ArrayList<>();
        int n = 30000;
        for (int i = 0; i < 20; i++) {
            list.add(String.valueOf(n));
            n++;
        }
        mAdapter = new MyAdapter(this, list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mNightModeSwitch.setOnCheckedChangeListener(this);
        mReCreateRadio.setOnCheckedChangeListener(this);
        mStartActivityRadio.setOnCheckedChangeListener(this);
        mAnimRadio.setOnCheckedChangeListener(this);
    }

    private void reductionView() {
        mNightModeSwitch.setChecked(mSharedPreferences.getBoolean("isNightMode", false));
        switch (mSharedPreferences.getInt("checkMode", 0)) {
            case 0:
                mReCreateRadio.setChecked(true);
                break;
            case 1:
                mStartActivityRadio.setChecked(true);
                break;
            case 2:
                mAnimRadio.setChecked(true);
                break;
        }

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.scrollBy(0, getIntent().getIntExtra("scrollY", 0));
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton v, boolean isChecked) {
        if (v.getId() == R.id.night_mode_switch) {
            mSharedPreferences.edit().putBoolean("isNightMode", isChecked).apply();
            changeTheme();
        } else if (v.getId() == R.id.re_create_radio) {
            if (isChecked) {
                mSharedPreferences.edit().putInt("checkMode", 0).apply();
                mStartActivityRadio.setChecked(false);
                mAnimRadio.setChecked(false);
            }
        } else if (v.getId() == R.id.start_activity_radio) {
            if (isChecked) {
                mSharedPreferences.edit().putInt("checkMode", 1).apply();
                mReCreateRadio.setChecked(false);
                mAnimRadio.setChecked(false);
            }
        } else if (v.getId() == R.id.anim_radio) {
            if (isChecked) {
                mSharedPreferences.edit().putInt("checkMode", 2).apply();
                mReCreateRadio.setChecked(false);
                mStartActivityRadio.setChecked(false);
            }
        }
    }

    private void changeTheme() {
        if (mReCreateRadio.isChecked()) {
            recreate();
        } else if (mStartActivityRadio.isChecked()) {
            startActivity(new Intent(this, MainActivity.class).putExtra("scrollY", getScrollYDistance()));
            overridePendingTransition(R.anim.start_anim, R.anim.out_anim);
            finish();
        } else if (mAnimRadio.isChecked()) {
            int startColorPrimary = ThemeUtil.getColorFromTheme(getTheme(), R.attr.colorPrimary);
            int startColorIcon = ThemeUtil.getColorFromTheme(getTheme(), R.attr.iconColor);
            int startColorText = ThemeUtil.getColorFromTheme(getTheme(), R.attr.textColor);
            int startColorBackground = ThemeUtil.getColorFromTheme(getTheme(), R.attr.windowBackgroundColor);
            int startColorItemBackground = ThemeUtil.getColorFromTheme(getTheme(), R.attr.itemBackgroundColor);
            if (mSharedPreferences.getBoolean("isNightMode", false)) {
                setTheme(R.style.NightTheme);
            } else {
                setTheme(R.style.NormalTheme);
            }
            int endColorPrimary = ThemeUtil.getColorFromTheme(getTheme(), R.attr.colorPrimary);
            int endColorIcon = ThemeUtil.getColorFromTheme(getTheme(), R.attr.iconColor);
            int endColorText = ThemeUtil.getColorFromTheme(getTheme(), R.attr.textColor);
            int endColorBackground = ThemeUtil.getColorFromTheme(getTheme(), R.attr.windowBackgroundColor);
            int endColorItemBackground = ThemeUtil.getColorFromTheme(getTheme(), R.attr.itemBackgroundColor);

            int[] colorPrimary = {startColorPrimary, endColorPrimary};
            int[] colorBackground = {startColorBackground, endColorBackground};
            int[] colorItemBackground = {startColorItemBackground, endColorItemBackground};
            int[] colorIcon = {startColorIcon, endColorIcon};
            int[] colorText = {startColorText, endColorText};

            ThemeUtil.changeColor(colorPrimary, new ThemeUtil.OnColorChangeListener() {
                @Override
                public void onColorChange(int color) {
                    mToolbar.setBackgroundColor(color);
                    mNightModeSwitch.setThumbTintList(ColorStateList.valueOf(color));
                    CompoundButtonCompat.setButtonTintList(mReCreateRadio, ColorStateList.valueOf(color));
                    CompoundButtonCompat.setButtonTintList(mStartActivityRadio, ColorStateList.valueOf(color));
                    CompoundButtonCompat.setButtonTintList(mAnimRadio, ColorStateList.valueOf(color));
                    ViewCompat.setBackgroundTintList(mBtn, ColorStateList.valueOf(color));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(color);
                    }
                }
            });

            ThemeUtil.changeColor(colorBackground, new ThemeUtil.OnColorChangeListener() {
                @Override
                public void onColorChange(int color) {
                    mLayout.setBackgroundColor(color);
                }
            });

            ThemeUtil.changeColor(colorText, new ThemeUtil.OnColorChangeListener() {
                @Override
                public void onColorChange(int color) {
                    mNightText.setTextColor(color);
                    mReCreateText.setTextColor(color);
                    mStartActivityText.setTextColor(color);
                    mAnimText.setTextColor(color);
                }
            });

            int childCount = mRecyclerView.getChildCount();
            for (int childIndex = 0; childIndex < childCount; childIndex++) {
                ViewGroup childView = (ViewGroup) mRecyclerView.getChildAt(childIndex);
                final CardView mCardView = (CardView) childView.findViewById(R.id.item_card_view);
                final TextView mText = (TextView) childView.findViewById(R.id.item_text);
                final ImageView mImg = (ImageView) childView.findViewById(R.id.item_img);
                ThemeUtil.changeColor(colorItemBackground, new ThemeUtil.OnColorChangeListener() {
                    @Override
                    public void onColorChange(int color) {
                        mCardView.setCardBackgroundColor(color);
                    }
                });
                ThemeUtil.changeColor(colorText, new ThemeUtil.OnColorChangeListener() {
                    @Override
                    public void onColorChange(int color) {
                        mText.setTextColor(color);
                    }
                });
                ThemeUtil.changeColor(colorIcon, new ThemeUtil.OnColorChangeListener() {
                    @Override
                    public void onColorChange(int color) {
                        mImg.setColorFilter(color);
                    }
                });
            }

            Class<RecyclerView> recyclerViewClass = RecyclerView.class;
            try {
                Field declaredField = recyclerViewClass.getDeclaredField("mRecycler");
                declaredField.setAccessible(true);
                Method declaredMethod = Class.forName(RecyclerView.Recycler.class.getName()).getDeclaredMethod("clear", (Class<?>[]) new Class[0]);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(declaredField.get(mRecyclerView), new Object[0]);
                RecyclerView.RecycledViewPool recycledViewPool = mRecyclerView.getRecycledViewPool();
                recycledViewPool.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int getScrollYDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisibleChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisibleChildView.getHeight();
        return (position) * itemHeight - firstVisibleChildView.getTop();
    }
}
