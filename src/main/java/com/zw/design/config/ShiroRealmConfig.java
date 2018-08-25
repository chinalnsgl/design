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
        session.setAttribute("roles", simpleAuthorizationInfo.getRoles());
        session.setAttribute("permissions", simpleAuthorizationInfo.getStringPermissions());
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        log.info("doGetAuthenticationInfo +"  + authenticationToken.toString());
        String userName = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());

        SysUser user = userService.findByUserName(userName);

        //这里校验了，CredentialsMatcher就不需要了，如果这里不校验，调用CredentialsMatcher校验
        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误！");
        }
        /*if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        if (user.getStatus() == 1) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }*/
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("user", user);
        //也可以在此处更新最后登录时间（或在登录方法实现）
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getUserName()), getName());
        return info;
    }

}
