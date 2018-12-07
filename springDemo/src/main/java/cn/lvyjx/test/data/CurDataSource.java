package cn.lvyjx.test.data;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Hashtable;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class CurDataSource implements DataSource {

	//配置文件中
	public Boolean whetherLocal = true;
	
	public  static ThreadLocal<String> curSchema = new ThreadLocal<String>();
	public static ThreadLocal<String> dsName = new ThreadLocal<String>();
	public static Hashtable<String,DataSource> sourceMap = new Hashtable<String,DataSource>();
	protected static DataSource dataSource = null;
	
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		dataSource.setLogWriter(out);
		
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		dataSource.setLoginTimeout(seconds);

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return dataSource.getParentLogger();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return dataSource.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return dataSource.isWrapperFor(iface);
	}

	@Override
	public Connection getConnection() throws SQLException {
		Connection conn = dataSource.getConnection();
		java.sql.Statement reset = null;//先清理
		try{
			reset = conn.createStatement();
			String sub = "myy_";
			reset.execute("use "+sub+"test"); //一个空库
		}finally{
			if(reset != null){
				try{
					reset.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		//切库
		if(whetherLocal != null && (!whetherLocal)){
			String schema = curSchema.get();
			if(schema == null || schema.trim().length() == 0){
				schema = "test";
			}
			schema = formatSQL(schema);
			schema = "myy_"+schema;
			String sql = "use" + schema;
			
			java.sql.PreparedStatement pstm = null;
			try{
				boolean autoCommit = conn.getAutoCommit();
				conn.setAutoCommit(false);
				pstm = conn.prepareStatement(sql);
				pstm.executeUpdate();
				conn.commit();
				conn.setAutoCommit(autoCommit);
			}catch(Exception e){
				e.printStackTrace();
				if(conn != null){
					conn.close();
				}
			}finally{
				if(pstm != null){
					pstm.close();
				}
			}
		}
		return conn;
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
	
		return dataSource.getConnection(username, password);
	}

	
	public String formatSQL(String param){
		if(param == null || param.trim().length() == 0){
			param = "";
		}
		param = param.replaceAll(";","");
		param = param.replaceAll("'","");
		param = param.replaceAll("\"","");
		return param.trim();
	}
	
	public void setDataSource(DataSource ds){
		dataSource = ds;
	}
}
