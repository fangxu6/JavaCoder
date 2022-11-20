package thread.balking_32.scence;

/**
 * className:InitTest
 * package:thread.balking_32.scence
 * Description:
 *
 * @Date:2022/11/2010:27
 * @Author:fangxu6@gmail.com
 */
//Balking 模式有一个非常典型的应用场景就是单次初始化，下面的示例代码是它的实现。这个实现方案中，我们将 init() 声明为一个同步方法，
// 这样同一个时刻就只有一个线程能够执行 init() 方法；init() 方法在第一次执行完时会将 inited 设置为 true，
// 这样后续执行 init() 方法的线程就不会再执行 doInit() 了。
class InitTest{
    boolean inited = false;
    synchronized void init(){
        if(inited){
            return;
        }
        //省略doInit的实现
        doInit();
        inited=true;
    }
    void doInit(){

    }
}
