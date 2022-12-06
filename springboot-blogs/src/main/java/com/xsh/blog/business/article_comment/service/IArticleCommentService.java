package com.xsh.blog.business.article_comment.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.xsh.blog.business.article_comment.entity.ArticleComment;
import com.xsh.blog.common.service.BasicServiceInterface;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 *
 */
public interface IArticleCommentService extends BasicServiceInterface<ArticleComment> {

    List<ArticleComment> getList(@Param(Constants.WRAPPER) Wrapper<ArticleComment> queryWrapper);

}
