package com.xsh.blog.business.article_love.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="ArticleLove对象", description="")
public class ArticleLove extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "点赞Id")
    private String articleLoveId;

    @ApiModelProperty(value = "文章Id")
    private String articleId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "点赞类型")
    private String articleLoveType;


}
