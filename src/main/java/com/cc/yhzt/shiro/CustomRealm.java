package com.cc.yhzt.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cc.yhzt.constant.Constant;
import com.cc.yhzt.entity.AccountData;
import com.cc.yhzt.entity.Players;
import com.cc.yhzt.service.IAccountDataService;
import com.cc.yhzt.service.IPlayersService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @author :cc
 * @date : 2019/6/10
 */
public class CustomRealm extends AuthorizingRealm {
    private String ClassName =this.getClass().getName();

    @Autowired
    @Lazy   //必须懒加载，否则Ehcache缓存注解及事务管理注解无效
    private IPlayersService playersService;
    @Autowired
    @Lazy
    private IAccountDataService accountDataService;

    {
        super.setName(ClassName);
    }

    /**
     * 权限处理
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String gamename = (String) principals.getPrimaryPrincipal();
        // 从数据库或者缓存中获得角色数据
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        Players one = playersService.getOne(new QueryWrapper<>(new Players().setName(gamename)));
        AccountData account = accountDataService.getOne(new QueryWrapper<>(new AccountData().setName(one.getAccountName())));
        if (account.getAccessLevel() >= 5) {
            roles.add(Constant.ADMIN_ACCOUNT);
        } else {
            roles.add(Constant.PLAYER_ACCOUNT);
        }
        roles.add(Constant.ALL_ACCOUNT);
        //上面的service层方法需要自己写
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证处理
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1.从主体传过来的认证信息中，获得用户名
        String gamename = (String) token.getPrincipal();
        String username = new String((char[])token.getCredentials()).toLowerCase();
        // 2.通过用户名到数据库中获取凭证
        Players player = playersService.getOne(new QueryWrapper<>(new Players().setAccountName(username).setName(gamename)));
        AccountData accountData = accountDataService.getById(player.getAccountId());
        if (0 == accountData.getActivated()) {
            return null;
        }
        if (null == player) {
            return null;
        }
        String password = player.getName().toLowerCase();
        if(password == null) {
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(password, username, ClassName);
        return simpleAuthenticationInfo;
    }
}