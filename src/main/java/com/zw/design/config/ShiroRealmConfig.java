package com.zw.design.config;

import com.zw.design.entity.SysPermission;
import com.zw.design.entity.SysRole;
import com.zw.design.entity.SysUser;
import com.zw.design.service.PermissionService;
import com.zw.design.service.RoleService;
import com.zw.design.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class ShiroRealmConfig extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        log.info("doGetAuthorizationInfo+"+principalCollection.toString());
        //获取登录用户
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if ("admin".equals(user.getUserName())) {
            List<SysRole> roleList = roleService.findAll();
            for (SysRole sysRole : roleList) {
                simpleAuthorizationInfo.addRole(sysRole.getRoleName());
            }
            simpleAuthorizationInfo.addRole("8");
            List<SysPermission> permissionList = permissionService.findPermissionAll();
            for (SysPermission permission : permissionList) {
                simpleAuthorizationInfo.addStringPermission(permission.getPermissionName());
            }
        } else {
            //添加角色和权限
            for (SysRole role:user.getRoles()) {
                //添加角色
                simpleAuthorizationInfo.addRole(role.getRoleName());
                for (SysPermission permission:role.getPermissions()) {
                    //添加权限
                    simpleAuthorizationInfo.addStringPermission(permission.getPermissionName());
                }
            }
        }
        Session session = SecurityUtils.getSubject().getSession();
        session.setTimeout(3600000);
        session.setAttribute("roles", simpleAuthorizationInfo.getRoles());
        session.setAttribute("permissions", simpleAuthorizationInfo.getStringPermissions());
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        SysUser user = userService.findByUserName(userName);

        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误！");
        }
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("user", user);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getUserName()), getName());
        return info;
    }

}
