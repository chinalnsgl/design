package com.zw.design.modules.system.permission.controller;

import com.zw.design.base.BaseResponse;
import com.zw.design.base.BaseValidResponse;
import com.zw.design.modules.system.permission.entity.SysPermission;
import com.zw.design.modules.system.permission.service.PermissionService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sys/permission")
public class PermissionController {

    private String prefix = "system/permission";

    @Autowired
    private PermissionService permissionService;

    /**
     * 权限列表页面
     */
    @GetMapping("/page")
    @RequiresPermissions({"permission:list"})
    public String permissions() {
        return prefix + "/list";
    }

    /**
     * 删除权限, 修改状态为0
     */
    @ResponseBody
    @PostMapping("/status")
    @RequiresPermissions({"permission:del"})
    public BaseResponse updatePermissionStatus(@RequestParam("id")Integer id) {
        SysPermission permission = permissionService.updatePermissionStatus(id,0);
        return BaseResponse.toResponse(permission);
    }


    /**
     * 权限添加页面
     */
    @GetMapping("/create")
    @RequiresPermissions({"permission:create"})
    public String permissionCreate() {
        return prefix + "/create";
    }
    /**
     * 子权限添加页面
     */
    @GetMapping("/create/{id}")
    @RequiresPermissions({"permission:create"})
    public String permissionCreate(@PathVariable Integer id, Model model) {
        model.addAttribute("id", id);
        return prefix + "/create";
    }

    /**
     * 权限名唯一验证
     */
    @ResponseBody
    @PostMapping("/checkPermissionNameUnique")
    @RequiresPermissions({"permission:create"})
    public BaseValidResponse checkPermissionNameUnique(@RequestParam("permissionName") String permissionName, @RequestParam(value = "id", required = false) Integer id) {
        SysPermission permission = permissionService.findByPermissionNameAndStatus(permissionName, 1);
        if (id == null) {
            return BaseValidResponse.toResponse(permission);
        }
        if (permission == null || permission.getId() == id) {
            return BaseValidResponse.SUCCESS;
        }
        return BaseValidResponse.FAILE;
    }
    /**
     * 保存权限
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions({"permission:create"})
    public BaseResponse permissionSave(SysPermission permission) {
        SysPermission sysPermission = permissionService.savePermission(permission);
        return BaseResponse.toResponse(sysPermission);
    }


    /*
    @GetMapping("/edit/{id}")
    @RequiresPermissions({"permission:edit"})
    public String permissionEdit(@PathVariable Integer id, Model model) {
        SysPermission permission = permissionService.findById(id);
        model.addAttribute("permission", permission);
        return prefix + "/edit";
    }*/
    /**
     * 修改权限
     */
    @ResponseBody
    @PostMapping("/update")
    @RequiresPermissions({"permission:edit"})
    public BaseResponse permissionUpdate(SysPermission permission) {
        SysPermission sysPermission = permissionService.updatePermission(permission);
        return BaseResponse.toResponse(sysPermission);
    }


    /**
     * 所有权限数据
     */
    @ResponseBody
    @GetMapping("/all")
    @RequiresPermissions(value = {"role:create", "permission:list"}, logical = Logical.OR)
    public List<SysPermission> allPermission() {
        return permissionService.findPermissionAll();
    }
}
