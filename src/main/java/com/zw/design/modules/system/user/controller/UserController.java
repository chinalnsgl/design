package com.zw.design.modules.system.user.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseValidResponse;
import com.zw.design.modules.system.role.entity.SysRole;
import com.zw.design.modules.system.user.entity.SysUser;
import com.zw.design.modules.system.user.query.UserQuery;
import com.zw.design.modules.system.user.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sys/user")
public class UserController {

    private String prefix = "system/user";

    @Autowired
    private UserService userService;

    /**
     * 到达用户列表页面
     */
    @GetMapping("/page")
    @RequiresPermissions({"user:list"})
    public String users() {
        return prefix + "/list";
    }
    /**
     * 用户列表数据
     */
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions({"user:list"})
    public BaseResponse userList(UserQuery query) {
        BaseDataTableModel<SysUser> dto = userService.findUserByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }
    /**
     * 用户拥有角色
     */
    @GetMapping("/roles/{id}")
    @ResponseBody
    @RequiresPermissions({"user:list"})
    public List<SysRole> userRoles(@PathVariable Integer id) {
        return userService.findUserRoleById(id);
    }


    /**
     * 锁定/解锁用户
     */
    @ResponseBody
    @PostMapping("/status")
    @RequiresPermissions(value = {"user:lock","user:unlock"}, logical = Logical.OR)
    public BaseResponse updateUserStatus(@RequestParam("id")Integer id, @RequestParam("status") Integer status) {
        SysUser user = userService.updateUserStatus(id,status);
        return BaseResponse.toResponse(user);
    }


    /**
     * 添加页面
     */
    @GetMapping("/create")
    @RequiresPermissions({"user:create"})
    public String userCreate() {
        return  prefix + "/create";
    }
    /**
     * 帐号唯一验证
     */
    @ResponseBody
    @PostMapping("/checkUserNameUnique")
    @RequiresPermissions({"user:create"})
    public BaseValidResponse checkUserNameUnique(@RequestParam("userName") String userName) {
        SysUser user = userService.findByUserNameAndStatusGreaterThanEqual(userName, 1);
        return BaseValidResponse.toResponse(user);
    }
    /**
     * 保存用户
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions({"user:create"})
    public BaseResponse userSave(SysUser user, Integer[] role) {
        SysUser sysUser = userService.saveUser(user,role);
        return BaseResponse.toResponse(sysUser);
    }


    /**
     * 修改用户
     */
    @ResponseBody
    @PostMapping("/update")
    @RequiresPermissions({"user:edit"})
    public BaseResponse userUpdate(SysUser user, Integer[] role) {
        SysUser sysUser = userService.updateUser(user,role);
        return BaseResponse.toResponse(sysUser);
    }

}
