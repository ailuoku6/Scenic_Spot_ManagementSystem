package com.company;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Gragh {
    public Map<Integer,S_Node> G;
    //public Map<Integer,Boolean> book;
    public final int OK = 1;
    public final int ERROR = 0;

    public Gragh(){
        G = new HashMap<>();
        //book = new HashMap<>();
    }

    public void Greate_gragh(String path){

    }

    public int Add_Node(int num,String name,String inTro){
        if(!G.containsKey(num)){
            S_Node s_node = new S_Node();
            s_node.num = num;
            s_node.name = name;
            s_node.inTro = inTro;
            s_node.isVisite = false;
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

    public void DFS_Init(int key){
        Iterator<Map.Entry<Integer,S_Node>> entrys = G.entrySet().iterator();
        while (entrys.hasNext()){
            Map.Entry<Integer,S_Node> entry = entrys.next();                    //
            entry.getValue().isVisite = false;
        }

        DFS(key);

    }

    public void DFS(int key){
        G.get(key).isVisite = true;
        vex_Node vex_node = G.get(key).vex_node;
        System.out.println(G.get(key).num);
        System.out.println(G.get(key).name);
        System.out.println(G.get(key).inTro);
        while (vex_node!=null){
            if(!(G.get(vex_node.LinkNum).isVisite)){
                DFS(vex_node.LinkNum);
            }
            vex_node = vex_node.Next;
        }
    }

    public void S_node_Detail(int key){
        S_Node s_node = G.get(key);
        System.out.println(s_node.name);
        System.out.println(s_node.inTro);
        vex_Node vex_node = s_node.vex_node;
        while (vex_node!=null){
            System.out.println(G.get(vex_node.LinkNum).name);
            System.out.println(vex_node.DisTan);
            vex_node = vex_node.Next;
        }
    }

    public int SaveData(){
        
        return OK;
    }

}
