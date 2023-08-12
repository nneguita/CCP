package ccp.project;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class BusGenerator implements Runnable{
    BusDepot bd;
    Settings st;
    int ID = 1;//naming buses will be based on this ID
    int busAmount = 20;
    public BusGenerator(BusDepot bd,Settings st)
    {
        this.bd = bd;
        this.st = st;
        this.busAmount = st.busAmount;
    }
    
    public void run()
    {
        while(bd.stopAcceptingBus == false&&busAmount > 0)
        {
            Bus bus = new Bus(bd);
            bus.setDate(new Date());
            bus.setID(ID);
            int cleanID = 0;
            cleanID = (int)(Math.random()*2 + 0);//generating cleanID from 0 to 1
            bus.setCleanID(cleanID);
            int serviceID = (int)(Math.random()*14+0);//generating serviceID from 0 to 13
            if(serviceID<4)
            {
                serviceID = 0;
            }
            bus.setServiceID(serviceID);
            if(cleanID == 0 && serviceID>=0 && serviceID < 4)//in case bus randomly gets "no service needed" it defaults into needing a cleaning instead
            {
                cleanID = 1;
                bus.setCleanID(cleanID);
            }
            Thread tbus = new Thread(bus);
            tbus.start();
            try
            {
                long duration = 2;//(long)Math.random()*10+5;
                TimeUnit.SECONDS.sleep(duration);
            }
            catch(Exception e)
            {
                
            }
            //link bus to the bus depot here and the arguments if stop generating is true and the condition for stop generating to be true
            ID++;//Next bus name is 1 number higher
            busAmount --; 
        }
        if(bd.stopAcceptingBus)
        {
            System.out.println("It's 30 minutes before closing. Stop accepting bus");
        }
    }
}
