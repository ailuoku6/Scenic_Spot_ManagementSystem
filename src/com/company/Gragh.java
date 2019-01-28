package com.company;

import java.util.HashMap;
import java.util.Map;

public class Gragh {
    public Map<Integer,S_Node> G;
    public final int OK = 1;
    public final int ERROR = 0;

    public Gragh(){
        G = new HashMap<>();
    }

    public void Greate_gragh(String path){

    }

    public int Add_Node(int num,String name,String inTro){
        if(!G.containsKey(num)){
            S_Node s_node = new S_Node();
            s_node.num = num;
            s_node.name = name;
            s_node.inTro = inTro;
            G.put(num,s_node);
            return OK;
        }
        return ERROR;
    }

    public int Delete_node(int key){
        if(!G.containsKey(key))return ERROR;
        G.remove(key);
        return OK;
    }

    public int Change_Node(int key,String name,String inTro){
        if(!G.containsKey(key)) return ERROR;
        S_Node s_node = G.get(key);
        s_node.name = name;
        s_node.inTro = inTro;
        return OK;
    }

    public S_Node Find_node(int key){
        if(!G.containsKey(key)) return null;
        return G.get(key);
    }

}
