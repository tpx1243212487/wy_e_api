package com.itmk.web.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.config.jwt.JwtUtils;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.user.entity.*;
import com.itmk.web.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 员工管理控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 获取用户信息
     */
    @GetMapping("/getInfo")
    public ResultVo getInfo(User user){
        User user1 = userService.getById(user.getUserId());
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user1.getUserId());
        userInfo.setName(user1.getUsername());
        userInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return ResultUtils.success("获取用户信息成功",userInfo);
    }

    /**
     *  用户登录
     * @param parm
     * @return
     */
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParm parm){
        if(StringUtils.isEmpty(parm.getUsername()) || StringUtils.isEmpty(parm.getPassword()) || StringUtils.isEmpty(parm.getUserType())){
            return ResultUtils.error("用户名、密码或用户类型不能为空!");
        }
        //查询用户信息
        String password = DigestUtils.md5DigestAsHex(parm.getPassword().getBytes());
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().eq(User::getUsername,parm.getUsername())
                .eq(User::getPassword,password);
        User one = userService.getOne(query);
        if(one == null){
            return ResultUtils.error("用户名、密码或用户类型错误!");
        }
        //如果用户存在，生成token返回给前端
        String token = jwtUtils.generateToken(one.getUsername());
        //获取生成的token的过期时间
        Long expireTime =  jwtUtils.getExpireTime(token,jwtUtils.getSecret());
        LoginResult result = new LoginResult();
        result.setUserId(one.getUserId());
        result.setToken(token);
        result.setExpireTime(expireTime);
        return ResultUtils.success("登录成功",result);
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping
    public ResultVo addUser(@RequestBody User user){
        //判断登录名 的唯一性
        if (StringUtils.isNotEmpty(user.getLoginName())) {
            QueryWrapper<User> query = new QueryWrapper<>();
            query.lambda().eq(User::getLoginName, user.getLoginName());
            User one = userService.getOne(query);
            if (one != null) {
                return ResultUtils.error("登录名已经被占用!", 500);
            }
        }
        //如果密码存在，MD5加密
        if (StringUtils.isNotEmpty(user.getPassword())) {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        }

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

//    /**
//     * 删除用户
//     * @param userId
//     * @return
//     */
//    @DeleteMapping
//    public ResultVo deleteUser(@RequestParam("userId") Long userId){
//        boolean b = userService.removeById(userId);
//        if(b){
//            return ResultUtils.success("删除用户成功");
//        }
//        return ResultUtils.error("删除用户失败");
//    }
    /**
     * 删除员工
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResultVo deleteUser(@PathVariable("userId") Long userId){
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
        //前端不展示密码
        list.getRecords().stream().forEach(item -> item.setPassword(""));
        return ResultUtils.success("查询成功",list);
    }
}
