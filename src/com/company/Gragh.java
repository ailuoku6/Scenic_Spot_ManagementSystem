package com.company;

import java.io.*;
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
                S_Node.Addvex(G.get(Integer.parseInt(data[0])),Integer.parseInt(data[1]),Integer.parseInt(data[2]));
                S_Node.Addvex(G.get(Integer.parseInt(data[1])),Integer.parseInt(data[0]),Integer.parseInt(data[2]));
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

    public int SaveData(String path,String path2){//保存两个文件,退出之前必须执行
        try {//保存景点信息
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(path,true)));
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

            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(path2,true)));

            Iterator<Map.Entry<Integer,S_Node>> entrys = G.entrySet().iterator();

            while (entrys.hasNext()){
                Map.Entry<Integer,S_Node> entry = entrys.next();
                vex_Node vex_node = entry.getValue().vex_node;

                while (vex_node!=null){
                    if(!vex_node.isSave){
                        printWriter.println(entry.getKey()+" "+vex_node.LinkNum+" "+vex_node.DisTan);
                        mark_Vex(entry.getKey(),vex_node.LinkNum);
                    }
                    vex_node = vex_node.Next;
                }

            }

        }catch (IOException e) {
            e.printStackTrace();
            return ERROR;
        }

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

}
