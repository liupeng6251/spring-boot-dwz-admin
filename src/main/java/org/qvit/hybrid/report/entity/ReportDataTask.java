package org.qvit.hybrid.report.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import org.qvit.hybrid.sys.entity.BaseEntity;

@Table(name = "report_data_task")
public class ReportDataTask extends BaseEntity {


    /**
	 * 
	 */
	private static final long serialVersionUID = 877360936509301946L;

	@Column(name = "task_name")
    private String taskName;
    
    @Column(name = "data_sources_id")
    private Integer dataSourcesId;
    
    @Column(name = "export_sql")
    private String exportSql;
    
    @Column(name = "report_head")
    private String reportHead;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "file_handle")
    private Integer fileHandle;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getDataSourcesId() {
		return dataSourcesId;
	}

	public void setDataSourcesId(Integer dataSourcesId) {
		this.dataSourcesId = dataSourcesId;
	}

	public String getExportSql() {
		return exportSql;
	}

	public void setExportSql(String exportSql) {
		this.exportSql = exportSql;
	}

	public String getReportHead() {
		return reportHead;
	}

	public void setReportHead(String reportHead) {
		this.reportHead = reportHead;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getFileHandle() {
		return fileHandle;
	}

	public void setFileHandle(Integer fileHandle) {
		this.fileHandle = fileHandle;
	}
}
