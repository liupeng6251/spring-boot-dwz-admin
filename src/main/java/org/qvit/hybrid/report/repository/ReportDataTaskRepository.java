package org.qvit.hybrid.report.repository;

import java.util.List;

import org.qvit.hybrid.report.entity.ReportDataTask;
import org.qvit.hybrid.report.mapper.ReportDataTaskMapper;
import org.qvit.hybrid.utils.PageHelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

@Repository
public class ReportDataTaskRepository {

    @Autowired
    private ReportDataTaskMapper reportDataTaskMapper;

    public void save(ReportDataTask dto) {
    	reportDataTaskMapper.insert(dto);
    }

    public ReportDataTask findById(Long id) {
        return reportDataTaskMapper.selectByPrimaryKey(id);
    }

    public void update(ReportDataTask task) {
    	reportDataTaskMapper.updateByPrimaryKey(task);        
    }

    public List<ReportDataTask> findAll() {
       return reportDataTaskMapper.selectAll();
    }

    public Page<ReportDataTask> findPage(Integer pageNum, Integer pageSize, ReportDataTask task) {
    	PageHelperUtils.startPage(pageNum, pageSize);
		return (Page<ReportDataTask>) reportDataTaskMapper.findPage(task);
    }
    public void deleteById(Long id) {
    	reportDataTaskMapper.deleteByPrimaryKey(id);        
    }

}
