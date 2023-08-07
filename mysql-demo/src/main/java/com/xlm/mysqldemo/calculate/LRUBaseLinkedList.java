package com.xlm.mysqldemo.calculate; /**
 * @author xlm
 * @date 2023/7/7 下午4:30
 */

import java.util.Scanner;

/**
 * 基于单链表LRU算法（java）
 *
 * @author hoda
 * @create 2018-12-17
 */
public class LRUBaseLinkedList<T> {


    /**
     * 默认链表容量
     */
    private final static Integer DEFAULT_CAPACITY = 10;

    /**
     * 头结点
     */
    private SNode<T> headNode;

    /**
     * 链表长度
     */
    private Integer length;

    /**
     * 链表容量
     */
    private Integer capacity;

    public LRUBaseLinkedList() {
        this.headNode = new SNode<>();
        this.capacity = DEFAULT_CAPACITY;
        this.length = 0;
    }

    public LRUBaseLinkedList(Integer capacity) {
        this.headNode = new SNode<>();
        this.capacity = capacity;
        this.length = 0;
    }

    public void add(T data) {
        SNode preNode = findPreNode(data);

        // 链表中存在，删除原数据，再插入到链表的头部
        if (preNode != null) {
            deleteElemOptim(preNode);
            intsertElemAtBegin(data);
        } else {
            if (length >= this.capacity) {
                //删除尾结点
                deleteElemAtEnd();
            }
            intsertElemAtBegin(data);
        }
    }

    /**
     * 删除preNode结点下一个元素
     *
     * @param preNode
     */
    private void deleteElemOptim(SNode preNode) {
        SNode temp = preNode.getNext();
        preNode.setNext(temp.getNext());
        temp = null;
        length--;
    }

    /**
     * 链表头部插入节点
     *
     * @param data
     */
    private void intsertElemAtBegin(T data) {
        SNode next = headNode.getNext();
        headNode.setNext(new SNode(data, next));
        length++;
    }

    /**
     * 获取查找到元素的前一个结点
     *
     * @param data
     * @return
     */
    private SNode findPreNode(T data) {
        SNode node = headNode;
        while (node.getNext() != null) {
            if (data.equals(node.getNext().getElement())) {
                return node;
            }
            node = node.getNext();
        }
        return null;
    }

    /**
     * 删除尾结点
     */
    private void deleteElemAtEnd() {
        SNode ptr = headNode;
        // 空链表直接返回
        if (ptr.getNext() == null) {
            return;
        }

        // 倒数第二个结点
        while (ptr.getNext().getNext() != null) {
            ptr = ptr.getNext();
        }

        SNode tmp = ptr.getNext();
        ptr.setNext(null);
        tmp = null;
        length--;
    }

    private void printAll() {
        SNode node = headNode.getNext();
        while (node != null) {
            System.out.print(node.getElement() + ",");
            node = node.getNext();
        }
        System.out.println();
    }

    public class SNode<T> {

        private T element;

        private SNode<T> next;

        public SNode(T element) {
            this.element = element;
        }

        public SNode(T element, SNode next) {
            this.element = element;
            this.next = next;
        }

        public SNode() {
            this.next = null;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public SNode getNext() {
            return next;
        }

        public void setNext(SNode next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {
        LRUBaseLinkedList<Integer> list = new LRUBaseLinkedList<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            list.add(sc.nextInt());
            list.printAll();
        }
    }

    public void reverseList(SNode<Integer> head) {
        SNode<Integer> curr = head;
        SNode<Integer> before = null;

        while (curr != null) {
            SNode<Integer> after = curr.next;

            curr.next = before;

            before = curr;
            curr = after;
        }
    }

    public boolean checkExistCycle(SNode<Integer> head) {
        if (head == null) {
            return false;
        }
        SNode<Integer> fastNode = head.next;
        SNode<Integer> slowNode = head;

        while (fastNode != null && fastNode.next != null) {
            fastNode = fastNode.next.next;
            slowNode = slowNode.next;

            if (slowNode == fastNode) {
                return true;
            }
        }
        return false;
    }

    public SNode<Integer> mergeList(SNode<Integer> l1, SNode<Integer> l2) {

        SNode<Integer> newHead = new SNode<>();
        SNode<Integer> temp = newHead;

        while (l1 != null && l2 != null) {
            if (l1.element >= l2.element) {
                temp.next = l2;
                l2 = l2.next;
            } else {
                temp.next = l1;
                l1 = l1.next;
            }
            temp = temp.next;
        }

        if (l1 == null) {
            temp.next = l2;
        } else {
            temp.next = l1;
        }
        return newHead.next;
    }


    public SNode<Integer> deleteNumKElement(SNode<Integer> head, int k) {
        if (head == null) {
            return null;
        }
        SNode<Integer> fastHead = head;
        SNode<Integer> slowHead = head;
        for (int integer = 0; integer < k; integer++) {
            if (fastHead != null) {
                fastHead = fastHead.next;
            }
        }
        SNode<Integer> slowHeadPre = null;
        while (fastHead != null) {
            fastHead = fastHead.next;
            slowHeadPre = slowHead;
            slowHead = slowHead.next;
        }

        if (slowHeadPre == null) {
            return null;
        } else {
            return head;
        }
    }

    public SNode<Integer> getMiddleElement(SNode<Integer> head) {
        if (head == null) {
            return null;
        }
        SNode<Integer> fastHead = head.next;
        SNode<Integer> slowHead = head;
        while (fastHead != null && fastHead.next != null) {
            fastHead = fastHead.next;
            slowHead = slowHead.next;
        }
        return slowHead;
    }

    public SNode<Integer> getIntersectElement(SNode<Integer> la, SNode<Integer> lb) {
        if (la == null || lb == null) {
            return null;
        }
        SNode<Integer> curia = la;
        SNode<Integer> curb = lb;
        while (curia != curb) {
            curia = curia == null ? curb : curia.next;
            curb = curb == null ? curia : curb.next;
        }
        return curia;
    }



}
