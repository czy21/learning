//typescript 可直接引用js文件,也可为本地js文件定义声明文件,但导入方式与为第三方库导入方式稍有不同
//通过dts-gen工具,可将node_modules中没有@types的js库生成声明文件
import owner from './owner'
import * as stuInterface from './basic/StuInterface'

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

//
let myArray: stuInterface.StringArray
myArray = ["ni", "wo", "ta"]
console.log(myArray)
//


/// <reference path="./other/stu-namespace/Validation.ts" />
/// <reference path="./other/stu-namespace/LettersOnlyValidator.ts" />

// Some samples to try
let strs = ["Hello", "98052", "101"];

// Validators to use
let validators: { [s: string]: Validation.StringValidator; } = {};
validators["Letters only"] = new Validation.LettersOnlyValidator();

// Show whether each string passed each validator
for (let s of strs) {
    for (let name in validators) {
        console.log(`"${s}" - ${validators[name].isAcceptable(s) ? "matches" : "does not match"} ${name}`);
    }
}