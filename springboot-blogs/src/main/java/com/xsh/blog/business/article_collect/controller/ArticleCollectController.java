package com.xsh.blog.business.article_collect.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xsh.blog.business.article_collect.dto.ArticleCollectAddDto;
import com.xsh.blog.business.article_collect.entity.ArticleCollect;
import com.xsh.blog.business.article_collect.service.IArticleCollectService;
import com.xsh.blog.business.article_collect.vo.ArticleCollectVo;
import com.xsh.blog.business.user.entity.User;
import com.xsh.blog.common.bean.ApiResult;
import com.xsh.blog.common.bean.BasePage;
import com.xsh.blog.common.utils.LocalUser;
import com.xsh.blog.config.annotation.authority.HasLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 */
@Api(tags = "业务-博客收藏")
@RestController
@RequestMapping("/business/article-collect")
public class ArticleCollectController {

    @Autowired
    private IArticleCollectService articleCollectService;

    @ApiOperation(value = "获取收藏列表")
    @GetMapping("/list")
    public ApiResult list(ArticleCollectVo param){
        BasePage<ArticleCollectVo> page = articleCollectService.getPage(param);
        return ApiResult.ok(page);
    }


    @ApiOperation(value = "获取详细信息")
    @GetMapping(value = "/{id}")
    public ApiResult getInfo(@PathVariable("id") String id)
    {
        ArticleCollect articleCollect = articleCollectService.getById(id);
        return ApiResult.ok(articleCollect);
    }

    @HasLogin
    @ApiOperation(value = "收藏文章")
    @PostMapping
    public ApiResult add(@RequestBody ArticleCollectAddDto param)
    {
        User user = LocalUser.getUser();
        String userId = user.getUserId();
        ArticleCollect articleCollect = ArticleCollect.toArticleCollect(param);
        articleCollect.setUserId(userId);
        ArticleCollect oldArticleCollect = articleCollectService.getOne(Wrappers.<ArticleCollect>lambdaQuery().eq(ArticleCollect::getArticleId, param.getArticleId()).eq(ArticleCollect::getUserId,userId));
        if (oldArticleCollect == null) {
            return ApiResult.result(articleCollectService.save(articleCollect));
        }else{
            return ApiResult.fail("该文章已收藏过啦");
        }

    }



    @HasLogin
    @ApiOperation(value = "取消文章收藏")
    @DeleteMapping("/{ids}")
    public ApiResult remove(@PathVariable String[] ids)
    {
        String userId = LocalUser.getUser().getUserId();
        List<String> list = Arrays.asList(ids);

        List<ArticleCollect> oldList = articleCollectService.list(Wrappers.<ArticleCollect>lambdaQuery().eq(ArticleCollect::getUserId, userId));
        List<String> idsList2 = oldList.stream().map(item -> {
            return item.getArticleCollectId();
        }).collect(Collectors.toList());
        boolean flag = idsList2.containsAll(list);
        if (flag){
            return ApiResult.result(articleCollectService.removeByIds(list));
        }else if (idsList2.size() == 0){
            return ApiResult.fail("删除失败");
        }else{
            return ApiResult.fail("不属于你的收藏,不可删除哦！");
        }
    }


}
