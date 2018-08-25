package com.zw.design.controller;

import com.zw.design.aspect.LogAnnotation;
import com.zw.design.dto.BaseResponse;
import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.dto.ValidResponse;
import com.zw.design.entity.SysUser;
import com.zw.design.query.UserQuery;
import com.zw.design.service.RoleService;
import com.zw.design.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sys")
public class UserController {

    private String prefix = "system";

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 用户列表页面
     */
    @GetMapping("/users")
    public String userList() {
        return prefix + "/user/list";
    }

    /**
     * 用户列表数据
     */
    @ResponseBody
    @PostMapping("/user/list")
    public BaseResponse userList(UserQuery query) {
        DataTablesCommonDto<SysUser> dto = userService.findUserByCriteria(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    /**
     * 锁定解锁用户
     */
    @ResponseBody
    @PostMapping("/user/status")
    @LogAnnotation(action = "锁定/解锁用户")
    public BaseResponse updateUserStatus(@RequestParam("id")Integer id, @RequestParam("status") Integer status) {
        SysUser user = userService.updateUserStatus(id,status);
        return BaseResponse.toResponse(user);
    }

    /**
     * 创建用户页面
     */
    @GetMapping("/user/create")
    public String userCreate(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return  prefix + "/user/create";
    }

    /**
     * 帐号唯一验证
     */
    @ResponseBody
    @PostMapping("/user/checkUserNameUnique")
    public ValidResponse checkUserNameUnique(@RequestParam("userName") String userName) {
        SysUser user = userService.findByUserName(userName);
        return ValidResponse.toResponse(user);
    }

    /**
     * 保存用户
     */
    @ResponseBody
    @PostMapping("/user/save")
    @LogAnnotation(action = "创建用户")
    public BaseResponse userCreate(SysUser user, Integer[] role) {
        SysUser sysUser = userService.saveUser(user,role);
        return BaseResponse.toResponse(sysUser);
    }

    /**
     * 编辑用户页面
     */
    @RequiresPermissions("user:edit")
    @GetMapping("/user/edit/{id}")
    public String userEdit(@PathVariable Integer id,Model model) {
        SysUser user = userService.findUserRoleById(id);
        model.addAttribute("user", user);
        return  prefix + "/user/edit";
    }

    /**
     * 更新用户
     */
    @ResponseBody
    @PostMapping("/user/update")
    @LogAnnotation(action = "修改用户")
    public BaseResponse userUpdate(SysUser user, Integer[] role) {
        SysUser sysUser = userService.updateUser(user,role);
        return BaseResponse.toResponse(sysUser);
    }
}