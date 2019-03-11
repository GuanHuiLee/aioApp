package com.lgh.aio;

import android.support.v4.content.ContextCompat;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.tencent.mmkv.MMKV;
import com.zgg.commonlibrary.base.BaseApplication;
import com.zgg.commonlibrary.glide.GlideHelper;

/**
 * Created by LiGuanHui on 2019/3/6 10:36
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MMKV.initialize(this);
        GlideHelper.init(R.mipmap.ic_error_img, R.mipmap.ic_error_img);
    }

    //下拉刷新
    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            MaterialHeader materialHeader = new MaterialHeader(context);
            materialHeader.setColorSchemeColors(ContextCompat.getColor(context, R.color.color_main));
            return materialHeader;
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(context));

    }
}
