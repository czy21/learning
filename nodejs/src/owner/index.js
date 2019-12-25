function a(val) {
    return val
}

//var 声明可以在包含它的函数，模块，命名空间或全局作用域内部任何位置被访问
//let 声明是块作用域，变量在包含它们的块或for循环之外是不能访问的
function b(val) {
    if (val) {
        var b = "a"
    }
    return b
}



module.exports = {
    a: (name) => a(name),
    b: (val) => b(val)
}