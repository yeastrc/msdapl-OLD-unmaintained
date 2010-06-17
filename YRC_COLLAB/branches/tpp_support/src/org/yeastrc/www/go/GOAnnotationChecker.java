/**
 * 
 */
package org.yeastrc.www.go;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.yeastrc.db.DBConnectionManager;

/**
 * GOAnnotationChecker.java
 * @author Vagisha Sharma
 * Jun 11, 2010
 * 
 */
public class GOAnnotationChecker {

	private GOAnnotationChecker () {}
	
	public static boolean isProteinAnnotated(int nrseqProteinId) throws SQLException {
		
		// Get our connection to the database.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sqlStr = "SELECT proteinID "+
							"FROM GOProteinLookup "+
							"WHERE proteinID="+nrseqProteinId+
							" LIMIT 1";
			
			conn = DBConnectionManager.getConnection("go");	
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sqlStr);
			
			
			if (rs.next()) {
				return true;
			}
			else
				return false;
			
		} finally {

			// Always make sure result sets and statements are closed,
			// and the connection is returned to the pool
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}
	
	public static List<Integer> getAnnotatedProteins (List<Integer> proteinIds) throws SQLException {
		
		// Get our connection to the database.
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Integer> annotated = new ArrayList<Integer>(proteinIds.size());
		
		try {
			String sqlStr = "SELECT proteinID "+
							"FROM GOProteinLookup "+
							"WHERE proteinID=?"+
							" LIMIT 1";
			
			conn = DBConnectionManager.getConnection("go");	
			stmt = conn.prepareStatement(sqlStr);
			
			for(Integer proteinId: proteinIds) {
				stmt.setInt(1, proteinId);
				rs = stmt.executeQuery();
				
				if (rs.next()) {
					annotated.add(proteinId);
				}
				else {
					System.out.println("No annotations found for "+proteinId);
				}
			}
			
		} finally {

			// Always make sure result sets and statements are closed,
			// and the connection is returned to the pool
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
		
		return annotated;
	}
}
