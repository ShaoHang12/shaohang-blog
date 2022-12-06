package com.xsh.blog.common.utils;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xsh.blog.business.article_comment.entity.ArticleComment;
import com.xsh.blog.business.article_comment.service.IArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetArticleCommentList {


    private static IArticleCommentService articleCommentService;

    @Autowired
    public void setArticleCommentService(IArticleCommentService articleCommentService) {
        this.articleCommentService = articleCommentService;
    }

    public static int getComment(String articleId){
        int size = articleCommentService.list(Wrappers.<ArticleComment>lambdaQuery().eq(ArticleComment::getArticleId, articleId).eq(ArticleComment::getArticleCommentPid, "0")).size();
        return size;
    }


    public static List<ArticleComment> getDataList(List<ArticleComment> articleCommentList){
        List<ArticleComment> voList = new ArrayList<>();
       if (articleCommentList.size()>0){
           for (ArticleComment articleComment : articleCommentList) {
                if (articleComment.getArticleCommentPid().equals("0")){
                    addChildren(articleCommentList,articleComment);
                    voList.add(articleComment);
                }
           }
       }
        return voList;
    }

    public static void addChildren(List<ArticleComment> articleCommentList, ArticleComment articleComment){
        if (articleCommentList.size() > 0 ){
            List<ArticleComment> childrenList = getChildrenList(articleCommentList, articleComment);
            if ( childrenList.size() > 0){
                articleComment.setArticleCommentChildren(childrenList);
                for (ArticleComment childrenComment : childrenList) {
                    addChildren(articleCommentList,childrenComment);
                }
            }else{
                articleComment.setArticleCommentChildren(null);
            }
        }

    }

    public static List<ArticleComment> getChildrenList(List<ArticleComment> articleCommentList,ArticleComment articleComment){
        List<ArticleComment> childrenList = new ArrayList<>();
        if (articleCommentList.size() > 0 ){
            for (ArticleComment comment : articleCommentList) {
                if (comment.getArticleCommentPid().equals(articleComment.getArticleCommentId())){
                    comment.setToUserName(articleComment.getNickname());
                    childrenList.add(comment);
                }
            }
        }
        return childrenList;
    }

}
