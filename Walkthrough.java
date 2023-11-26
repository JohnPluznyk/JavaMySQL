import java.util.Scanner;
import java.sql.*;
//import java.io.*;
import java.lang.String;
//import java.util.InputMismatchException;

/*
 * @John Pluznyk
 * 1. Keep accepting user’s command (or selection) until he/she chooses to stop.
 *    a. Ask user what they would like to do in database games
 * 2. Use Tans class to comunnicate with MySQL class
 *    a. Trans class should one be able to create a new table
 *    b. Inset a row
 *    c. select some records usig where in one column and display them in the application side
 * 
 *  so far so good need to check functionality of each function
 *  need to understand exactly what all functions should entail
 *  will try to complete function tommorw morning and start testing
 */

public class Walkthrough {
    static Scanner kb = new Scanner(System.in);
    //Trans trans = new Trans();
    Trans trans = new Trans();
    static String url = Trans.url;
    static String user = Trans.user;
    static String password = Trans.password;
    

     public Walkthrough(){  //simple constructor takes user to input
        input();
    }
    public static void main(String[] args) {
        System.out.println("Please enter in an option 1-4: ");
        System.out.println("[1] Create table");
        System.out.println("[2] Insert new row");
        System.out.println("[3] Select records");
        System.out.println("[4] Exit");
        new Walkthrough();
    }
/////////////////////////////////Input///////////////////////////////////
    public void input(){
        System.out.print("Please enter in a number 1-4: ");
        int x = 0;

        try{
            x = kb.nextInt();
        }
        catch(Exception e){
            x = 0;//write now I just want to do nothing and catch it im not sure if this will make it hit default statement still;
        }
        
        switch(x){
            case 1:
                System.out.println("option 1\n");
                kb.nextLine();
                create_table();
                break;
            case 2:
                System.out.println("option 2\n");
                kb.nextLine();
                insert_row();
                break;
            case 3:
                 System.out.println("option 3\n");
                 kb.nextLine();
                 select_record();
                 break;
            case 4:
                System.out.println("System exit\n");
                kb.nextLine();
                System.exit(0);
                break;
            default:
                System.out.println("Error! Please input a valid number (1-4)\n");
                kb.nextLine(); //this clears the buffer if a non int value is entered
                input();
        }
    }

/////////////////////////////////Create_Table////////////////////////////////////////
    public void create_table(){
        System.out.print("Please enter a name for the table: ");
        String tableName = kb.nextLine();  //take in input for the tableName probably shouldn't allow spaces right????

        if(Trans.exist(tableName)){  //if the table exists
            System.out.println("Error! Table already exists!\n");  //print out Error
            input();//create the actual table
        }

        System.out.print("Please enter in variables: ");  //ask user for varibles
        String variables = kb.nextLine();
        Trans.create(tableName, variables);  //create the actual table
        System.out.println("The table " + "\"" + tableName + "\" has been crated.\n");
        input();  //take user back to input menu
    }
//////////////////////////////////Insert Row////////////////////////////////////////
    public void insert_row(){
        System.out.println("Please select table you would like to add to: ");
        //print out all tables with a tab
        /*
         *      print out all tables like this with a tab \t
         */
        System.out.print("Table: ");
        String table = kb.nextLine();

        if(!Trans.exist(table)){ //if the table doesn't exist take us back to input
            System.out.println("Error!!! Table does not exist!");
            input();
        }
        //ok need to figure out a method call to fugure out how to get exact number of attributes without user input
        //System.out.println("How many attributes: ");  //this is bad b/c user can enter in as many attributes as they want
       
        int num_attributes = 0;
        try {
            String str = "select * from " + table + ";";
            Class.forName( "com.mysql.jdbc.Driver" );

            Connection cx = DriverManager.getConnection(url, user, password );
            Statement st = cx.createStatement();
            ResultSet rs = st.executeQuery(str);

            ResultSetMetaData metaData = rs.getMetaData();
            num_attributes = metaData.getColumnCount();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("\nError Could not get number of attributes from " + table);
            System.exit(-1);
        }

        String[] attributes = new String[num_attributes];
        for(int i = 0; i < num_attributes; i++){
            //TODO: Stuff
            int x = i+1;
            System.out.print("Enter attribute [" + x +"]: ");
            attributes[i] = kb.nextLine();
        }

        System.out.println("Attributes entered!\n please check table!\n");
        Trans.write(table, attributes);  //write entry to the table
        input();
    }
//////////////////////////Select Record//////////////////////
    public void select_record(){
        System.out.println("Please select table you would like to get information from: ");
        //print out all tables with a tab
        /*
         *  //TODO:   
         *  print out all tables like this with a tab \t
         */
        System.out.print("Table: ");
        String table = kb.nextLine();

        if(!Trans.exist(table)){ //if the table doesn't exist take us back to input
            System.out.println("Error!!! Table does not exist!");
            input();
        }
        //maybe show which columns the user can select from
        System.out.print("Please select which column you would like: ");
        String column = kb.nextLine();

        System.out.print("\nEnter condtions: ");
        String condition = kb.nextLine();
        
        String[] information = Trans.read(table, column, condition);
        //now I just need to print out data
        for(int i = 0; i < information.length; i++){
            System.out.println(information[i]);
        }
        System.out.println();
        input();
    }
}
        /*
         * SELECT COUNT(*) AS `columns`
         * FROM `information_schema`.`columns`
         * WHERE `table_schema` = 'database_name' 
         * AND `table_name` = 'tablename'
        */