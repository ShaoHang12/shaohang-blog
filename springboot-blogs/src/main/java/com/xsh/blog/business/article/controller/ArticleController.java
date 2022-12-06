package com.xsh.blog.business.article.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xsh.blog.business.article.entity.Article;
import com.xsh.blog.business.article.service.IArticleService;
import com.xsh.blog.business.article.vo.ArticleVo;
import com.xsh.blog.business.article_category.service.IArticleCategoryService;
import com.xsh.blog.business.article_comment.entity.ArticleComment;
import com.xsh.blog.business.article_comment.service.IArticleCommentService;
import com.xsh.blog.business.article_love.entity.ArticleLove;
import com.xsh.blog.business.article_love.service.IArticleLoveService;
import com.xsh.blog.common.bean.ApiResult;
import com.xsh.blog.common.bean.BasePage;
import com.xsh.blog.common.utils.GetArticleCommentList;
import com.xsh.blog.common.utils.LocalUser;
import com.xsh.blog.config.annotation.authority.HasLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 */
@Api(tags = "业务-博客文章")
@RestController
@RequestMapping("/business/article")
public class ArticleController {

    /**
     * 文章
     */
    private IArticleService articleService;
    /**
     * 文章评论
     */
    private IArticleCommentService articleCommentService;

    /**
     * 文章分类
     */
    private IArticleCategoryService articleCategoryService;

    /**
     * 文章点赞
     */
    private IArticleLoveService articleLoveService;

    @Autowired
    public ArticleController(IArticleService articleService, IArticleCommentService articleCommentService, IArticleCategoryService articleCategoryService, IArticleLoveService articleLoveService) {
        this.articleService = articleService;
        this.articleCommentService = articleCommentService;
        this.articleCategoryService = articleCategoryService;
        this.articleLoveService = articleLoveService;
    }




    @ApiOperation(value = "获取文章列表")
    @GetMapping("/list")
    public ApiResult list(ArticleVo param){
        try{
            // 显示登录的列表
            String userId = LocalUser.getUser().getUserId();
            BasePage<ArticleVo> page = articleService.getPage(param);
            List<ArticleVo> records = page.getRecords();
            for (ArticleVo record : records) {
                ArticleLove love = articleLoveService.getOne(Wrappers.<ArticleLove>lambdaQuery().eq(ArticleLove::getArticleId, record.getArticleId()).eq(ArticleLove::getUserId, userId));
                if (love != null){
                    record.setLove(true);
                }else{
                    record.setLove(false);
                }
            }
            return ApiResult.ok(page);
        }catch (Exception e){
            // 未登录显示列表
            BasePage<ArticleVo> page = articleService.getPage(param);
            return ApiResult.ok(page);
        }
    }

    @ApiOperation(value = "发布文章列表")
    @PutMapping("/publish")
    public ApiResult list(@RequestBody Article param){
        param.setArticleCg("1");
        return ApiResult.result(articleService.updateById(param));
    }



    @ApiOperation(value = "获取文章评论列表")
    @GetMapping("/getArticleCommentList")
    public ApiResult list(ArticleComment param){
        // 增加此次文章浏览次数 + 1
        Article article = articleService.getById(param.getArticleId());
        if (article != null){
            article.setArticleLook(article.getArticleLook() + 1);
            articleService.updateById(article);
        }
        // 查询评论列表
        List<ArticleComment> list = articleCommentService.getList(Wrappers.<ArticleComment>lambdaQuery().eq(ArticleComment::getArticleId,param.getArticleId()));
        List<ArticleComment> collect = list.stream().sorted(Comparator.comparing(ArticleComment::getCreateTime).reversed()).collect(Collectors.toList());
        List<ArticleComment> voList = GetArticleCommentList.getDataList(collect);
        return ApiResult.ok(voList);
    }

    @ApiOperation(value = "获取详细信息")
    @GetMapping(value = "/{id}")
    public ApiResult getInfo(@PathVariable("id") String id)
    {
        Article dutyActive = articleService.getById(id);
        dutyActive.setArticleContent(dutyActive.getArticleContent().replaceAll("<img", "<img style=\"max-width:100%;\" " ));
        return ApiResult.ok(dutyActive);
    }

    @HasLogin
    @ApiOperation(value = "新增")
    @PostMapping
    public ApiResult add(@RequestBody Article param)
    {
        param.setCreateUsername(LocalUser.getUser().getUsername());
        return ApiResult.result(articleService.save(param));
    }

    @HasLogin
    @ApiOperation(value = "更新")
    @PutMapping
    public ApiResult edit(@RequestBody Article param)
    {
        param.setUpdateUsername(LocalUser.getUser().getUsername());
        return ApiResult.result(articleService.updateById(param));
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{ids}")
    public ApiResult remove(@PathVariable String[] ids)
    {
        List<String> list = Arrays.asList(ids);
        return ApiResult.result(articleService.removeByIds(list));
    }
}
