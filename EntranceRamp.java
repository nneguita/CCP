package ccp.project;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;


public class EntranceRamp{

    BusDepot bd;
    BlockingQueue<Bus> entranceQueue = new ArrayBlockingQueue(100);
    BlockingQueue<Bus> fuelEntranceQueue = new ArrayBlockingQueue(100);  
    BlockingQueue<Bus> criticalEntranceQueue = new ArrayBlockingQueue(100);
    boolean rampAvailability = true;
    
    public EntranceRamp(BusDepot bd)
    {
        this.bd = bd;
    }
    
    public void busEnters(Bus bus)  
    {
        if (bus.getServiceID()<11)
        {
            //entranceQueue.offer(bus);
            synchronized(this)
            {
                entranceQueue.offer(bus);
                while(entranceQueue.element().getID()!=bus.getID())
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
            }
            try
            {
                synchronized(this)
                {
                    while(rampAvailability == false)
                    {
                        this.wait();
                    }
                    while(!fuelEntranceQueue.isEmpty())
                    {
                        while(!criticalEntranceQueue.isEmpty())
                        {
                            this.wait();
                        }
                        this.wait();
                    }
                    rampAvailability = false;
                }
                long duration = (long)(Math.random()*9+1);
                TimeUnit.SECONDS.sleep(3);//(duration);
            }
            catch(InterruptedException e)
            {
                
            }
            synchronized(this)
            {
                System.out.println("Bus "+entranceQueue.poll().getID()+" has reached the depot waiting area");
                rampAvailability = true;
                this.notifyAll();
            }//what to do now
        }
        else if (bus.getServiceID()>10&&bus.getServiceID()<13)
        {
            System.out.println("Bus "+bus.getID()+" is low on fuel and is getting high priority");
           // fuelEntranceQueue.offer(bus);
            synchronized(this)
            {
                fuelEntranceQueue.offer(bus);
                while(fuelEntranceQueue.element().getID()!=bus.getID())
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
            }
            try
            {
                synchronized(this)
                {
                    while(rampAvailability == false)
                    {
                        this.wait();
                    }
                    while(!criticalEntranceQueue.isEmpty())
                    {
                        this.wait();
                    }
                rampAvailability = false;
                }
                long duration = (long)(Math.random()*9+1);
                TimeUnit.SECONDS.sleep(3);//(duration);
            }
            catch(InterruptedException e)
            {
                System.out.println("Wait Error");
            }
            synchronized(this)
            {
                System.out.println("Bus "+fuelEntranceQueue.poll().getID()+" has reached the depot waiting area");
                rampAvailability = true;
                this.notifyAll();
            }
        }   
        else if (bus.getServiceID()==13)
        {
            System.out.println("Bus "+bus.getID()+" is needing an emergency repair and is given maximum priority");
            //criticalEntranceQueue.offer(bus);
            synchronized(this)
            {
                criticalEntranceQueue.offer(bus);
                while(criticalEntranceQueue.element().getID()!=bus.getID())
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
            }
            try
            {
                synchronized(this)
                {
                    while(rampAvailability == false)
                    {
                        this.wait();
                    }
                    rampAvailability = false;
                }
                long duration = (long)(Math.random()*9+1);
                TimeUnit.SECONDS.sleep(3);//(duration);
            }
            catch(InterruptedException e)
            {
                System.out.println("Wait Error");
            }
            synchronized(this)
            {
                System.out.println("Bus "+criticalEntranceQueue.poll().getID()+" has reached the depot waiting area");
                rampAvailability = true;
                this.notifyAll();
            }
        }
        else
        {
            System.out.println("Invalid Bus ServiceID at Bus "+bus.getID());
        }
    }
}