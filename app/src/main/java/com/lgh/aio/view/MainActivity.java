package com.lgh.aio.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lgh.aio.Listener.DataListener;
import com.lgh.aio.R;
import com.lgh.aio.adapter.TestAdapter;
import com.lgh.aio.base.BaseActivity;
import com.lgh.aio.model.User;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements DataListener<List<User>> {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private TestAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new TestAdapter(mContext);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemViewClickListener((view, position) -> {
            showToast("点击");
        });

        mAdapter.addItem(new User("李白", "123"));
        mAdapter.addItem(new User("赵云", "1234"));
        mAdapter.addItem(new User("王磊", "1235"));
        mAdapter.addItem(new User("哈哈", "1233"));
    }

    @Override
    public void onDataChanged(List<User> o) {
        mAdapter.addItems(o);
    }
}
