package com.zw.design.controller;

import com.zw.design.dto.BaseResponse;
import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.dto.ValidResponse;
import com.zw.design.entity.SysPermission;
import com.zw.design.query.PermissionQuery;
import com.zw.design.service.PermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sys")
public class PermissionController {

    private String prefix = "system";

    @Autowired
    private PermissionService permissionService;

    /**
     * 权限列表页面
     */
    @GetMapping("/permissions")
    @RequiresPermissions({"permission:list"})
    public String permissions() {
        return prefix + "/permission/list";
    }
    /**
     * 权限列表数据
     */
    @ResponseBody
    @PostMapping("/permission/list")
    @RequiresPermissions({"permission:list"})
    public BaseResponse permissionList(PermissionQuery query) {
        DataTablesCommonDto<SysPermission> dto = permissionService.findPermissionByCriteria(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    /**
     * 删除权限, 修改状态为0
     */
    @ResponseBody
    @PostMapping("/permission/status")
    @RequiresPermissions({"permission:del"})
    public BaseResponse updatePermissionStatus(@RequestParam("id")Integer id) {
        SysPermission permission = permissionService.updatePermissionStatus(id,0);
        return BaseResponse.toResponse(permission);
    }


    /**
     * 权限添加页面
     */
    @GetMapping("/permission/create")
    @RequiresPermissions({"permission:create"})
    public String permissionCreate() {
        return prefix + "/permission/create";
    }
    /**
     * 子权限添加页面
     */
    @GetMapping("/permission/create/{id}")
    @RequiresPermissions({"permission:create"})
    public String permissionCreate(@PathVariable Integer id, Model model) {
        model.addAttribute("id", id);
        return prefix + "/permission/create";
    }
    /**
     * 权限名唯一验证
     */
    @ResponseBody
    @PostMapping("/permission/checkPermissionNameUnique")
    @RequiresPermissions({"permission:create"})
    public ValidResponse checkPermissionNameUnique(@RequestParam("permissionName") String permissionName) {
        SysPermission permission = permissionService.findByPermissionName(permissionName);
        return ValidResponse.toResponse(permission);
    }
    /**
     * 保存权限
     */
    @ResponseBody
    @PostMapping("/permission/save")
    @RequiresPermissions({"permission:create"})
    public BaseResponse permissionSave(SysPermission permission) {
        SysPermission sysPermission = permissionService.savePermission(permission);
        return BaseResponse.toResponse(sysPermission);
    }


    /**
     * 权限编辑页面
     */
    @GetMapping("/permission/edit/{id}")
    @RequiresPermissions({"permission:edit"})
    public String permissionEdit(@PathVariable Integer id, Model model) {
        SysPermission permission = permissionService.findbyId(id);
        model.addAttribute("permission", permission);
        return prefix + "/permission/edit";
    }
    /**
     * 修改权限
     */
    @ResponseBody
    @PostMapping("/permission/update")
    @RequiresPermissions({"permission:edit"})
    public BaseResponse permissionUpdate(SysPermission permission) {
        SysPermission sysPermission = permissionService.updatePermission(permission);
        return BaseResponse.toResponse(sysPermission);
    }


    /**
     * 所有权限数据
     */
    @ResponseBody
    @GetMapping("/permission/all")
    @RequiresPermissions({"role:create"})
    public List<SysPermission> allPermission() {
        return permissionService.findPermissionAll();
    }
}
