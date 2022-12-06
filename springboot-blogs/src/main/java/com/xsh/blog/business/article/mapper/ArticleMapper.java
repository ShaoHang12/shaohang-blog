package com.xsh.blog.business.article.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.xsh.blog.business.article.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xsh.blog.business.article.vo.ArticleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 */
@Service
public interface ArticleMapper extends BaseMapper<Article> {

    IPage<ArticleVo> getPage(IPage  page, @Param(Constants.WRAPPER) Wrapper<ArticleVo> queryWrapper);
}
