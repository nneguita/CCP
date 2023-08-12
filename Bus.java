package ccp.project;
import java.util.Date;

public class Bus implements Runnable{
    private int ID;
    private Date date;
    private int cleanID;//0 for clean(don't need cleaning services), 1 for dirty(need cleaning services)
    private int serviceID;//0-3 for no service,4-10 for basic service,11-12 for refuel, 13 for critical mechanical malfunctions
    BusDepot bd;
    
    public Bus(BusDepot bd)
    {
        this.bd = bd;
    }
    
    public void setID(int ID)
    {
        this.ID=ID;
    }
    
    public void setDate(Date date)
    {
        this.date=date;
    }
    
    public void setCleanID(int cleanID)
    {
        this.cleanID = cleanID;
    }
    
    public void setServiceID(int serviceID)
    {
        this.serviceID = serviceID;
    }
    
    public int getID()
    {
        return ID;
    }
    
    public Date getDate()
    {
        return date;
    }
    
    public int getCleanID()
    {
        return cleanID;
    }
    
    public int getServiceID()
    {
        return serviceID;
    }
    
    public void run()
    {
        try
        {
            bd.addBus(this);
        }
        catch (Exception e)
        {
            
        }
    }
}
