import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/business/article/list',
    method: 'get',
    params: query
  })
}

export function publishArticle(data){
  return request({
    url: '/business/article/publish',
    method: 'put',
    data
  })
}



export function getArticleById(id) {
  return request({
    url: '/business/article/' + id,
    method: 'get'
  })
}

//updateArticle

export function updateArticle(data) {
  return request({
    url: '/business/article',
    method: 'put',
    data
  })
}

//saveArticle

export function saveArticle(data) {
  return request({
    url: '/business/article',
    method: 'post',
    data
  })
}


// deleteUser

export function deleteArticle(ids) {
  return request({
    url: '/business/article/' + ids,
    method: 'delete',
  
  })
}


export function fetchArticle(id) {
  return request({
    url: '/business/article/' + id,
    method: 'get',
  })
}

// 获取评论

export function fetchComment(query) {
  return request({
    url: '/business/article/getArticleCommentList',
    method: 'get',
    params: query
  })
}


// 评论文章
export function commentArticle(data) {
  return request({
    url: '/business/articleComment',
    method: 'post',
    data
  })
}


