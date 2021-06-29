package com.oracle.demosjon.demojson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import oracle.ucp.jdbc.PoolDataSourceFactory;
import oracle.ucp.jdbc.PoolDataSource;

/*
 * The sample demonstrates connecting to Autonomous Database using 
 * Oracle JDBC driver and UCP as a client side connection pool.
 */
public class TestConnection {  

  public static final String DB_URL="jdbc:oracle:thin:@winedemo_medium?TNS_ADMIN=/Users/bogomez/development/mywallet";
  public static final String DB_USER = "admin";
  //public String DB_PASSWORD = null;
  public static final String DB_PASSWORD = "Autonomous#2021" ;
  public static final String CONN_FACTORY_CLASS_NAME="oracle.jdbc.pool.OracleDataSource";
 
  public static void main(String args[]) throws Exception {
       
    // For security purposes, you must enter the password through the console 
    /*
    try {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Enter the password for Autonomous Database: ");
      DB_PASSWORD = scanner.nextLine();
    }
    catch (Exception e) {    
       System.out.println("ADBQuickStart - Exception occurred : " + e.getMessage());
       System.exit(1);
    } 
    */
    
    // Get the PoolDataSource for UCP
    PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();

    // Set the connection factory first before all other properties
    pds.setConnectionFactoryClassName(CONN_FACTORY_CLASS_NAME);
    pds.setURL(DB_URL);
    pds.setUser(DB_USER);
    pds.setPassword(DB_PASSWORD);
    pds.setConnectionPoolName("JDBC_UCP_POOL");

    // Default is 0. Set the initial number of connections to be created
    // when UCP is started.
    pds.setInitialPoolSize(5);

    // Default is 0. Set the minimum number of connections
    // that is maintained by UCP at runtime.
    pds.setMinPoolSize(5);

    // Default is Integer.MAX_VALUE (2147483647). Set the maximum number of
    // connections allowed on the connection pool.
    pds.setMaxPoolSize(20);


    // Get the database connection from UCP.
    try (Connection conn = pds.getConnection()) {
      System.out.println("Available connections after checkout: "
          + pds.getAvailableConnectionsCount());
      System.out.println("Borrowed connections after checkout: "
          + pds.getBorrowedConnectionsCount());       
      // Perform a database operation
      doSQLWork(conn);
    } catch (SQLException e) {
        System.out.println("ADBQuickStart - "
          + "doSQLWork()- SQLException occurred : " + e.getMessage());
    } 
    
    System.out.println("Available connections after checkin: "
        + pds.getAvailableConnectionsCount());
    System.out.println("Borrowed connections after checkin: "
        + pds.getBorrowedConnectionsCount());
  }
 /*
 * Selects 20 rows from the SH (Sales History) Schema that is the accessible to all 
 * the database users of autonomous database. 
 */
 private static void doSQLWork(Connection conn) throws SQLException {

    String queryStatement = "select employeeid, employeename from EMP";
    System.out.println("\n Query is " + queryStatement);
    
    conn.setAutoCommit(false);
    // Prepare a statement to execute the SQL Queries.
    try (Statement statement = conn.createStatement(); 
      // Select 20 rows from the CUSTOMERS table from SH schema. 
      ResultSet resultSet = statement.executeQuery(queryStatement)) {

        System.out.println("EMP ID" + "  " + "EMP NAME");
        System.out.println("---------------------");

        while (resultSet.next()) {
          System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
        }
      System.out.println("\nCongratulations! You have successfully used Oracle Autonomous Database\n");
      } 
  } // End of doSQLWork
  
} // End of ADBQuickStart