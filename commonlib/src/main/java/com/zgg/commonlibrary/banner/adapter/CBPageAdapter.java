package com.zgg.commonlibrary.banner.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.zgg.commonlibrary.banner.holder.CBViewHolderCreator;
import com.zgg.commonlibrary.banner.holder.Holder;
import com.zgg.commonlibrary.banner.view.CBLoopViewPager;

import java.util.List;

/**
 * Created by LiGuanHui on 2018/11/27 09:36
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public class CBPageAdapter<T> extends PagerAdapter {
    protected List<T> mDatas;
    protected CBViewHolderCreator holderCreator;
    //    private View.OnClickListener onItemClickListener;
    private boolean canLoop = true;
    private CBLoopViewPager viewPager;
    private final int MULTIPLE_COUNT = 300;

    public int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0)
            return 0;
        int realPosition = position % realCount;
        return realPosition;
    }

    @Override
    public int getCount() {
        return canLoop ? getRealCount() * MULTIPLE_COUNT : getRealCount();
    }

    public int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = toRealPosition(position);

        View view = getView(realPosition, null, container);
//        if(onItemClickListener != null) view.setOnClickListener(onItemClickListener);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        int position = viewPager.getCurrentItem();
        if (position == 0) {
            position = viewPager.getFirstItem();
        } else if (position == getCount() - 1) {
            position = viewPager.getLastItem();
        }
        try {
            viewPager.setCurrentItem(position, false);
        } catch (IllegalStateException e) {
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    public void setViewPager(CBLoopViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public CBPageAdapter(CBViewHolderCreator holderCreator, List<T> datas) {
        this.holderCreator = holderCreator;
        this.mDatas = datas;
    }

    private View getView(int position, View view, ViewGroup container) {
        Holder holder;
        holder = (Holder) holderCreator.createHolder();
        view = holder.createView(container.getContext());

        if (mDatas != null && !mDatas.isEmpty()) {
            holder.UpdateUI(container.getContext(), position, mDatas.get(position));
        }
        return view;
    }

}
