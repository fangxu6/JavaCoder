package algorithms;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BinarySearch {
    public static void main(String[] args) {

//        String[] strArr = {"1", "2", "3", "4"};
//        List<String> strList = new ArrayList<String>();
//        // 返回数组的列表表示
//        // 数组元素的任何更改也将更改arrayList元素
//        strList = Arrays.asList(strArr);
//        System.out.println("Original ArrayList from Arrays.asList()");


//        int[] arr=[1,3,5,7,9];
        int[] arr = new int[]{4, 25, 10, 95, 06, 21};
        System.out.println("原始数组为：");
        for (int a : arr) {
            System.out.print(a + " ");
        }
        Arrays.sort(arr);
        System.out.println("排序后为：");
        for (int x : arr) {
            System.out.print(x + " ");
        }
        System.out.println();

//        int index=Arrays.binarySearch(arr, 25);
        List<Integer> list = new ArrayList<Integer>();
        list = Ints.asList(arr);
        int index = biSearch(arr, 25);
        System.out.println("关键字25的返回值为：" + index);

        index = Arrays.binarySearch(arr, 10);
        System.out.println("关键字10的返回值为：" + index);

        index = Collections.binarySearch(list, 6);
        System.out.println("关键字6的返回值为：" + index);
    }

    static int biSearch(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        int result = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] == target) {
                result = mid;
                return result;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

}
