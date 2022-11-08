package thread.lifecycle;
public class InterruptDemo {
    public static void main(){

        Thread th = Thread.currentThread();
        while(true) {
          if(th.isInterrupted()) {
            break;
          }
          // 省略业务代码无数
          try {
            Thread.sleep(100);//可能出现无限循环，线程在sleep期间被打断了，抛出一个InterruptedException异常，try catch捕捉此异常，应该重置一下中断标示，因为抛出异常后，中断标示会自动清除掉！
          }catch (InterruptedException e){
            e.printStackTrace();
            Thread.currentThread().interrupt(); //所以要重置下
          }
        }

    }
}
