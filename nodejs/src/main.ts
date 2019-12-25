//typescript 可直接引用js文件,也可为本地js文件定义声明文件
//通过dts-gen工具,可将node_modules中没有@types的js库生成声明文件
import owner from './owner'


console.log(owner.get("s"))