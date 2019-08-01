package com.mss.backup;



public class DumpConfig {

	/**
	 * mysql安装bi
	 */
	private String installPath;
	
	/**
	 * 
	 */
	private String backupPath;
	
	/**
	 * 
	 */
	private String jdbcUserName;
	
	/**
	 * 
	 */
	private String jdbcPassword;
	
	/**
	 * 
	 */
	private String jdbcPort;
	
	/**
	 * 
	 */
	private String mysqlDataBase;

	public String getInstallPath() {
		return installPath;
	}

	public void setInstallPath(String installPath) {
		this.installPath = installPath;
	}

	public String getBackupPath() {
		return backupPath;
	}

	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
	}

	public String getJdbcUserName() {
		return jdbcUserName;
	}

	public void setJdbcUserName(String jdbcUserName) {
		this.jdbcUserName = jdbcUserName;
	}

	public String getJdbcPassword() {
		return jdbcPassword;
	}

	public void setJdbcPassword(String jdbcPassword) {
		this.jdbcPassword = jdbcPassword;
	}

	public String getJdbcPort() {
		return jdbcPort;
	}

	public void setJdbcPort(String jdbcPort) {
		this.jdbcPort = jdbcPort;
	}

	public String getMysqlDataBase() {
		return mysqlDataBase;
	}

	public void setMysqlDataBase(String mysqlDataBase) {
		this.mysqlDataBase = mysqlDataBase;
	}
}
