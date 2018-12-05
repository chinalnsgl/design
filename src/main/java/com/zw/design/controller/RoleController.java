package com.zw.design.controller;

import com.zw.design.dto.BaseResponse;
import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.dto.ValidResponse;
import com.zw.design.entity.SysPermission;
import com.zw.design.entity.SysRole;
import com.zw.design.query.RoleQuery;
import com.zw.design.service.LogService;
import com.zw.design.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/sys")
public class RoleController {

    private String prefix = "system";

    @Autowired
    private RoleService roleService;
    @Autowired
    private LogService logService;

    /**
     * 角色列表页面
     */
    @GetMapping("/roles")
    @RequiresPermissions({"role:list"})
    public String roles() {
        return prefix + "/role/list";
    }
    // 角色列表数据
    /*@ResponseBody
    @PostMapping("/role/list")
    @RequiresPermissions({"role:list"})
    public BaseResponse roleList(RoleQuery query) {
        DataTablesCommonDto<SysRole> dto = roleService.findRoleByCriteria(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }*/
    /**
     * 角色拥有权限数据
     */
    @ResponseBody
    @GetMapping("role/permissions/{id}")
    @RequiresPermissions({"role:list"})
    public List<SysPermission> rolePermissions(@PathVariable Integer id) {
        return roleService.findRolePermissionById(id);
    }


    /**
     * 删除角色， 修改状态为0
     */
    @ResponseBody
    @PostMapping("/role/status")
    @RequiresPermissions({"role:del"})
    public BaseResponse updateRoleStatus(@RequestParam("id")Integer id,  HttpServletRequest request) {
        SysRole role = roleService.updateRoleStatus(id, 0);
        logService.saveLog("删除角色：" + role.getRoleName(), request);
        return BaseResponse.toResponse(role);
    }

    /**
     * 创建角色页面
     */
    @GetMapping("/role/create")
    @RequiresPermissions({"role:create"})
    public String roleCreate() {
        return  prefix + "/role/create";
    }

    /**
     * 创建角色页面
     */
    @GetMapping("/role/create/{id}")
    @RequiresPermissions({"role:create"})
    public String roleCreate(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("id", id);
        return prefix + "/role/create";
    }

    /**
     * 角色唯一验证
     */
    @ResponseBody
    @PostMapping("/role/checkRoleNameUnique")
    @RequiresPermissions({"role:create"})
    public ValidResponse checkRoleNameUnique(@RequestParam("roleName") String roleName) {
        SysRole role = roleService.findByRoleName(roleName);
        return ValidResponse.toResponse(role);
    }
    /**
     * 保存角色
     */
    @ResponseBody
    @PostMapping("/role/save")
    @RequiresPermissions({"role:create"})
    public BaseResponse roleSave(SysRole role, Integer [] permissions, HttpServletRequest request) {
        SysRole sysRole = roleService.saveRole(role,permissions);
        logService.saveLog("新建角色：" + role.getRoleName(), request);
        return BaseResponse.toResponse(sysRole);
    }

    /*@GetMapping("/role/edit/{id}")
    @RequiresPermissions({"role:edit"})
    public String roleEdit(@PathVariable Integer id, Model model) {
        SysRole role = roleService.findRoleById(id);
        model.addAttribute("role", role);
        return  prefix + "/role/edit";
    }*/

    /**
     * 修改角色
     */
    @ResponseBody
    @PostMapping("/role/update")
    @RequiresPermissions({"role:edit"})
    public BaseResponse roleUpdate(SysRole role, Integer [] permissions, HttpServletRequest request) {
        SysRole sysRole = roleService.updateRole(role,permissions);
        logService.saveLog("修改角色：" + role.getRoleName(), request);
        return BaseResponse.toResponse(sysRole);
    }


    /**
     * 获取所有角色
     */
    @ResponseBody
    @GetMapping("/role/all")
    @RequiresPermissions({"user:create"})
    public List<SysRole> findAllRole() {
        return roleService.findAll();
    }
}
