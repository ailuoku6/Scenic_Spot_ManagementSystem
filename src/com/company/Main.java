package com.company;

import java.util.Scanner;

public class Main {

    public static final int OK = 1;
    public static final int ERROR = 0;

    public static void main(String[] args) {
	// write your code here
//        vex_Node vex_node = new vex_Node();
//        vex_Node cpvex = vex_node;
//        vex_node.LinkNum = -1;
//        for(int i = 0;i<10;i++){
//            vex_Node newnode = new vex_Node();
//            newnode.LinkNum = i;
//            vex_node.Next = newnode;
//            vex_node = vex_node.Next;
//        }
//        while (cpvex!=null){
//            System.out.println(cpvex.Next.LinkNum);
//            cpvex = cpvex.Next;
//        }

        Gragh gragh = new Gragh();

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
        //Scanner sc = new Scanner(System.in);
        System.out.print("please enter scenic num:");
        if(gragh.S_node_Detail(sc.nextInt())==ERROR){
            System.out.println("没有此景点!");
        }
        //sc.close();
    }

    public static void Scenic_nav(Gragh gragh,Scanner scanner){
        //Scanner scanner = new Scanner(System.in);
        System.out.print("please enter start point");
        gragh.DFS_Init(scanner.nextInt());
        //scanner.close();
    }

    public static void Shortest_Path(Gragh gragh,Scanner scanner){
        int p1,p2;
        System.out.print("enter p1:");
        p1 = scanner.nextInt();
        System.out.print("enter p2:");
        p2 = scanner.nextInt();
        if (gragh.Find_short_path(p1,p2)!=OK){
            System.out.println("景点不存在!");
        }
    }

    public static void Circuit_planning(Gragh gragh,Scanner scanner){

    }

    public static void Change_Gragh(Gragh gragh,Scanner scanner){
        boolean isRun = true;
        while (isRun){
            Menu.ShowSubMenu();
            System.out.print("please enter a option:");
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

    }




    public static void AddScenic(Gragh gragh,Scanner scanner){
        int num;
        String name;
        String inTro;
        System.out.print("please enter num:");
        num = scanner.nextInt();
        System.out.print("please enter name:");
        name = scanner.next();
        System.out.print("please enter inTro:");
        inTro = scanner.next();

        if(gragh.Add_Node(num,name,inTro)==OK){
            System.out.println("增加完毕!");
        }else {
            System.out.println("该景点已存在!");
        }

    }

    public static void DeleteScenic(Gragh gragh,Scanner scanner){
        int num;
        System.out.print("please enter num:");
        num = scanner.nextInt();
        if (gragh.Delete_node(num)==OK){
            System.out.println("删除成功!");
        }else {
            System.out.println("无此景点!");
        }
    }

    public static void ChangeScenic(Gragh gragh,Scanner scanner){
        int num;
        String name;
        String inTro;
        System.out.print("please enter num:");
        num = scanner.nextInt();
        System.out.print("please enter name:");
        name = scanner.next();
        System.out.print("please enter inTro:");
        inTro = scanner.next();
        if(gragh.Change_Node(num,name,inTro)==OK){
            System.out.println("修改成功!");
        }else System.out.println("无此景点!");
    }

    public static void Addpath(Gragh gragh,Scanner scanner){
        int point1,point2,distan;
        System.out.print("please enter point1:");
        point1 = scanner.nextInt();
        System.out.print("please enter point2:");
        point2 = scanner.nextInt();
        System.out.print("please enter distan:");
        distan = scanner.nextInt();
        if(gragh.Addvex(point1,point2,distan)==OK&&gragh.Addvex(point2,point1,distan)==OK){
            System.out.println("添加路径成功!");
        }else System.out.println("添加路径失败!");
    }

    public static void DeletePath(Gragh gragh,Scanner scanner){
        int point1,point2;
        System.out.print("please enter point1:");
        point1 = scanner.nextInt();
        System.out.print("please enter point2:");
        point2 = scanner.nextInt();
        if(gragh.delete_vex(point1,point2)==OK&&gragh.delete_vex(point2,point1)==OK){
            System.out.println("删除路径成功!");
        }else System.out.println("删除路径失败!");
    }

    public static void ChangePath(Gragh gragh,Scanner scanner){
        int Old_p1,Old_p2,newP1,newp2,newDistan;
        System.out.print("enter old_p1 and old_p2:");
        Old_p1 = scanner.nextInt();
        Old_p2 = scanner.nextInt();
        System.out.print("enter newp1 and newp2 and newdistan:");
        newP1 = scanner.nextInt();
        newp2 = scanner.nextInt();
        newDistan = scanner.nextInt();
        if(gragh.Change_vex(Old_p1,Old_p2,newP1,newp2,newDistan)==OK){
            System.out.println("修改成功!");
        }else System.out.println("修改失败!");
    }

}
