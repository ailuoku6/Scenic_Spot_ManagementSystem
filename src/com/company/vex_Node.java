package com.company;

public class vex_Node {//路径结点
    public int DisTan;
    public int LinkNum;
    public vex_Node Next;
    public boolean isSave;//标记是否存入道路
    public boolean isPrim_path;//标记该路径是否是最小生成树的路径

    public vex_Node(){
        Next = null;
        isSave = false;
        isPrim_path = false;
    }

}
