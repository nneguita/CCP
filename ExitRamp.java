package ccp.project;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class ExitRamp {
    BusDepot bd;
    BlockingQueue<Bus> exitQueue = new ArrayBlockingQueue(100);
    boolean rampAvailability = true;
    
    public ExitRamp(BusDepot bd)
    {
        this.bd = bd;
    }
    
    public void busExits(Bus bus)
    {
        synchronized(this)
        {
            System.out.println("Bus "+bus.getID()+" is exitting");
            exitQueue.offer(bus);
            while(exitQueue.element().getID()!=bus.getID())
            {
                try
                {
                    this.wait();
                }
                catch(InterruptedException e)
                {
                    System.out.println("Wait Error");
                }
            }
            try
            {
                while(rampAvailability == false)
                {
                    this.wait();
                }
                long duration = (long)(Math.random()*9+1);
                TimeUnit.SECONDS.sleep(3);//(duration);
            }
            catch(InterruptedException e)
            {
                System.out.println("Wait error");
            }
            
            System.out.println("Bus "+exitQueue.poll().getID()+" has left the bus depot");
            rampAvailability = true;
            this.notifyAll();
        }
    }
    
}
