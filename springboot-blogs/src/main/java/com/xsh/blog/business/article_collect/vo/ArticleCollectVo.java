package com.xsh.blog.business.article_collect.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleCollectVo {

    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "收藏Id")
    private String articleCollectId;

    @ApiModelProperty(value = "文章Id")
    private String articleId;

    @ApiModelProperty(value = "用户Id")
    private String userId;

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

    @ApiModelProperty(value = "文章时间")
    private LocalDateTime articleTime;

    @ApiModelProperty(value = "文章作者")
    private String articleAuthor;

    @ApiModelProperty(value = "备注")
    private String remark;

    @TableField(exist = false)
    @ApiModelProperty(value = "昵称")
    private String nickname;


}
