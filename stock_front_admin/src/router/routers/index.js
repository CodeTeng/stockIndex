import Layout from '@/components/layout'
import {routes} from "../routes.js"
//查看子路由，收集到集合下
function findChild(data){
    let children = [];
    if (!data){
        return children;
    }
    for (let i = 0,leng = data.length; i < leng; i++) {
        if (!data[i].component || data[i].children && data[i].children.length > 0){
            children.push(...findChild(data[i].children));
        }else {
            children.push(data[i]);
        }
    }
    return children;
}
//找出根路径，也就是/默认路径
let rootRoute = null;
//找出权限中没有子路由的路由配置
const notChildren = [];
//找出路由配置中所有的子路由
const child = [];
routes && routes.map(item => {
    // debugger;
    if (item.path != "/"){
        if (item.children && item.children.length > 0){
            //item.children.map(router => child.push(router));
            child.push(...findChild(item.children));
        }else{
            notChildren.push(item);
        }
    }else {
        rootRoute = item;
    }
    return item;
});
let index = -1;
let defaultRouter = [];
notChildren.length>0 && routes.some(router => {
    index = notChildren.findIndex(item => item.path == router.path);
    return index != -1;
}) && (index != -1) && (defaultRouter=notChildren.splice(index,1));

const children = {
    path:rootRoute.path,
    name:rootRoute.name,
    component: Layout,
    redirect:rootRoute.redirect,
    isMenu:rootRoute.isMenu!==false,
    children:[rootRoute.children[0],...child,...notChildren]
};
const startRouter = [...defaultRouter,children];
export default startRouter;