package com.lgh.aio.Listener;

/**
 * Created by LiGuanHui on 2019/3/6 17:01
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public interface DataListener<T> {
    void onDataChanged(T t);
}
