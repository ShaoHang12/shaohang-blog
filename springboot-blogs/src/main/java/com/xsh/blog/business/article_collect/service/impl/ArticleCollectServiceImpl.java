package com.xsh.blog.business.article_collect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xsh.blog.business.article_collect.entity.ArticleCollect;
import com.xsh.blog.business.article_collect.mapper.ArticleCollectMapper;
import com.xsh.blog.business.article_collect.service.IArticleCollectService;
import com.xsh.blog.business.article_collect.vo.ArticleCollectVo;
import com.xsh.blog.common.bean.BasePage;
import com.xsh.blog.common.service.impl.BasicServiceImpl;
import com.xsh.blog.common.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 *
 */
@Service
public class ArticleCollectServiceImpl extends BasicServiceImpl<ArticleCollectMapper, ArticleCollect> implements IArticleCollectService {

    @Autowired
    private ArticleCollectMapper articleCollectMapper;
    @Override
    public BasePage<ArticleCollectVo> getPage(ArticleCollectVo param) {
        Page<ArticleCollectVo> pageModel = PageUtil.getPageModel(param);
        QueryWrapper<ArticleCollectVo> articleQueryWrapper = PageUtil.queryWrapper(param);
        IPage<ArticleCollectVo> page = articleCollectMapper.getPage(pageModel, articleQueryWrapper);
        BasePage conventPage = new BasePage(page.getRecords(),page.getTotal(),page.getSize(),page.getCurrent(),page.getPages());
        return conventPage;
    }
}
