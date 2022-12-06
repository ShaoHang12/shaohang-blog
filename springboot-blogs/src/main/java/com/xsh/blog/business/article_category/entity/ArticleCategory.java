package com.xsh.blog.business.article_category.entity;

import java.time.LocalDateTime;

import com.xsh.blog.common.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="ArticleCategory对象", description="")
public class ArticleCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章分类编号")
    private String articleCategoryId;

    @ApiModelProperty(value = "文章分类名称")
    private String articleCategoryName;

    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
    private Integer delFlag;

    @ApiModelProperty(value = "创建者")
    private String createUsername;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private String updateUsername;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;


}
