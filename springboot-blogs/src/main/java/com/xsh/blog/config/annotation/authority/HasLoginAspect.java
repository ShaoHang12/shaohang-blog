package com.xsh.blog.config.annotation.authority;

import com.xsh.blog.common.utils.LocalUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class HasLoginAspect {

    @Pointcut("@annotation(com.xsh.blog.config.annotation.authority.HasLogin)")
    public void hasLogin(){}

    @Before("@annotation(hasLogin)")
    public void Interceptor(JoinPoint joinPoint, HasLogin hasLogin){
        /**
         * 获取当前用户实例
         * 如 未登录自动提示信息
         */
        LocalUser.getUser();
    }


}
