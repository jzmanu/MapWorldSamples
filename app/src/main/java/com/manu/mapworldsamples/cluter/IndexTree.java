package com.manu.mapworldsamples.cluter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * author: lx
 * date: 16-9-27
 */
public class IndexTree<K, T> {

    private HashMap<K, IndexTree<K, T>> children;
    private IndexTree<K, T> parent;
    private K index;
    private T data;

    private final Object mLock = new Object();

    public IndexTree(K index, T data) {
        this.index = index;
        this.data = data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public K getIndex() {
        return index;
    }

    public void setParent(IndexTree<K, T> parent) {
        this.parent = parent;
    }

    public IndexTree<K, T> putChild(K index, T data) {
        if (index == null) {
            throw new IllegalArgumentException("null index is not acceptable");
        }
        IndexTree<K, T> node = getChild(index);
        if (node != null) {
            node.data = data;
        } else {
            node = new IndexTree<>(index, data);
            node.parent = this;
            synchronized (mLock) {
                if (children == null) {
                    children = new HashMap<>();
                }
                children.put(node.index, node);
            }
        }
        return node;
    }

    public boolean putNodeByIndexList(List<K> indexList, T data) {
        int length;
        if (indexList == null || (length = indexList.size()) == 0) {
            throw new IllegalArgumentException("empty index is not acceptable");
        }
        if (!indexList.get(0).equals(index)) {
            return false;
        }
        if (indexList.size() == 1) {
            this.data = data;
        } else {
            IndexTree<K, T> parent = this;
            K idx;
            for (int i = 1; i < length; i ++) {
                idx = indexList.get(i);
                if (i == length - 1) {
                    // end of index, put data.
                    parent.putChild(idx, data);
                } else {
                    // way index. create if not exist.
                    IndexTree<K, T> node = parent.getChild(idx);
                    if (node == null) {
                        node = parent.putChild(idx, null);
                    }
                    parent = node;
                }
            }
        }
        return true;
    }

    public IndexTree<K, T> getParent() {
        return parent;
    }

    public IndexTree<K, T> getRoot() {
        IndexTree<K, T> node = this;
        while (node.parent != null) {
            node = node.parent;
        }
        return node;
    }

    public IndexTree<K, T> getChild(K index) {
        if (children == null) {
            return null;
        } else {
            return children.get(index);
        }
    }

    public IndexTree<K, T> getNodeByIndexList(List<K> indexList) {
        int length;
        if (indexList == null || (length = indexList.size()) == 0) {
            throw new IllegalArgumentException("empty index is not acceptable");
        }
        if (!indexList.get(0).equals(index)) {
            return null;
        }
        if (indexList.size() == 1) {
            return this;
        } else {
            IndexTree<K, T> node = this;
            K idx;
            for (int i = 1; i < length; i ++) {
                idx = indexList.get(i);
                node = node.getChild(idx);
                if (node == null) {
                    // search fail before reach index end, return null
                    return null;
                }
            }
            return node;
        }
    }

    public List<T> getChildDataList() {
        if (children == null) {
            return null;
        } else {
            ArrayList<T> list = new ArrayList<>(children.size());
            synchronized (mLock) {
                Iterator<IndexTree<K, T>> it = children.values().iterator();
                while (it.hasNext()) {
                    list.add(it.next().data);
                }
            }
            return list;
        }
    }

    public int getChildCount() {
        if (children == null) {
            return 0;
        } else {
            return children.size();
        }
    }

    public Iterator<IndexTree<K, T>> iterateChild() {
        if (children == null) {
            return null;
        } else {
            return children.values().iterator();
        }
    }

    public List<IndexTree<K, T>> getNodeListByLevel(int level) {
        LinkedList<IndexTree<K, T>> ret = new LinkedList<>();
        if (level <= 1) {
            ret.add(this);
            return ret;
        } else if (children == null || children.size() == 0) {
            return null;
        } else {
            synchronized (mLock) {
                Iterator<IndexTree<K, T>> it = children.values().iterator();
                while (it.hasNext()) {
                    List<IndexTree<K, T>> list = it.next().getNodeListByLevel(level - 1);
                    if (list != null) {
                        ret.addAll(list);
                    }
                }
            }
            return ret;
        }
    }

    @Override
    public String toString() {
        return index + "-" + (children == null ? 0 : children.size());
    }
}