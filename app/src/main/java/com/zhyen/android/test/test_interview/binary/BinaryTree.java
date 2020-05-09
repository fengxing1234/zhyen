package com.zhyen.android.test.test_interview.binary;

import java.util.LinkedList;

public class BinaryTree {
    private BinaryTreeNode root;

    //初始化二叉树
    public BinaryTree() {
    }

    public BinaryTree(BinaryTreeNode root) {
        this.root = root;
    }

    public void setRoot(BinaryTreeNode root) {
        this.root = root;
    }

    public BinaryTreeNode getRoot() {
        return root;
    }

    /**
     * 根据name找到指定节点
     *
     * @param name
     * @return
     */
    public BinaryTreeNode findTreeNode(String name) {
        LinkedList<BinaryTreeNode> nodes = new LinkedList<>();
        nodes.add(root);
        while (!nodes.isEmpty()) {
            BinaryTreeNode node = nodes.poll();
            if (node.getName().equals(name)) {
                return node;
            }
            BinaryTreeNode leftChild = node.getLeftChild();
            BinaryTreeNode rightChild = node.getRightChild();
            if (leftChild != null) {
                nodes.push(leftChild);
            }
            if (rightChild != null) {
                nodes.push(rightChild);
            }
        }
        return null;
    }

    /**
     * 二叉树的清空：
     * 首先提供一个清空以某个节点为根节点的子树的方法，既递归地删除每个节点；
     * 接着提供一个删除树的方法，直接通过第一种方法删除到根节点即可
     */
    //清除某个子树的所有节点
    public void clear(BinaryTreeNode node) {
        if (node != null) {
            clear(node.getLeftChild());
            clear(node.getRightChild());
            node.clear();
            node = null;
        }
    }

    //清空树
    public void clear() {
        clear(root);
    }

    //判断二叉树是否为空
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * 获取整棵树的高度
     * <p>
     * 求二叉树的高度：
     * 首先要一种获取以某个节点为子树的高度的方法，使用递归调用。
     * 如果一个节点为空，那么这个节点肯定是一颗空树，高度为0；
     * 如果不为空，那么我们要遍历地比较它的左子树高度和右子树高度，
     * 高的一个为这个子树的最大高度，然后加上自己本身的高度就是了
     * 获取二叉树的高度，只需要调用第一种方法，即传入根节点
     *
     * @return
     */
    public int treeHeight() {
        return treeHeight(root);
    }

    /**
     * 获取某个节点的高度
     *
     * @param node
     * @return
     */
    public int treeHeight(BinaryTreeNode node) {
        if (node == null) {
            return 0;
        }
        int l = treeHeight(node.getLeftChild());
        int r = treeHeight(node.getRightChild());
        return l > r ? l + 1 : r + 1;
    }

    /**
     * 获取所有节点个数
     * <p>
     * 求二叉树的节点数：
     * 求节点数时，我们看看获取某个节点为子树的节点数的实现。
     * 首先节点为空，则个数肯定为0；
     * 如果不为空，那就算上这个节点之后继续递归所有左右子树的子节点数，
     * 全部相加就是以所给节点为根的子树的节点数
     * 如果求二叉树的节点数，则输入根节点即可
     * <p>
     * 思路：获取二叉树节点数，需要获取以某个节点为根的子树的节点数实现。
     * 如果节点为空，则个数肯定为0；如果不为空，则算上这个节点之后，继续递归计算所有子树的节点数，全部相加即可
     */
    public int nodeSize() {
        return nodeSize(root);
    }

    /**
     * 获取某个节点下的所有节点个数
     * <p>
     * 计算本节点 所以要+1
     * 递归获取左子树节点数和右子树节点数，最终相加
     *
     * @param node
     * @return
     */
    public int nodeSize(BinaryTreeNode node) {
        if (node == null) {
            return 0;
        }
        //计算本节点 所以要+1
        //递归获取左子树节点数和右子树节点数，最终相加
        return 1 + nodeSize(node.getLeftChild()) + nodeSize(node.getRightChild());
    }


