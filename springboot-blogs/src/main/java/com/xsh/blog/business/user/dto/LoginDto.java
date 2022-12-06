package com.xsh.blog.business.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 登录
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotBlank(message="用户名为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotBlank(message="密码为空")
    @ApiModelProperty(value = "密码")
    private String password;


}
