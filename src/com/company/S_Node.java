package com.company;

public class S_Node {

    public static final int OK = 1;
    public static final int ERROR = 0;

    public int num;
    public String name;
    public String inTro;
    public vex_Node vex_node;
    public vex_Node near;
    public boolean isVisite;

    public static int Addvex(S_Node s_node,int diatan,int linkNum){//增
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

    public static int delete_vex(S_Node s_node,int linkTo){//删
        vex_Node vex = s_node.vex_node;
        if(vex==null) return ERROR;
        while (vex.Next!=null&&vex.Next.LinkNum!=linkTo){
            vex = vex.Next;
        }
        if(vex.Next==null) return ERROR;
        vex_Node deletenode = vex.Next;
        vex.Next = deletenode.Next;
        deletenode = null;
        return OK;
    }

    public static int Change_vex(S_Node s_node,int linkTo,int linkToChange){
        vex_Node vex_node = s_node.vex_node;
        while (vex_node.Next!=null&&vex_node.Next.LinkNum!=linkTo){
            vex_node = vex_node.Next;
        }
        if(vex_node.Next==null) return ERROR;
        vex_node.Next.LinkNum = linkToChange;
        return OK;
    }

//    public static vex_Node Find_vex(S_Node s_node,int ){
//
//    }

}
