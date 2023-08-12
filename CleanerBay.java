package ccp.project;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class CleanerBay {
    BusDepot bd;
    Settings st;
    WeatherHandler wh;
    BlockingQueue<Bus> cleaningQueue = new ArrayBlockingQueue(100);
    
    public CleanerBay (BusDepot bd,Settings st,WeatherHandler wh)
    {
        this.bd = bd;
        this.st = st;
        this.wh = wh;
    }
    
    
    public void cleanBus(Cleaner cl)
    {
        synchronized(wh)
        {
            if(wh.WeatherStatus>6)
            System.out.println("Cleaner "+cl.getID()+" is waiting for the rain to pass by");
            while(wh.WeatherStatus>6)
            {
                try
                {   
                    wh.wait();
                }
                catch(InterruptedException e)
                {   
                   
                }
            }
            System.out.println("Cleaner "+cl.getID()+" has decided that the weather is good enough");
        }
        
        Bus bus;
        synchronized(cleaningQueue)
        {
            while(cleaningQueue.size()==0)
            {
                System.out.println("Cleaner "+cl.getID()+" is waiting for a bus");
                try
                {
                   cleaningQueue.wait();
                }
                catch(InterruptedException e)
                {
                    System.out.println("Cleaner wait error");
                }
            }
            bus = cleaningQueue.poll();
            System.out.println("Bus "+bus.getID()+" is being cleaned by cleaner "+cl.getID());
            try
            {
                TimeUnit.SECONDS.sleep(st.timeClean);
            }
            catch(InterruptedException e)
            {
                System.out.println("Cleaning Time Sleep Error");
            }
            System.out.println("Bus "+bus.getID()+" has been cleaned");
            synchronized(bus)
            {
                bus.notify();
            }
        }
        
    }
    
    public void addBus(Bus bus)
    {
        synchronized(cleaningQueue)
        {
            System.out.println("Bus "+bus.getID()+" is queueing for a cleaning service in front of the cleaning bay");
            cleaningQueue.offer(bus);
            cleaningQueue.notify();
        }
    }
}
