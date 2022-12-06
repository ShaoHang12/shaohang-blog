package com.xsh.blog.common.utils;

import com.xsh.blog.business.user.entity.User;
import com.xsh.blog.common.bean.ApiCode;
import com.xsh.blog.common.excetion.ErrorRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
public class LocalUser {


    private static RedisUtils redisUtils;
    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    /**
     * 获取用户实例
     *
     * @return
     */
    public static User getUser() {
      try{
          ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
          String token = servletRequestAttributes.getRequest().getHeader("token"); //格式
          if (Objects.isNull(token)) {
              throw new ErrorRequestException(ApiCode.LOGIN_EXPIRED);
          }
          JwtUtil.checkToken(token);
          User user = redisUtils.getObject(token);
          if (user == null){
              throw new ErrorRequestException(ApiCode.LOGIN_EXPIRED);
          }
          return user;
      }catch (Exception e){
          throw new ErrorRequestException(ApiCode.LOGIN_EXPIRED);
      }
    }

    public static String getToken(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = servletRequestAttributes.getRequest().getHeader("token");
        return token;
    }


}
