package com.company;

public class Menu {
    public static void ShowMenu(){
        System.out.println("-------------------------主菜单----------------------");
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


    public static void ShowSubMenu(){
        //插入、删除、修改顶点、边的信息，注意顶点和边的关系，之后保存文件，重新读取文件建立图的存储结构并显示。
        //重点注意顶点和边的关系，考虑边是否重复？顶点是否存在？……
        //完善程序的健壮性
        //两个顶点之间是否能有多条路径，应该可以
        System.out.println("--------------------景点及路径管理--------------------");
        System.out.println("|                     A:增加景点                     |");
        System.out.println("|                     B:删除景点                     |");
        System.out.println("|                     C:修改景点                     |");
        System.out.println("|                     D:增加路径                     |");
        System.out.println("|                     E:删除路径                     |");
        System.out.println("|                     F:修改路径                     |");
        System.out.println("|                     G:保存地图                     |");

        System.out.println("|                      H:退出                       |");
        System.out.println("-----------------------------------------------------");

    }

    public static void ShowERROR(){
        System.out.println("输入有误!");
    }

}
