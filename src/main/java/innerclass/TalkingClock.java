package innerclass;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class TalkingClock {
    private int interval;
    private boolean beep;

    public TalkingClock(int interval, boolean beep){
        this.interval=interval;
        this.beep=beep;
    }

    public void start(){

    }

    public class TimePrinter implements ActionListener
    // an inner class
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("At he tone, the time is "+ new Date());
            if (beep) Toolkit.getDefaultToolkit().beep();
        }


        //automatically generated code
//        public TimePrinter(TalkingClock clock)
//        {
//            outer=clock;
//        }
    }
}
