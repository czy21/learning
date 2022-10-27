package main

import (
	"fmt"
)

func channel1() {
	ch := make(chan int, 2)
	ch <- 1
	ch <- 2
	fmt.Println(<-ch)
	fmt.Println(<-ch)
}

//func main() {
//	ch := make(chan string, runtime.NumCPU())
//	go func() {
//		defer close(ch)
//		for i := 0; i < 20; i++ {
//			ch <- strconv.Itoa(i)
//		}
//	}()
//	for t := range ch {
//		fmt.Println(t)
//	}
//}
