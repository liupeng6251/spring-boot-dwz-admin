package org.qvit.hybrid.report.dto;

import org.qvit.hybrid.sys.dto.BaseDto;

public class ReportDataSourcesDto extends BaseDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4460808231678826469L;

	private String name;

	private String driverClass;

	private String dbUrl;

	private String userName;
	
	private String password;
	
	private Integer port;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

}
