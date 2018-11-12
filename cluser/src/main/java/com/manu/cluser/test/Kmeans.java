package com.manu.cluser.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Kmeans {
    private class Node {
        int label;// label 用来记录点属于第几个 cluster
        double[] attributes;

        public Node() {
            attributes = new double[100];
        }
    }

    private class NodeComparator {
        Node nodeOne;
        Node nodeTwo;
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

    private ArrayList<Node> arraylist;
    private ArrayList<Node> centroidList;
    private double averageDis;
    private int dimension;
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

    public void setKmeansInput(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String str;
            String[] strArray;
            arraylist = new ArrayList<>();
            while ((str = br.readLine()) != null) {
                strArray = str.split(",");
                dimension = strArray.length;
                Node node = new Node();
                for (int i = 0; i < dimension; ++i) {
                    node.attributes[i] = Double.parseDouble(strArray[i]);
                }
                arraylist.add(node);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void computeTheK() {
        int cntTuple = 0;
        for (int i = 0; i < arraylist.size() - 1; ++i) {
            for (int j = i + 1; j < arraylist.size(); ++j) {
                NodeComparator nodecomp = new NodeComparator();
                nodecomp.nodeOne = new Node();
                nodecomp.nodeTwo = new Node();
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

    public double getDistance(Node one, Node two) {// 计算两点间的欧氏距离
        double val = 0;
        for (int i = 0; i < dimension; ++i) {
            val += (one.attributes[i] - two.attributes[i]) * (one.attributes[i] - two.attributes[i]);
        }
        return val;
    }

    public void chooseCentroid(Queue<NodeComparator> queue) {
        centroidList = new ArrayList<Node>();
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
                    Node testnode = centroidList.get(i);
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

    private void doIteration(ArrayList<Node> centroid) {

        int cnt = 1;
        int cntEnd = 0;
        int numLabel = centroid.size();
        while (true) {// 迭代，直到所有的质心都不变化为止
            boolean flag = false;
            for (int i = 0; i < arraylist.size(); ++i) {
                double dis = 0x7fffffff;
                cnt = 1;
                for (int j = 0; j < centroid.size(); ++j) {
                    Node node = centroid.get(j);
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
                Node node = new Node();
                for (int i = 0; i < arraylist.size(); ++i) {
                    if (arraylist.get(i).label == j + 1) {
                        for (int k = 0; k < dimension; ++k) {
                            node.attributes[k] += arraylist.get(i).attributes[k];
                        }
                        c++;
                    }
                }
                DecimalFormat df = new DecimalFormat("#.###");// 保留小数点后三位
                double[] attributelist = new double[100];
                for (int i = 0; i < dimension; ++i) {
                    attributelist[i] = Double.parseDouble(df.format(node.attributes[i] / c));
                    if (attributelist[i] != centroid.get(j).attributes[i]) {
                        centroid.get(j).attributes[i] = attributelist[i];
                        flag = true;
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

    public void printKmeansResults(String path) {
        try {
            PrintStream out = new PrintStream(path);
            computeTheK();
            doIteration(centroidList);
            out.println("There are " + centroidList.size() + " clusters!");
            for (int i = 0; i < arraylist.size(); ++i) {
                out.print("(");
                for (int j = 0; j < dimension - 1; ++j) {
                    out.print(arraylist.get(i).attributes[j] + ", ");
                }
                out.print(arraylist.get(i).attributes[dimension - 1] + ") ");
                out.println("belongs to cluster " + arraylist.get(i).label);
            }
            out.close();
            System.out.println("Please check results in: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Kmeans kmeans = new Kmeans();
        kmeans.setKmeansInput("c:/kmeans.txt");
        kmeans.printKmeansResults("c:/kmeansResults.txt");
    }
}