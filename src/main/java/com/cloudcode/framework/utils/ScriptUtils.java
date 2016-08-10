/**
 * Project Name:Cloudcode-Framework
 * File Name:ScriptUtils.java
 * Package Name:com.cloudcode.framework.utils
 * Date:2016-4-29下午2:41:50
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.cloudcode.framework.utils;

import java.io.FileNotFoundException;

import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

/**
 * ClassName:ScriptUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-4-29 下午2:41:50 <br/>
 * @author   cloudscode   ljzhuanjiao@Gmail.com 
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class ScriptUtils {

	public static String ExcuteJs1(String paras) throws FileNotFoundException, ScriptException, NoSuchMethodException 
    {
        ScriptEngineManager engineManager = new ScriptEngineManager();  
        ScriptEngine engine = engineManager.getEngineByName("JavaScript"); 
        engine.eval(new java.io.FileReader("data/test.js"));  
        Invocable inv = (Invocable)engine;  
        Object a = inv.invokeFunction("test", paras );  
        
        return a.toString();
    }
	
	public static String ExcuteJs2(String condition,String param) throws ScriptException,
    FileNotFoundException, NoSuchMethodException 
    {
        ScriptEngineManager engineManager = new ScriptEngineManager();  
        ScriptEngine engine = engineManager.getEngineByName("JavaScript"); 
        ScriptContext scriptContext=new SimpleScriptContext();
        scriptContext.setAttribute("param", param, 100);
        String val = engine.eval(condition, scriptContext).toString();
        return val;
    }
}

