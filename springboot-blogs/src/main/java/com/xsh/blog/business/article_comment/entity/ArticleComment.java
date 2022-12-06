package com.xsh.blog.business.article_comment.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xsh.blog.common.bean.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="ArticleComment对象", description="")
public class ArticleComment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "评论编号")
    private String articleCommentId;

    @ApiModelProperty(value = "文章Id")
    private String articleId;

    @ApiModelProperty(value = "评论父Id")
    private String articleCommentPid;

    @ApiModelProperty(value = "评论用户Id")
    private String articleCommentUserId;

    @ApiModelProperty(value = "评论内容")
    private String articleCommentContent;

    @TableField(exist = false)
    @ApiModelProperty(value = "回复人昵称")
    private String nickname;

    @TableField(exist = false)
    @ApiModelProperty(value = "被回复人昵称")
    private String toUserName;

    @TableField(exist = false)
    @ApiModelProperty(value = "评论内容")
    private List<ArticleComment> articleCommentChildren;


    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createTime;




}
