package com.lgh.aio.adapter;

import android.content.Context;

import com.lgh.aio.R;
import com.lgh.aio.base.BaseBindingAdapter;
import com.lgh.aio.databinding.ItemTestBinding;
import com.lgh.aio.model.User;

/**
 * Created by LiGuanHui on 2019/3/7 11:12
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public class TestAdapter extends BaseBindingAdapter<User, ItemTestBinding> {
    public TestAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.item_test;
    }

    @Override
    protected void onBindItem(ItemTestBinding binding, User item) {
        binding.setUser(item);
        binding.executePendingBindings();
    }
}
