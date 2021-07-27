package com.yqWu.skipList;

//链表节点
public class SkipNode<K extends Comparable<? super K>, V> {
    //key值
    public K key;

    //value值
    public V val;

    //next[i]表示当前第i层节点的下一个节点
    public SkipNode<K, V>[] next;

    public SkipNode(int MAX_LEVEL, K key, V val){
        this.key = key;
        this.next = new SkipNode[MAX_LEVEL];
        this.val = val;
    }

}
