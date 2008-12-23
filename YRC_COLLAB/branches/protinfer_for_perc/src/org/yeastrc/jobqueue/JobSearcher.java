/**
 * 
 */
package org.yeastrc.jobqueue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.yeastrc.db.DBConnectionManager;

/**
 * @author Mike
 *
 */
public class JobSearcher {

	// private constructor
	private JobSearcher() { }

	/**
	 * Get an instance of this class
	 * @return
	 */
	public static JobSearcher getInstance() {
		return new JobSearcher();
	}
	
	/**
	 * Get the number of jobs in the queue
	 * @return
	 * @throws Exception
	 */
	public int getJobCount() throws Exception {
		int count = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			String sql = "SELECT COUNT(*) FROM YRC_JOB_QUEUE.tblJobs";
			if (this.status != null && this.status.size() > 0) {
				sql += " WHERE status IN (";
				int cnt = 0;
				for (int st : this.status) {
					if (cnt != 0) sql += ",";
					else cnt++;
					
					sql += st;
				}
				sql += ")";
			}
			
			conn = DBConnectionManager.getConnection( "yrc" );
			stmt = conn.prepareStatement( sql );
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				try {
					count = rs.getInt( 1 );
				} catch (Exception e) { ; }
			}
			
			rs.close(); rs = null;
			stmt.close(); stmt = null;
			conn.close(); conn = null;
			
		} finally {
			
			if (rs != null) {
				try { rs.close(); rs = null; } catch (Exception e) { ; }
			}

			if (stmt != null) {
				try { stmt.close(); stmt = null; } catch (Exception e) { ; }
			}
			
			if (conn != null) {
				try { conn.close(); conn = null; } catch (Exception e) { ; }
			}			
		}
		
		return count;
	}
	
	/**
	 * Get all jobs in the database with the supplied status
	 * @return
	 * @throws Exception
	 */
	public List<MSJob> getJobs() throws Exception {
		List<MSJob> jobs = new ArrayList<MSJob>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			String sql = "SELECT id FROM YRC_JOB_QUEUE.tblJobs";
			if (this.status != null && this.status.size() > 0) {
				sql += " WHERE status IN (";
				int cnt = 0;
				for (int st : this.status) {
					if (cnt != 0) sql += ",";
					else cnt++;
					
					sql += st;
				}
				sql += ") ORDER BY id DESC LIMIT " + this.index + ", 50";
			}
			
			conn = DBConnectionManager.getConnection( "yrc" );
			stmt = conn.prepareStatement( sql );
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				try {
					jobs.add( MSJobFactory.getInstance().getJob( rs.getInt( "id" ) ) );
				} catch (Exception e) { ; }
			}
			
			rs.close(); rs = null;
			stmt.close(); stmt = null;
			conn.close(); conn = null;
			
		} finally {
			
			if (rs != null) {
				try { rs.close(); rs = null; } catch (Exception e) { ; }
			}

			if (stmt != null) {
				try { stmt.close(); stmt = null; } catch (Exception e) { ; }
			}
			
			if (conn != null) {
				try { conn.close(); conn = null; } catch (Exception e) { ; }
			}			
		}
		
		
		return jobs;
	}

	/**
	 * Add the supplied status as a search constraint
	 * @param status
	 */
	public void addStatus( int status ) {
		if (this.status == null)
			this.status = new ArrayList<Integer>();
		
		if (this.status.contains( status ))
			return;
		
		this.status.add( status );
	}
	
	
	
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Get status
	 * @return
	 */
	public List<Integer> getStatus() { return this.status; }
	
	private List<Integer> status;
	private int index;
}
