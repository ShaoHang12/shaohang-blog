import axios from 'axios'
import store from '@/store'

import router from '../router/index'
import { MessageBox, Message } from 'element-ui'
// create an axios instance
const service = axios.create({
    baseURL: "http://localhost:8080/",
    timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
    config => {
        if (localStorage.getItem("token")) {
            config.headers['token'] = localStorage.getItem("token")
        }
        // do something before request is sent

        // if (store.getters.token) {
        //     config.headers['X-Token'] = getToken()
        // }
        return config
    },
    error => {
        // do something with request error
        console.log(error) // for debug
        return Promise.reject(error)
    }
)

// response interceptor
service.interceptors.response.use(
    /**
     * If you want to get http information such as headers or status
     * Please return  response => response
     */

    /**
     * Determine the request status by custom code
     * Here is just an example
     * You can also judge the status by HTTP Status Code
     */
    response => {
        const res = response.data
        console.log(res.status);
        if (res.status == 4001) {

            console.log('4001 拦截');
            Message({
                message: res.msg,
                type: 'error',
                duration: 5 * 1000
            })
            localStorage.removeItem("token")
            store.commit("setIsLogin",false)
            router.push({ name: "login" })

            Message({
                message: res.msg,
                type: 'error',
                duration: 5 * 1000
            })
        } else if (res.status !== 200) {
            Message({
                message: res.msg,
                type: 'error',
                duration: 5 * 1000
            })

        } else {
            return res
        }
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)

export default service
