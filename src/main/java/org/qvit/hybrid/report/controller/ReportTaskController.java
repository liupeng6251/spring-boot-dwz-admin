package org.qvit.hybrid.report.controller;

import javax.servlet.http.HttpServletRequest;

import org.qvit.hybrid.report.dto.ReportDataTaskDto;
import org.qvit.hybrid.report.service.ReportDataTaskService;
import org.qvit.hybrid.utils.ResponseJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;

@Controller
@RequestMapping("/report/task")
public class ReportTaskController {

	@Autowired
	private ReportDataTaskService reportDataTaskService;

	@RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Integer pageNo, Integer pageSize, Model model, HttpServletRequest req) {
		ReportDataTaskDto dto = new ReportDataTaskDto();
		Page<ReportDataTaskDto> value = reportDataTaskService.findPage(pageNo, pageSize, dto);
		model.addAttribute("page", value);
		return "report/task/list";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add() {
		return "report/task/add";
	}
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String save(Model model, HttpServletRequest req) {
		ReportDataTaskDto dto = new ReportDataTaskDto();
		reportDataTaskService.save(dto);
		return ResponseJsonUtils.getSuccessMsg("添加成功！", "/report/datasources/list");
	}


}
