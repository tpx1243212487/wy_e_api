package com.itmk.web.menu.controller;

import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.menu.entity.Menu;
import com.itmk.web.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    //树数据列表
    @GetMapping("/list")
    public ResultVo list(){
        List<Menu> list = menuService.getList();
        return ResultUtils.success("查询成功",list);
    }

    //新增菜单
    @PostMapping
    public ResultVo addMenu(@RequestBody Menu menu){
        boolean save = menuService.save(menu);
        if(save){
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    //编辑菜单
    @PutMapping
    public ResultVo editMenu(@RequestBody Menu menu){
        boolean save = menuService.updateById(menu);
        if(save){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除菜单
    @DeleteMapping("/{menuId}")
    public ResultVo deleteMenu(@PathVariable("menuId") Long menuId){
        boolean b = menuService.removeById(menuId);
        if(b){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    //获取上级菜单
    @GetMapping("/parent")
    public ResultVo getParent(){
        List<Menu> parentList = menuService.getParentList();
        return ResultUtils.success("查询成功",parentList);
    }
}