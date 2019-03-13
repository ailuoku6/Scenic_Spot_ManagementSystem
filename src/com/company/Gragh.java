package com.company;

import java.io.*;
import java.util.*;

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

        File file = new File(path1);

        if (!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        try {//path1读取景点信息
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String num = bufferedReader.readLine();

            int count = num==null?0:Integer.parseInt(num);

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

        File file1 = new File(path2);

        if (!file1.exists()){
            try {
                file1.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }


        try {//path2读取路径信息
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file1));

            String str;
            String[] data;

            while ((str = bufferedReader.readLine())!=null){
                data = str.split(" ");
//                S_Node.Addvex(G.get(Integer.parseInt(data[0])),Integer.parseInt(data[1]),Integer.parseInt(data[2]));
//                S_Node.Addvex(G.get(Integer.parseInt(data[1])),Integer.parseInt(data[0]),Integer.parseInt(data[2]));
                Addvex(Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2]));
                Addvex(Integer.parseInt(data[1]),Integer.parseInt(data[0]),Integer.parseInt(data[2]));
            }

            bufferedReader.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("file not find");
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("file error");
        }


        //读取完要输出邻接表

        printAdj_list();

    }

    public void printAdj_list(){

        System.out.println("地图的邻接表如下:");

        Iterator<Map.Entry<Integer,S_Node>> entrys = G.entrySet().iterator();
        while (entrys.hasNext()){
            S_Node s_node = entrys.next().getValue();
            System.out.print(s_node.num+" ");
            vex_Node vex_node = s_node.vex_node;
            while (vex_node!=null){
                System.out.print(" -> "+vex_node.LinkNum);
                vex_node = vex_node.Next;
            }
            System.out.println();
        }
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

    public int DFS_Init(int key){

        if (G.get(key)==null) return ERROR;

        Iterator<Map.Entry<Integer,S_Node>> entrys = G.entrySet().iterator();
        while (entrys.hasNext()){
            Map.Entry<Integer,S_Node> entry = entrys.next();                    //
            entry.getValue().isVisite = false;
        }

        DFS(key);

        return OK;

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
        System.out.println("简介:"+s_node.inTro);
        System.out.println("附近的景点有:");
        vex_Node vex_node = s_node.vex_node;
        while (vex_node!=null){
//            System.out.println(G.get(vex_node.LinkNum).name);
//            System.out.println(vex_node.DisTan);
            System.out.println(G.get(vex_node.LinkNum).name+",距"+s_node.name+vex_node.DisTan);
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


    public int Addvex(int key,int linkNum,int diatan){//增,根据路径长短进行升序排列

//        S_Node s_node = G.get(key);
//
//        if (s_node==null||G.get(linkNum)==null) return ERROR;//找不到对应结点
//
//        vex_Node newNode = new vex_Node();
//        newNode.DisTan = diatan;
//        newNode.LinkNum = linkNum;
//
//        if(s_node.vex_node==null) {
//            s_node.vex_node = newNode;
//            return OK;
//        }
//
//        vex_Node vex = s_node.vex_node;
//        while (vex.Next!=null){//验证路径是否已存在
//            if(vex.LinkNum==linkNum) return ERROR;
//            vex = vex.Next;
//        }
//
//        if(vex.LinkNum==linkNum) return ERROR;//验证最后一个路径是否重复
//
//        vex.Next = newNode;
//
//        return OK;

        S_Node s_node = G.get(key);
        vex_Node vex = s_node.vex_node;

        if (s_node==null||G.get(linkNum)==null) return ERROR;//找不到对应结点

        vex_Node newNode = new vex_Node();
        newNode.DisTan = diatan;
        newNode.LinkNum = linkNum;
        //该节点还没有任何路径||新路径的距离比第一个路径小
        if (vex==null||vex.DisTan>diatan){
            newNode.Next = vex;
            s_node.vex_node = newNode;
            return OK;
        }

        if (vex.LinkNum==linkNum) return ERROR;

        while (vex.Next!=null){
            if (vex.Next.DisTan>=diatan) break;
            vex = vex.Next;
        }

        if (vex.Next!=null&&vex.Next.LinkNum==linkNum) return ERROR;

        newNode.Next = vex.Next;
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

        if(delete_vex(Old_p1,Old_p2)==OK&&
                delete_vex(Old_p2,Old_p1)==OK&&
                Addvex(newp1,newp2,newDistan)==OK&&
                Addvex(newp2,newp1,newDistan)==OK) {
            return OK;
        }

        return ERROR;
    }

    public int Get_distan(int p1,int p2){
        S_Node s_node = G.get(p1);
        if(s_node!=null&&G.get(p2)!=null){
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

        Iterator<Map.Entry<Integer,S_Node>> entrys = G.entrySet().iterator();
        while (entrys.hasNext()){
            Map.Entry<Integer,S_Node> entry = entrys.next();
            entry.getValue().minDistan = Integer.MAX_VALUE;
        }//遍历所有结点并初始化minDistan为MAX_VALUE

        Map<Integer,LinkedList<Integer>> Path = new HashMap<>();
        Path.put(p1,new LinkedList<>());

        G.get(p1).minDistan = 0;

        PriorityQueue<S_Node> queue = new PriorityQueue<>();
        queue.add(G.get(p1));

        while (!queue.isEmpty()){
            S_Node u = queue.poll();
            vex_Node vex_node = u.vex_node;
            while (vex_node!=null){

                int newDistan = G.get(u.num).minDistan+vex_node.DisTan;

                if(G.get(vex_node.LinkNum).minDistan>newDistan) {
                    queue.remove(G.get(vex_node.LinkNum));

                    G.get(vex_node.LinkNum).minDistan = newDistan;

                    Path.put(vex_node.LinkNum, new LinkedList<>(Path.get(u.num)));

                    Path.get(vex_node.LinkNum).add(u.num);

                    //queue.add(G.get(vex_node.LinkNum));

                    if (vex_node.LinkNum!=p2) queue.add(G.get(vex_node.LinkNum));

                }

                vex_node = vex_node.Next;
            }
        }

        System.out.println("最短距离为"+G.get(p2).minDistan);

        LinkedList<Integer> path = Path.get(p2);

        for (int i:path) {
            System.out.print(G.get(i).name+" -> ");
        }

        System.out.println(G.get(p2).name);

        return OK;
    }

    public int Circuit_plan(){
        //S_Node s_node = G.entrySet().iterator().next().getValue();//取一个节点出来

        Map<Integer,S_Node> Contained = new HashMap<>();
        Map<Integer,S_Node> UnContained = new HashMap<>();


        //1.如何判断剩余结点
        //2.如何判断一条路径是否在集合中

        //Contained.put(s_node.num,s_node);

        Iterator<Map.Entry<Integer,S_Node>> entrys = G.entrySet().iterator();//遍历所有结点

        while (entrys.hasNext()){
            S_Node sNode = entrys.next().getValue();
            UnContained.put(sNode.num,sNode);
        }

        Integer i = UnContained.entrySet().iterator().next().getKey();

        Contained.put(i,UnContained.remove(i));//所有结点分为两个Map,一个Map存入已在最小生成树的结点,另一个存不在最小生成树中的结点

        while (!UnContained.isEmpty()){
            Iterator<Map.Entry<Integer,S_Node>> Container_entry = Contained.entrySet().iterator();
            int Min = Integer.MAX_VALUE;
            vex_Node ShortVex_node = new vex_Node();

            S_Node Short_Snode = new S_Node();


            while (Container_entry.hasNext()){//遍历Contained的所有路径
                S_Node s_node1 = Container_entry.next().getValue();
                vex_Node vex_node = s_node1.vex_node;
                while (vex_node!=null){//遍历每个景点的所有路径
                    if (vex_node.DisTan<Min&&Contained.get(vex_node.LinkNum)==null){//如果当前路径小于最短的路径且该路径连接两个Map
                        Min = vex_node.DisTan;
                        ShortVex_node = vex_node;
                        Short_Snode = s_node1;
                        break;//不用往后找了，因为越靠前的路径的长度越小
                    }
                    vex_node = vex_node.Next;
                }
            }

            if (Min==Integer.MAX_VALUE) break;//防止不是连通图造成的死循环

            //遍历完后进行对两个集合的操作
            ShortVex_node.isPrim_path = true;//描黑最小生成树的路径

            Contained.put(ShortVex_node.LinkNum,UnContained.remove(ShortVex_node.LinkNum));//把结点从UnContained移入Contained

        }

        Iterator<Map.Entry<Integer,S_Node>> entrys_ = G.entrySet().iterator();

        int path_length = 0;

        while (entrys_.hasNext()){
            S_Node node = entrys_.next().getValue();
            vex_Node vexNode = node.vex_node;
            while (vexNode!=null){
                if(vexNode.isPrim_path){
                    path_length+=vexNode.DisTan;
                    System.out.println(node.name+" -> "+G.get(vexNode.LinkNum).name+",长度为: "+vexNode.DisTan);
                }
                vexNode = vexNode.Next;
            }
        }

        System.out.println("总长度为:"+path_length);

        return OK;
    }

}
