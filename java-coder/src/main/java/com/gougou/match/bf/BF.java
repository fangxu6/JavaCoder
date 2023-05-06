package com.gougou.match.bf;


public class BF {

    public static void main(String[] args) {
        String a = "aaabbaaaccssdd";
        String b = "acc";
        System.out.println(bfFind(a, b, 3));

    }

    public static int bfFind(String S, String T, int pos) {
        char[] arr1 = S.toCharArray();
        char[] arr2 = T.toCharArray();
        int i = pos;
        int j = 0;
        while(i < arr1.length && j < arr2.length) {
            if(arr1[i] == arr2[j]) {
                i++;
                j++;
            }
            else {
                i = i - j + 1;
                j = 0;
            }
        }
        if(j == arr2.length) return i - j;
        else return -1;
    }

}

