package com.tydic.udbh.queue;


import java.util.LinkedList;
import java.util.Queue;

/**
 *     队列是一种特殊的线性表，它只允许在表的前端进行删除操作，而在表的后端进行插入操作。
 *     LinkedList类实现了Queue接口，因此我们可以把LinkedList当成Queue来用。
 * 测试java内部的队列
 *
 */
public class TestQueue {


    public static void main(String[] args) {
        Queue<String> queue = new LinkedList();
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        queue.offer("e");
        queue.offer("f");
        System.out.println("queue的长度"+queue.size());
        for (Object o : queue) {
            System.out.println(o);
        }
        System.out.println("================");
        System.out.println("poll="+queue.poll()); //返回第一个元素，并在队列中删除
        for (Object o : queue) {
            System.out.println(o);
        }
        System.out.println("===");
        System.out.println("element="+queue.element()); //返回第一个元素
        for(String q : queue){
            System.out.println(q);
        }
        System.out.println("===");
        System.out.println("peek="+queue.peek()); //返回第一个元素
        for(String q : queue){
            System.out.println(q);
        }

    }


}
