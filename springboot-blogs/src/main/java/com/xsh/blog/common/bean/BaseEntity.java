package com.xsh.blog.common.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: StudentHeart
 * @description: Entity基类
 *
 **/
@Data
public class BaseEntity  {

    /** 页数 */
    @TableField(exist = false)
    @ApiModelProperty(value = "页数")
    @JsonIgnore
    private  Integer page;

    /** 数量 */
    @TableField(exist = false)
    @ApiModelProperty(value = "数量")
    @JsonIgnore
    private  Integer limit;


    /** 创建者 */
    @ApiModelProperty(value = "创建者")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private  String createUsername;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @JsonIgnore
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    /** 更新者 */
    @ApiModelProperty(value = "更新者")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private  String updateUsername;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private  LocalDateTime updateTime;

    /** 删除 */
    @ApiModelProperty(value = "删除")
//    @TableLogic
    @JsonIgnore
    private  Integer delFlag;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;

}
