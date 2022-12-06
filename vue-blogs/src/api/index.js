import request from '@/utils/request'

export function fetchList(query) {
    return request({
        url: '/business/article/list',
        method: 'get',
        params: query
    })
}

export function getArticleById(id) {
    return request({
        url: '/business/article/' + id,
        method: 'get'
    })
}

export function fetchFocus() {
    return request({
        url: '/focus/list',
        method: 'get',
        params: {}
    })
}

export function fetchCategory() {
    return request({
        url: '/category',
        method: 'get',
        params: {}
    })
}

export function fetchFriend() {
    return request({
        url: '/friend',
        method: 'get',
        params: {}
    })
}

export function fetchSocial() {
    return request({
        url: '/social',
        method: 'get',
        params: {}
    });
}

export function fetchSiteInfo() {
    return request({
        url: '/site',
        method: 'get',
        params: {}
    })
}

export function fetchComment() {
    return request({
        url: '/comment',
        method: 'get',
        params: {}
    })
}
