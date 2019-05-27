package org.qvit.hybrid.report.dto;

import org.qvit.hybrid.enums.StartStatus;
import org.qvit.hybrid.sys.dto.BaseDto;

public class ReportDataTaskDto extends BaseDto{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2338963423319636813L;

	private String taskName;
    
    private Integer dataSourcesId;
    
    private String exportSql;
    
    private String reportHead;
    
    private StartStatus status;
    
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



	public StartStatus getStatus() {
		return status;
	}

	public void setStatus(StartStatus status) {
		this.status = status;
	}

	public Integer getFileHandle() {
		return fileHandle;
	}

	public void setFileHandle(Integer fileHandle) {
		this.fileHandle = fileHandle;
	}
    
    
}
