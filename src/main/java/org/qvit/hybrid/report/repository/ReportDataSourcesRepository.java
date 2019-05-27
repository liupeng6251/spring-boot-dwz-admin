package org.qvit.hybrid.report.repository;

import java.util.List;

import org.qvit.hybrid.report.entity.ReportDataSources;
import org.qvit.hybrid.report.mapper.ReportDataSourcesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDataSourcesRepository {

    @Autowired
    private ReportDataSourcesMapper reportDataSourcesMapper;

    public void save(ReportDataSources dto) {
    	reportDataSourcesMapper.insert(dto);
    }

    public ReportDataSources findById(Long id) {
        return reportDataSourcesMapper.selectByPrimaryKey(id);
    }

    public void update(ReportDataSources sources) {
    	reportDataSourcesMapper.updateByPrimaryKey(sources);        
    }

    public List<ReportDataSources> findAll() {
       return reportDataSourcesMapper.selectAll();
    }

    public List<ReportDataSources> findList(ReportDataSources sources) {
        return reportDataSourcesMapper.findPage(sources);
    }

    public void deleteById(Long id) {
    	reportDataSourcesMapper.deleteByPrimaryKey(id);        
    }

}
