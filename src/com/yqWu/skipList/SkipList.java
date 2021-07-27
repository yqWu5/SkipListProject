package com.yqWu.skipList;

import java.io.*;
import java.util.Random;

//跳表实现
public class SkipList<K extends Comparable<? super K>, V> {
    //跳表最大层数32，理论上可以对2^32 - 1 个元素的查询最优
    private final int MAX_LEVEL = 32;

    //跳表当前层数，0表示原始链表，没有形成跳表
    private int level = 0;

    //跳表头部节点
    private final SkipNode<K, V> header = new SkipNode<K, V>(MAX_LEVEL, null, null);;

    //随机数种子，用于维持跳表平衡性
    private final Random randomSeed = new Random();

    private int count = 0;

    //维持跳表平衡的关键方法
    private int randomLevel(){
        int nextLevel = 1;

        while(randomSeed.nextInt() % 2 == 1){
            nextLevel += 3;
        }

        return nextLevel < MAX_LEVEL ? nextLevel : MAX_LEVEL;
    }

    /**
     * 获得节点value值的方法
     * @param key   单个节点的key值
     * @return
     */
    public V get(K key){
        SkipNode<K, V> cur = header;
        //for循环控制跳表往下移动
        for(int i = level; i >= 0; i--){
            //如果下一个节点key值小于要查询的key值，那么将当前节点往后移动（while循环控制当前层链表向后移动）
            while(cur.next != null && cur.next[i].key.compareTo(key) < 0){
                cur = cur.next[i];
            }

            //如果下一个节点的key值等于参数key值，则返回该key对应的value值
            if(cur.next[i].key.equals(key)){
                return cur.next[i].val;
            }
        }

        return null;//无结果，返回null
    }

    /**
     * 插入数据方法
     * @param key   单个节点的key值
     * @param val   单个节点的val值
     * @return      true插入成功，false插入失败
     */
    public boolean insert(K key, V val){
        SkipNode<K, V> cur = header;
        SkipNode<K, V>[] preNode = new SkipNode[MAX_LEVEL];

        //在插入新节点之前先找到插入位置的前置节点
        for(int i = level; i >= 0; i--){
            while(cur.next[i] != null && cur.next[i].key.compareTo(key) < 0){
                cur = cur.next[i];
            }
            preNode[i] = cur;
        }

        cur = cur.next[0];//跳到第0层（原始链表）中的下一个位置
        int nextLevel = randomLevel();

        //只有在cur为空或者当前key值与插入key值不相等时才插入
        if(cur == null || !cur.key.equals(key)){
            //通过平衡函数得到的nextLevel来与level进行判断是否新增一层
            if(nextLevel > level){
                for(int i = level + 1; i < nextLevel + 1; i++){
                    preNode[i] = header;//新一层的头节点为header
                }
                level = nextLevel;
            }

            SkipNode<K, V> node = new SkipNode<>(MAX_LEVEL, key, val);
            for(int i = level; i >= 0; i--){
                node.next[i] = preNode[i].next[i];
                preNode[i].next[i] = node;
            }

            //元素个数+1
            count++;
            return true;
        }

        return false;
    }

    /**
     * 删除数据的方法
     * @param key   单个节点的key值
     * @return      true删除成功，false删除失败
     */
    public boolean delete(K key){
        SkipNode<K, V> cur = header;
        SkipNode<K, V>[] preNode = new SkipNode[MAX_LEVEL];

        for(int i = level; i >= 0; i--){
            while(cur.next != null && cur.next[i].key.compareTo(key) < 0){
                cur = cur.next[i];
            }
            preNode[i] = cur;
        }

        cur = cur.next[0];//定位到第0层要删除的节点

        //跳表中不存在要删除的key值
        if(!cur.key.equals(key)){
            return false;
        }

        for(int i = level; i >=0; i--){
            //如果当前层前驱节点的下一个节点与删除key值不一样，则不删除
            if(!preNode[i].next[i].key.equals(key)){
                continue;
            }

            preNode[i].next[i] = cur.next[i];
        }

        while(level > 0 && header.next[level] == null){
            level--;
        }

        //元素个数-1
        count--;

        return true;
    }

    /**
     * 数据持久化 字节流
     */
    public void dumpFile(){
        SkipNode<K, V> cur = header;
        cur = cur.next[0];

        File file = null;
        FileOutputStream fs = null;
        BufferedOutputStream bis = null;

        try {
            file = new File("dump.aof");
            if(!file.exists()){
                file.createNewFile();
            }

            fs = new FileOutputStream(file);
            bis = new BufferedOutputStream(fs);
            while(cur != null){
                String str = cur.key + ":" + cur.val + "\r\n";
                bis.write(str.getBytes());
                cur = cur.next[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 转换流，把硬盘里的字节数据转换为字符（防止出现中文乱码）
     * @return
     */
    public boolean loadFile(){
        File file = new File("dump.aof");
        if(!file.exists()){
            return false;
        }
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String str = null;
            while((str = br.readLine()) != null){
                String[] array = str.split(":");
                insert((K)array[0], (V)array[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * 重载的toString方法，用于打印链表所有节点信息
     * @return
     */
    public String toString(){
        StringBuffer sb = new StringBuffer();
        SkipNode<K, V> cur = header.next[0];


        while(cur.next[0] != null){
            sb.append(cur.key);
            sb.append(":");
            sb.append(cur.val);
            sb.append("\r\n");
            cur = cur.next[0];
        }
        sb.append(cur.key);
        sb.append(":");
        sb.append(cur.val);


        return sb.toString();
    }

    /**
     * 返回跳表信息，包含元素个数以及跳表层数
     * @return
     */
    public String listInfo(){
        return "跳表元素个数：" + count + "，跳表层数：" + level;
    }

}
