import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'home',
        component: () => import('../views/Home.vue'),
        meta: { title: '首页' }
    },
    {
        path: '/category/:cate',
        name: 'category',
        component: () => import('../views/Home.vue'),
        meta: { title: '分类', params: 'cate' }
    },
    {
        path: '/search/:words',
        name: 'search',
        component: () => import('../views/Home.vue'),
        meta: { title: '搜索', params: 'words' }
    },
    {
        path: '/about',
        name: 'about',
        component: () => import('../views/About.vue'),
        meta: { title: '关于' }
    },

    {
        path: '/article/:id',
        name: 'article',
        component: () => import('../views/Articles.vue'),
        meta: { title: '文章' }
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/Login/index.vue'),
        meta: { title: '登录' }
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('../views/Register/index.vue'),
        meta: { title: '注册' }
    },
    {
        path: '/admin',
        name: 'admin',
        component: () => import('../views/admin'),
        redirect: '/userManager',
        children: [
            {
                path: '/userManager',
                name: 'userManager',
                component: () => import('../views/admin/UserManager'),
                meta: { title: '用户管理' }
            },
            {
                path: '/newsManager',
                name: 'newsManager',
                component: () => import('../views/admin/NewsManager'),
                meta: { title: '文章管理' }
            },
            {
                path: '/cgManager',
                name: 'cgManager',
                component: () => import('../views/admin/CgManager'),
                meta: { title: '草稿箱' }
            },
        ]
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})
router.beforeEach((to, from, next) => {
    let title = 'Blog'
    if (to.meta.params) {
        title = `${to.meta.title}:${to.params[to.meta.params] || ''} - ${title}`
    } else {
        title = `${to.meta.title} - ${title}`
    }
    document.title = title
    if (to.path !== from.path) {
        store.dispatch('setLoading', true);
    }
    next();
})
router.afterEach((to, from) => {
    // 最多延迟 关闭 loading
    setTimeout(() => {
        store.dispatch('setLoading', false);
    }, 1500)
})
export default router
