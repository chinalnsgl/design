package com.zw.design.controller;

import com.zw.design.dto.BaseResponse;
import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.dto.ValidResponse;
import com.zw.design.entity.SysRole;
import com.zw.design.entity.SysUser;
import com.zw.design.query.UserQuery;
import com.zw.design.service.LogService;
import com.zw.design.service.UserService;
import com.zw.design.utils.FileUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/sys")
public class UserController {

    private String prefix = "system";

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @Value("${upload.path}")
    private String uploadPath;


    /**
     * 到达用户列表页面
     */
    @GetMapping("/users")
    @RequiresPermissions({"user:list"})
    public String users() {
        return prefix + "/user/list";
    }
    /**
     * 用户列表数据
     */
    @ResponseBody
    @PostMapping("/user/list")
    @RequiresPermissions({"user:list"})
    public BaseResponse userList(UserQuery query) {
        DataTablesCommonDto<SysUser> dto = userService.findUserByCriteria(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }
    /**
     * 用户拥有角色
     */
    @GetMapping("/user/roles/{id}")
    @ResponseBody
    @RequiresPermissions({"user:list"})
    public List<SysRole> userRoles(@PathVariable Integer id) {
        return userService.findUserRoleById(id);
    }


    /**
     * 锁定/解锁用户
     */
    @ResponseBody
    @PostMapping("/user/status")
    @RequiresPermissions(value = {"user:lock","user:unlock"}, logical = Logical.OR)
    public BaseResponse updateUserStatus(@RequestParam("id")Integer id, @RequestParam("status") Integer status, HttpServletRequest request) {
        SysUser user = userService.updateUserStatus(id,status);
        logService.saveLog((status == 1 ? "解锁用户：" : "锁定用户：") + user.getName(), request);
        return BaseResponse.toResponse(user);
    }


    /**
     * 添加页面
     */
    @GetMapping("/user/create")
    @RequiresPermissions({"user:create"})
    public String userCreate() {
        return  prefix + "/user/create";
    }
    /**
     * 帐号唯一验证
     */
    @ResponseBody
    @PostMapping("/user/checkUserNameUnique")
    @RequiresPermissions({"user:create"})
    public ValidResponse checkUserNameUnique(@RequestParam("userName") String userName) {
        SysUser user = userService.findByUserName(userName);
        return ValidResponse.toResponse(user);
    }
    /**
     * 保存用户
     */
    @ResponseBody
    @PostMapping("/user/save")
    @RequiresPermissions({"user:create"})
    public BaseResponse userSave(SysUser user, Integer[] role, HttpServletRequest request) {
        SysUser sysUser = userService.saveUser(user,role);
        logService.saveLog("创建用户：" + sysUser.getName(), request);
        return BaseResponse.toResponse(sysUser);
    }


    /**
     * 修改用户
     */
    @ResponseBody
    @PostMapping("/user/update")
    @RequiresPermissions({"user:edit"})
    public BaseResponse userUpdate(SysUser user, Integer[] role, HttpServletRequest request) {
        SysUser sysUser = userService.updateUser(user,role);
        logService.saveLog("修改用户：" + sysUser.getName(), request);
        return BaseResponse.toResponse(sysUser);
    }


    /**
     * 所有用户列表
     */
    @ResponseBody
    @GetMapping("/user/all")
    public BaseResponse getAll() {
        List<SysUser> users = userService.findUserList();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(users);
        return baseResponse;
    }


    /**
     * 修改头像
     */
    @ResponseBody
    @PostMapping("/user/updateImage")
    public BaseResponse updateImage(String imageData) {
        String imgName = UUID.randomUUID().toString() + ".png";
        SysUser user = userService.updateImage("/files/" + imgName);
        try {
            FileUtils.decodeBase64DataURLToImage(imageData, uploadPath, imgName);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setContent(user.getImg());
            return baseResponse;
        } catch (IOException e) {
            return BaseResponse.STATUS_400;
        }
    }
}
