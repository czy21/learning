//typescript 可直接引用js文件,也可为本地js文件定义声明文件,但导入方式与为第三方库导入方式稍有不同
//通过dts-gen工具,可将node_modules中没有@types的js库生成声明文件
import owner from './owner'
import * as stuInterface from './basic/StuInterface'

//js 与 ts 类型映射
console.log("ts与js的类型映射: ", owner.a("a"), owner.b("b"));

let person1 = stuInterface.of({name: "张三", extra: "zhangsan"});
console.log("对象类型: ", person1);

let mySum: stuInterface.SearchFunc;
mySum = function (a, b) {
    return a + b
};
console.log("方法类型: ", mySum(1, 2));

let myArray: stuInterface.StringArray
myArray = ["ni", "wo", "ta"]
console.log("集合类型", myArray)