package com.cloudcode.framework.common.system.mvc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudcode.framework.controller.CrudController;
import com.cloudcode.framework.model.ExtTree;
import com.cloudcode.framework.model.ModelObject;
import com.cloudcode.framework.utils.statics.StaticVar;

@Controller
@RequestMapping("/resourcefile")
public class ResourceFileController extends CrudController<ModelObject> {

	private String path="/cccommon/images/icons/application";
	
	@RequestMapping(value = "/queryIconFilePathList", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody List<ExtTree> queryIconFilePathList( HttpServletRequest request) {
		String filePath = "";
		if ("root".equals(request.getParameter("node"))) {
			filePath = path;
		} else {
			if(null != request.getParameter("node"))
			filePath = request.getParameter("node").toString();
		}
		String fileRealPath = StaticVar.webappPath + filePath;
		System.out.println(fileRealPath);
		File file = new File(fileRealPath);
		List<ExtTree> extTrees = new ArrayList<ExtTree>();
		if(file.exists()){
			File[] subFile = file.listFiles();
			for (File file2 : subFile) {
				ExtTree extTree = new ExtTree();
				extTree.setId(filePath + "/" + file2.getName());
				extTree.setText(filePath + "/" + file2.getName());
				extTree.setName(file2.getName());
				if (!file2.isDirectory()) {
					extTree.setIcon(filePath + "/" + file2.getName());
					extTree.setLeaf(1);
				}
				extTrees.add(extTree);
			}
		}
		return extTrees;
	}
}
