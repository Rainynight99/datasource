package com.xlm.mysqldemo.calculate;

import net.bytebuddy.dynamic.Nexus;
import org.hibernate.cache.spi.SecondLevelCacheLogger;
import org.ietf.jgss.Oid;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import javax.swing.text.html.ObjectView;

/**
 * 1) 单链表反转
 * 2) 链表中环的检测
 * 3) 两个有序的链表合并
 * 4) 删除链表倒数第n个结点
 * 5) 求链表的中间结点
 * <p>
 * Author: Zheng
 */
public class LinkedListAlgo {

    // 单链表反转
    public static Node reverse(Node list) {
        Node curr = list, pre = null;
        while (curr != null) {
            Node next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }

    // 检测环
    public static boolean checkCircle(Node list) {
        if (list == null) return false;

        Node fast = list.next;
        Node slow = list;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (slow == fast) return true;
        }

        return false;
    }

    // 有序链表合并
    // public static Node mergeSortedLists(Node la, Node lb) {
    // if (la == null) return lb;
    // if (lb == null) return la;

    // Node p = la;
    // Node q = lb;
    // Node head;
    // if (p.data < q.data) {
    //   head = p;
    //   p = p.next;
    // } else {
    //   head = q;
    //   q = q.next;
    // }
    // Node r = head;

    // while (p != null && q != null) {
    //   if (p.data < q.data) {
    //     r.next = p;
    //     p = p.next;
    //   } else {
    //     r.next = q;
    //     q = q.next;
    //   }
    //   r = r.next;
    // }

    // if (p != null) {
    //   r.next = p;
    // } else {
    //   r.next = q;
    // }

    // return head;
    //}

    //-----------------------------------------

    // 有序链表合并 Leetcode 21

