package com.company;

public class S_Node {

    public static final int OK = 1;
    public static final int ERROR = 0;

    public int num;
    public String name;
    public String inTro;
    public vex_Node vex_node;
    public vex_Node near;

    public static int Addvex(S_Node s_node,int diatan,int linkNum){
        vex_Node vex = s_node.vex_node;
        while (vex!=null){
            if(vex.LinkNum==linkNum) return ERROR;
            vex = vex.Next;
        }
        vex_Node newNode = new vex_Node();
        newNode.DisTan = diatan;
        newNode.LinkNum = linkNum;
        newNode.Next = null;
        s_node.near.Next = newNode;
        s_node.near = newNode;
        return OK;
    }

    //对象置为null时，虚拟机视其为可回收

    public static int delete_vex(S_Node s_node,int linkTo){

        return ERROR;
    }

}
