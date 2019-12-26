"use strict";
exports.__esModule = true;
//typescript 可直接引用js文件,也可为本地js文件定义声明文件,但导入方式与为第三方库导入方式稍有不同
//通过dts-gen工具,可将node_modules中没有@types的js库生成声明文件
var owner_1 = require("./owner");
var stuInterface = require("./basic/StuInterface");
//
console.log(owner_1["default"].a("s"));
console.log(owner_1["default"].b(true));
//
//
var person1 = stuInterface.of({ name: "张三", extra: "zhangsan" });
console.log(person1);
//
//
var mySum;
mySum = function (a, b) {
    return a + b;
};
console.log(mySum(1, 2));
//
//
var myArray;
myArray = ["ni", "wo", "ta"];
console.log(myArray);
//
/// <reference path="./other/stu-namespace/Validation.ts" />
/// <reference path="./other/stu-namespace/LettersOnlyValidator.ts" />
// Some samples to try
var strs = ["Hello", "98052", "101"];
// Validators to use
var validators = {};
validators["Letters only"] = new Validation.LettersOnlyValidator();
// Show whether each string passed each validator
for (var _i = 0, strs_1 = strs; _i < strs_1.length; _i++) {
    var s = strs_1[_i];
    for (var name_1 in validators) {
        console.log("\"" + s + "\" - " + (validators[name_1].isAcceptable(s) ? "matches" : "does not match") + " " + name_1);
    }
}
