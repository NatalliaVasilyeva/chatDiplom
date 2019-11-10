package com.bsu.nvasilyeva.controller;

import com.bsu.nvasilyeva.service.AuthenticationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AdminController {

	@Autowired
	AuthenticationLogService authenticationLogService;

	/**
	 * After request to this address finds log information
	 * @return page @log.jsp with log information
	 */

	@RequestMapping("/log")
	public ModelAndView showLog() {
		return new ModelAndView("log", "logs", authenticationLogService.list());
	}
}
