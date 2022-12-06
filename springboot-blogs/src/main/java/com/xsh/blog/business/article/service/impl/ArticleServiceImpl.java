package com.xsh.blog.business.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xsh.blog.business.article.entity.Article;
import com.xsh.blog.business.article.mapper.ArticleMapper;
import com.xsh.blog.business.article.service.IArticleService;
import com.xsh.blog.business.article.vo.ArticleVo;
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
public class ArticleServiceImpl extends BasicServiceImpl<ArticleMapper, Article> implements IArticleService {


    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public BasePage<ArticleVo> getPage(ArticleVo param) {
        Page<ArticleVo> pageModel = PageUtil.getPageModel(param);
        QueryWrapper<ArticleVo> articleQueryWrapper = PageUtil.queryWrapper(param);
        IPage<ArticleVo> page = articleMapper.getPage(pageModel, articleQueryWrapper);
        BasePage conventPage = new BasePage(page.getRecords(),page.getTotal(),page.getSize(),page.getCurrent(),page.getPages());
        return conventPage;
    }
}
