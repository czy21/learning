package main

import "fmt"

type IName interface {
	Name() string
}

type A struct {
}

func (a A) Name() string {
	fmt.Println("我是A")
	return "A"
}

type B struct {
	A
}

//func (b B) Name() string {
//	fmt.Println("我是B")
//	return "B"
//}

//func main() {
//	b := B{}
//	b.Name()
//}
