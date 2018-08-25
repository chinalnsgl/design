package com.zw.design.controller;

import com.zw.design.aspect.LogAnnotation;
import com.zw.design.dto.BaseResponse;
import com.zw.design.dto.DataTablesCommonDto;
import com.zw.design.dto.ValidResponse;
import com.zw.design.entity.SysRole;
import com.zw.design.query.RoleQuery;
import com.zw.design.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sys")
public class RoleController {

    private String prefix = "system";

    @Autowired
    private RoleService roleService;

    /**
     * 角色列表页面
     */
    @GetMapping("/roles")
    public String roleList() {
        return prefix + "/role/list";
    }

    /**
     * 角色列表数据
     */
    @ResponseBody
    @PostMapping("/role/list")
    public BaseResponse roleList(RoleQuery query) {
        DataTablesCommonDto<SysRole> dto = roleService.findRoleByCriteria(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }

    /**
     * 删除角色， 修改状态为0
     */
    @ResponseBody
    @PostMapping("/role/status")
    @LogAnnotation(action = "删除角色")
    public BaseResponse updateRoleStatus(@RequestParam("id")Integer id, @RequestParam("status") Integer status) {
        SysRole role = roleService.updateRoleStatus(id, status);
        return BaseResponse.toResponse(role);
    }

    /**
     * 创建角色页面
     */
    @GetMapping("/role/create")
    public String roleCreate() {
        return  prefix + "/role/create";
    }

    /**
     * 角色唯一验证
     */
    @ResponseBody
    @PostMapping("/role/checkRoleNameUnique")
    public ValidResponse checkRoleNameUnique(@RequestParam("roleName") String roleName) {
        SysRole role = roleService.findByRoleName(roleName);
        return ValidResponse.toResponse(role);
    }

    /**
     * 创建角色
     */
    @ResponseBody
    @PostMapping("/role/save")
    @LogAnnotation(action = "新建角色")
    public BaseResponse roleCreate(SysRole role, Integer [] permissions) {
        SysRole sysRole = roleService.saveRole(role,permissions);
        return BaseResponse.toResponse(sysRole);
    }

    /**
     * 编辑角色页面
     */
    @GetMapping("/role/edit/{id}")
    public String roleEdit(@PathVariable Integer id, Model model) {
        SysRole role = roleService.findRoleById(id);
        model.addAttribute("role", role);
        return  prefix + "/role/edit";
    }

    /**
     * 更新角色
     */
    @ResponseBody
    @PostMapping("/role/update")
    @LogAnnotation(action = "修改角色")
    public BaseResponse roleUpdate(SysRole role, Integer [] permissions) {
        SysRole sysRole = roleService.updateRole(role,permissions);
        return BaseResponse.toResponse(sysRole);
    }

}
