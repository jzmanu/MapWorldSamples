package com.manu.test.cluser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Kmeans {

    public Kmeans(ArrayList<DefaultGeoPoint> dataList) {
        this.arraylist = dataList;
    }


    private class NodeComparator {
        DefaultGeoPoint nodeOne;
        DefaultGeoPoint nodeTwo;
        double distance;

        public void compute() {
            double val = 0;
            for (int i = 0; i < dimension; ++i) {
                val += (this.nodeOne.attributes[i] - this.nodeTwo.attributes[i]) *
                        (this.nodeOne.attributes[i] - this.nodeTwo.attributes[i]);
            }
            this.distance = val;
        }
    }

    private ArrayList<DefaultGeoPoint> arraylist;
    private ArrayList<DefaultGeoPoint> centroidList;
    private double averageDis;
    private int dimension = 2;
    private Queue<NodeComparator> FsQueue =
            new PriorityQueue<>(150, // 用来排序任意两点之间的距离，从大到小排
                    new Comparator<NodeComparator>() {
                        public int compare(NodeComparator one, NodeComparator two) {
                            if (one.distance < two.distance)
                                return 1;
                            else if (one.distance > two.distance)
                                return -1;
                            else
                                return 0;
                        }
                    });


    private void computeTheK() {
        int cntTuple = 0;
        for (int i = 0; i < arraylist.size() - 1; ++i) {
            for (int j = i + 1; j < arraylist.size(); ++j) {
                NodeComparator nodecomp = new NodeComparator();
                nodecomp.nodeOne = new DefaultGeoPoint();
                nodecomp.nodeTwo = new DefaultGeoPoint();
                for (int k = 0; k < dimension; ++k) {
                    nodecomp.nodeOne.attributes[k] = arraylist.get(i).attributes[k];
                    nodecomp.nodeTwo.attributes[k] = arraylist.get(j).attributes[k];
                }
                nodecomp.compute();
                averageDis += nodecomp.distance;
                FsQueue.add(nodecomp);
                cntTuple++;
            }
        }
        averageDis /= cntTuple;// 计算平均距离
        chooseCentroid(FsQueue);
    }

    private double getDistance(DefaultGeoPoint one, DefaultGeoPoint two) {// 计算两点间的欧氏距离
        double val = 0;
        for (int i = 0; i < dimension; ++i) {
            val += (one.attributes[i] - two.attributes[i]) * (one.attributes[i] - two.attributes[i]);
        }
        return val;
    }

    public void chooseCentroid(Queue<NodeComparator> queue) {
        centroidList = new ArrayList<DefaultGeoPoint>();
        boolean flag = false;
        while (!queue.isEmpty()) {
            boolean judgeOne = false;
            boolean judgeTwo = false;
            NodeComparator nc = FsQueue.poll();
            if (nc.distance < averageDis)
                break;// 如果接下来的元组，两节点间距离小于平均距离，则不继续迭代
            if (!flag) {
                centroidList.add(nc.nodeOne);// 先加入所有点中距离最远的两个点
                centroidList.add(nc.nodeTwo);
                flag = true;
            } else {// 之后从之前已加入的最远的两个点开始，找离这两个点最远的点，
                // 如果距离大于所有点的平均距离，则认为找到了新的质心，否则不认定为质心
                for (int i = 0; i < centroidList.size(); ++i) {
                    DefaultGeoPoint testnode = centroidList.get(i);
                    if (centroidList.contains(nc.nodeOne) || getDistance(testnode, nc.nodeOne) < averageDis) {
                        judgeOne = true;
                    }
                    if (centroidList.contains(nc.nodeTwo) || getDistance(testnode, nc.nodeTwo) < averageDis) {
                        judgeTwo = true;
                    }
                }
                if (!judgeOne) {
                    centroidList.add(nc.nodeOne);
                }
                if (!judgeTwo) {
                    centroidList.add(nc.nodeTwo);
                }
            }
        }
    }

    private void doIteration(ArrayList<DefaultGeoPoint> centroid) {
        int cnt = 1;
        int cntEnd = 0;
        int numLabel = centroid.size();
        while (true) {// 迭代，直到所有的质心都不变化为止
            boolean flag = false;
            for (int i = 0; i < arraylist.size(); ++i) {
                double dis = 0x7fffffff;
                cnt = 1;
                for (int j = 0; j < centroid.size(); ++j) {
                    DefaultGeoPoint node = centroid.get(j);
                    if (getDistance(arraylist.get(i), node) < dis) {
                        dis = getDistance(arraylist.get(i), node);
                        arraylist.get(i).label = cnt;
                    }
                    cnt++;
                }
            }
            int j = 0;
            numLabel -= 1;
            while (j < numLabel) {
                int c = 0;
                DefaultGeoPoint node = new DefaultGeoPoint();
                for (int i = 0; i < arraylist.size(); ++i) {
                    if (arraylist.get(i).label == j + 1) {
                        for (int k = 0; k < dimension; ++k) {
                            node.attributes[k] += arraylist.get(i).attributes[k];
                        }
                        c++;
                    }
                }
                DecimalFormat df = new DecimalFormat("#.###");// 保留小数点后三位
                double[] attributeList = new double[100];
                for (int i = 0; i < dimension; ++i) {
                    if (c > 0) {
                        System.out.println("node.attributes[i] / c-->" + node.attributes[i]);
                        System.out.println("c-->" + c);
                        System.out.println("node.attributes[i] / c-->" + node.attributes[i] / c);
                        attributeList[i] = Double.parseDouble(df.format(node.attributes[i] / c));
                        if (attributeList[i] != centroid.get(j).attributes[i]) {
                            centroid.get(j).attributes[i] = attributeList[i];
                            flag = true;
                        }
                    }

                }
                if (!flag) {
                    cntEnd++;
                    if (cntEnd == numLabel) {// 若所有的质心都不变，则跳出循环
                        break;
                    }
                }
                j++;
            }
            if (cntEnd == numLabel) {// 若所有的质心都不变，则 success
                System.out.println("run kmeans successfully.");
                break;
            }
        }
    }

    public void cluser() {
        computeTheK();
        doIteration(centroidList);
    }
}