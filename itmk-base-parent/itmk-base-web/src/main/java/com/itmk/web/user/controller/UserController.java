package com.itmk.web.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.user.entity.User;
import com.itmk.web.user.entity.UserParm;
import com.itmk.web.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 员工管理控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping
    public ResultVo addUser(@RequestBody User user){
        boolean save = userService.save(user);
        if(save){
            return ResultUtils.success("新增用户成功");
        }
        return ResultUtils.error("新增用户失败");
    }

    /**
     * 编辑用户
     * @param user
     * @return
     */
    @PutMapping
    public ResultVo editUser(@RequestBody User user){
        boolean b = userService.updateById(user);
        if(b){
            return ResultUtils.success("编辑用户成功");
        }
        return ResultUtils.error("编辑用户失败");
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @DeleteMapping
    public ResultVo deleteUser(@RequestParam("userId") Long userId){
        boolean b = userService.removeById(userId);
        if(b){
            return ResultUtils.success("删除用户成功");
        }
        return ResultUtils.error("删除用户失败");
    }

    /**
     * 获取用户列表
     * @param parm
     * @return
     */
    @GetMapping("/list")
    public ResultVo list(UserParm parm){
        IPage<User> list = userService.list(parm);
        return ResultUtils.success("查询成功",list);
    }
}
