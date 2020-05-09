package com.zhyen.android.test.test_interview;

import android.util.Log;

import com.zhyen.android.test.test_interview.binary.BinaryTree;
import com.zhyen.android.test.test_interview.binary.BinaryTreeNode;
import com.zhyen.android.test.test_interview.recursion.Fibonacci;
import com.zhyen.android.test.test_interview.recursion.HanoiRecursion;

import java.util.concurrent.ConcurrentHashMap;

public class TreeTest {
    private static final String TAG = TreeTest.class.getSimpleName();

    public static void main(String[] args) {
        ArrayTreeNode[] arrayTreeNodes = new ArrayTreeNode[10];
        ArrayTreeNode<String> node = new ArrayTreeNode<>("0", 0);
        arrayTreeNodes[0] = node;
        System.out.println("输出内容 = " + arrayTreeNodes[0].getData());

        int moveCount = new HanoiRecursion().hanoi(6, 'A', 'B', 'C');
        printf("移动次数 = " + moveCount);

        Fibonacci fi = new Fibonacci();
        int fibonacci = fi.fibonacci(5);
        printf("总共有几只动物 = " + fibonacci);
        printf("总共有几只动物 = " + fi.fibonacciNormal(7));

        BinaryTree binaryTree = new BinaryTree();
        BinaryTreeNode node1 = new BinaryTreeNode(5, "1");
        BinaryTreeNode node2 = new BinaryTreeNode(2, "2");
        BinaryTreeNode node3 = new BinaryTreeNode(6, "3");
        BinaryTreeNode node4 = new BinaryTreeNode(8, "4");
        BinaryTreeNode node5 = new BinaryTreeNode(3, "5");
        BinaryTreeNode node6 = new BinaryTreeNode(1, "6");
        BinaryTreeNode node7 = new BinaryTreeNode(0, "7");
        BinaryTreeNode node8 = new BinaryTreeNode(9, "8");
        BinaryTreeNode node9 = new BinaryTreeNode(7, "9");
        BinaryTreeNode node10 = new BinaryTreeNode(4, "10");
        binaryTree.insert(node1);
        binaryTree.insert(node2);
        binaryTree.insert(node3);
        binaryTree.insert(node4);
        binaryTree.insert(node5);
        binaryTree.insert(node6);
        binaryTree.insert(node7);
        binaryTree.insert(node8);
        binaryTree.insert(node9);
        binaryTree.insert(node10);
        binaryTree.preOrder();
        binaryTree.postOrder();
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        printf("我是修改后的提交");
    }

    public static void printf(String s) {
        System.out.println("输出内容 = " + s);
    }
}
