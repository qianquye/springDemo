package cn.lvyjx.test.antiattack;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class CurConnectionWrapper implements Connection {

	/**
	 * 每个机构最大允许并发数，
	 */
	private static int schemaMaxConcurrent = 12;
	
	/**
	 * 机构限流阀map
	 */
	private static ConcurrentHashMap<String, AtomicInteger> schemaConcurrentMap = 
			new ConcurrentHashMap<String, AtomicInteger>();
	
	/**
	 * 当前活跃（未close)connection列表的map
	 */
	private static ConcurrentHashMap<CurConnectionWrapper, String> connectioMap = 
			new ConcurrentHashMap<CurConnectionWrapper, String>();
	
	private static long lastClear = System.currentTimeMillis();
	
	private String curSchema = null;
	
	private Connection wrappedConnection = null;
	
	//private final StackTraceElement[] stackTraceElement;
	//当前限流阀，因为可能旧限流阀已经被清空，直接减去当前流阀数值不准确，出现负数
	private AtomicInteger value = null;
	
	public static void main(String[] args) {
		
	}
	
	
	
	public CurConnectionWrapper(String schema) throws SQLException{
		this.curSchema = schema;
		schemaConcurrentIn(schema);
		
		connectioMap.put(this, schema);
	}
	
	
	
	
	private void schemaConcurrentIn(String schema) throws SQLException {
		// TODO Auto-generated method stub
		if(System.currentTimeMillis() - lastClear > 1000* 60 * 3){
			schemaConcurrentMap.clear();
			lastClear = System.currentTimeMillis();
		}
		
		if(schemaConcurrentMap.contains(schema) == false){
			value = new AtomicInteger(1);
			schemaConcurrentMap.put(schema, value);
			return;
		}
		
		value = schemaConcurrentMap.get(schema);
		int conCurrent = value.addAndGet(1);
		if(conCurrent > schemaMaxConcurrent){
			value.addAndGet(-1);
			throw new SQLException("schema overflow");
		}
	}


	protected void schemaConcurrentOut(String schema){
		AtomicInteger currentValue = schemaConcurrentMap.get(schema);
		if(currentValue != null){ //当前限流阀，可能不是记录中的限流阀，因为可能已经清空
			int conCurrent = currentValue.addAndGet(-1);
		}else{
			
		}
	}

	
	
	public static int getSchemaMaxConcurrent() {
		return schemaMaxConcurrent;
	}

	public static void setSchemaMaxConcurrent(int schemaMaxConcurrent) {
		CurConnectionWrapper.schemaMaxConcurrent = schemaMaxConcurrent;
	}

	public static ConcurrentHashMap<String, AtomicInteger> getSchemaConcurrentMap() {
		return schemaConcurrentMap;
	}

	public static void setSchemaConcurrentMap(
			ConcurrentHashMap<String, AtomicInteger> schemaConcurrentMap) {
		CurConnectionWrapper.schemaConcurrentMap = schemaConcurrentMap;
	}

	public static ConcurrentHashMap<CurConnectionWrapper, String> getConnectioMap() {
		return connectioMap;
	}

	public static void setConnectioMap(
			ConcurrentHashMap<CurConnectionWrapper, String> connectioMap) {
		CurConnectionWrapper.connectioMap = connectioMap;
	}

	public String getCurSchema() {
		return curSchema;
	}

	public void setCurSchema(String curSchema) {
		this.curSchema = curSchema;
	}

	public Connection getWrappedConnection() {
		return wrappedConnection;
	}

	public void setWrappedConnection(Connection wrappedConnection) {
		this.wrappedConnection = wrappedConnection;
	}

	public AtomicInteger getValue() {
		return value;
	}

	public void setValue(AtomicInteger value) {
		this.value = value;
	}




	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Statement createStatement() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void commit() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws SQLException {
		try{
			schemaConcurrentOut(this.curSchema); //并发数减一
			connectioMap.remove(this); //活跃列表清除 关闭连接
		}finally{
			wrappedConnection.close();
		}

	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isReadOnly() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCatalog(String catalog) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCatalog() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHoldability(int holdability) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Statement createStatement(int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSchema() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
