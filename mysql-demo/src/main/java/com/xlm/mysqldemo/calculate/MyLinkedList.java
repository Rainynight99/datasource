package com.xlm.mysqldemo.calculate;

import java.time.Period;

/**
 * @author xlm
 * @date 2023/7/24 上午11:16
 */
public class MyLinkedList {

    private MNode head;

    private Integer length;

    private MNode last;


    public MyLinkedList() {
        head = new MNode();
        last = head;
    }

    public void add(MNode node) {
        last.next = node;
        last = node;
    }

    public class MNode {

       private Integer data;

       private MNode next;

       public MNode(Integer data) {
           this.data = data;
       }

        public MNode() {
           this.next = null;
        }

        public Integer getData() {
            return data;
        }

        public void setData(Integer data) {
            this.data = data;
        }

        public MNode getNext() {
            return next;
        }

        public void setNext(MNode next) {
            this.next = next;
        }
    }
}
