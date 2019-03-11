package com.lgh.aio.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lgh.aio.R;
import com.lgh.aio.base.BaseBindingAdapter;
import com.lgh.aio.databinding.ItemTestBinding;
import com.lgh.aio.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiGuanHui on 2019/3/7 11:12
 * 难写的代码，肯定很难读。因此，我没有注释留给你。
 */
public class UserAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<User> items;

    public UserAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<User>() {{
//            add(new User("张三", 18));
//            add(new User("李四", 28));
//            add(new User("王五", 38));
        }};
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTestBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this.context), R.layout.item_test, parent, false);
        return new UserViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemTestBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setUser(this.items.get(position));
        binding.executePendingBindings();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        public UserViewHolder(View itemView) {
            super(itemView);
        }
    }

}
