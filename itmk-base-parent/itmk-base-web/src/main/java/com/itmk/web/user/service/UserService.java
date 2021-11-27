package com.itmk.web.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.user.entity.User;
import com.itmk.web.user.entity.UserParm;

public interface UserService extends IService<User> {
    //查询用户列表
    IPage<User> list(UserParm parm);
}
