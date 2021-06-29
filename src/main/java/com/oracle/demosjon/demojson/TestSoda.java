package com.oracle.demosjon.demojson;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

/*
 * The sample demonstrates connecting to Autonomous Database using 
 * Oracle JDBC driver and UCP as a client side connection pool.
 */
public class TestSoda {  

  public static final String DB_URL="jdbc:oracle:thin:@winedemo_medium?TNS_ADMIN=/Users/bogomez/development/mywallet";
  public static final String DB_USER = "admin";
  //public String DB_PASSWORD = null;
  public static final String DB_PASSWORD = "Autonomous#2021" ;
  public static final String CONN_FACTORY_CLASS_NAME="oracle.jdbc.pool.OracleDataSource";
 
  public static void main(String args[]) throws Exception {
       
    
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
      doSoda(conn);
    } catch (SQLException e) {
        System.out.println("ADBQuickStart - "
          + "doSQLWork()- SQLException occurred : " + e.getMessage());
    } 
    
  }
/* Invokes controller to make doSoda example method */
 private static void doSoda(Connection conn) throws SQLException {

    System.out.println("Starting doSoda");
  
 //   conn.setAutoCommit(false);
    SodaController sc = new SodaController (conn);
    sc.doSodaWork();
  } // End of doSQLWork
  
} // End of ADBQuickStart