package com.xlm.mysqldemo.calculate;

import java.util.Objects;

/**
 * @author xlm
 * @date 2023/7/7 上午10:25
 */
public class SingleLinkList {

    private final Node head = new Node(5);



    public void add1(Node newNode) {
        Node temp = head;

        while (temp.next != null) {
            temp = temp.next;
        }

        temp.next = newNode;
    }

    public void add2(Node newNode) {
        Node temp = head;

        while (temp.next != null) {
            //从大到小的顺序排列
            if (temp.next.val < newNode.val) {
                newNode.next = temp.next;
                temp.next = newNode;
                return;
            }
            temp = temp.next;
            if (Objects.equals(temp.val, newNode.val)) {
                System.out.println("error");
                return;
            }
        }

        temp.next = newNode;
    }

    public void deleteNode(Node deleteNode) {
        Node temp = head;

        while (temp.next != null) {
            if (Objects.equals(temp.next.val, deleteNode.val)) {
                temp.next = temp.next.next;
                return;
            }
            temp = temp.next;
        }
    }

    public Node reverseList() {
        Node pre = null;
        Node curr = head;
        Node post;

        while (curr!= null) {
            post = curr.next;
            curr.next = pre;
            pre = curr;
            curr = post;
        }
        //head.next = pre;
        return pre;
    }

    public void printList(Node head) {
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.val);
            temp = temp.next;
        }
    }

    public Boolean fastSlowPointer(Node head) {
        Node tempFast = head;
        Node tempSlow = head;
        while (tempFast != null && tempFast.next != null) {
            tempSlow = tempSlow.next;
            tempFast = tempFast.next.next;
            if (tempFast == null) {
                return false;
            }
            if (Objects.equals(tempFast.val, tempSlow.val)) {
                return true;
            }
        }
        return false;
    }


    public Node mergeTwoListToOne(Node headOne, Node headTwo) {
        Node mergeHead = new Node(-1);
        Node currentNode = mergeHead;
        while (headOne != null && headTwo != null) {
            if (headOne.val >= headTwo.val) {
                currentNode.next = headTwo;
                headTwo = headTwo.next;
            }else {
                currentNode.next = headOne;
                headOne = headOne.next;
            }
            currentNode = currentNode.next;
        }

        if (headOne == null) {
            currentNode.next = headTwo;
        }
        if (headTwo == null) {
            currentNode.next = headOne;
        }

        return mergeHead.next;
    }

    public Node mergeTwoLists(Node l1, Node l2) {
        // 创建一个新链表的头结点
        Node mergedList = new Node(-1);
        // 创建指向新链表的指针
        Node currentMerged = mergedList;

        // 当两个链表都非空时
        while (l1 != null && l2 != null) {
            // 比较两个链表当前节点的值，将较小的节点添加到新链表中
            if (l1.val < l2.val) {
                currentMerged.next = l1;
                l1 = l1.next;
            } else {
                currentMerged.next = l2;
                l2 = l2.next;
            }
            // 将新链表的指针指向当前节点
            currentMerged = currentMerged.next;
        }

        // 将剩余的节点添加到新链表中
        if (l1 != null) {
            currentMerged.next = l1;
        } else {
            currentMerged.next = l2;
        }

        return mergedList.next;
    }

    public Node deleteRepeatElement(Node headNode) {
        if (headNode == null) {
            return null;
        }

        Node curr = headNode;
        Node before = null;

        while (curr != null) {
            Node after = curr.next;
            if (before != null && Objects.equals(before.val, curr.val)) {
                before.next = after;
            }else {
                before = curr;
            }
            curr = after;
        }
        return headNode;
    }

    public static void main(String[] args) {
        SingleLinkList singleLinkList = new SingleLinkList();
        Node head = new Node(1);
        singleLinkList.add1(head);
        singleLinkList.add1(new Node(2));
        //singleLinkList.add1(new Node(7));
        //singleLinkList.add1(new Node(7));
        //singleLinkList.add1(new Node(4));
        //singleLinkList.add1(new Node(5));
        //singleLinkList.add1(new Node(6));
        //Node node = singleLinkList.removeSpecificNode(head, 7);
        boolean palindrome = singleLinkList.isPalindrome(head);
        System.out.println();

        //singleLinkList.deleteNode(new Node(3));
        //Node head = singleLinkList.reverseList();
        //Boolean check = singleLinkList.fastSlowPointer(head);
        //Node merged = singleLinkList.mergeTwoLists(head, head);
        //Node node = singleLinkList.deleteRepeatElement(singleLinkList.head);
        //singleLinkList.printList(node);
        //System.out.println(check);
        //if (!check) {
        //    singleLinkList.printList(head);
        //}
    }

    public Node deleteElementRepeat(Node head) {
        if (head == null) {
            return null;
        }
        Node temp = head;
        while (temp != null && temp.next != null) {
            if (Objects.equals(temp.val, temp.next.val)) {
                temp = temp.next.next;
            }
        }
        return head;
    }

    public Node getIntersectionNode2(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node tmpHead1 = head1;
        Node tmpHead2 = head2;
        while (tmpHead1 != tmpHead2) {
            tmpHead1 = tmpHead1 == null ? head2 : tmpHead1.next;
            tmpHead2 = tmpHead2 == null ? head1 : tmpHead2.next;
        }
        return tmpHead1;
    }

//[1,2,6,3,4,5,6]  6
    public Node removeSpecificNode(Node head, Integer val) {
        if (head == null) {
            return null;
        }
        while (head != null && Objects.equals(head.val, val)) {
            head = head.next;
        }
        Node curr = head;
        Node before = null;
        while (curr != null) {
            Node after = curr.next;
            if (Objects.equals(curr.val, val)) {
                assert before != null;
                before.next = after;
                curr = after;
            }else {
                before = curr;
                curr = curr.next;
            }
        }
        return head;
    }

    public boolean isPalindrome(Node head) {
        if (head == null) {
            return false;
        }
        Node curr = head;
        //Stack<Node> stack = new Stack<>();
        //while (curr != null) {
        //    stack.push(curr);
        //    curr = curr.next;
        //}
        //Node curr1 = head;
        //while (curr1 != null) {
        //    Node pop = stack.pop();
        //    if (!Objects.equals(pop.val, curr1.val)) {
        //        return false;
        //    }
        //    curr1 = curr1.next;
        //}
        //return true;
        Node mid = findMid(head);
        Node reverse = reverse(mid.next);
        while (curr != null && reverse != null) {
            if (!Objects.equals(curr.val, reverse.val)) {
                return false;
            }
            curr = curr.next;
            reverse = reverse.next;
        }
        return true;
    }

    private Node findMid(Node head) {
        if (head == null) {
            return null;
        }
        Node fastNode = head;
        Node slowNode = head;

        while (fastNode.next != null && fastNode.next.next != null) {
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;
        }
        return slowNode;
    }

    private Node reverse(Node head) {
        if (head == null) {
            return null;
        }

        Node curr = head;
        Node before = null;

        while (curr != null) {
            Node after = curr.next;
            curr.next = before;
            before = curr;
            curr = after;
        }
        return before;
    }
}
