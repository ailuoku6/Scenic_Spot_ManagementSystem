package com.company;

public class S_Node implements Comparable<S_Node>{//景点结点

    public static final int OK = 1;
    public static final int ERROR = 0;

    public int num;
    public String name;
    public String inTro;
    public vex_Node vex_node;
    //public vex_Node near;
    public boolean isVisite;
    public int minDistan;

    public S_Node(){
        minDistan = Integer.MAX_VALUE;
    }

    @Override
    public int compareTo(S_Node o) {
        return Integer.compare(minDistan,o.minDistan);
    }

}
