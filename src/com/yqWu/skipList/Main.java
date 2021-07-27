package com.yqWu.skipList;

import java.util.LinkedList;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SkipList<Integer, String> skipList = new SkipList<>();
//        long start = System.currentTimeMillis();
//        for(int i = 1; i <= 40000; i++){
//            skipList.insert(i, i);
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("跳表插入数据耗时：" + (end - start) + "ms");
//        System.out.println(skipList.listInfo());
//
//        long start1 = System.currentTimeMillis();
//        skipList.get(40000);
//        long end1 = System.currentTimeMillis();
//        System.out.println("跳表读取4w个数据耗时：" + (end1 - start1) + "ms");
//
//
//        LinkedList<Integer> list = new LinkedList<>();
//        long start2 = System.currentTimeMillis();
//        for(int i = 0; i < 40000; i++){
//            list.add(i);
//        }
//        long end2 = System.currentTimeMillis();
//        System.out.println("链表插入4w个数据耗时：" + (end2 - start2) + "ms");
//
//        long start3 = System.currentTimeMillis();
//        list.indexOf(40000);
//        long end3 = System.currentTimeMillis();
//        System.out.println("链表读取4w个数据耗时：" + (end3 - start3) + "ms");
//        skipList.insert(1, "学");
//        skipList.insert(3, "算法");
//        skipList.insert(7, "认准");
//        skipList.insert(8, "微信公众号：代码随想录");
//        skipList.insert(9, "学习");
//        skipList.insert(19, "算法不迷路");
//        skipList.insert(19, "赶快关注吧你会发现详见很晚！");

//        System.out.println(skipList.toString());

//        skipList.dumpFile();
        skipList.loadFile();
//        System.out.println(skipList.listInfo());
        System.out.println(skipList.toString());
    }
}
