package com.xsh.blog.business.article_collect.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ArticleCollectAddDto {

    @ApiModelProperty(value = "文章Id")
    private String articleId;


}
