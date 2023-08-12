package ccp.project;
import java.util.Scanner;
import java.io.*;

public class Main {


    public static void main(String[] args)
    {
        WeatherHandler wh = new WeatherHandler();
        boolean a = true;
        Scanner sc = new Scanner(System.in);
        Settings st = new Settings();
        while(a == true)//main menu will keep running until exit is prompted
        {
            System.out.println("--------------------------------------");
            System.out.println("|    Bus Depot Management System     |");
            System.out.println("--------------------------------------");
            System.out.println("|1. Open The Bus Depot               |");
            System.out.println("|2. Configure Settings               |");
            System.out.println("|3. View Last Run's Statistic        |");
            System.out.println("|4. Exit System                      |");
            System.out.println("--------------------------------------");
            System.out.println("Current Weather : "+wh.Weather);
            System.out.println("--------------------------------------");
            System.out.print("Please enter your choice : ");
            String choice = "999999";
            choice = sc.nextLine();
            choice.replaceAll(" ","");
            choice = choice.toLowerCase();
            if(choice.length() == 0)
            {
                System.out.println("Invalid Input");
            }
            else if(choice.charAt(0)=='1' || choice.contains("open"))
            {
                System.out.println("Bus Depot is Being Openned");
                BusDepot bd = new BusDepot(st,wh);
                Thread tbd = new Thread(bd);
                tbd.start();
                try{
                tbd.join();
                }
                catch(InterruptedException e)
                {
                    
                }
            }
            else if(choice.charAt(0)=='2' || choice.contains("configure")||choice.contains("configuration")||choice.contains("settings"))
            {
                System.out.println("System Configuration is being openned");
                st.view();
            }
            else if(choice.charAt(0)=='3'||choice.contains("view")||choice.contains("statistics"))
            {
                System.out.println("Last Run's statistic is being shown");
            }
            else if(choice.charAt(0)=='4'||choice.contains("exit"))
            {
                System.out.println("System is being closed");
                a = false;
            }
            else
            {
                System.out.println("Invalid Input");
            }
        }
    }
    
}
