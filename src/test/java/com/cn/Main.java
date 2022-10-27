package com.cn;

/**
 * @Author YaoJie
 * @Date 2022/2/21
 */
public class Main {

    public static void main(String[] args) {


        // chengFa(9);

        int[] arr={32,12,44,10,98,33,14};
        for (int i = 0; i < arr.length-1 ; i++) {
            for (int j = 0; j < arr.length-1-i ; j++) {

                if(arr[j]>arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        for (int a:arr){
            System.out.println(a);
        }
    }


    public static void chengFa(int a){

        for (int i =1;i<=a; i++){
            for (int j=1;j<=i; j++){
                System.out.print(""+i+"*"+j+"="+(i*j));
            }
            System.out.println();
        }
    }

}

