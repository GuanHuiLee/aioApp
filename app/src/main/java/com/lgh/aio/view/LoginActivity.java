package com.lgh.aio.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lgh.aio.R;
import com.lgh.aio.base.BaseActivity;
import com.lgh.aio.databinding.ActivityLoginBinding;
import com.lgh.aio.model.LoginResult;
import com.lgh.aio.util.DataUtil;
import com.lgh.aio.viewmodel.LoginViewModel;
import com.orhanobut.logger.Logger;
import com.zgg.commonlibrary.utils.NullUtils;

public class LoginActivity extends BaseActivity implements LoginViewModel.DataListener {

    private static final int REGISTER = 1;
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewModel = new LoginViewModel(this, this);
        binding.setViewModel(viewModel);
        setData();
    }

    @Override
    protected void initData() {
        setTitle("登录");
    }

    private void setData() {
        String phone = DataUtil.getPhone();
        if (!NullUtils.isEmptyString(phone)) {
            viewModel.setPhone(phone);
            viewModel.setPwd(DataUtil.getPasswd());
        }
    }

    @Override
    public void onDataChanged(LoginResult t) {
        showToast("登录成功");
        DataUtil.setPhone(t.getPhone());
        DataUtil.setPasswd(t.getPwd());

        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    @Override
    public void onRegister() {
        startActivityForResult(new Intent(mContext, RegisterActivity.class), REGISTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REGISTER) {
                Logger.d("注册成功");
                setData();
            }
        }
    }
}
