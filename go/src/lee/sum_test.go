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
	var prep *ListNode
	var next *ListNode
	for {
		if l1 != nil {
			next = l1.Next
			l1.Next = prep
			prep = l1
			l1 = next
		} else {
			break
		}
	}
	return &ListNode{}
}

func TestSum2(t *testing.T) {
	l1 := ListNode{Val: 1, Next: &ListNode{Val: 2, Next: &ListNode{Val: 3, Next: &ListNode{Val: 4}}}}
	l2 := ListNode{Val: 5, Next: &ListNode{Val: 6, Next: &ListNode{Val: 4}}}
	addTwoNumbers(&l1, &l2)
}
