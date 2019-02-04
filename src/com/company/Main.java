package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static final int OK = 1;
    public static final int ERROR = 0;

    //增加一个取消操作的选项

    public static void main(String[] args) {

        Gragh gragh = new Gragh();

        gragh.Greate_gragh(Filepath.scenic_path,Filepath.Road_path);//读取文件

        char option;

        Scanner scanner = new Scanner(System.in);

        boolean isRun = true;

        while (isRun){
            Menu.ShowMenu();
            System.out.print("输入需要操作的选项:");
            option = scanner.next().charAt(0);
            switch (option){
                case 'a'://读取地图
                case 'A':gragh.Greate_gragh(Filepath.scenic_path,Filepath.Road_path);
                    break;
                case 'b'://查询景点
                case 'B':Scenic_detail(gragh,scanner);
                    break;
                case 'c'://景点导航,待完善
                case 'C':Scenic_nav(gragh,scanner);
                    break;
                case 'd'://搜索最短路径
                case 'D':Shortest_Path(gragh,scanner);
                    break;
                case 'e'://铺设电路规划
                case 'E':Circuit_planning(gragh,scanner);
                    break;
                case 'f'://修改图保存文件
                case 'F':Change_Gragh(gragh,scanner);
                    break;
                case 'g'://退出程序
                case 'G':isRun = false;
                    break;
                default:
                    break;

            }
        }

        scanner.close();

        //ShowMenu();

    }


    public static void Scenic_detail(Gragh gragh,Scanner sc){
        try {
            System.out.print("请输入景点编号:");
            if(gragh.S_node_Detail(sc.nextInt())==ERROR){
                System.out.println("没有此景点!");
            }
        }catch (InputMismatchException e){
            //e.printStackTrace();
            Menu.ShowERROR();
            return;
        }
    }

    public static void Scenic_nav(Gragh gragh,Scanner scanner){
        try {
            System.out.print("请输入你所在的景点编号:");
            if (gragh.DFS_Init(scanner.nextInt())==ERROR) {
                System.out.println("无此景点");
            }
        }catch (InputMismatchException e){
            //e.printStackTrace();
            Menu.ShowERROR();
            return;
        }

    }

    public static void Shortest_Path(Gragh gragh,Scanner scanner){

        try {
            int p1,p2;
            System.out.println("输入-1取消操作");
            System.out.print("输入你所在的景点编号:");
            p1 = scanner.nextInt();
            if (p1==-1) return;
            System.out.print("输入目标景点编号:");
            p2 = scanner.nextInt();
            if (p2==-1) return;
            if (gragh.Find_short_path(p1,p2)!=OK){
                System.out.println("景点不存在!");
            }
        }catch (InputMismatchException e){
            //e.printStackTrace();
            Menu.ShowERROR();
            return;
        }
    }

    public static void Circuit_planning(Gragh gragh,Scanner scanner){
        gragh.Circuit_plan();
    }

    public static void Change_Gragh(Gragh gragh,Scanner scanner){
        boolean isRun = true;
        while (isRun){
            Menu.ShowSubMenu();
            System.out.print("输入需要操作的选项:");
            switch (scanner.next().charAt(0)){
                case 'a'://增加景点
                case 'A':AddScenic(gragh,scanner);
                    break;
                case 'b'://删除景点
                case 'B':DeleteScenic(gragh,scanner);
                    break;
                case 'c'://修改景点
                case 'C':ChangeScenic(gragh,scanner);
                    break;
                case 'd'://增加路径
                case 'D':Addpath(gragh,scanner);
                    break;
                case 'e'://删除路径
                case 'E':DeletePath(gragh,scanner);
                    break;
                case 'f'://修改路径
                case 'F':ChangePath(gragh,scanner);
                    break;
                case 'g'://保存
                case 'G':gragh.SaveData(Filepath.scenic_path,Filepath.Road_path);
                    break;
                case 'h'://退出
                case 'H':isRun = false;
                    break;
                default:
                    break;
            }
        }

        gragh.SaveData(Filepath.scenic_path,Filepath.Road_path);//退出后保存文件

    }




    public static void AddScenic(Gragh gragh,Scanner scanner){

        try {
            int num;
            String name;
            String inTro;
            System.out.println("输入-1取消操作");
            System.out.print("输入要增加的景点编号:");
            num = scanner.nextInt();
            if (num==-1) return;
            System.out.print("输入要增加的景点名称:");
            name = scanner.next();
            if (name.equals("-1")) return;
            System.out.print("输入要增加的景点简介:");
            inTro = scanner.next();
            if (inTro.equals("-1")) return;
            if(gragh.Add_Node(num,name,inTro)==OK){
                System.out.println("增加完毕!");
            }else {
                System.out.println("该景点已存在!");
            }
        }catch (InputMismatchException e){
            //e.printStackTrace();
            Menu.ShowERROR();
            return;
        }
    }

    public static void DeleteScenic(Gragh gragh,Scanner scanner){

        try {
            int num;
            System.out.println("输入-1取消操作");
            System.out.print("输入要删除的景点编号:");
            num = scanner.nextInt();
            if (num==-1) return;
            if (gragh.Delete_node(num)==OK){
                System.out.println("删除成功!");
            }else {
                System.out.println("无此景点!");
            }
        }catch (InputMismatchException e){
            //e.printStackTrace();
            Menu.ShowERROR();
            return;
        }
    }

    public static void ChangeScenic(Gragh gragh,Scanner scanner){

        try {
            int num;
            String name;
            String inTro;
            System.out.println("输入-1取消操作");
            System.out.print("输入要修改的景点编号:");
            num = scanner.nextInt();
            if (num==-1) return;
            System.out.print("输入要修改的景点名称:");
            name = scanner.next();
            if (name.equals("-1")) return;
            System.out.print("输入要修改的景点简介:");
            inTro = scanner.next();
            if (inTro.equals("-1")) return;
            if(gragh.Change_Node(num,name,inTro)==OK){
                System.out.println("修改成功!");
            }else System.out.println("无此景点!");
        }catch (InputMismatchException e){
            //e.printStackTrace();
            Menu.ShowERROR();
            return;
        }
    }

    public static void Addpath(Gragh gragh,Scanner scanner){

        try {
            int point1,point2,distan;
            System.out.println("输入-1取消操作");
            System.out.print("输入要增加的路径的起始端:");
            point1 = scanner.nextInt();
            if (point1==-1) return;
            System.out.print("输入要增加的路径的到达端:");
            point2 = scanner.nextInt();
            if (point2==-1) return;
            System.out.print("输入要增加的路径的长度:");
            distan = scanner.nextInt();
            if (distan==-1) return;
            if(gragh.Addvex(point1,point2,distan)==OK&&gragh.Addvex(point2,point1,distan)==OK){
                System.out.println("添加路径成功!");
            }else System.out.println("添加路径失败!");
        }catch (InputMismatchException e){
            //e.printStackTrace();
            Menu.ShowERROR();
            return;
        }

    }

    public static void DeletePath(Gragh gragh,Scanner scanner){

        try {
            int point1,point2;
            System.out.println("输入-1取消操作");
            System.out.print("输入要删除的路径的起始端:");
            point1 = scanner.nextInt();
            if (point1==-1) return;
            System.out.print("输入要删除的路径的到达端:");
            point2 = scanner.nextInt();
            if (point2==-1) return;
            if(gragh.delete_vex(point1,point2)==OK&&gragh.delete_vex(point2,point1)==OK){
                System.out.println("删除路径成功!");
            }else System.out.println("删除路径失败!");
        }catch (InputMismatchException e){
            //e.printStackTrace();
            Menu.ShowERROR();
            return;
        }
    }

    public static void ChangePath(Gragh gragh,Scanner scanner){

        try {
            int Old_p1,Old_p2,newP1,newp2,newDistan;
            System.out.println("输入-1取消操作");
            System.out.print("输入旧路径的起始端和到达端:");
            Old_p1 = scanner.nextInt();
            if (Old_p1==-1) return;
            Old_p2 = scanner.nextInt();
            if (Old_p2==-1) return;
            System.out.print("输入新路径的起始端和到达端及长度:");
            newP1 = scanner.nextInt();
            if (newP1==-1) return;
            newp2 = scanner.nextInt();
            if (newp2==-1) return;
            newDistan = scanner.nextInt();
            if (newDistan==-1) return;
            if(gragh.Change_vex(Old_p1,Old_p2,newP1,newp2,newDistan)==OK){
                System.out.println("修改成功!");
            }else System.out.println("修改失败!");
        }catch (InputMismatchException e){
            //e.printStackTrace();
            Menu.ShowERROR();
            return;
        }
    }

}
