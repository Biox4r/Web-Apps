/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbConnection;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;



/**
 * Abstract class for connection as singleton class
 * 
 */
public abstract class ConnectionSingl {

    
    public OracleConnection connection ;
    public OracleResultSet rs ;
    public OraclePreparedStatement prSt ;

    
    //Acquiring connection from connection pool
    public ConnectionSingl() {
        connection =  Jdbc.getInstance().getConnection();
    }
}
