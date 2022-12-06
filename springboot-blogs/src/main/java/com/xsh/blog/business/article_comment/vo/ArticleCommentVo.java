package com.xsh.blog.business.article_comment.vo;

import com.xsh.blog.business.article_comment.entity.ArticleComment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ArticleCommentVo extends ArticleComment {

    @ApiModelProperty(value = "回复人昵称")
    private String userNickname;

    @ApiModelProperty(value = "被回复人昵称")
    private String toUserName;


}
