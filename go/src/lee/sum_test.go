package lee

import (
	"fmt"
	"strconv"
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
	var prep1 *ListNode
	for {
		if l1 != nil {
			next := l1.Next
			l1.Next = prep1
			prep1 = l1
			l1 = next
		} else {
			break
		}
	}
	a1 := ""
	for {
		if prep1 != nil {
			a1 += strconv.Itoa(prep1.Val)
			prep1 = prep1.Next
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
