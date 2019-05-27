package org.qvit.hybrid.report.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.qvit.hybrid.report.entity.ReportDataTask;

import tk.mybatis.mapper.common.Mapper;

public interface ReportDataTaskMapper extends Mapper<ReportDataTask>{

    @Select("<script>"                                        
            +" SELECT id,task_name taskName,data_sources_id dataSourcesId,export_sql exportSql,report_head reportHead,STATUS,file_handle fileHandle,create_date createDate,update_date updateDate,VERSION "
            + " from report_data_task "
            + " where 1=1 "
            + "<if test='task.id != null'>"
            + " and id=#{task.id} "
            + "</if>"
            + "<if test='task.taskName != null'>"
            + " and task_name=#{task.taskName}"
            + "</if> "
            + "<if test='task.dataSourcesId != null'>"
            + " and data_sources_id=#{task.dataSourcesId} "
            + "</if> "
            + "<if test='task.reportHead != null'>"
            + " and report_head=#{task.reportHead} "
            + "</if> "
            + " order by id  "
            + "</script>"
            )
    List<ReportDataTask> findPage(@Param("task") ReportDataTask task);


}
