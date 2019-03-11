package com.lgh.aio.viewmodel;

import android.databinding.BindingAdapter;

import com.lgh.aio.Listener.DataListener;
import com.lgh.aio.api.ApiFactory;
import com.lgh.aio.base.BaseObserver;
import com.lgh.aio.model.User;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LiGuanHui on 2019/3/7 11:44
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public class MainViewModel extends RefreshViewModel {
    private DataListener<List<User>> listener;

    public MainViewModel(DataListener<List<User>> listener) {
        this.listener = listener;
        getData();
    }

    private void getData() {
        ApiFactory.getService().getAllUser().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<List<User>>() {
                    @Override
                    protected void onSuccess(List<User> users) {
                        listener.onDataChanged(users);
                    }

                    @Override
                    protected void onStart() {

                    }

                    @Override
                    protected void onFinish() {

                    }

                    @Override
                    public void onError(String t) {

                    }
                });
    }

    @Override
    protected void loadData() {

    }


    @BindingAdapter(value = "listener")
    public static void listener(SmartRefreshLayout refreshLayout, String text) {
        Logger.d("listener: ");

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                Logger.d("加载");
            }
        });
    }
}
