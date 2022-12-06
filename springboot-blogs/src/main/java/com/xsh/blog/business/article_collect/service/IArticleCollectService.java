package com.xsh.blog.business.article_collect.service;

import com.xsh.blog.business.article_collect.entity.ArticleCollect;
import com.xsh.blog.business.article_collect.vo.ArticleCollectVo;
import com.xsh.blog.common.bean.BasePage;
import com.xsh.blog.common.service.BasicServiceInterface;

/**
 * <p>
 *  服务类
 * </p>
 *
 *
 */
public interface IArticleCollectService extends BasicServiceInterface<ArticleCollect> {

    /**
     * 获取收藏列表
     * @param param
     * @return
     */
    BasePage<ArticleCollectVo> getPage(ArticleCollectVo param);
}
