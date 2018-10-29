package com.zw.design.controller;

import com.zw.design.dto.BaseResponse;
import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.dto.ValidResponse;
import com.zw.design.entity.SysPermission;
import com.zw.design.query.PermissionQuery;
import com.zw.design.service.PermissionService;
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
     * 所有权限数据
     */
    @ResponseBody
    @GetMapping("/permission/all")
    public List<SysPermission> allPermission() {
        return permissionService.findPermissionAll();
    }

    /**
     * 角色拥有权限数据
     */
    @ResponseBody
    @GetMapping("/permission/{id}")
    public List<SysPermission> allPermission(@PathVariable Integer id) {
        return permissionService.findPermissionByRoleId(id);
    }

    /**
     * 权限列表页面
     */
    @GetMapping("/permissions")
    public String permissionList() {
        return prefix + "/permission/list";
    }

    /**
     * 权限列表数据
     */
    @ResponseBody
    @PostMapping("/permission/list")
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
//    @LogAnnotation(action = "删除权限")
    public BaseResponse permissionDel(@RequestParam("id")Integer id, @RequestParam("status") Integer status) {
        SysPermission permission = permissionService.updatePermissionStatus(id,status);
        return BaseResponse.toResponse(permission);
    }

    /**
     * 权限添加页面
     */
    @GetMapping("/permission/create")
    public String permissionCreate() {
        return prefix + "/permission/create";
    }

    /**
     * 子权限添加页面
     */
    @GetMapping("/permission/create/{id}")
    public String permissionCreate(@PathVariable Integer id, Model model) {
        model.addAttribute("id", id);
        return prefix + "/permission/create";
    }

    /**
     * 权限名唯一验证
     */
    @ResponseBody
    @PostMapping("/permission/checkPermissionNameUnique")
    public ValidResponse checkPermissionNameUnique(@RequestParam("permissionName") String permissionName) {
        SysPermission permission = permissionService.findByPermissionName(permissionName);
        return ValidResponse.toResponse(permission);
    }

    /**
     * 添加权限
     */
    @ResponseBody
    @PostMapping("/permission/save")
//    @LogAnnotation(action = "添加权限")
    public BaseResponse permissionCreate(SysPermission permission) {
        SysPermission sysPermission = permissionService.savePermission(permission);
        return BaseResponse.toResponse(sysPermission);
    }

    /**
     * 权限编辑页面
     */
    @GetMapping("/permission/edit/{id}")
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
//    @LogAnnotation(action = "修改权限")
    public BaseResponse permissionEdit(SysPermission permission) {
        SysPermission sysPermission = permissionService.updatePermission(permission);
        return BaseResponse.toResponse(sysPermission);
    }
}
