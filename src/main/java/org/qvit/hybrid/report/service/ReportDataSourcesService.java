package org.qvit.hybrid.report.service;

import java.util.ArrayList;
import java.util.List;

import org.qvit.hybrid.report.dto.ReportDataSourcesDto;
import org.qvit.hybrid.report.entity.ReportDataSources;
import org.qvit.hybrid.report.repository.ReportDataSourcesRepository;
import org.qvit.hybrid.utils.BaseEntityUtil;
import org.qvit.hybrid.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportDataSourcesService {

	@Autowired
	private ReportDataSourcesRepository reportDataSourcesRepository;

	public List<ReportDataSourcesDto> findList(ReportDataSourcesDto dto) {
		ReportDataSources sources = convertEntity(dto);
		List<ReportDataSources> findList = reportDataSourcesRepository.findList(sources);
		List<ReportDataSourcesDto> dtos = new ArrayList<>();
		for (ReportDataSources o : findList) {
			dtos.add(convertDto(o));
		}
		return dtos;
	}

	private ReportDataSources convertEntity(ReportDataSourcesDto dto) {
		ReportDataSources sources = new ReportDataSources();
		BeanUtils.copyProperties(dto, sources);
		return sources;
	}

	private ReportDataSourcesDto convertDto(ReportDataSources o) {
		ReportDataSourcesDto dto = new ReportDataSourcesDto();
		BeanUtils.copyProperties(o, dto);
		return dto;
	}

	public void save(ReportDataSourcesDto dto) {
		ReportDataSources convertEntity = convertEntity(dto);
		BaseEntityUtil.bindNewBaseEntity(convertEntity);
		reportDataSourcesRepository.save(convertEntity);
	}

	public ReportDataSourcesDto findById(Long id) {
		ReportDataSources sources = reportDataSourcesRepository.findById(id);
		if (sources == null) {
			return null;
		}
		return convertDto(sources);
	}

	public void update(ReportDataSourcesDto dto) {
		ReportDataSources convertEntity = convertEntity(dto);
		BaseEntityUtil.bindUpdateBaseEntity(convertEntity);
		reportDataSourcesRepository.update(convertEntity);
	}

}
