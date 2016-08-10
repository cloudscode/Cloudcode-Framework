/**
 * Project Name:Cloudcode-Framework
 * File Name:UUIDMethodModel.java
 * Package Name:com.cloudcode.framework.template
 * Date:2016-5-11下午3:19:50
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.cloudcode.framework.template;

import java.util.List;

import com.cloudcode.framework.utils.UUID;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**freemarker 自定义函数
 * ClassName:UUIDMethodModel <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-5-11 下午3:19:50 <br/>
 * @author   cloudscode   ljzhuanjiao@Gmail.com 
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class UUIDMethodModel implements TemplateMethodModel {

	@Override
	public Object exec(List arguments) throws TemplateModelException {
		
		return UUID.generateUUID();
	}

}

