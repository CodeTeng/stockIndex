// 入口文件
// 引入包
import Vue from 'vue'
import store from './store'
import App from './App.vue'
import router from './router'
import directives from './directives'
import ElementComponent from 'element-component'

// import 'font-awesome/css/font-awesome.min.css' // https://www.thinkcmf.com/font/font_awesome/icons.html
import 'normalize.css'
import './assets/style/globalstyle.css'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import echarts from 'echarts'
Vue.prototype.$echarts = echarts

Vue.use(ElementUI);

Vue.config.productionTip = false

// Vue.use(common);
Vue.use(directives);
Vue.use(ElementComponent);

Vue.config.productionTip = false;
const vm = new Vue({
	el:'#app',
    store,
	router,
	render:c => c(App),
});
