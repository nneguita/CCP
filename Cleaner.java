package ccp.project;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class Cleaner implements Runnable{
    CleanerBay cb;
    BusDepot bd;
    private int id = 0;
    
    public Cleaner(BusDepot bd,CleanerBay cb,int id)
    {
        this.bd = bd;
        this.cb = cb;
        this.id = id;
    }
    
    public void run()
    {
        try
        {
            Thread.sleep(300);
        }
        catch(InterruptedException e)
        {
            
        }
        System.out.println("Cleaner "+this.id+" is on standby");
        while(!bd.closingTime||!cb.cleaningQueue.isEmpty())
        {
            cb.cleanBus(this);
        }
        System.out.println("Cleaner "+id+" leaving");
    }
    public int getID()
    {
        return id;
    }
}
