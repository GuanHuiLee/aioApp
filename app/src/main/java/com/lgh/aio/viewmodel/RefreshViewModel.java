package com.lgh.aio.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;

import com.lgh.aio.BR;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;


/**
 * Created by LiGuanHui on 2019/3/8 11:33
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public abstract class RefreshViewModel extends BaseObservable {
    private boolean isShowEmptyView;
    private boolean canLoadMore;
    private String emptyText = "暂无数据";

    @Bindable
    public String getEmptyText() {
        return emptyText;
    }

    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
        notifyPropertyChanged(BR.emptyText);
    }

    public boolean isCanLoadMore() {
        return canLoadMore;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
    }

    @Bindable
    public boolean isShowEmptyView() {
        return isShowEmptyView;
    }

    public void setShowEmptyView(boolean showEmptyView) {
        isShowEmptyView = showEmptyView;
        notifyPropertyChanged(BR.showEmptyView);
    }


    protected abstract void loadData();

}
