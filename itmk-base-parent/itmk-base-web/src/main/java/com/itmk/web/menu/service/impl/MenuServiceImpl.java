package com.itmk.web.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.menu.entity.MakeMenuTree;
import com.itmk.web.menu.entity.Menu;
import com.itmk.web.menu.mapper.MenuMapper;
import com.itmk.web.menu.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Override
    public List<Menu> getList() {
        //构造查询条件
        QueryWrapper<Menu> query = new QueryWrapper<>();
        query.lambda().orderByAsc(Menu::getName);
        List<Menu> menus = this.baseMapper.selectList(query);
        //组装成树数据
        List<Menu> menus1 = MakeMenuTree.makeTree(menus, 0L);
        return menus1;
    }

    @Override
    public List<Menu> getParentList() {
        //只查询目录和菜单 即只查询 0 和 1 的数据
        String[] types = {"0","1"};
        //构造查询条件
        QueryWrapper<Menu> query = new QueryWrapper<>();
        query.lambda().in(Menu::getType,types).orderByAsc(Menu::getOrderNum);
        List<Menu> menus = this.baseMapper.selectList(query);
        //构造顶级菜单
        Menu menu = new Menu();
        menu.setMenuId(0L);
        menu.setParentId(-1L);
        menu.setMenuLabel("顶级菜单");
        menus.add(menu);
        //构造菜单树
        List<Menu> menus1 = MakeMenuTree.makeTree(menus, -1L);
        return menus1;
    }
}