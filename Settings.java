package ccp.project;
import java.util.Scanner;
import java.io.*;

public class Settings 
{
    Scanner sc = new Scanner(System.in);    
    int busAmount = 20;
    int mechanicAmount = 2;
    int cleanerAmount = 2;
    int timeService = 15;
    int timeClean = 10;
    int timeOpen = 480;
    File settingsInfo = new File("settings.txt");
    public Settings()
    {
        if (settingsInfo.exists() == false)
        {
            this.generateFile();//creating new file if there isn't any
        }
        try//reading the file
        {
        FileReader fr = new FileReader(settingsInfo);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        String[] part = line.split(",");
        busAmount = Integer.parseInt(part[0]);
        mechanicAmount = Integer.parseInt(part[1]);
        cleanerAmount = Integer.parseInt(part[2]);
        timeService = Integer.parseInt(part[3]);
        timeClean = Integer.parseInt(part[4]);
        timeOpen = Integer.parseInt(part[5]);
        } 
        catch(IOException e)
        {
            System.out.println("Settings Read Error");
        }
        
    }
    
    void view()
    {
        boolean a = true;
        while (a == true){
            System.out.println("--------------------------------------");
            System.out.println("|1.Number of buses daily : "+busAmount+"      ");
            System.out.println("|2.Number of mechanic    : "+mechanicAmount+" ");
            System.out.println("|3.Number of cleaner     : "+cleanerAmount+"  ");
            System.out.println("|4.Service Time (Seconds): "+timeService+"s   ");
            System.out.println("|5.Clean Time (Seconds)  : "+timeClean+"s     ");
            System.out.println("|6.Open Duration(Seconds): "+timeOpen+"s      ");
            System.out.println("|7.Exit                              |");
            System.out.println("--------------------------------------");
            System.out.println("| Choose which setting would you like|");
            System.out.println("| to edit or if you want to return to|");
            System.out.println("| the main menu                      |");
            System.out.println("--------------------------------------");
            System.out.print("Please enter your choice : ");
            String choice = "";
            choice = sc.nextLine();
            choice.replaceAll(" ","");
            choice = choice.toLowerCase();
            if(choice.charAt(0)=='1' || choice.contains("bus"))
            {
                System.out.print("Please enter the new number of daily bus : ");
                int amount = 0;
                try
                {
                    amount = Integer.parseInt(sc.nextLine());
                    this.editBusAmount(amount);
                }
                catch(Exception e)
                {
                    System.out.println("Please enter a valid number");
                }
                
            }
            else if(choice.charAt(0)=='2' || choice.contains("mechanic")) 
            {
                System.out.print("Please enter the new number of mechanic : ");
                int amount = 0;
                try
                {
                    amount = Integer.parseInt(sc.nextLine());
                    this.editMechanicAmount(amount);
                }
                catch(Exception e)
                {
                    System.out.println("Please enter a valid number");
                }
            }
            else if(choice.charAt(0)=='3' || choice.contains("clean")||choice.contains("cleaner")) 
            {
                System.out.print("Please enter the new number of cleaners : ");
                int amount = 0;
                try
                {
                    amount = Integer.parseInt(sc.nextLine());
                    this.editCleanerAmount(amount);
                }
                catch(Exception e)
                {
                    System.out.println("Please enter a valid number");
                }
            }
            else if(choice.charAt(0)=='4'||choice.contains("service")||choice.contains("fix"))
            {
                System.out.print("Please enter the new time of each service(in seconds) : ");
                int amount = 0;
                try
                {
                    amount = Integer.parseInt(sc.nextLine());
                    this.editServiceLength(amount);
                }
                catch(Exception e)
                {
                    System.out.println("Please enter a valid number");
                }
            }
            else if(choice.charAt(0)=='5'||choice.contains("clean"))
            {
                System.out.print("Please enter the new time of each cleaning(in seconds) : ");
                int amount = 0;
                try
                {
                    amount = Integer.parseInt(sc.nextLine());
                    this.editCleanLength(amount);
                }
                catch(Exception e)
                {
                    System.out.println("Please enter a valid number");
                }
            }
            else if(choice.charAt(0)=='6'||choice.contains("duration")||choice.contains("open"))
            {
                System.out.print("Please enter the new duration of each session(in seconds) : ");
                int amount = 0;
                try
                {
                    amount = Integer.parseInt(sc.nextLine());
                    this.editOpenLength(amount);
                }
                catch(Exception e)
                {
                    System.out.println("Please enter a valid number");
                }
            }
            else if(choice.charAt(0)=='7'||choice.contains("exit"))
            {
                System.out.println("Returning to Main Menu");
                a = false;
            }
            else
            {
                System.out.println("Invalid Input");
            }
        }
    }
    
