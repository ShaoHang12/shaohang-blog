import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './assets/css/style.less'
import './assets/font/iconfont.css'
import {parseTime} from './utils'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
// import './styles/element-variables.scss'
import hljs from 'highlight.js';
import 'highlight.js/styles/atom-one-dark.css'	//样式

//刷新保存状态
if (sessionStorage.getItem("store")) {
  store.replaceState(
      Object.assign(
          {},
          store.state,
          JSON.parse(sessionStorage.getItem("store"))
      )
  );
  sessionStorage.removeItem("store")
}
//监听，在页面刷新时将vuex里的信息保存到sessionStorage里
window.addEventListener("beforeunload", () => {
  sessionStorage.setItem("store", JSON.stringify(store.state));
});


Vue.directive('highlight',function (el) {
  let blocks = el.querySelectorAll('pre code');
  blocks.forEach((block)=>{
    hljs.highlightBlock(block)
  })
})
Vue.use(Element)
Vue.config.productionTip = false
Vue.filter('parseTime', (v) => parseTime(v,'{y}-{m}-{d}'))
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
