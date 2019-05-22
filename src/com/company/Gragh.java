package com.company;

import java.io.*;
import java.util.*;

public class Gragh {
    public Map<Integer,S_Node> G;//地图存于map中
    //public Map<Integer,Boolean> book;
    public final int OK = 1;
    public final int ERROR = 0;
    public final int INF = 999999999;

    //public Stack<Integer> BookStack;

    public MyStack BookStack;

    public Gragh(){
        G = new HashMap<>();
        BookStack = new MyStack();
        //BookStack = new Stack<>();
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
                //s_node.near = null;
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

    public int DFS_Init(int key){//key是起始景点编号

        BookStack.clear();//清空标记栈

        if (G.get(key)==null) return ERROR;//如果没有这个景点，返回错误并退出，不进行后序操作

        //遍历所有景点并把isVisite标记为false，表示没访问过
        Iterator<Map.Entry<Integer,S_Node>> entrys = G.entrySet().iterator();
        while (entrys.hasNext()){
            Map.Entry<Integer,S_Node> entry = entrys.next();
            entry.getValue().isVisite = false;
        }

        DFS(key);//已完成初始化工作，开始进行深度优先搜索

        return OK;

    }

    public void DFS(int key){
        BookStack.push(key);//访问该结点后，把该节点标号入栈
        G.get(key).isVisite = true;//把isVisite标记为true，表示已访问
        vex_Node vex_node = G.get(key).vex_node;//访问景点的道路
        while (vex_node!=null){//当道路结点不为空时
            if(!(G.get(vex_node.LinkNum).isVisite)){//道路指向的景点还没有被访问过
                DFS(vex_node.LinkNum);//访问道路指向的景点
            }
            vex_node = vex_node.Next;//看下一条路
        }
        if (BookStack.size()==G.size()){//当栈的大小等于景点数量时，说明所有景点都访问到了
            int size = BookStack.size();
            //int[] array = BookStack.getArray();
            //按入栈顺序输出所有景点
            for (int i = 0;i<size;i++){
                System.out.print("->"+G.get(BookStack.getItem(i)).name);
            }
            System.out.println();
        }
        G.get(key).isVisite = false;//访问结束，把isVisite标记为false，方便从另外道路进来访问
        BookStack.pop();//把该节点标号出栈
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

    public int mark_Vex(int position1,int position2){

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

    public int Find_short_path(int p1,int p2){//p1起始点,p2目的地
        if(G.get(p1)==null||G.get(p2)==null) return ERROR;

        Iterator<Map.Entry<Integer,S_Node>> entrys = G.entrySet().iterator();
        while (entrys.hasNext()){
            Map.Entry<Integer,S_Node> entry = entrys.next();
            entry.getValue().minDistan = Integer.MAX_VALUE;
        }//遍历所有结点并初始化minDistan为MAX_VALUE

        Map<Integer,LinkedList<Integer>> Path = new HashMap<>();//Path存储到索引景点的最短距离所要经过的景点的编号
        Path.put(p1,new LinkedList<>());//把起始景点放入path中

        G.get(p1).minDistan = 0;//起始点到自身最短距离为0

        PriorityQueue<S_Node> queue = new PriorityQueue<>();//用队列存储景点，根据minDisTan升序排列，小的先出队
        queue.add(G.get(p1));//把起始点加进去

        while (!queue.isEmpty()){
            S_Node u = queue.poll();//把队列中minDisTan最小的出队
            vex_Node vex_node = u.vex_node;
            while (vex_node!=null){//遍历所有道路

                int newDistan = u.minDistan+vex_node.DisTan;//到vex_node.LinkNum景点的距离是当前景点的距离加道路长度

                if(G.get(vex_node.LinkNum).minDistan>newDistan) {//如果到vex_node.LinkNum景点的距离小于旧的最短距离
                    //去掉vex_node.LinkNum景点，目的是后面加入更新最短距离后的vex_node.LinkNum景点
                    queue.remove(G.get(vex_node.LinkNum));
                    G.get(vex_node.LinkNum).minDistan = newDistan;//更新最短距离

                    //加入景点，其最短路径途经景点是u景点的最短路径途经景点加u景点
                    Path.put(vex_node.LinkNum, new LinkedList<>(Path.get(u.num)));
                    Path.get(vex_node.LinkNum).add(u.num);

                    if (vex_node.LinkNum!=p2) queue.add(G.get(vex_node.LinkNum));//若还没到目的地，则把途经的景点加入队列

                }

                vex_node = vex_node.Next;
            }
        }

        System.out.println("最短距离为"+G.get(p2).minDistan);

        LinkedList<Integer> path = Path.get(p2);//获取 到目的地的途经景点

        //输出所有途经景点和终点
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
