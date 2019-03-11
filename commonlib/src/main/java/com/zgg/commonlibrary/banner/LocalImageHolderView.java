package com.zgg.commonlibrary.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.zgg.commonlibrary.banner.holder.Holder;

/**
 * Created by LiGuanHui on 2018/11/27 09:35
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public class LocalImageHolderView implements Holder<Integer> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        imageView.setImageResource(data);
    }
}