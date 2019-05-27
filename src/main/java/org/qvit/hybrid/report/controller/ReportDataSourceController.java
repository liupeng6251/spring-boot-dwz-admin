package org.qvit.hybrid.report.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.qvit.hybrid.report.dto.ReportDataSourcesDto;
import org.qvit.hybrid.report.service.ReportDataSourcesService;
import org.qvit.hybrid.utils.ResponseJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

@Controller
@RequestMapping("/report/datasources")
public class ReportDataSourceController {

	@Autowired
	private ReportDataSourcesService reportDataSourcesService;

	@Value("${encrypt.key}")
	private String encryptKey;

	@RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model, HttpServletRequest req) {
		ReportDataSourcesDto dto = new ReportDataSourcesDto();
		List<ReportDataSourcesDto> findList = reportDataSourcesService.findList(dto);
		model.addAttribute("list", findList);
		return "report/datasources/list";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add() {
		return "report/datasources/add";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String save(Model model, HttpServletRequest req) {
		ReportDataSourcesDto dto = new ReportDataSourcesDto();
		String name = req.getParameter("name");
		String driverClass = req.getParameter("driverClass");
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String port = req.getParameter("port");
		String dbUrl = req.getParameter("dbUrl");
		dto.setDbUrl(StringUtils.trim(dbUrl));
		dto.setDriverClass(StringUtils.trim(driverClass));
		dto.setName(StringUtils.trim(name));
		dto.setPassword(StringUtils.trim(password));
		dto.setPort(Integer.parseInt(port));
		dto.setUserName(StringUtils.trim(userName));
		reportDataSourcesService.save(dto);
		return ResponseJsonUtils.getSuccessMsg("添加成功！", "/report/datasources/list");
	}

	@RequestMapping(value = "change", method = RequestMethod.GET)
	public String change(Model model, HttpServletRequest req) {
		String id = req.getParameter("id");
		ReportDataSourcesDto dto = reportDataSourcesService.findById(Long.parseLong(id));
		if (dto == null) {
			throw new RuntimeException("数据有误!");
		}
		model.addAttribute("source", dto);
		return "report/datasources/change";
	}

	@RequestMapping(value = "change", method = RequestMethod.POST)
	@ResponseBody
	public String changeSave(HttpServletRequest req) {
		String id = req.getParameter("id");

		ReportDataSourcesDto dto = reportDataSourcesService.findById(Long.parseLong(id));
		if (dto == null) {
			throw new RuntimeException("数据有误!");
		}
		String name = req.getParameter("name");
		String driverClass = req.getParameter("driverClass");
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String port = req.getParameter("port");
		String dbUrl = req.getParameter("dbUrl");
		dto.setDbUrl(StringUtils.trim(dbUrl));
		dto.setDriverClass(StringUtils.trim(driverClass));
		dto.setName(StringUtils.trim(name));
		dto.setPassword(StringUtils.trim(password));
		dto.setPort(Integer.parseInt(port));
		dto.setUserName(StringUtils.trim(userName));
		reportDataSourcesService.update(dto);
		return ResponseJsonUtils.getSuccessMsg("修改成功！", "/report/datasources/list");

	}
}
