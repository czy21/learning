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
	var prep2 *ListNode
	for {
		// 反转
		if l1 != nil {
			next := l1.Next
			l1.Next = prep1
			prep1 = l1
			l1 = next
		}
		if l2 != nil {
			next := l2.Next
			l2.Next = prep2
			prep2 = l2
			l2 = next
		}
		if l1 == nil && l2 == nil {
			break
		}
	}
	a1 := ""
	a2 := ""
	for {
		if prep1 != nil {
			a1 += strconv.Itoa(prep1.Val)
			prep1 = prep1.Next
		}
		if prep2 != nil {
			a2 += strconv.Itoa(prep2.Val)
			prep2 = prep2.Next
		}
		if prep1 == nil && prep2 == nil {
			break
		}
	}
	ai1, _ := strconv.Atoi(a1)
	ai2, _ := strconv.Atoi(a2)
	sumChars := []rune(strconv.FormatInt(int64(ai1+ai2), 10))
	var head *ListNode
	for i := len(sumChars) - 1; i >= 0; i-- {
		m, _ := strconv.Atoi(string(sumChars[i]))
		if head == nil {
			head = &ListNode{Val: m}
			continue
		}
		// 头插法
		node := &ListNode{m, nil}
		node.Next = head
		head = node
		// 尾插法1
		//node := head
		//for {
		//	if node != nil {
		//		if node.Next == nil {
		//			node.Next = &ListNode{m, nil}
		//			break
		//		}
		//	}
		//	node = node.Next
		//}
		// 尾插法2
		//for node := head; node != nil; node = node.Next {
		//	if node.Next == nil {
		//		node.Next = &ListNode{m, nil}
		//		break
		//	}
		//}
	}
	return head
}

// 链表反转并相加
func TestSum2(t *testing.T) {
	l1 := ListNode{Val: 2, Next: &ListNode{Val: 4, Next: &ListNode{Val: 3}}}
	l2 := ListNode{Val: 5, Next: &ListNode{Val: 6, Next: &ListNode{Val: 4}}}
	addTwoNumbers(&l1, &l2)
}
