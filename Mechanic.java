package ccp.project;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class Mechanic implements Runnable{
    MechanicBay mb;
    BusDepot bd;
    private int id = 0;//for naming the mechanics
    
    public Mechanic(BusDepot bd,MechanicBay mb,int id)
    {
        this.bd = bd;
        this.mb = mb;
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
        System.out.println("Mechanic "+this.id+" is on standby");
        while(!bd.closingTime||!mb.criticalMechanicQueue.isEmpty()||!mb.fuelMechanicQueue.isEmpty()||!mb.normalMechanicQueue.isEmpty())
        {
            mb.serviceBus(this);
        }
        System.out.println("Mechanic "+id+" leaving");
    }
    
    public int getID()
    {
        return this.id;
    }
}
