package com.company;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class S_Node implements Comparable<S_Node>{//景点结点

    public static final int OK = 1;
    public static final int ERROR = 0;

    public int num;
    public String name;
    public String inTro;
    public vex_Node vex_node;
    public vex_Node near;
    public boolean isVisite;
    public int minDistan;
    //public Integer prim_Link2Num;//最小生成路径
    //public boolean isContained;//是否已经被最小生成树包含
    //public Queue<Integer> Prim_link;

    public S_Node(){
        near = vex_node;
        minDistan = Integer.MAX_VALUE;
        //prim_Link2Num = null;
        //Prim_link = new LinkedList<>();
    }

    @Override
    public int compareTo(S_Node o) {
        return Integer.compare(minDistan,o.minDistan);
    }

}
