package com.manu.mapworldsamples.cluter;


import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * author: lx
 * date: 16-9-26
 */
public class MarkerCluster {

    // 单次等分一边分母
    private int divider;
    // 单元格可允许的最小边长
    private int grid_min_length;

    private int area_length;
    private double lon1;
    private double lat1;
    private double lon2;
    private double lat2;
    private double grid_lon;
    private double grid_lat;
    private int grid_length;
    private int layer_count;

    private List<? extends IMarker> list;
    private IndexTree<Index, MarkerInfo> index_tree;

    public MarkerCluster(int divider, int gridMinLength) {
        this.divider = divider;
        this.grid_min_length = gridMinLength;
    }

    public boolean isValid() {
        return index_tree != null &&
                (index_tree.getChildCount() != 0 || index_tree.getData() != null);
    }

    public void reset() {
        list = null;
        index_tree = null;
    }

    public void cluster(List<? extends IMarker> markerList) {
        list = markerList;
        cluster();
    }

    public void cluster() {
        if (divider <= 0 || grid_min_length <= 0) {
            throw new IllegalStateException("pre condition not met !");
        }
        if (list == null || list.size() == 0) {
            Log.w("liuxu", "cluster with no data at all");
            return;
        }

        index_tree = new IndexTree<>(new Index(0, 0), null);

        lon1 = lon2 = list.get(0).getLongitude();
        lat1 = lat2 = list.get(0).getLatitude();
        for (int i = list.size() - 1; i >= 0; i --) {
            IMarker m = list.get(i);
            if (m.getLatitude() == 0 || m.getLongitude() == 0) {
                list.remove(m);
                continue;
            }
            lon1 = lon1 < m.getLongitude() ? lon1 : m.getLongitude();
            lat1 = lat1 < m.getLatitude() ? lat1 : m.getLatitude();
            lon2 = lon2 > m.getLongitude() ? lon2 : m.getLongitude();
            lat2 = lat2 > m.getLatitude() ? lat2 : m.getLatitude();
        }
        if (list.size() == 0) {
            // no valid marker in list, just return
            Log.w("liuxu", "cluster with no data at all");
            return;
        }
        {
            // enlarge area
            if (lat1 == lat2) {
                lat1 -= 0.05D;
                lat2 += 0.05D;
            }
            if (lon1 == lon2) {
                lon1 -= 0.05D;
                lon2 += 0.05D;
            }
            double d_lon = (lon2 - lon1) * divider / 2;
            double d_lat = (lat2 - lat1) * divider / 2;
            lon1 -= d_lon;
            lon2 += d_lon / 2;
            lat1 -= d_lat / 2;
            lat2 += d_lat / 2;
        }

        int x_delta = lat2dis(lat2 - lat1);
        int y_delta = lon2dis(lon2 - lon1);
        area_length = x_delta > y_delta ? x_delta : y_delta;

        layer_count = 1;
        grid_length = area_length;
        while (grid_length > grid_min_length) {
            grid_length = grid_length / divider;
            layer_count ++;
        }
        grid_lon = dis2lon(grid_length);
        grid_lat = dis2lat(grid_length);

        for (IMarker m : list) {
            List<Index> index_list = getIndexByLatLon(m.getLatitude(), m.getLongitude());
            IndexTree<Index, MarkerInfo> t = index_tree.getNodeByIndexList(index_list);
            if (t != null && t.getData() != null) {
                t.getData().addMarker(m);
            } else {
                MarkerInfo info = new MarkerInfo(m, index_list);
                index_tree.putNodeByIndexList(info.index_list, info);
            }
        }

        clusterData(index_tree);
    }

