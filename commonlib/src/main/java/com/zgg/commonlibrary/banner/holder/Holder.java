package com.zgg.commonlibrary.banner.holder;

import android.content.Context;
import android.view.View;

/**
 * Created by LiGuanHui on 2018/11/27 09:36
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public interface Holder<T>{
    View createView(Context context);
    void UpdateUI(Context context, int position, T data);
}