package com.lgh.aio.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lgh.aio.Listener.DataListener;
import com.lgh.aio.R;
import com.lgh.aio.base.BaseActivity;
import com.lgh.aio.databinding.ActivityRegisterBinding;
import com.lgh.aio.model.RegisterInput;
import com.lgh.aio.util.DataUtil;
import com.lgh.aio.viewmodel.RegisterViewModel;
import com.orhanobut.logger.Logger;

public class RegisterActivity extends BaseActivity implements DataListener<RegisterInput> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        RegisterViewModel viewModel = new RegisterViewModel(this, this);
        binding.setViewModel(viewModel);
    }


    @Override
    protected void initData() {
        setTitle("注册");
    }

    @Override
    public void onDataChanged(RegisterInput registerInput) {
        DataUtil.setPhone(registerInput.getPhone());
        DataUtil.setPasswd(registerInput.getPwd());

        setResult(RESULT_OK);
        finish();
    }
}