    void generateFile()
    {
        try
        {
            settingsInfo.createNewFile();
            FileWriter fw = new FileWriter("settings.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("20,2,2,15,10,480");//default numbers
            bw.newLine();
            bw.flush();
            bw.close();
        }
        catch(Exception e)
        {
            
        }
    }
    
    void editBusAmount(int amount)
    {
        busAmount = amount;
        System.out.println("The new daily bus amount is : "+busAmount);
        try
        {
            FileWriter fw = new FileWriter(settingsInfo);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(busAmount+","+mechanicAmount+","+cleanerAmount+","+timeService+","+timeClean+","+timeOpen);
            bw.newLine();
            bw.flush();
            bw.close();
        }
        catch (IOException e)
        {
            
        }
    }
    
    void editMechanicAmount(int amount)
    {
        mechanicAmount = amount;
        System.out.println("The new mechanic amount is : "+mechanicAmount);
        try
        {
            FileWriter fw = new FileWriter(settingsInfo);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(busAmount+","+mechanicAmount+","+cleanerAmount+","+timeService+","+timeClean+","+timeOpen);
            bw.newLine();
            bw.flush();
            bw.close();
        }
        catch(IOException e)
        {
            
        }
    }
    
    void editCleanerAmount(int amount)
    {
        cleanerAmount = amount;
        System.out.println("The new cleaner amount is : "+cleanerAmount);
        try
        {
            FileWriter fw = new FileWriter(settingsInfo);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(busAmount+","+mechanicAmount+","+cleanerAmount+","+timeService+","+timeClean+","+timeOpen);
            bw.newLine();
            bw.flush();
            bw.close();
        }
        catch (IOException e)
        {
            
        }
    }
    
    void editServiceLength(int amount)
    {
        timeService = amount;
        System.out.println("The new service length is : "+timeService+"s");
        try
        {
            FileWriter fw = new FileWriter(settingsInfo);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(busAmount+","+mechanicAmount+","+cleanerAmount+","+timeService+","+timeClean+","+timeOpen);
            bw.newLine();
            bw.flush();
            bw.close();
        }
        catch (IOException e)
        {
            
        }
    }
    
    void editCleanLength(int amount)
    {
        timeClean = amount;
        System.out.println("The new cleaning length is : "+timeClean+"s");
        try
        {
            FileWriter fw = new FileWriter(settingsInfo);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(busAmount+","+mechanicAmount+","+cleanerAmount+","+timeService+","+timeClean+","+timeOpen);
            bw.newLine();
            bw.flush();
            bw.close();
        }
        catch (IOException e)
        {
            
        }
    }
    
    void editOpenLength(int amount)
    {
        timeOpen = amount;
        System.out.println("The new session duration is : "+timeOpen+"s");
        try
        {
            FileWriter fw = new FileWriter(settingsInfo);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(busAmount+","+mechanicAmount+","+cleanerAmount+","+timeService+","+timeClean+","+timeOpen);
            bw.newLine();
            bw.flush();
            bw.close();
        }
        catch (IOException e)
        {
            
        }
    }
}
