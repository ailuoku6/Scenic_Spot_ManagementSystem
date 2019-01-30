package com.company;

import java.util.Scanner;

public class Main {

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
            ShowMenu();
            System.out.print("输入需要操作的选项:");
            option = scanner.next().charAt(0);
            switch (option){
                case 'a'://读取地图
                case 'A':gragh.Greate_gragh("Vex.txt","Edge.txt");
                    break;
                case 'b'://查询景点
                case 'B':Scenic_detail(gragh,scanner);
                    break;
                case 'c'://景点导航
                case 'C':Scenic_nav(gragh,scanner);
                    break;
                case 'd'://搜索最短路径
                case 'D':
                    break;
                case 'e'://铺设电路规划
                case 'E':
                    break;
                case 'f'://修改图保存文件
                case 'F':gragh.SaveData("Vex.txt","Edge.txt");
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


    public static void ShowMenu(){
        System.out.println("-----------------------------------------------------");
        System.out.println("|                      A:读取地图                    |");
        System.out.println("|                      B:查询景点                    |");
        System.out.println("|                      C:景点导航                    |");
        System.out.println("|                     D:搜索最短路径                  |");
        System.out.println("|                     E:铺设电路规划                  |");


        System.out.println("|                    F:修改图保存文件                 |");
        //插入、删除、修改顶点、边的信息，注意顶点和边的关系，之后保存文件，重新读取文件建立图的存储结构并显示。
        //重点注意顶点和边的关系，考虑边是否重复？顶点是否存在？……

        System.out.println("|                      G:退出程序                    |");
        System.out.println("-----------------------------------------------------");

    }

    public static void Scenic_detail(Gragh gragh,Scanner sc){
        //Scanner sc = new Scanner(System.in);
        System.out.print("please enter scenic num:");
        gragh.S_node_Detail(sc.nextInt());
        //sc.close();
    }

    public static void Scenic_nav(Gragh gragh,Scanner scanner){
        //Scanner scanner = new Scanner(System.in);
        System.out.print("please enter start point");
        gragh.DFS_Init(scanner.nextInt());
        //scanner.close();
    }

}
