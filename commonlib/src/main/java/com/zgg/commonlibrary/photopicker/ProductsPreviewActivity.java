package com.zgg.commonlibrary.photopicker;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.zgg.commonlibrary.R;
import com.zgg.commonlibrary.photopicker.widget.CircleIndicator;
import com.zgg.commonlibrary.photopicker.widget.ViewPagerFixed;

import java.util.ArrayList;

/**
 * Created by foamtrace on 2015/8/25.
 */
public class ProductsPreviewActivity extends AppCompatActivity implements PhotoPagerAdapter.PhotoViewClickListener {

    public static final String EXTRA_PHOTOS = "extra_photos";
    public static final String EXTRA_CURRENT_ITEM = "extra_current_item";

    private ArrayList<String> paths;
    private ViewPagerFixed mViewPager;
    private PhotoPagerAdapter mPagerAdapter;
    private CircleIndicator indicator;
    private TextView tv_num;

    public static final int TYPE_POINT = 1;
    public static final String TYPE = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.color_black));

        setContentView(R.layout.activity_image_preview_products);
        initViews();
        paths = getIntent().getStringArrayListExtra(EXTRA_PHOTOS);

        final int currentItem = getIntent().getIntExtra(EXTRA_CURRENT_ITEM, 0);
        final int type = getIntent().getIntExtra(TYPE, 0);

        final int size = paths.size();


        if (type == TYPE_POINT) {
            /**
             * 红点
             */
            indicator.setCount(size);
            indicator.setVisibility(size != 1 ? View.VISIBLE : View.GONE);
            tv_num.setVisibility(View.GONE);
            indicator.setPosition(currentItem);
        } else {
            tv_num.setVisibility(size != 1 ? View.VISIBLE : View.GONE);
            indicator.setVisibility(View.GONE);
            tv_num.setText(getString(R.string.num, currentItem + 1, size));
        }

        mPagerAdapter = new PhotoPagerAdapter(this, paths);
        mPagerAdapter.setPhotoViewClickListener(this);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(currentItem);
        mViewPager.setOffscreenPageLimit(5);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (type == TYPE_POINT) {
                    indicator.setPosition(position);
                } else
                    tv_num.setText(getString(R.string.num, position + 1, size));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViews() {
        indicator = findViewById(R.id.indicator);
        mViewPager = findViewById(R.id.vp_photos);
        tv_num = findViewById(R.id.tv_num);
    }

    @Override
    public void OnPhotoTapListener(View view, float v, float v1) {
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

}
