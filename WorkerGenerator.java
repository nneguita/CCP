package ccp.project;
import java.util.*;
import java.io.*;
import java.util.concurrent.*;

public class WorkerGenerator implements Runnable{
    BusDepot bd;
    Settings st;
    MechanicBay mb;
    CleanerBay cb;
    int generatorCode = 0;//0 for mechanic, 1 for cleaner
    
    public WorkerGenerator(CleanerBay cb ,MechanicBay mb,BusDepot bd,Settings st,int generatorCode)
    {
        this.cb = cb;
        this.mb = mb;
        this.bd = bd;
        this.st = st;
        this.generatorCode = generatorCode;
    }
    
    public void run()
    {
        if(generatorCode == 0)
        {
            this.generateMechanic();
        }
        else if(generatorCode == 1)
        {
            this.generateCleaner();
        }
    }
    
    public void generateMechanic()
    {
        for(int i = st.mechanicAmount;i>0;i--)
        {
            Mechanic mc = new Mechanic(bd,mb,i);
            Thread tmc = new Thread(mc);
            tmc.start();
        }
    }
    
    public void generateCleaner()
    {
        for(int i = st.cleanerAmount;i>0;i--)
        {
            Cleaner cl = new Cleaner(bd,cb,i); 
            Thread tcl = new Thread(cl);
            tcl.start();
        }
    }
}
