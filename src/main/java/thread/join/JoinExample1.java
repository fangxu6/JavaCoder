package thread.join;

import java.util.Vector;

/**
 * @author fangxu
 * on date:2022/10/8
 */
public class JoinExample1 extends Thread
{
    public void run()
    {
        for(int i=1; i<=4; i++)
        {
            try
            {
                Thread.sleep(500);
            }catch(Exception e){System.out.println(e);}
            System.out.println(i);
        }
    }
    public static void main(String args[])
    {
        // creating three threads
        JoinExample1 t1 = new JoinExample1();
        JoinExample1 t2 = new JoinExample1();
        JoinExample1 t3 = new JoinExample1();
        Thread.yield();
        // thread t1 starts
        t1.start();
        // starts second thread when first thread t1 is died.
        try
        {
//            t1.join(1500);
            t1.join();
            System.out.println("---------------");
        }catch(Exception e){System.out.println(e);}
        // start t2 and t3 thread
        t2.start();
        t3.start();

        Vector<String> vector=new Vector<>();
        vector.add("a");
    }
}

