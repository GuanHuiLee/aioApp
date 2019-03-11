package com.lgh.aio.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lgh.aio.R;
import com.lgh.aio.widget.MLoadingDialog;
import com.lgh.aio.widget.NotchUtils;
import com.orhanobut.logger.Logger;
import com.zgg.commonlibrary.base.AppManager;
import com.zgg.commonlibrary.utils.ToastTools;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 类描述：Activity 基类
 * 创建人：mhl
 * 创建时间：2016/10/10 12:03
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    protected String TAG;
    private Unbinder unbinder;
    public Context mContext;
    private TextView titleTV;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);

        if (setStatusBarTranslate()) {
            statusBarTranslate();
        }


        View back = findViewById(R.id.back);
        if (null != back) {
            back.setOnClickListener(v -> finish());
        }

        titleTV = findViewById(R.id.title_tv);

        RelativeLayout rl_top = findViewById(R.id.rl_top);
        if (null != rl_top) {
            setNotchBarHeight(rl_top);
        }

        if (null != getIntent() && null != getIntent().getExtras()) {
            getExtras(getIntent().getExtras());
        }


        showLog("页面地址：" + this.getClass().getName());
    }

    protected void setTitle(String name) {
        if (null != titleTV) {
            titleTV.setText(name);
        }
    }

    protected void getExtras(Bundle extras) {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        AppManager.getAppManager().addActivity(this);

        initData();
    }

    protected void setNotchBarHeight(View view) {
        if (NotchUtils.hasNotchScreen(this) && null != view) {
            int notchHeight = NotchUtils.getNotchHeight(this);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.setMargins(0, notchHeight, 0, 0);
            view.setLayoutParams(params);
        }
    }


    public void showLog(String log) {
        Logger.d(log);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().destroyActivity(this);
        if (null != unbinder) {
            unbinder.unbind();
        }
    }

    @Override
    public void showProgress(String message) {
        MLoadingDialog.show(this, message);
    }

    public void showProgress() {
        MLoadingDialog.show(this, "加载中...");
    }

    @Override
    public void hideProgress() {
        MLoadingDialog.dismiss();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void showToast(String message) {
        ToastTools.showShort(message);
    }

    /**
     * 设置最顶上的状态栏为透明色
     */
    protected boolean setStatusBarTranslate() {
        return false;
    }


    /**
     * 数据初始化
     */
    protected abstract void initData();

    /**
     * 设置最顶上的状态栏为透明色
     */
    protected void statusBarTranslate() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
