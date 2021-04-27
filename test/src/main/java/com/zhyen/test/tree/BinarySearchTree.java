package com.zhyen.test.tree;


import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * author : fengxing
 * date : 2021/4/25 下午2:50
 * description :
 */
public class BinarySearchTree {

    private TreeNode rootTree;

    public BinarySearchTree(int value) {
        rootTree = new TreeNode(value);
    }

    public TreeNode getRootTree() {
        return rootTree;
    }

    /**
     * 插入
     * 如果插入节点等于当前root节点,直接结束不做任何处理。
     * 如果插入节点小于当前root节点,从左节点开始查找。 （root=root.left）
     * 如果插入节点大于当前root节点,从右节点开始查找。 （root=root.right）
     * 当当前节点的左节点或者右节点为空时，插入新节点，结束过程。
     *
     * @param value
     */
    public TreeNode insert(int value) {
        TreeNode currentNode = rootTree;
        TreeNode treeNode = new TreeNode(value);
        while (true) {
            if (value == currentNode.value) {
                return null;
            }
            if (value < currentNode.value) {
                if (currentNode.left == null) {
                    return currentNode.left = treeNode;
                } else {
                    currentNode = currentNode.left;
                }
            }
            if (value > currentNode.value) {
                if (currentNode.right == null) {
                    return currentNode.right = treeNode;
                } else {
                    currentNode = currentNode.right;
                }
            }
        }
    }

    public boolean insert(TreeNode treeNode) {
        //不能插入空节点
        if (treeNode == null) {
            return false;
        }

        //null树
        if (rootTree == null) {
            return false;
        }

        //不能插入重复
        if (find(treeNode.value) != null) {
            return false;
        }
        TreeNode current = rootTree;
        while (true) {
            if (treeNode.value < current.value) {
                if (current.left == null) {
                    current.left = treeNode;
                    return true;
                } else {
                    current = current.left;
                }
            } else {
                if (current.right == null) {
                    current.right = treeNode;
                    return true;
                } else {
                    current = current.right;
                }
            }
        }
    }

    /**
     * 查找
     *
     * @param value
     * @return
     */
    public TreeNode get(int value) {
        if (rootTree == null) {
            System.out.println("搜索二叉树是空的");
            return null;
        }
        TreeNode current = rootTree;
        while (true) {
            if (current == null) {
                return null;
            }
            if (current.value == value) {
                return current;
            }
            if (value < current.value) {
                current = current.left;
            }
            if (value > current.value) {
                current = current.right;
            }
        }
    }

    public TreeNode find(int value) {
        if (rootTree == null) {
            System.out.println("搜索二叉树是空的");
            return null;
        }
        TreeNode treeNode = rootTree;
        while (treeNode.value != value) {
            if (value < treeNode.value) {
                treeNode = treeNode.left;
            }
            if (treeNode == null) {
                return null;
            }
            if (value > treeNode.value) {
                treeNode = treeNode.right;
            }
        }
        return treeNode;
    }

