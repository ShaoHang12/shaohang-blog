import request from '@/utils/request'
export function giveLike(data) {
    return request({
        url: '/business/article-love/love',
        method: 'get',
        data
    })
}