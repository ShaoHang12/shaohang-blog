package com.xsh.blog.business.article_collect.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xsh.blog.business.article_collect.dto.ArticleCollectAddDto;
import com.xsh.blog.common.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="ArticleCollect对象", description="")
public class ArticleCollect extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "收藏Id")
    private String articleCollectId;

    @ApiModelProperty(value = "文章Id")
    private String articleId;

    @ApiModelProperty(value = "用户Id")
    private String userId;

    public static ArticleCollect toArticleCollect(ArticleCollectAddDto dto){
        return new ArticleCollect().builder().articleId(dto.getArticleId()).build();
    }


}
