package com.cloudcode.framework.common.system.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cloudcode.framework.controller.CrudController;
import com.cloudcode.framework.model.ModelObject;

@Controller
@RequestMapping("/systools")
public class ToolsController extends CrudController<ModelObject> {
	
	
	@RequestMapping(value = "treeselect")
	public ModelAndView treeselect() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("classpath:com/cloudcode/framework/common/system/ftl/treeselect.ftl");
		return modelAndView;
	}
	
	@RequestMapping(value = "picselect")
	public ModelAndView picselect() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("classpath:com/cloudcode/framework/common/system/ftl/picselect.ftl");
		return modelAndView;
	}
	
	@RequestMapping(value = "bigpicselect")
	public ModelAndView bigpicselect() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("classpath:com/cloudcode/framework/common/system/ftl/bigpicselect.ftl");
		return modelAndView;
	}
	
	
}
