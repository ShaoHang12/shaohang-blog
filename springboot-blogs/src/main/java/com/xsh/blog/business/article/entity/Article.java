package com.xsh.blog.business.article.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xsh.blog.common.bean.BaseEntity;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Article对象", description="")
public class Article extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "文章编号")
    private String articleId;

    @ApiModelProperty(value = "文章封面")
    private String articleCover;

    @ApiModelProperty(value = "文章标题")
    private String articleTitle;

    @ApiModelProperty(value = "文章简介")
    private String articleBrief;

    @ApiModelProperty(value = "文章点赞数")
    private Integer articleLove;

    @ApiModelProperty(value = "文章浏览量")
    private Integer articleLook;

    @ApiModelProperty(value = "文章内容")
    private String articleContent;

    @JsonIgnore
    @ApiModelProperty(value = "文章类型Id")
    private String articleCategoryId;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @ApiModelProperty(value = "文章时间")
    private LocalDateTime articleTime;

    @ApiModelProperty(value = "文章作者")
    private String articleAuthor;

    @ApiModelProperty(value = "备注")
    private String remark;

    private String articleCg;


}
