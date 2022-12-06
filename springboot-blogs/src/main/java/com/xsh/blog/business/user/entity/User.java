package com.xsh.blog.business.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xsh.blog.common.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("user")
@ApiModel(value="User对象", description="")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "编号")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String username;


    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "性别(0男1女)")
    private String sex;

    @ApiModelProperty(value = "个人简介")
    private String info;

    @ApiModelProperty(value = "角色")
    private String role;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

}