    /**
     * 获取最小节点
     *
     * @return
     */
    public TreeNode getMinNode() {
        //空树
        if (rootTree == null) {
            return null;
        }

        TreeNode current = rootTree;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    /**
     * 树或者子树
     *
     * @param treeNode
     * @return
     */
    public TreeNode getMinNode(TreeNode treeNode) {
        //不存在
        if (find(treeNode.value) == null) {
            return null;
        }
        TreeNode current = treeNode;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public TreeNode getMaxNode(TreeNode node) {
        if (find(node.value) == null) {
            return null;
        }
        TreeNode current = node;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    /**
     * 一、如果要删除的节点只有一个子节点（只有左子节点或者右子节点），我们只需要直接将父节点中，指向要删除节点的指针置为null
     * 二、如果要删除的节点只有一个子节点，那我们只需要更新父节点中的指针了，让它指向要删除的子节点就可以了
     * 三、如果要删除的节点有多个子节点，那么我们需要查找这个节点右子树中的最小节点，把他替换到要删除的节点上，然后在删除这个节点！
     *
     * @param value
     */
    public void delete(int value) {
        TreeNode currentRoot = rootTree;

        while (true) {
            if (currentRoot == null) {
                return;//没找到,返回或者抛异常
            }

            if (value < currentRoot.value) {
                currentRoot = currentRoot.left;
            } else if (value > currentRoot.value) {
                currentRoot = currentRoot.right;
            } else {
                TreeNode replacement;//代替者
                if (currentRoot.left == null && currentRoot.right == null) {//无双子节点
                    resetParentNode(currentRoot, null);
                } else if (currentRoot.left != null && currentRoot.right != null) {//双子节点
                    replacement = getMinNode(currentRoot.right);

                    replacement.left = currentRoot.left;
                    replacement.right = currentRoot.right;

                    resetParentNode(replacement, null);       //重设后继节点的父节点
                    resetParentNode(currentRoot, replacement);//重设正在移除节点的父节点
                } else if (currentRoot.left != null) {
                    resetParentNode(currentRoot, currentRoot.left);  //
                }                                                                  //
                else {                                                             //单节点
                    resetParentNode(currentRoot, currentRoot.right);//
                }                                                                  //
                return;
            }
        }
    }

    private TreeNode getSuccessor(TreeNode node) {
        List<TreeNode> list = new ArrayList<>();

        getTreeByInOrder(list, rootTree);

        int i = list.indexOf(node);

        if (i > 0 && ++i < list.size()) {
            return list.get(i);
        }

        return null;
    }

    private void resetParentNode(TreeNode node, TreeNode newNode) {
        Pair<TreeNode, Boolean> pair = getParent(node);

        if (pair == null) {
            return;
        }

        TreeNode parent = pair.first;

        if (pair.second) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }


    public Pair<TreeNode, Boolean> getParent(TreeNode treeNode) {
        if (rootTree == null) {
            return null;
        }
        TreeNode current = rootTree;
        TreeNode parent = null;
        boolean isLeft = false;
        while (true) {
            if (current == null) {
                return null;
            }
            if (treeNode.value < current.value) {
                isLeft = true;
                parent = current;
                current = current.left;
            } else if (treeNode.value > current.value) {
                isLeft = false;
                parent = current;
                current = current.right;
            } else {
                return new Pair<>(parent, isLeft);
            }
        }
    }

    public void delete2(int value) {
        if (rootTree == null) {
            return;
        }
        TreeNode targetParent = rootTree;
        TreeNode target = rootTree;
        boolean isLeft = false;
        while (target.value != value) {
            if (value < target.value) {
                isLeft = true;
                targetParent = target;
                target = target.left;
            } else if (value > target.value) {
                isLeft = false;
                targetParent = target;
                target = target.right;
            }
            // 没有此元素
            if (target == null) {
                break;
            }
            //要删除的节点有左右儿子节点
            if (target.left != null && target.right != null) {
                //1. 找到要删除的节点右儿子最小的节点及其父节点
                TreeNode minNode = target.right;
                TreeNode minParent = target;
                while (minNode.left != null) {
                    minParent = minNode;
                    minNode = minNode.left;
                }
                //2. 最小节点的父节点删除其左节点
                minParent.left = null;
                //3. 被删除的右子节点替换成最小节点
                minNode.left = target.left;
                minNode.right = target.right;
                //4. 被删除节点的父节点的子节点替换成最小节点
                if (isLeft) {
                    targetParent.left = minNode;
                } else {
                    targetParent.right = minNode;
                }
            } else if (target.left == null && target.right == null) {//要删除的元素有没子节点
                if (rootTree.value == value) {
                    rootTree = null;
                    return;
                }
                if (isLeft) {
                    targetParent.left = null;
                } else {
                    targetParent.right = null;
                }

            } else if (target.left != null) {
                if (isLeft) {
                    targetParent.left = target.left;
                } else {
                    targetParent.right = target.left;
                }

            } else {
                if (isLeft) {
                    targetParent.left = target.right;
                } else {
                    targetParent.right = target.right;
                }

            }
        }
    }

    public List<List<Integer>> printTreeByHierarchy() {
        TreeNode currentRoot = rootTree;

        Queue<TreeNode> queue = new LinkedList<>();

        TreeNode last = currentRoot;
        TreeNode nextLast = null;

        queue.offer(currentRoot);

        List<List<Integer>> lists = new ArrayList<>();
        List<Integer> nodes = new ArrayList<>();
        while (!queue.isEmpty()) {
            currentRoot = queue.poll();

            nodes.add(currentRoot.value);

            if (currentRoot.left != null) {
                queue.offer(currentRoot.left);
                nextLast = currentRoot.left;
            }

            if (currentRoot.right != null) {
                queue.offer(currentRoot.right);
                nextLast = currentRoot.right;
            }

            if (last == currentRoot) {//因为last是当前层的最后一个节点，如果等式成立，说明这层已经遍历完
                last = nextLast;

                lists.add(nodes);
                nodes = new ArrayList<>();
            }
        }

        System.out.println(lists);
        return lists;
    }


    /**
     * 中序排序
     *
     * @param list
     * @param treeNode
     */
    public void getTreeByInOrder(List<TreeNode> list, TreeNode treeNode) {
        if (treeNode != null) {
            getTreeByInOrder(list, treeNode.left);
            list.add(treeNode);
            getTreeByInOrder(list, treeNode.right);
        }
    }

    /**
     * 中序排序
     *
     * @param rootTree
     */
    public void inorder_iterator(TreeNode rootTree) {
        if (rootTree.left != null) {
            inorder_iterator(rootTree.left);
        }
        System.out.print(rootTree.value + " ");
        if (rootTree.right != null) {
            inorder_iterator(rootTree.right);
        }
    }


    public class TreeNode {
        public int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "value=" + value +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }
}

