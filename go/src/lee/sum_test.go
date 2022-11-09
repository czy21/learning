package lee

import (
	"fmt"
	"testing"
)

func TestSum1(t *testing.T) {
	arr := []int{1, 2, 3, 4, 5}
	target := 5
	for i := 0; i < len(arr); i++ {
		for j := i + 1; j < len(arr); j++ {
			v := arr[i] + arr[j]
			if v == target {
				fmt.Println(i, j)
			}
		}
	}
}

type ListNode struct {
	Val  int
	Next *ListNode
}

func addTwoNumbers(l1 *ListNode, l2 *ListNode) *ListNode {
	l := l1
	var k []int
	for {
		k = append(k, l.Val)
		if l.Next != nil {
			l = l.Next
		} else {
			break
		}
	}
	fmt.Println(k)
	return &ListNode{}
}

func TestSum2(t *testing.T) {
	l1 := ListNode{Val: 2, Next: &ListNode{Val: 4, Next: &ListNode{Val: 3}}}
	l2 := ListNode{Val: 5, Next: &ListNode{Val: 6, Next: &ListNode{Val: 4}}}
	addTwoNumbers(&l1, &l2)
}
