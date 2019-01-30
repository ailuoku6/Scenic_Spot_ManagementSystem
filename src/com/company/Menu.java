package com.company;

public class Menu {
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


    public static void ShowSubMenu(){
        //插入、删除、修改顶点、边的信息，注意顶点和边的关系，之后保存文件，重新读取文件建立图的存储结构并显示。
        //重点注意顶点和边的关系，考虑边是否重复？顶点是否存在？……
        System.out.println("-----------------------------------------------------");
        System.out.println("                          A:                           ");
        System.out.println("                          B:                           ");
        System.out.println("                          C:                           ");
        System.out.println("                          D:                           ");
        System.out.println("                          E:                           ");
        System.out.println("                          F:                           ");
        System.out.println("                          G:                           ");

        System.out.println("-----------------------------------------------------");

    }

}
