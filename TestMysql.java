import java.sql.*;

/*
 * This class is just  a test class to test the connection of MySQL with java
 */

public class TestMysql
{
  public static void main(String args[]) {
    try {
      /* Test loading driver */

      String driver = "com.mysql.jdbc.Driver";

      System.out.println( "\n=> loading driver:" );
      Class.forName( driver ).newInstance();
      System.out.println( "OK" );

      /* Test the connection */

      String url = "jdbc:mysql://localhost/test";

      System.out.println( "\n=> connecting:" );
      DriverManager.getConnection( url, "root", "password" );  //username: root //Password: password
      System.out.println( "OK" );
    }
    catch( Exception x ) {
      System.err.println( x );
    }
  }
}

