package com.xsh.blog.business.user.dto;

import com.xsh.blog.business.user.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 注册实体
 */
@Data
public class RegisterDto {

    @NotBlank(message="用户名为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotBlank(message="密码为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotBlank(message="昵称为空")
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @NotBlank(message="性别为空")
    @ApiModelProperty(value = "性别(0男1女)")
    private String sex;

    @ApiModelProperty(value = "个人简介")
    private String info;

    @NotBlank(message="手机号码为空")
    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    public User toUser(){
        return new User().builder().username(username).password(password).sex(sex).avatar(avatar).info(info).phoneNumber(phoneNumber).nickname(nickname).role("user").build();
    }

}
