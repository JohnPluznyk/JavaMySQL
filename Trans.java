import java.sql.*;
import java.lang.String;

/*
 * Trans.java is the driver class for Walkthrough.java.  This class will be used for communicating with MySQL.
 * 1. Trans.java should first prompt user with database and ask user which database would they like to select
 * 2. Ask 
 */

public class Trans{
  static String url = "jdbc:mysql://localhost/grading";  //ill just change this to games
  static String user = "root";	
  static String password = "password";

  public static boolean isNumeric(String s) {
      return java.util.regex.Pattern.matches("\\d+", s);
  }
  public static boolean exist(String table){  //check to see if a table exists
        boolean flag = false;
        try {
          String str = "show tables;";
          Class.forName( "com.mysql.jdbc.Driver" ); 		
      
          Connection cx = DriverManager.getConnection( url, user, password );
          Statement st = cx.createStatement();
          ResultSet rs = st.executeQuery(str);

          ResultSetMetaData metaData = rs.getMetaData();  //what is the points of creating this variable if it is not used?
  
          table = table.toUpperCase();

          while (rs.next()){
            if(table.equals((rs.getObject(1)+"").toUpperCase()))  //if we find table in ResultSet return True;
              flag = true;
          }
        }
        catch( Exception x ) {
            System.err.println( "table existance reading interrupted by "+x );
        }
        return flag;
  } 

  public static String [] read(String table, String column, String condition){
        // e.g., read("abc", "qty", "order by qty") for "select qty from abc order by qty;"
        String [] results = {""};
        try {
              String str = "select "+column+" from "+table+" "+condition+";";

              Class.forName( "com.mysql.jdbc.Driver" ); 		
              Connection cx = DriverManager.getConnection( url, user, password );
              Statement st = cx.createStatement();
              ResultSet rs = st.executeQuery(str);

              ResultSetMetaData metaData = rs.getMetaData();
              int numberOfColumns = metaData.getColumnCount();
              
              results = new String [numberOfColumns];
              for(int i = 0; i<numberOfColumns; i++){
                results[i]="";
              }
              
  /*
            for(int i =1 ; i<=numberOfColumns;i++)
              results[i-1]=metaData.getColumnName(i)+"\n";
  */ 
            while (rs.next()){
          for(int i =1; i<=numberOfColumns; i++)
              results[i-1]+=rs.getObject(i)+"\n";
          }
          }
          catch( Exception x ) {
              System.err.println( "remote reading interrupted by "+x );
          }
          return results;
  }

  public static boolean found(String table, String column, String condition){
        // e.g., read("abc", "qty", "order by qty") for "select qty from abc order by qty;"
        boolean flag = false;
        try {
              String str = "select "+column+" from "+table+" "+condition+";";
          Class.forName( "com.mysql.jdbc.Driver" ); 		
      
          Connection cx = DriverManager.getConnection( url, user, password );

            Statement st = cx.createStatement();

            ResultSet rs = st.executeQuery(str);

            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
              
            if (rs.next()){
                if(!(((rs.getObject(1)+"").substring(0,5)).equals("Empty")))
                      flag = true;
          }
          }
          catch( Exception x ) {
              System.out.println("Found is interrupted by "+x);
          }
          return flag;
  }

  public static void create (String table, String values){
  // need to ensure not existing. 	   
  // values = "id VARCHAR(6), quiz INT, avg decimal(3,2)";

      try {
        Class.forName( "com.mysql.jdbc.Driver" ); 
        Connection cx = DriverManager.getConnection( url, user, password );
        Statement st = cx.createStatement();
  
        String sql_drop = "DROP TABLE IF EXISTS " + table;  //if the table exists already it will drop it
        st.executeUpdate( sql_drop );  //sound pretty bad to me should warn the user before dropping data from a database

        String sql_create = "CREATE TABLE " + table + "(" + values + ")";
        st.executeUpdate( sql_create );
      }
      catch( Exception x ) {
        System.err.println("table creation is interrupted by "+ x );
      }
  }

  public static void write (String table, String [] values){
  // insertion, e.g., write ("abc", { "1-56592-488-6", "Database Management System", "paper", "100" })
  //             for "insert into abc Values ('1-56592-488-6', 'Database Management System', 'paper', 100);"
      if(values.length <1) {
        System.out.println("Nothing to insert");
        return;
      }
      try {

      Class.forName( "com.mysql.jdbc.Driver" ); 		
      
      Connection cx = DriverManager.getConnection( url, user, password );

        Statement st = cx.createStatement();

      String sql_insert = "INSERT INTO " + table + " VALUES " + "("; 
        int i=0;
        for (; i< values.length-1; i++){
        if (isNumeric(values[i])) 
          sql_insert += values[i]+",";
        else 
          sql_insert += "'"+values[i]+"',";
        }
      
        if (isNumeric( values[i])) 
          sql_insert += values[i]+");";
        else 
          sql_insert += "'"+values[i]+"');";
  
      st.executeUpdate( sql_insert );
        }
      catch( Exception x ) {
        System.err.println( "Insertion is interrupted by "+x );
      }
  }

  public static int write (String table, String column, String condition){
  // update, e.g., write("abc", "qty=qty+1", "where type = 'paper'")
  // for "UPDATE abc SET qty=qty+1 WHERE type='paper';"
      int n=0;
      try {

      Class.forName( "com.mysql.jdbc.Driver" ); 		
      
      Connection cx = DriverManager.getConnection( url, user, password );


        Statement st = cx.createStatement();

        String sql_update = "UPDATE " + table + " SET "+ column + " "+condition+";";

        n = st.executeUpdate( sql_update );

      }
      catch( Exception x ) {
        System.err.println("online writing is interrupted by "+ x );
      }
      return n;
  }
}