    public List<Index> getIndexByLatLon(double lat, double lon) {
        ArrayList<Index> index_list = new ArrayList<>(layer_count);
        double lon_delta = lon - lon1;
        double lat_delta = lat - lat1;
        int nx = (int) (lon_delta / grid_lon);
        int ny = (int) (lat_delta / grid_lat);
        int idx_x = nx > 1 ? nx - 1 : 0;
        int idx_y = ny > 1 ? ny - 1 : 0;
        for (int i = 0; i < layer_count; i ++) {
            Index idx = new Index(idx_x % divider, idx_y % divider);
            idx_x = idx_x / divider;
            idx_y = idx_y / divider;
            index_list.add(0, idx);
        }
        return index_list;
    }

    public int getLevelByArea(
            double lat_bottom, double lon_left, double lat_top, double lon_right) {
        if (lat_bottom > lat2 || lat_top < lat1 || lon_left > lon2 || lon_right < lon1) {
            // out of range
            return 0;
        }
        if ((lat_top - lat_bottom) > 3 * (lat2 - lat1)) {
            return 0;
        }
        int dis_lat = lat2dis(lat_top - lat_bottom);
        int dis_lon = lon2dis(lon_right - lon_left);
        int dis = dis_lat < dis_lon ? dis_lat : dis_lon;
        int level = getLevelByDis(dis * 2 / 3) + 1;
        level = level < layer_count ? level : layer_count;
        //Log.d("liuxu", "getLevelByArea, level: " + level);
        return level;
    }

    public List<MarkerInfo> getMarkerListByLevel(int level) {
        List<IndexTree<Index, MarkerInfo>> nodeList = index_tree.getNodeListByLevel(level);
        if (nodeList != null && nodeList.size() != 0) {
            ArrayList<MarkerInfo> list = new ArrayList<>(nodeList.size());
            for (IndexTree<Index, MarkerInfo> n : nodeList) {
                list.add(n.getData());
            }
            return list;
        } else {
            return null;
        }
    }

    public List<MarkerInfo> getMarkerListByArea(
            double lat_bottom, double lon_left, double lat_top, double lon_right) {
        return getMarkerListByLevel(getLevelByArea(lat_bottom, lon_left, lat_top, lon_right));
    }

    public MarkerInfo clusterData(IndexTree<Index, MarkerInfo> index_tree) {
        int childCount = index_tree.getChildCount();
        if (childCount == 0) {
            return index_tree.getData();
        } else {
            MarkerInfo data = index_tree.getData();
            if (data == null) {
                int marker_count = 0;
                int center_x = divider / 2;
                int center_y = divider / 2;
                double offset = divider * divider;
                MarkerInfo childData = null;
                Iterator<IndexTree<Index, MarkerInfo>> it = index_tree.iterateChild();
                while (it != null && it.hasNext()) {
                    IndexTree<Index, MarkerInfo> tree = it.next();
                    marker_count += clusterData(tree).marker_count;
                    double f = Math.pow(tree.getIndex().x - center_x, 2) +
                            Math.pow(tree.getIndex().y - center_y, 2);
                    if (f < offset) {
                        offset = f;
                        childData = tree.getData();
                    }
                }
                if (childData != null) {
                    data = new MarkerInfo(
                            childData.marker,
                            childData.index_list.subList(0, childData.index_list.size() - 1));
                    data.marker_count = marker_count;
                    index_tree.setData(data);
                }
            }
            return data;
        }
    }

    public int getLevelByDis(int dis) {
        int level = layer_count;
        int d = grid_length;
        while (d < dis) {
            level --;
            d = d * divider;
        }
        return level > 0 ? level : 0;
    }

    public boolean isEndPointMarker(MarkerInfo marker) {
        if (marker == null || marker.getIndexList() == null) {
            return false;
        }
        return marker.getIndexList().size() == layer_count;
    }

    public List<IMarker> getEndPointDataList(MarkerInfo marker) {
        List<IMarker> list = new ArrayList<>();
        return getEndPointDataList(marker, list);
    }

