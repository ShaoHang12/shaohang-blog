package com.xsh.blog.business.article_comment.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.xsh.blog.business.article_comment.entity.ArticleComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 */
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {

    List<ArticleComment> getList(@Param(Constants.WRAPPER) Wrapper<ArticleComment> queryWrapper);
}
