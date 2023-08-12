package ccp.project;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class BusDepot implements Runnable{
    boolean closingTime = false;
    boolean stopAcceptingBus = false;
    Settings st;
    WeatherHandler wh;
    EntranceRamp er = new EntranceRamp(this);
    ExitRamp ex = new ExitRamp(this);
    MechanicBay mb;
    CleanerBay cb;
    List<Bus> BusList;
    BusGenerator bg;
    ClosingClock cc;
    WorkerGenerator mg;//1 for mechanic
    WorkerGenerator cg;//and 1 for cleaner
    
    public BusDepot(Settings st,WeatherHandler wh)
    {
        BusList = new LinkedList<Bus>();
        this.st=st;
        this.wh=wh;
    }
    
    public void run()
    {
        cc = new ClosingClock(this,st);
        bg = new BusGenerator(this,st);
        mb = new MechanicBay(this,st);
        cb = new CleanerBay(this,st,wh);
        mg = new WorkerGenerator(cb,mb,this,st,0);
        cg = new WorkerGenerator(cb,mb,this,st,1);
        Thread tbg = new Thread(bg);
        Thread tmg = new Thread(mg);
        Thread twg = new Thread(cg);
        Thread twh = new Thread(wh);
        Thread tcc = new Thread(cc);
        tcc.start();
        twh.start();
        tmg.start();
        twg.start();
        tbg.start();
        while(!closingTime||!BusList.isEmpty())
        {
            synchronized(this)
            {
                try
                {
                    this.wait();
                }
                catch(InterruptedException e)
                {
                    
                }
            }
        }
        wh.stopRun();
        System.out.println("Enter statistics here");
    }
    
    public void addBus(Bus bus) throws Exception//what happens when the bus approaches the bus depot
    {
        System.out.println("Bus "+bus.getID()+" approaching the ramp to the waiting bay at "+bus.getDate());
        BusList.add(bus);
        er.busEnters(bus);
        while(bus.getServiceID()!=0&&!closingTime||bus.getCleanID()!=0&&!closingTime)
        {
            if(bus.getServiceID()>=4&&!closingTime)
            {
                mb.addBus(bus);
                synchronized(bus)
                {
                    bus.wait();
                }
            }
            if(bus.getCleanID()==1&&!closingTime) 
            {
                cb.addBus(bus);
                synchronized(bus)
                {
                    bus.wait();
                }
            }
        }
        ex.busExits(bus);
        BusList.remove(bus);
        synchronized(this)
        {
            this.notify();
        }
    }
}
