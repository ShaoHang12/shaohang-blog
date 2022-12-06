package com.xsh.blog.business.article_comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.xsh.blog.business.article_comment.entity.ArticleComment;
import com.xsh.blog.business.article_comment.mapper.ArticleCommentMapper;
import com.xsh.blog.business.article_comment.service.IArticleCommentService;
import com.xsh.blog.common.service.impl.BasicServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 *

 */
@Service
public class ArticleCommentServiceImpl extends BasicServiceImpl<ArticleCommentMapper, ArticleComment> implements IArticleCommentService {

    @Autowired
    private ArticleCommentMapper articleCommentMapper;
    @Override
    public List<ArticleComment> getList(@Param(Constants.WRAPPER) Wrapper<ArticleComment> queryWrapper) {
        return articleCommentMapper.getList(queryWrapper);
    }
}
