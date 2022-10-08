package lambda.functionalInterface;

/**
 * className:TestFunctional
 * package:lambda.functionalInterface
 * Description:
 *
 * @Date:2022/9/2521:48
 * @Author:fangxu6@gmail.com
 */
public class TestFunctional {
    public static void main(String[] args) {


        GreetingService greetService1 = message -> System.out.println("Hello " + message);
        greetService1.sayMessage("World");


    }
}
