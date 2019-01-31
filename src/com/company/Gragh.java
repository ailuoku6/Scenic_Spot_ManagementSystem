package com.company;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Gragh {
    public Map<Integer,S_Node> G;//地图存于map中
    //public Map<Integer,Boolean> book;
    public final int OK = 1;
    public final int ERROR = 0;
    public final int INF = 999999999;

    public Gragh(){
        G = new HashMap<>();
        //book = new HashMap<>();
    }

    public void Greate_gragh(String path1,String path2){//读取两个文件
        try {//path1读取景点信息
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path1));
            int count =Integer.parseInt(bufferedReader.readLine());


            for (int i = 0;i<count;i++){
                S_Node s_node = new S_Node();
                s_node.num = Integer.parseInt(bufferedReader.readLine());
                s_node.name = bufferedReader.readLine();
                s_node.inTro = bufferedReader.readLine();
                s_node.vex_node = null;
                s_node.near = null;
                G.put(s_node.num,s_node);
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("file not find");
        }catch (IOException e){
            System.out.println("file error");
        }


        try {//path2读取路径信息
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path2));

            String str;
            String[] data;

            while ((str = bufferedReader.readLine())!=null){
                data = str.split(" ");
//                S_Node.Addvex(G.get(Integer.parseInt(data[0])),Integer.parseInt(data[1]),Integer.parseInt(data[2]));
//                S_Node.Addvex(G.get(Integer.parseInt(data[1])),Integer.parseInt(data[0]),Integer.parseInt(data[2]));
                Addvex(Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2]));
                Addvex(Integer.parseInt(data[1]),Integer.parseInt(data[0]),Integer.parseInt(data[2]));
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("file not find");
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("file error");
        }


        //读取完要输出邻接表

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

    public int Delete_node(int key){//删除一个节点之后,相关的路径也要考虑删除
        if(!G.containsKey(key))return ERROR;
        G.remove(key);

        Iterator<Map.Entry<Integer,S_Node>> entrys = G.entrySet().iterator();
        while (entrys.hasNext()){
            Map.Entry<Integer,S_Node> entry = entrys.next();
            delete_vex(entry.getKey(),key);
        }

        return OK;
    }

    public int Change_Node(int key,String name,String inTro){
        if(!G.containsKey(key)) return ERROR;
        S_Node s_node = G.get(key);
        //s_node.num = num;
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

    public int S_node_Detail(int key){
        S_Node s_node = G.get(key);
        if(s_node==null) return ERROR;
        System.out.println(s_node.name);
        System.out.println(s_node.inTro);
        vex_Node vex_node = s_node.vex_node;
        while (vex_node!=null){
            System.out.println(G.get(vex_node.LinkNum).name);
            System.out.println(vex_node.DisTan);
            vex_node = vex_node.Next;
        }
        return OK;
    }

    public int SaveData(String path,String path2){//保存两个文件
        try {//保存景点信息
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(path)));
            int count = G.size();
            printWriter.println(count);

            Iterator<Map.Entry<Integer,S_Node>> entrys = G.entrySet().iterator();
            while (entrys.hasNext()){
                Map.Entry<Integer,S_Node> entry = entrys.next();                    //
                //entry.getValue().isVisite = false;

                printWriter.println(entry.getValue().num);
                printWriter.println(entry.getValue().name);
                printWriter.println(entry.getValue().inTro);

            }

            printWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
            return ERROR;
        }


        try {//保存路径信息

            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(path2)));

            Iterator<Map.Entry<Integer,S_Node>> entrys = G.entrySet().iterator();

            while (entrys.hasNext()){
                Map.Entry<Integer,S_Node> entry = entrys.next();
                vex_Node vex_node = entry.getValue().vex_node;

                while (vex_node!=null){
                    if(!vex_node.isSave){//函数结束后，isSave必须还原
                        printWriter.println(entry.getKey()+" "+vex_node.LinkNum+" "+vex_node.DisTan);
                        mark_Vex(entry.getKey(),vex_node.LinkNum);
                    }
                    vex_node = vex_node.Next;
                }

            }

            printWriter.close();

        }catch (IOException e) {
            e.printStackTrace();
            return ERROR;
        }

        Recover_Vexmark();//还原isSave标记

        return OK;
    }

    public int mark_Vex(int position1,int position2){//标注这里还得能恢复,方便下次保存

        vex_Node vex_node = G.get(position1).vex_node;

        while (vex_node!=null){
            if(vex_node.LinkNum==position2){
                vex_node.isSave = true;
                break;
            }
            vex_node = vex_node.Next;
        }

        if(vex_node==null) return ERROR;

        vex_node = G.get(position2).vex_node;

        while (vex_node!=null){
            if(vex_node.LinkNum==position1){
                vex_node.isSave = true;
                break;
            }
            vex_node = vex_node.Next;
        }

        if(vex_node==null) return ERROR;

        return OK;
    }


    public void Recover_Vexmark(){
        Iterator<Map.Entry<Integer,S_Node>> entrys = G.entrySet().iterator();
        while (entrys.hasNext()){
            Map.Entry<Integer,S_Node> entry = entrys.next();
            vex_Node vex_node = entry.getValue().vex_node;
            while (vex_node!=null){
                vex_node.isSave = false;
                vex_node = vex_node.Next;
            }
        }
    }


    public int Addvex(int key,int linkNum,int diatan){//增

        S_Node s_node = G.get(key);

        if (s_node==null||G.get(linkNum)==null) return ERROR;//找不到对应结点

        vex_Node newNode = new vex_Node();
        newNode.DisTan = diatan;
        newNode.LinkNum = linkNum;

        if(s_node.vex_node==null) {
            s_node.vex_node = newNode;
            return OK;
        }

        vex_Node vex = s_node.vex_node;

//        while (vex!=null&&vex.Next!=null){//验证路径是否已存在
//            if(vex.LinkNum==linkNum) return ERROR;
//            vex = vex.Next;
//        }
        while (vex.Next!=null){//验证路径是否已存在
            if(vex.LinkNum==linkNum) return ERROR;
            vex = vex.Next;
        }

        if(vex.LinkNum==linkNum) return ERROR;//验证最后一个路径是否重复

        vex.Next = newNode;

        return OK;
    }

    public int delete_vex(int key,int linkTo){//删
        S_Node s_node = G.get(key);

        if(s_node==null) return ERROR;

        vex_Node deletenode;

        vex_Node vex = s_node.vex_node;
        if(vex==null) return ERROR;

        //删除结点为第一个结点的情况
        if(vex.LinkNum==linkTo){
            deletenode = vex;
            s_node.vex_node = s_node.vex_node.Next;
            deletenode = null;
            return OK;
        }

        while (vex.Next!=null&&vex.Next.LinkNum!=linkTo){
            vex = vex.Next;
        }

        //没有找到相应的路径
        if(vex.Next==null) return ERROR;

        deletenode = vex.Next;
        vex.Next = deletenode.Next;
        deletenode = null;
        return OK;
    }

    public int Change_vex(int Old_p1,int Old_p2,int newp1,int newp2,int newDistan){//改
//        S_Node s_node = G.get(key);
//        vex_Node vex_node = s_node.vex_node;
//        while (vex_node.Next!=null&&vex_node.Next.LinkNum!=linkTo){
//            vex_node = vex_node.Next;
//        }
//        if(vex_node.Next==null) return ERROR;
//        vex_node.Next.LinkNum = linkToChange;

        if(delete_vex(Old_p1,Old_p2)==OK&&
                delete_vex(Old_p2,Old_p1)==OK&&
                Addvex(newp1,newp2,newDistan)==OK&&
                Addvex(newp2,newp1,newDistan)==OK) {
            return OK;
        }

//        delete_vex(Old_p1,Old_p2);
//        delete_vex(Old_p2,Old_p1);
//
//        Addvex(newp1,newp2,newDistan);
//        Addvex(newp2,newp1,newDistan);

        return ERROR;
    }

    public int Get_distan(int p1,int p2){
        S_Node s_node = G.get(p1);
        if(s_node!=null){
            vex_Node vex_node = s_node.vex_node;
            while (vex_node!=null){
                if(vex_node.LinkNum==p2) return vex_node.DisTan;
                vex_node = vex_node.Next;
            }
        }
        return INF;
    }

    public int Find_short_path(int p1,int p2){
        if(G.get(p1)==null||G.get(p2)==null) return ERROR;

        Map<Integer,Integer> dis_known = new LinkedHashMap<>();//已知最短路径顶点集合
        Map<Integer,Integer> Unknown = new LinkedHashMap<>();//未知最短路径顶点集合

        dis_known.put(p1,0);

        Iterator<Map.Entry<Integer,S_Node>> entrys = G.entrySet().iterator();

        while (entrys.hasNext()){
            Map.Entry<Integer,S_Node> entry = entrys.next();
            if(entry.getKey()!=p1){
                Unknown.put(entry.getKey(),Get_distan(p1,entry.getKey()));
            }
        }

        

        return OK;
    }

}
