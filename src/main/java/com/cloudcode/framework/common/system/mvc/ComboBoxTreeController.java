package com.cloudcode.framework.common.system.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudcode.framework.ProjectConfig;
import com.cloudcode.framework.controller.CrudController;
import com.cloudcode.framework.dao.ModelObjectDao;
import com.cloudcode.framework.model.ModelObject;
import com.cloudcode.framework.utils.Check;
import com.cloudcode.framework.utils.Convert;

@Controller
@RequestMapping("/syscomboboxtree")
public class ComboBoxTreeController extends CrudController<ModelObject> {
	@Resource(name=ProjectConfig.PREFIX+"modelDao")
	ModelObjectDao<ModelObject> hibernateDAO;
	
	@RequestMapping(value = "/findTextById", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json")
	public @ResponseBody Object findTextById(HttpServletRequest request) {
		String id = request.getParameter("id");
		String table_name = request.getParameter("table_name");
		if (Check.isNoEmpty(id)) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (id.indexOf(",") > -1) {
				if (Check.isEmpty(table_name)) {
					map.put("text", "表名无效-指定控件取值表");
					return map;
				}
				List<Map<String, Object>> mapList = hibernateDAO
						.queryListBySql("select * from " + table_name
								+ " where id in ('" + id.replaceAll(",", "','")
								+ "')");
				mapList = Convert.dTox(mapList);
				String ids = "";
				String texts = "";
				for (Map<String, Object> map2 : mapList) {
					ids += map2.get("id") + ",";
					texts += map2.get("text") + ",";
				}
				if (Check.isNoEmpty(ids)) {
					ids = ids.substring(0, ids.length() - 1);
				}
				if (Check.isNoEmpty(texts)) {
					texts = texts.substring(0, texts.length() - 1);
				}
				map.put("id", ids);
				map.put("text", texts);
				//map.put("object", new Gson().toJson(mapList));
				return map;
			} else {
				if (Check.isEmpty(table_name)) {
					map.put("text", "表名无效-指定控件取值表");
					return map;
				}
				map = hibernateDAO.findEntity(table_name, id);
				if (map == null) {
					map = new HashMap<String, Object>();
					map.put("id", id);
					map.put("text", id);
				}
				map = Convert.dTox(map);
				//map.put("object", new Gson().toJson(map));
				return map;
			}
		} else {
			
			return new Object();
		}
	}
}
