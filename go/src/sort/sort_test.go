package sort

import (
	"fmt"
	"testing"
)

func swap(arr []int, i int, j int) {
	t := arr[i]
	arr[i] = arr[j]
	arr[j] = t
}

// 插入
func InsertionSort(arr []int) {
	for i := 0; i < len(arr); i++ {
		for j := i; j > 0; j-- {
			if arr[j] < arr[j-1] {
				swap(arr, j, j-1)
			}
		}
	}
}

// 冒泡
func BubbleSort(arr []int) {
	for i := 0; i < len(arr)-1; i++ {
		for j := 0; j < len(arr)-1-i; j++ {
			if arr[j] > arr[j+1] {
				swap(arr, j+1, j)
			}
		}
	}
}
func SelectionSort(arr []int) []int {
	for i := 0; i < len(arr)-1; i++ {
		min := i
		for j := i + 1; j < len(arr); j++ {
			if arr[min] > arr[j] {
				min = j
			}
		}
		swap(arr, i, min)
	}
	return arr
}

var arr = []int{20, 15, 12, 40, 5, 7, 2, 1}

func TestInsertionSort(t *testing.T) {
	fmt.Println(arr)
	InsertionSort(arr)
	fmt.Println(arr)
}
func TestBubbleSort(t *testing.T) {
	fmt.Println(arr)
	BubbleSort(arr)
	fmt.Println(arr)
}
func TestSelectionSort(t *testing.T) {
	fmt.Println(arr)
	SelectionSort(arr)
	fmt.Println(arr)
}
