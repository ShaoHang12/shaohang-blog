import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/business/user/list',
    method: 'get',
    params: query
  })
}


export function getUserById(id) {
  return request({
    url: '/business/user/' + id,
    method: 'get'
  })
}

export function saveUser(data) {
  return request({
    url: '/business/user',
    method: 'post',
    data
  })
}


//updateUser

export function updateUser(data) {
  return request({
    url: '/business/user',
    method: 'put',
    data
  })
}

// deleteUser

export function deleteUser(ids) {
  return request({
    url: '/business/user/' + ids,
    method: 'delete',
  
  })
}
