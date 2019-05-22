package com.company;

public class MyStack {
    private int CurLength;
    private int position;
    private int[] array;

    private final int InitLength = 10;

    public MyStack(){
        this.position = 0;
        this.CurLength = 10;
        this.array = new int[CurLength];
    }
    public void push(int key){
        if(position>=CurLength){
            CurLength+=5;
            int[] newArray = new int[CurLength];
            for (int i = 0;i<position;i++){
                newArray[i] = array[i];
            }
            array = newArray;
        }
        array[position++] = key;
    }
    public int size(){
        return position;
    }

    public int pop(){
        if (position==0) return Integer.MIN_VALUE;
        return array[--position];
    }

//    public int[] getArray(){
//        return array;
//    }

    public int getItem(int index){
        return array[index];
    }

    public void clear(){
        CurLength = 10;
        position = 0;
        array = new int[CurLength];
    }

}
