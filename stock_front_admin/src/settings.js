/************************* 配置 *************************/
module.exports = {
    components: '/components',// 指定views下组件所在目录,动态路由组件必须放在该目录下，且路径名称为组件名称
    affix: true,// 是否显示图钉
    isRefrushBack: true,// 刷新后是否停在当前页,默认回到首页
    authorization: 'authorization',// 请求头 token key
    title: '今日指数', // 标题
    // footer:'权限管理系统',// 底部文字，注释掉默认没有底部
    iconPath: './assets/images/logo-0.png',// 网页 icon 图标路径
    publicPath: '/',// 网站根目录，打包时用到
    devServer: {
        port: 8080,
        host: '127.0.0.1',// 开发host
        open: true,// 是否启动时打开浏览器
        disableHostCheck: true, // 映射外网时，需要设置为true
        /**
         * 域名，他将会基于 window.location来链接服务器，需要使用public配置
         * dev-server被代理到nginx中配置的 itheima.com
         */
        public: "127.0.0.1:8080",
        publicPath: '/',
        compress: true,
        overlay: {// 是否在浏览器全屏显示错误与警告
            warnings: false,
            errors: true
        },
        proxy: {// 跨域请求配置
            "/api": {
                secure: false,// 关闭安全检测，默认请求 https
                // target: "http://192.168.188.131:8081",
                target: "http://localhost:8081",
                changeOrigin: true,
                // pathRewrite: {"^/api" : ""},
            }
        },
    },
    alias: {
        // 如：jquery
        //"$": 'jquery.js'
    },
    //TODO 页面按钮操作权限先写死，后续 删除，从数据库动态获取
    permissoins: ['btn-user-delete', 'btn-log-delete', 'btn-user-add', 'btn-role-update',
        'btn-permission-delete', 'btn-permission-list', 'btn-user-list', 'btn-role-delete',
        'btn-role-add', 'btn-role-detail', 'btn-permission-update', 'btn-user-update-role',
        'btn-user-update', 'btn-role-add', 'btn-permission-add', 'btn-role-list', 'btn-log-list'
    ]
};
