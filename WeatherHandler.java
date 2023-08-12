package ccp.project;
import java.util.Scanner;
import java.io.*;

public class WeatherHandler implements Runnable{
    double WeatherStatus = 3;
    String Weather = "Sunny";
    private boolean keepChanging = true;
    
    public WeatherHandler()
    {
        WeatherStatus = Math.random()* 30;
        this.getWeather();
    }
    public void run()
    {
        keepChanging = true;
        while(keepChanging)//replace with closing time later
        {
            try
            {
            Thread.sleep(1500);
            }
            catch(InterruptedException e)
            {}
            synchronized(this)
            {
                WeatherStatus += Math.random()*4 + -2;
                if(WeatherStatus > 10)//in case it gets higher than the maximum cap
                {   
                    WeatherStatus = 10;
                }
                else if (WeatherStatus <0)//in case it got lower than the minimum cap
                {
                    WeatherStatus = 0;
                }
                this.notifyAll();
            }
        }
    }
    
    
    public void getWeather()
    {
        if (this.WeatherStatus <1)
        {
            Weather = "Sunny and Hot";
        }
        else if (this.WeatherStatus >= 1 && WeatherStatus <= 4)
        {
            Weather = "Sunny";
        }
        else if (this.WeatherStatus > 4 && WeatherStatus <= 6)
        {
            Weather = "Cloudy";
        }
        else if (this.WeatherStatus >6 && WeatherStatus <= 9)
        {
            Weather = "Raining";
        }
        else if (this.WeatherStatus >9)
        {
            Weather = "Storm";
        }
        
    }
    
    public void stopRun()
    {
        keepChanging = false;
    }
}
