//typescript 可直接引用js文件 但本地声明文件不生效
//通过dts-gen工具,可将node_modules中没有@types的js库生成声明文件
import owner from './owner'


console.log(owner.get("s"))