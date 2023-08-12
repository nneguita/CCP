package ccp.project;
import java.util.*;
import java.io.*;
import java.time.*;
import java.util.concurrent.*;

public class MechanicBay {
    BusDepot bd;
    Settings st;
    BlockingQueue<Bus> normalMechanicQueue = new ArrayBlockingQueue(100);//normal service
    BlockingQueue<Bus> fuelMechanicQueue = new ArrayBlockingQueue(100);//fuel service (half the duration)
    BlockingQueue<Bus> criticalMechanicQueue = new ArrayBlockingQueue(100);//emergency error services(double the duration)
    
    public MechanicBay(BusDepot bd,Settings st)
    {
        this.bd = bd;
        this.st = st;
    }
    
    
    public void serviceBus(Mechanic mc)
    {
        Bus bus = null;
        int checkStatus = 0;
        synchronized(criticalMechanicQueue)
        {
            if(criticalMechanicQueue.size()==0)
            {
                checkStatus = 1;
            }
            else
            {
                bus = criticalMechanicQueue.poll();
            }
        }
        synchronized(fuelMechanicQueue)
        {
            if(fuelMechanicQueue.size()==0&&checkStatus == 1)
            {
                checkStatus = 2;
            }
            else if(fuelMechanicQueue.size()!=0&&checkStatus==1)
            {
                bus = fuelMechanicQueue.poll();
            }
        }
        synchronized(normalMechanicQueue)
        {
            if(normalMechanicQueue.size()==0&&checkStatus == 2)
            {
                checkStatus = 3;
            }
            else if(normalMechanicQueue.size()!=0&&checkStatus == 2)
                
            {
                bus = normalMechanicQueue.poll();
            }
        }
        if(checkStatus == 0)
        {
            synchronized(criticalMechanicQueue)
            {
                System.out.println("Bus "+bus.getID()+" is being fixed under mechanic "+mc.getID());
                try
                {
                    int repairTime = st.timeService*2;
                    TimeUnit.SECONDS.sleep(repairTime);
                }
                catch(Exception e)
                {
                    System.out.println("Mechanic wait error");
                }
                System.out.println("Bus "+bus.getID()+" has been repaired and is returning to the waiting area");
                bus.setServiceID(0);
                if(bus.getCleanID()==1 && !bd.closingTime)
                {
                    System.out.println("Mechanic "+mc.getID()+" has suggested Bus "+bus.getID()+" to be cleaned");
                }
                synchronized(bus)
                {
                    bus.notify();
                }
            }
        }
        else if(checkStatus == 1)
        {
            synchronized(fuelMechanicQueue)
            {
                System.out.println("Bus "+bus.getID()+" is getting a quick refuel by mechanic "+mc.getID());
                try
                {
                    int refuelTime = st.timeService/2;
                    TimeUnit.SECONDS.sleep(refuelTime);
                }
                catch(Exception e)
                {   
                    System.out.println("Mechanic wait error");
                }
                System.out.println("Bus "+bus.getID()+" has been refuelled and is returning to the waiting area");
                bus.setServiceID(0);
                if(bus.getCleanID()==1&&!bd.closingTime)
                {
                    System.out.println("Mechanic "+mc.getID()+" has suggested Bus "+bus.getID()+" to be cleaned");
                }
                synchronized(bus)
                {
                    bus.notify();
                }
            }
            
        }
        else if(checkStatus == 2)
        {
            synchronized(normalMechanicQueue)
            {
                System.out.println("Bus "+bus.getID()+" is getting a service by mechanic "+mc.getID());
                try
                {
                    int serviceTime = st.timeService;
                    TimeUnit.SECONDS.sleep(serviceTime);
                }
                catch(Exception e)
                {   
                    System.out.println("Mechanic wait error");
                }
                System.out.println("Bus "+bus.getID()+" has been serviced and is returning to the waiting area");
                bus.setServiceID(0);
                if(bus.getCleanID()==1&&!bd.closingTime)
                {
                    System.out.println("Mechanic "+mc.getID()+" has suggested Bus "+bus.getID()+" to be cleaned");
                }
                synchronized(bus)
                {
                    bus.notify();
                }
            }
        }
        else if(checkStatus == 3)
        {
            synchronized(this)
            {
                System.out.println("Mechanic "+mc.getID()+" is waiting for a bus");
                try
                {
                    this.wait();
                }
                catch(Exception e)
                {
                
                }
            } 
        }
    }
    
    public void addBus(Bus bus)
    {
        if(bus.getServiceID()<11)//if normal services
        {
            synchronized(normalMechanicQueue)
            {
                System.out.println("Bus "+bus.getID()+" is queueing for a bus service in front of the mechanic bay");
                normalMechanicQueue.offer(bus);
                synchronized(this)
                {
                    this.notify();
                }
            }
        }
        else if(bus.getServiceID()>10&&bus.getServiceID()<13)//if fuel needed
        {
            synchronized(fuelMechanicQueue)
            {
                System.out.println("Bus "+bus.getID()+" is queueing for a quick refuel");
                fuelMechanicQueue.offer(bus);
                synchronized(this)
                {
                    this.notify();
                }
            }
        }
        else if(bus.getServiceID()==13)//if critical repair needed
        {
            synchronized(criticalMechanicQueue)
            {
                System.out.println("Bus "+bus.getID()+" is queueing for an emergency repair");
                criticalMechanicQueue.offer(bus);
                synchronized(this)
                {
                    this.notify();
                }
            }
        }
    }
}
