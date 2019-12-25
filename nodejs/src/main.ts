//typescript 可直接引用js文件,也可为本地js文件定义声明文件,但导入方式与为第三方库导入方式稍有不同
//通过dts-gen工具,可将node_modules中没有@types的js库生成声明文件
import owner from './owner'
import * as stuInterface from './basic/stuInterface'

//
console.log(owner.a("s"));
console.log(owner.b(true));
//

//
let person1 = stuInterface.of({name: "张三", extra: "zhangsan"});
console.log(person1);
//

//
let mySum: stuInterface.SearchFunc;
mySum = function (a, b) {
    return a + b
};
console.log(mySum(1, 2));

//
let a: ReadonlyArray<number> = [1, 2, 3];
