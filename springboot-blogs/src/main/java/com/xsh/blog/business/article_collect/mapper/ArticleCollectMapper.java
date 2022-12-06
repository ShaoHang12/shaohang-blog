package com.xsh.blog.business.article_collect.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.xsh.blog.business.article_collect.entity.ArticleCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xsh.blog.business.article_collect.vo.ArticleCollectVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 *
 */
@Service
public interface ArticleCollectMapper extends BaseMapper<ArticleCollect> {

    IPage<ArticleCollectVo> getPage(IPage  page, @Param(Constants.WRAPPER) Wrapper<ArticleCollectVo> queryWrapper);

}
