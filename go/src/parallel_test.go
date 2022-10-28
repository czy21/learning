package main

import (
	"fmt"
	"testing"
)

func TestChannel1(t *testing.T) {
	ch := make(chan int, 2)
	ch <- 1
	ch <- 2
	fmt.Println(<-ch)
	fmt.Println(<-ch)
}

func func1(c chan int) {
	for i := 0; i < 800; i++ {
		c <- i
		fmt.Println("create :", i)
	}
}

func TestChannel2(t *testing.T) {
	c := make(chan int, 5)
	defer close(c)
	x := 0
	for i := 0; i < 5; i++ {
		go func(t int) {
			x += t
			c <- x
		}(i)
	}
	for t := range c {
		fmt.Println(t)
	}
}
