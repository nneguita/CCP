package ccp.project;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class ClosingClock implements Runnable{
    
    BusDepot bd;
    Settings st;
    int timeOpen;
    int timeAcceptingBus;
    
    public ClosingClock(BusDepot bd,Settings st)
    {
        this.bd = bd;
        this.st = st;
        
    }
    public void run()
    {
        timeOpen = st.timeOpen;
        timeAcceptingBus = timeOpen - 30;
        try
        {
            TimeUnit.SECONDS.sleep(timeAcceptingBus);
            bd.stopAcceptingBus = true;
            System.out.println("We have stopped accepting buses");
            TimeUnit.SECONDS.sleep(30);
            bd.closingTime = true;
            System.out.println("It's closing time");
            synchronized(bd.mb)
            {
                bd.mb.notifyAll();
            }
            synchronized(bd.cb)
            {
                bd.cb.notifyAll();
            }
        }
        catch(InterruptedException e)
        {
            
        }
    }
}
