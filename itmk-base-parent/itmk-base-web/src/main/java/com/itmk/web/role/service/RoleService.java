package com.itmk.web.role.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.role.entity.Role;
import com.itmk.web.role.entity.RoleParm;

/**
 * 角色service层
 */
public interface RoleService extends IService<Role> {
    IPage<Role> list(RoleParm parm);
}