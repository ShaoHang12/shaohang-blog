package com.xsh.blog.business.article_comment.controller;


import com.xsh.blog.business.article_comment.entity.ArticleComment;
import com.xsh.blog.business.article_comment.service.IArticleCommentService;
import com.xsh.blog.common.bean.ApiResult;
import com.xsh.blog.common.utils.LocalUser;
import com.xsh.blog.config.annotation.authority.HasLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 *
 */
@Api(tags = "业务-文章评论")
@RestController
@RequestMapping("/business/articleComment")
public class ArticleCommentController {

    @Autowired
    private IArticleCommentService articleCommentService;


    @HasLogin
    @ApiOperation(value = "评论文章")
    @PostMapping
    public ApiResult add(@RequestBody ArticleComment param)
    {
        String userId = LocalUser.getUser().getUserId();
        // 判断是否回复是否回复自己
        String articleCommentPid = param.getArticleCommentPid();
        if (articleCommentPid != null ){
            ArticleComment articleComment = articleCommentService.getById(articleCommentPid);
            if (articleComment !=null){
                String articleCommentUserId = articleComment.getArticleCommentUserId();
                if (articleCommentUserId.equals(userId)){
                    return ApiResult.fail("不能评论自己哦");
                }
            }
        }
        // 正常回复
        param.setArticleCommentUserId(userId);
        return ApiResult.result(articleCommentService.save(param));
    }

}
