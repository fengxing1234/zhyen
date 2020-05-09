package com.zhyen.android.test.test_interview;

public class ArrayTreeNode<T> {

    private T mData;
    private int mParentIndex;

    public ArrayTreeNode() {

    }

    public ArrayTreeNode(T data, int index) {
        this.mData = data;
        this.mParentIndex = index;
    }

    public T getData() {
        return mData;
    }

    public void setData(T mData) {
        this.mData = mData;
    }

    public int getParentIndex() {
        return mParentIndex;
    }

    public void setParentIndex(int mParentIndex) {
        this.mParentIndex = mParentIndex;
    }
}
