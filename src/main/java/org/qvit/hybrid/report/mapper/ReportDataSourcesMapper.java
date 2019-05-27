package org.qvit.hybrid.report.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.qvit.hybrid.report.entity.ReportDataSources;

import tk.mybatis.mapper.common.Mapper;

public interface ReportDataSourcesMapper extends Mapper<ReportDataSources>{

    @Select("<script>"                                        
            +" SELECT id,name,driver_class driverClass,db_url dbUrl,user_name userName,PASSWORD,PORT,create_date createDate,update_date updateDate,VERSION "
    		+" FROM report_data_sources "
            + " where 1=1 "
            + "<if test='source.id != null'>"
            + " and id=#{source.id} "
            + "</if>"
            + "<if test='source.name != null'>"
            + " and name=#{source.name}"
            + "</if> "
            + " order by id  "
            + "</script>"
            )
    List<ReportDataSources> findPage(@Param("source") ReportDataSources source);

}