    /**
     * 查找node节点在二叉树中的父节点
     * <p>
     * 思路：首先，同样需要通过一种方法来获取某个节点在某个子树中的父节点，这里使用递归实现，接着通过这种方法获取这个节点在二叉树中的父节点
     * 事实上，以现有的这种二叉树的形式，我们并没有办法直接获取一个节点的父节点， 这里只能通过从根节点遍历来比较获取
     *
     * @param node
     * @return
     */
    public BinaryTreeNode getParent(BinaryTreeNode node) {
        return (root == null || root == node) ? null : getParent(root, node);
    }

    /**
     * node节点在subTree子树中的父节点
     *
     * @param subTree
     * @param node
     * @return
     */
    public BinaryTreeNode getParent(BinaryTreeNode subTree, BinaryTreeNode node) {
        if (subTree == null) {
            return null;   //如果是空子树，则没有父节点
        }
        if (subTree.getLeftChild() == node || subTree.getRightChild() == node) {
            return subTree;   //如果子树的根节点的左右孩子之一是待查节点，则返回子树的根节点
        }
        BinaryTreeNode parent = null;
        if (getParent(subTree.getLeftChild(), node) != null) {
            parent = getParent(subTree.getLeftChild(), node);
            return parent;
        } else {
            //递归左右子树
            return getParent(subTree.getRightChild(), node);
        }
    }

    //获取某个节点的左子树
    public BinaryTreeNode getLeftTree(BinaryTreeNode node) {
        return node.getLeftChild();
    }

    //获取某个节点的右子树
    public BinaryTreeNode getRightTree(BinaryTreeNode node) {
        return node.getRightChild();
    }

    /**
     * 给某个节点插入左节点
     * 分两种情况：插入某个节点的左子节点；插入某个节点的右子节点
     * 值得指出的是，当这个节点本身有子节点时，这样的插入也会覆盖原来在这个位置上的节点。
     * 另外，虽然插入的是子节点，但是子节点也可以代表一颗子树。
     * 因为单从这个节点来看并不知道这个节点是否有左右子树存在，所以虽然插入的是一个节点，但有可能
     * 插入可很多节点（插入的是一颗子树）
     *
     * @param parent
     * @param newNode
     */
    public void insertLeft(BinaryTreeNode parent, BinaryTreeNode newNode) {
        parent.setLeftChild(newNode);
    }

    //给某个节点插入右节点
    public void insertRight(BinaryTreeNode parent, BinaryTreeNode newNode) {
        parent.setRightChild(newNode);
    }

    /**
     *   事实上对于二叉查找树的插入操作的设计是比较简单，
     * 我们只要利用二叉查找树的特性（即对每个父结点，它的左子树中所有项的值小T中的值，而它的右子树中所有项的值都大于T中的值），
     * 找到只对应的插入位置即可，假如现在我们要插入data=4的结点，那么可以这样操作，
     * 沿着树查找(比较结点的数据与data的大小从而决定往左/右子树继续前行)，如果找到data(4),则什么也不做，
     * 否则将data插入到遍历的路径上的最后一个点.
     */
    public void insert(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        if (root == null) {
            root = node;
            return;
        }
        BinaryTreeNode parent, currentNode;
        currentNode = root;
        while (true) {
            parent = currentNode;
            if (parent.getData() > node.getData()) {
                currentNode = parent.getLeftChild();
                if (currentNode == null) {
                    parent.setLeftChild(node);
                    return;
                }
            } else {
                currentNode = parent.getRightChild();
                if (currentNode == null) {
                    parent.setRightChild(node);
                    return;
                }
            }
        }
    }

    public void preOrder() {
        preOrder(root);
    }

    /**
     * 访问根节点
     * 先根遍历左子树
     * 先根遍历右子树
     * 退出
     *
     * @param node
     */
    public void preOrder(BinaryTreeNode node) {
        if (node != null) {
            System.out.println(node.getName() + " == " + node.getData()); //先访问根节点
            preOrder(node.getLeftChild());  //先根遍历左子树
            preOrder(node.getRightChild());  //先根遍历右子树
        }
    }

    public void postOrder() {
        postOrder(root);
    }

    public void postOrder(BinaryTreeNode node) {
        if (node != null) {
            postOrder(node.getLeftChild());  //后根遍历左子树
            postOrder(node.getRightChild());  //后根遍历右子树
            System.out.println(node.getName() + " == " + node.getData());   //访问根节点
        }
    }
}
