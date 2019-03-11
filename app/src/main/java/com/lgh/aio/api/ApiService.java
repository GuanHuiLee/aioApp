package com.lgh.aio.api;


import com.lgh.aio.base.BaseResult;
import com.lgh.aio.model.LoginInput;
import com.lgh.aio.model.LoginResult;
import com.lgh.aio.model.RegisterInput;
import com.lgh.aio.model.User;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;


public interface ApiService {

    /**
     * 注册账号
     *
     * @param input
     * @return
     */
    @POST("user/register")
    Observable<BaseResult<String>> register(@Body RegisterInput input);


    /**
     * 登录
     *
     * @param input
     * @return
     */
    @POST("user/login")
    Observable<BaseResult<LoginResult>> login(@Body LoginInput input);


    /**
     * 获取用户
     */
    @POST("user/getAllUser")
    Observable<BaseResult<List<User>>> getAllUser();
}
