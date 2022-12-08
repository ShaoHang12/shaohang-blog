package com.xsh.blog.business.article.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xsh.blog.business.article.entity.Article;
import com.xsh.blog.business.article.vo.ArticleVo;
import com.xsh.blog.business.user.entity.User;
import com.xsh.blog.common.bean.BasePage;
import com.xsh.blog.common.service.BasicServiceInterface;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface IArticleService extends BasicServiceInterface<Article> {

    /**
     * 获取文章列表
     * @param param
     * @return
     */
    BasePage<ArticleVo> getPage(ArticleVo param);
}
