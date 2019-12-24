// import custom from 'custom'
import inn from 'src/index.js'

inn("a")

// custom.getName("a")

//列表
let list: number[] = [1, 2, 3]

//元组
let x: [string, number]
x = ["3", 2]

//枚举
enum Color {Red, Green}


function warnUser(): void {
    console.log("void")
}


declare function fn(x: HTMLDivElement): string;

var myElem: HTMLDivElement;
var l = fn(myElem); // x: string, :)

console.log()