package com.itmk.web.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.menu.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    //获取菜单列表树数据
    List<Menu> getList();
    //查询上级菜单
    List<Menu> getParentList();
}