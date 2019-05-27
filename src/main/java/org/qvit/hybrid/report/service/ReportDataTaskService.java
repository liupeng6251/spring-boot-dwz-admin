package org.qvit.hybrid.report.service;

import org.qvit.hybrid.report.dto.ReportDataTaskDto;
import org.qvit.hybrid.report.entity.ReportDataTask;
import org.qvit.hybrid.report.repository.ReportDataTaskRepository;
import org.qvit.hybrid.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public class ReportDataTaskService {

	@Autowired
	private ReportDataTaskRepository reportDataTaskRepository;

	public Page<ReportDataTaskDto> findPage(Integer pageNum, Integer pageSize, ReportDataTaskDto dto) {
		ReportDataTask task = convertEntity(dto);
		Page<ReportDataTask> findPage = reportDataTaskRepository.findPage(pageNum, pageSize, task);
		Page<ReportDataTaskDto> dtos = new Page<>();
		for (ReportDataTask o : findPage) {
			dtos.add(convertDto(o));
		}
		dtos.setPageNum(findPage.getPageNum());
		dtos.setTotal(findPage.getTotal());
		dtos.setPageSize(findPage.getPageSize());
		return dtos;
	}

	private ReportDataTaskDto convertDto(ReportDataTask o) {
		ReportDataTaskDto dto = new ReportDataTaskDto();
		BeanUtils.copyProperties(o, dto);
		return dto;
	}

	private ReportDataTask convertEntity(ReportDataTaskDto dto) {
		ReportDataTask task = new ReportDataTask();
		BeanUtils.copyProperties(dto, task);
		return task;
	}

	public void save(ReportDataTaskDto dto) {
		// TODO Auto-generated method stub
		
	}

}