    //Definition for singly-linked list.
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode soldier = new ListNode(0); //利用哨兵结点简化实现难度 技巧三
        ListNode p = soldier;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                p.next = l1;
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }

        if (l1 != null) {
            p.next = l1;
        }
        if (l2 != null) {
            p.next = l2;
        }
        return soldier.next;
    }


    // 删除倒数第K个结点
    public static Node deleteLastKth(Node list, int k) {
        Node fast = list;
        int i = 1;
        while (fast != null && i < k) {
            fast = fast.next;
            ++i;
        }

        if (fast == null) return list;

        Node slow = list;
        Node prev = null;
        while (fast.next != null) {
            fast = fast.next;
            prev = slow;
            slow = slow.next;
        }

        if (prev == null) {
            list = list.next;
        } else {
            prev.next = prev.next.next;
        }
        return list;
    }

    // 求中间结点
    public static Node findMiddleNode(Node list) {
        if (list == null) return null;

        Node fast = list;
        Node slow = list;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    public static void printAll(Node list) {
        Node p = list;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.next;
        }
        System.out.println();
    }

    public static Node createNode(int value) {
        return new Node(value, null);
    }

    public static class Node {
        private int data;
        private Node next;
        private Node head;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public Node(int data) {
            this.data = data;
        }

        public int getData() {
            return data;
        }

        public Node add (Node node) {
            if (head == null) {
                head = node;
            }
            Node curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = node;
            return node;
        }
    }

    //1
    public Node reverseList(Node headNode) {
        Node curr = headNode;
        Node before = null;

        while (curr != null) {
            Node after = curr.next;

            curr.next = before;

            before = curr;
            curr = after;
        }
        return before;
    }

    //2
    public boolean checkCycleList(Node headNode) {
        if (headNode == null) {
            return false;
        }
        Node fastNode = headNode.next;
        Node slowNode = headNode;

        while (fastNode != null && fastNode.next != null) {
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;
            if (slowNode == fastNode || fastNode.next == slowNode) {
                return true;
            }
        }
        return false;
    }

    //3
    public Node mergeListTow(Node head1, Node head2) {
        Node newHead = new Node(0);
        Node temp = newHead;
        while (head1 != null && head2 != null) {
            if (head1.data >= head2.data) {
                temp.next = head2;
                head2 = head2.next;
            } else {
                temp.next = head1;
                head1 = head1.next;
            }
            temp = temp.next;
        }

        if (head1 == null) {
            temp.next = head2;
        } else {
            temp.next = head1;
        }
        return newHead.next;
    }

    //4
    public Node deleteNumKElement(Node headNode, int k) {
        if (headNode == null) {
            return null;
        }

        Node faseNode = headNode;
        Node slowNode = headNode;

        for (int i = 0; i < k; i++) {
            if (faseNode != null) {
                faseNode = faseNode.next;
            }
        }
        Node preSlowNode = null;
        while (faseNode != null) {
            faseNode = faseNode.next;
            preSlowNode = slowNode;
            slowNode = slowNode.next;
        }
        if (preSlowNode == null) {
            headNode = headNode.next;
        } else {
            preSlowNode.next = slowNode.next;
        }
        return headNode;
    }

    //5
    public Node getMiddleElement(Node headNode) {
        if (headNode == null) {
            return null;
        }
        Node faseNode = headNode;
        Node slowNode = headNode;

        while (faseNode != null && faseNode.next != null) {
            faseNode = faseNode.next.next;
            slowNode = slowNode.next;
        }
        return slowNode;
    }

    public Node deleteRepeatElement(Node headNode) {
        if (headNode == null) {
            return null;
        }

        Node curr = headNode;
        Node before = null;

        while (curr != null) {
            Node after = curr.next;
            if (before != null && before.data == curr.data) {
                before.next = after;
            }else {
                before = curr;
            }
            curr = after;
        }
        return headNode;
    }


    /**
     * 两个链表相交元素
     *
     * @param head1 head1
     * @param head2 head2
     * @return {@link Node}
     */
    public Node getIntersectElement(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node curr1 = head1;
        Node curr2 = head2;
        while (curr1.data != curr2.data) {
            curr1 = curr1.next == null ? head2 : curr1.next;
            curr2 = curr2.next == null ? head1 : curr2.next;
        }
        return curr1;
    }

    public Node reverseLinkList(Node head) {
        if (head == null) {
            return null;
        }

        Node current = head;
        Node before = null;

        while (current != null) {
            Node after = current.next;

            current.next = before;
            before = current;
            current = after;
        }

        return before;
    }

    public boolean checkHasCycleInList(Node head) {
        if (head == null) {
            return false;
        }

        Node fastNode = head;
        Node slowNode = head;

        while (fastNode != null && fastNode.next != null) {
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;

            if (fastNode == slowNode) {
                return true;
            }
        }
        return false;
    }

    public Node mergeTwoLinkedList(Node head1, Node head2) {
        Node newHead = new Node(-1);
        Node current = newHead;
        while (head1 != null && head2 != null) {
            if (head1.data >= head2.data) {
                current.next = head2;
                head2 = head2.next;
            }else {
                current.next = head1;
                head1 = head1.next;
            }
            current = current.next;
        }
        current.next = head1 == null ? head2 : head1;
        return newHead.next;
    }

    public Node getIntersectListNode(Node list1, Node list2) {
        if (list1 == null || list2 == null) {
            return null;
        }
        Node curr1 = list1;
        Node curr2 = list2;
        while (curr1.data != curr2.data) {
            if (curr1.next == null) {
                curr1.next = list2;
            }
            curr1 = curr1.next;
            if (curr2.next == null) {
                curr2.next = list1;
            }
            curr2 = curr2.next;
        }
        return curr1;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode curr1 = headA;
        ListNode curr2 = headB;
        while (curr1 != curr2) {
            curr1 = curr1 == null ? headB : headA.next;
            curr2 = curr2 == null ? headA : headB.next;
        }
        return curr1;
    }


    public static void main(String[] args) {
    }
}
