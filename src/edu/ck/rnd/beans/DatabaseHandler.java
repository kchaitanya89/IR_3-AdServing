package edu.ck.rnd.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class DatabaseHandler {
	
	static String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
	//CHANGEME: change the userid and password to match your database user's account
    static String userid = "hr";
    static String password = "hr";
    static Connection conn;
    
    public static Connection getDBConnection() throws SQLException {
    	
    	try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
        conn=DriverManager.getConnection(jdbcUrl,userid,password);
        return conn;
    }
    
    public static HashMap<String,Double> getBidAmts(List<AdDocs> docs){
    	String sqlBase = "SELECT keylink_id,bid_amount FROM link_master WHERE keylink_id IN (";
    	for( AdDocs doc : docs){
    	    sqlBase += doc.getKEYLINK_ID() + ",";
    	}
    	sqlBase = sqlBase.substring(0, sqlBase.length()-1) + ") order by bid_amount desc";
    	System.out.println(sqlBase);
    	HashMap<String, Double> results = new HashMap<String,Double>();
    	Statement stmt = null;
    	ResultSet rs = null;
    	try{
    		getDBConnection();
    		stmt = conn.createStatement();
    		stmt.execute(sqlBase);
    		rs = stmt.getResultSet();
    		while(rs.next()){
    			results.put(new Integer(rs.getInt(1)).toString(), rs.getDouble(2));
    		}
    	} catch (SQLException e) {
			e.printStackTrace();
		}finally{
    		try{
    			if(stmt!=null){
    				stmt.close();
    			}
    		}catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    	System.out.println(results);
    	closeConn();
    	return results;
    }
    
    public static boolean incrementClickCount(long keylinkid) throws SQLException {
    	getDBConnection();
        String query;
        PreparedStatement pstmt=null;
        ResultSet rset;
        query= "UPDATE LINK_MASTER SET LINK_CLICKS=LINK_CLICKS+1 WHERE KEYLINK_ID=?";
        pstmt=conn.prepareStatement(query);
        
        pstmt.setLong(1, keylinkid);
        
        int m;
        m = pstmt.executeUpdate();
        
        query= "SELECT KEYWORD_NAME FROM LINK_MASTER WHERE KEYLINK_ID=?";
        
        pstmt=conn.prepareStatement(query);
        
        pstmt.setLong(1,keylinkid);
        rset=pstmt.executeQuery();
        String KeyWordName = "";
        if(rset.next()){
        	KeyWordName=rset.getString(1);	
        }
        
        
        Random random=new Random();
        int x=random.nextInt(1000);
        
        query= "UPDATE KEYWORD_MASTER SET CLICKS_COUNT=CLICKS_COUNT+1 WHERE KEYWORD_NAME=?";
        pstmt=conn.prepareStatement(query);
        
        pstmt.setString(1, KeyWordName);
        
        m=pstmt.executeUpdate();
        
        if (x%6==0) {
        
            query= "SELECT CEIL(CTR/100*CLICKS_COUNT),CLICKS_COUNT FROM KEYWORD_MASTER WHERE KEYWORD_NAME=?";
        
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1, KeyWordName);
        
            rset=pstmt.executeQuery();
            int y=0;
            int z = 0;
            if(rset.next()){
            	y=rset.getInt(1);
                z=rset.getInt(2);
            }
            
            float target=(y+1)/z;
        
            query= "UPDATE KEYWORD_MASTER SET CTR=? WHERE KEYWORD_NAME=?";
            pstmt=conn.prepareStatement(query);
         
            pstmt.setFloat(1, target);
            pstmt.setString(2, KeyWordName);
         
            m=pstmt.executeUpdate();
         
        }
        closeConn();
        return true;
    }
    
    public static void closeConn(){
    	try{
    		if(conn!=null){
    			conn.close();
    		}
    	}catch (SQLException e) {
			e.printStackTrace();
		}
    }

}
