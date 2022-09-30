package lambda;

import lombok.val;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static javax.xml.bind.JAXBIntrospector.getValue;

/**
 * @author fangxu
 * on date:2022/9/30
 */
public class TestLambda {
    List<Employee> emps = Arrays.asList(
            new Employee(101, "张三", 18, 9999.99),
            new Employee(102, "李四", 59, 6666.66),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 18, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );

    // 1. 调用 Collection.sort()方法，通过定制排序比较两个Employee(先按年龄，年龄相同按姓名比)。
    /**
     * 要求：调用 Collection.sort()方法，通过定制排序比较两个Employee(先按年龄，年龄相同按姓名比)。
     */
    @Test
    public void test1() {

        Collections.sort(emps,
                (x, y) -> {
                    if (x.getAge() == y.getAge()) {
                        return Double.compare(x.getSalary(), y.getSalary());
                    } else {
                        return Integer.compare(x.getAge(), y.getAge());
                    }
                }
        );
        System.out.println("emps = " + emps);
        Collections.sort(emps,(x,y) -> {
            if(x.getId()==y.getId()){
                return Double.compare(x.getSalary(),y.getSalary());
            } else{
                return Integer.compare(x.getId(),y.getId());
            }
        });
        System.out.println("emps order by id: "+emps);

    }

    /**
     * 要求：
     * ①声明函数式接口，接口中声明抽象方法，public String getValue();
     * ②声明测试类，在类中编写方法使用接口作为参数，将每一个字符品转换成大写，并作为方法的返回值
     * ③再将一个字符串的第2个和第4个索引位置进行截取子串
     */
    @Test
    public void test2() {
        String str = "admin NBA Cba";
        String valueF = getValueF(str,
                x -> x.substring(0, 1).toUpperCase() + x.substring(1)

        );
        System.out.println("valueF = " + valueF);

//        x -> x.substring(0, 1).toUpperCase() + x.substring(1);
        MyFunction1 function1 = x -> x.substring(0, 1).toUpperCase() + x.substring(1);

        String str1="admin CBA";
        String value = function1.getValue(str1);
        System.out.println(value);


        String valueF2 = getValueF(str,
                x -> x.substring(1, 3)
        );
        System.out.println("valueF2 = " + valueF2);

    }

    public String getValueF(String str, MyFunction1 string2UpChar) {
        return string2UpChar.getValue(str);
    }

    /**
     * 要求：
     * ①声明一个带两个泛型的函数式接口，泛型类型为<T,R> T为参数，R为返回值
     * ②接口中声明对应抽象方法
     * ③在测试类中声明方法，使得接口作为参数，计算两个long型参数的和
     * ④再计算两个long型参数的乘积
     */
    @Test
    public void test3() {

        String calc = calc(10L, 3L,
                (a, b) -> new BigDecimal(a).divide(new BigDecimal(b), 3, BigDecimal.ROUND_HALF_UP).toString()
        );
        System.out.println("calc = " + calc);

    }

    public String calc(Long l1, long l2, MyFunction2<Long, String> myFunction2) {

        return myFunction2.calc(l1, l2);
    }
}
