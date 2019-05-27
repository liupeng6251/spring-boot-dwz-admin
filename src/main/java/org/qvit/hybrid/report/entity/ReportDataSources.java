package org.qvit.hybrid.report.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import org.qvit.hybrid.sys.entity.BaseEntity;

@Table(name = "report_data_sources")
public class ReportDataSources extends BaseEntity {

	private static final long serialVersionUID = -6705765756550913693L;

	private String name;

	@Column(name = "driver_class")
	private String driverClass;

	@Column(name = "db_url")
	private String dbUrl;

	@Column(name = "user_name")
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