    private List<IMarker> getEndPointDataList(MarkerInfo marker, List<IMarker> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (isEndPointMarker(marker)) {
            if (marker.getMarkerList() != null) {
                list.addAll(marker.getMarkerList());
            } else {
                list.add(marker.getMarker());
            }
            return list;
        } else {
            IndexTree<Index, MarkerInfo> node = index_tree.getNodeByIndexList(marker.getIndexList());
            List<MarkerInfo> childList = node.getChildDataList();
            for (MarkerInfo m : childList) {
                getEndPointDataList(m, list);
            }
            return list;
        }
    }

    // ===========================================
    // about index list

    public static List<Index> getCommonIndexList(List<Index> index1, List<Index> index2) {
        if (index1 == null || index2 == null) {
            return null;
        }
        int size1 = index1.size();
        int size2 = index2.size();
        int size = size1 < size2 ? size1 : size2;
        if (size == 0) {
            return null;
        }
        ArrayList<Index> list = new ArrayList<>(size);
        for (int i = 0; i < size; i ++) {
            Index idx1 = index1.get(i);
            Index idx2 = index2.get(i);
            if (idx1.equals(idx2)) {
                list.add(idx1);
            } else {
                break;
            }
        }
        return list;
    }

    public static boolean containsIndexList(List<Index> list1, List<Index> list2) {
        if (list1 == null || list2 == null) {
            return false;
        }
        if (list1.size() < list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i ++) {
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean equalsIndexList(List<Index> list1, List<Index> list2) {
        if (list1 == null || list2 == null) {
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i ++) {
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    private Comparator<Index> index_comparator = new Comparator<Index>() {
        @Override
        public int compare(Index lhs, Index rhs) {
            if (lhs.x < rhs.x) {
                return -1;
            } else if (lhs.y < rhs.y) {
                return 1;
            } else {
                return 0;
            }
        }
    };

    // =========================================


    public static int lon2dis(double lon) {
        return (int) Math.abs(lon * 111 * 1000);
    }

    public static int lat2dis(double lat) {
        return (int) Math.abs(lat * 111 * 1000);
    }

    public static double dis2lon(int dis) {
        return Math.abs((double) dis / 1000 / 111);
    }

    public static double dis2lat(int dis) {
        return Math.abs((double) dis / 1000 / 111);
    }

    public static String indexList2string(List<Index> list) {
        if (list == null || list.size() == 0) return null;
        StringBuilder sb = new StringBuilder();
        for (Index idx : list) {
            sb.append("[").append(idx.x).append(",").append(idx.y).append("]");
        }
        return sb.toString();
    }


    // =========================================


    public static class Index {
        int x;
        int y;

        public Index(int x, int y) {
            this.x = x;
            this.y = y;

        }

        @Override
        public boolean equals(Object that) {
            if (this == that) return true;
            if (that == null) return false;
            if (!(that instanceof Index)) return false;
            Index index = (Index) that;
            return (this.x == index.x && this.y == index.y);
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }

        @Override
        public String toString() {
            return "[" + x + "," + y + "]";
        }
    }

    public static class MarkerInfo {
        IMarker marker;
        List<IMarker> marker_list;
        List<Index> index_list;
        int marker_count;

        public MarkerInfo(IMarker marker, List<Index> index_list) {
            this.marker = marker;
            this.index_list = index_list;
            this.marker_count = 1;
        }

        public void addMarker(IMarker marker) {
            if (marker_list == null) {
                marker_list = new LinkedList<>();
                if (this.marker != null) {
                    marker_list.add(this.marker);
                }
            }
            marker_list.add(marker);
            marker_count = marker_list.size();
        }

        public IMarker getMarker() {
            return marker;
        }

        public List<IMarker> getMarkerList() {
            return marker_list;
        }

        public boolean isMarkerListEmpty() {
            return marker_list != null && marker_list.size() != 0;
        }

        public int getMarkerCount() {
            return marker_count;
        }

        public List<Index> getIndexList() {
            return index_list;
        }

        public String getIndexListString() {
            return indexList2string(index_list);
        }

        @Override
        public String toString() {
            return getIndexListString() + getMarkerCount();
        }
    }

}