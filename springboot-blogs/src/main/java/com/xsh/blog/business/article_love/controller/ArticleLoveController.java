package com.xsh.blog.business.article_love.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xsh.blog.business.article.entity.Article;
import com.xsh.blog.business.article.service.IArticleService;
import com.xsh.blog.business.article_love.entity.ArticleLove;
import com.xsh.blog.business.article_love.service.IArticleLoveService;
import com.xsh.blog.common.bean.ApiResult;
import com.xsh.blog.common.utils.LocalUser;
import com.xsh.blog.config.annotation.authority.HasLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 */
@Api(tags = "业务-文章点赞")
@RestController
@RequestMapping("/business/article-love")
public class ArticleLoveController {


    private IArticleLoveService articleLoveService;
    private IArticleService articleService;
    @Autowired
    public ArticleLoveController(IArticleLoveService articleLoveService, IArticleService articleService) {
        this.articleLoveService = articleLoveService;
        this.articleService = articleService;
    }


    @HasLogin
    @ApiOperation(value = "点赞")
    @GetMapping("/love")
    public ApiResult love(@RequestParam String articleId){
        String userId = LocalUser.getUser().getUserId();
        Article article = articleService.getById(articleId);
        if (article == null){
            return ApiResult.fail("该文章不存在");
        }
        ArticleLove oldLove = articleLoveService.getOne(Wrappers.<ArticleLove>lambdaQuery().eq(ArticleLove::getUserId, userId).eq(ArticleLove::getArticleId, articleId));
        if (oldLove == null){
            // 新增 点赞记录
            ArticleLove articleLove = new ArticleLove().builder().articleId(articleId).userId(userId).build();
            articleLoveService.save(articleLove);
            // 新增 文章点赞数
            article.setArticleLove(article.getArticleLove() + 1);
            articleService.updateById(article);
            return ApiResult.ok();
        }else{
            return ApiResult.fail("该文章已点赞过啦，不能重复点赞哦");
        }
    }



    @HasLogin
    @ApiOperation(value = "取消点赞")
    @GetMapping("/noLove")
    public ApiResult noLove(@RequestParam String articleId){
        String userId = LocalUser.getUser().getUserId();
        ArticleLove articleLove = articleLoveService.getOne(Wrappers.<ArticleLove>lambdaQuery().eq(ArticleLove::getArticleId, articleId).eq(ArticleLove::getUserId, userId));
        Article article = articleService.getById(articleLove.getArticleId());
        if (articleLove != null){
            // 删除点赞记录
            articleLoveService.removeById(articleLove.getArticleLoveId());
            // 减少点赞记录
            article.setArticleLove(article.getArticleLove() - 1);
            articleService.updateById(article);
            return ApiResult.ok();
        }else{
            return ApiResult.fail("取消点赞失败");
        }

    }


}
