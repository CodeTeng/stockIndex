## 1.目录结构
目录  
```  
   │──dist            
   │──src             
   │  │──api             接口请求目录  
   │  │──assets          静态资源目录
   │  │──common          公共目录(如：公共函数,可直接修改，不影响打包后的文件)    
   │  │──components      组件目录  
   │  │──config          配置目录  
   │  │──lib             核心库目录  
   │  │──router          路由目录  
   │  │──store           vuex目录  
   │  │──views           视图目录  
   │  │──App.vue         根组件  
   │  │──index.html      模板文件    
   │  │──main.js         入口文件                  
   │  └──settings.js     配置文件
   │    ...
```

## 2.Build
```
# install dependency
npm install

# test development dev
npm run dev

# build
npm run build

# build test
npm run build:test

# watch
npm run watch
```
常见打包错误：
运行 npm run build,报错：
Module build failed (from ./node_modules/image-webpack-loader/index.js)
解决方案：

~~~js
先卸载
npm uninstall image-webpack-loader
然后使用
cnpm install image-webpack-loader --save-dev
~~~
