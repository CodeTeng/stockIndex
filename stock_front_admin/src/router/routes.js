/**
 * 	注意：
 * 		权限是通过路由中的 name 属性来进行定位，所以 name 必须填写，且唯一
 *
 * 		isMenu  false表示该路由仅仅是路由，而不生成菜单
 * 				默认所有路由都生成菜单，不想生成菜单直接添加属性 isMenu 为 false 即可
 * 			注意：1.不需要每个路由都添加 isMenu 属性，只需要给不需要生成菜单的路由添加该属性，需要生成菜单的路由不需要加该属性
 * 		         2.二级菜单 isMenu 必须写在 children 里的每条路由上，一级菜单写在 children 外面（当前路由即可）
 *				 3.权限路由不需要添加 isMenu ，因为权限路由是有后端返回的，所以不需要添加 isMenu 属性
 *		特别注意： 后台返回的权限菜单数据
 *	        	path值与name的值（后台返回的权限菜单中的path值与name值），必须对应组件目录中的组件所在目录
 *	         如：角色路由组件 roles 目录必须与后台返回的数据中的角色路由 name 值一致，否则加载不到组件
 *	            path：'/组件目录名称',
 *	            name:'组件目录名称'
 *	         path 与 name 必须对应组件目录名称
 */

import stockList from '@/views/components/stock/componment/list';
import fa from "element-ui/src/locale/lang/fa";

// 所有人都可以访问
const routes = [
	{
		path:'/login',
		name:'login',
		isMenu:false,
		component: () => import(/* webpackChunkName: "login" */ '@/views/login.vue')
	},

	{
		path:'/',
		name:'welcome',
		redirect:'/stock',
		isMenu:false,
		children:[{
			path:'/stock',
			name:'stock',
			isMenu:false,
			redirect: '/stockHome',
			// meta:{title:'大屏显示', icon:'el-icon-s-data'},
			component: () => import(/* webpackChunkName: "test" */ '@/views/components/stock'),
			children:[
				{
					path: '/stockHome',
					name: 'stockHome',
					component: ()=> import('@/views/components/stock/componment/home')
				},
				{
					path: '/stockList',
					name: 'stockList',
					component: ()=> import('@/views/components/stock/componment/list')
				},
				{
					path: '/stockDetail',
					name: 'stockDetail',
					component: ()=> import('@/views/components/stock/componment/details')
				}
			]
		}]
	},

	//后续动态从数据库获取
	{
		path:'/org',
		name:"org",
		isMenu: true,
		meta:{title:'组织管理', icon:'el-icon-s-tools'},
		children:[
			{
				path:'/user',
				name:'user',
				meta:{title:'用户管理', icon:'el-icon-user-solid'},
				component: () => import('@/views/components/user')
			},
			{
				path:'/roles',
				name:'roles',
				meta:{title:'角色管理', icon:'el-icon-user'},
				component: () => import('@/views/components/roles')
			},
			{
				path:'/menus',
				name:'menus',
				meta:{title:'菜单列表', icon:'el-icon-menu'},
				component: () => import('@/views/components/menus')
			},
		]
	},
	{
		path:'/sys',
		name:"sys",
		isMenu: true,
		meta:{title:'系统管理', icon:'el-icon-s-tools'},
		children:[
			{
				path:'/logs',
				name:'logs',
				meta:{title:'日志管理', icon:'el-icon-s-marketing'},
				component: () => import('@/views/components/logs')},
			{
				path:'/swagger',
					name:'swagger',
				meta:{title:'接口管理', icon:'el-icon-s-ticket'},
				component: () => import('@/views/components/swagger')
			},
			{
				path:'/druid/sql',
					name:'sql',
				meta:{title:'SQL监控', icon:'el-icon-s-data'},
				component: () => import('@/views/components/sql')
			},
		]
	},
	{
		path:'/jobAdmin',
		name:'jobAdmin',
		isMenu: true,
		meta:{title:'定时任务管理', icon:'el-icon-s-tools'},
		component: () => import(/* webpackChunkName: "login" */ '@/views/components/jobAdmin/index.vue')
	},

	//后续动态从数据库获取\\

	{
		path:'/401',
		name:'401',
		isMenu:false,
		component: () => import(/* webpackChunkName: "401" */ '@/views/error/401.vue')
	},
	{
		path:'/404',
		name:'404',
		isMenu:false,
		component: () => import(/* webpackChunkName: "404" */ '@/views/error/404.vue')
	}
];
// 需要权限验证
//从后台返回的数据中进行动态加载路由，这样就不用在把权限路由写在 routesMap
// 数组中了，后台返回的权限格式清参数 《权限格式数据.json》
const routesMap = [
	/*{
		path:'/org',
		name:"org",
		meta:{title:'组织管理', icon:'el-icon-s-tools'},
		children:[
			{
				path:'/user',
				name:'user',
				meta:{title:'用户管理', icon:'el-icon-user-solid'},
				component: () => import(/!* webpackChunkName: "user" *!/ '@/views/components/user')
			},
			{
				path:'/roles',
				name:'roles',
				meta:{title:'角色管理', icon:'el-icon-user'},
				component: () => import(/!* webpackChunkName: "roles" *!/ '@/views/components/roles')
			},
			{
				path:'/menus',
				name:'menus',
				meta:{title:'菜单列表', icon:'el-icon-menu'},
				component: () => import(/!* webpackChunkName: "menus" *!/ '@/views/components/menus')
			},
		]
	},
	{
		path:'/sys',
		name:"sys",
		meta:{title:'系统管理', icon:'el-icon-s-tools'},
		children:[
			{
				path:'/logs',
				name:'logs',
				meta:{title:'日志管理', icon:'el-icon-s-marketing'},
				component: () => import(/!* webpackChunkName: "logs" *!/ '@/views/components/logs')
			},
			{
				path:'/swagger',
				name:'swagger',
				meta:{title:'接口管理', icon:'el-icon-s-ticket'},
				component: () => import(/!* webpackChunkName: "rule" *!/ '@/views/components/swagger')
			},
			{
				path:'/druid/sql',
				name:'sql',
				meta:{title:'SQL监控', icon:'el-icon-s-data'},
				component: () => import(/!* webpackChunkName: "sql" *!/ '@/views/components/sql')
			},
		]
	},*/
	{path:'*',redirect:'/404',isMenu:false}
];
export {routesMap,routes}
