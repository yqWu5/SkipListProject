## 阅读项目前必备知识
1. Java基础
2. Java泛型、IO操作、集合
3. 跳表数据结构

## 基于跳表实现的轻量级KV存储引擎
本项目是基于跳表实现的轻量级键值型存储引，使用Java实现。
功能有：插入数据、删除数据、数据查询、数据显示、数据持久化、从硬盘加载数据。

## 项目文件
1. SkipNode.java 链表节点类
2. SkipList.java 跳表实现类
3. Main.java 测试主类

## 可用的方法
1. public V get(K key); 查询数据
2. public boolean insert(K key, V val); 插入数据
3. public boolean delete(K key); 删除数据
4. public void dumpFile(); 数据持久化
5. public boolean loadFile(); 从硬盘加载数据到内存
6. public String toString(); 显示链表节点信息
7. public String listInfo(); 跳表信息（包含层数及元素个数）

## 待优化
1. 不支持多线程操作，会导致线程安全问题
