package com.cloudcode.framework.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class Sorter implements Serializable{

	private static final long serialVersionUID = -6373394706784489139L;

	/**
     * 排序类型
     */
    private static enum Type {

        /**
         * 升序
         */
        ASCENDING,

        /**
         * 降序
         */
        DESCENDING
    }

    /**
     * 排序指示对象
     */
    public static class Sort implements Serializable {

		private static final long serialVersionUID = -1599386645244047497L;

		public String property;

		public Type type;

		/**
		 * 用默认构造器
		 * @param property
		 * @param type
		 */
        private Sort(String property, Type type) {
            this.property = property;
            this.type = type;
        }

        public boolean isAscending() {
            return type == Type.ASCENDING;
        }

        @Override
        public String toString() {
            return property + " " + (type == Type.ASCENDING ? "asc" : "desc");
        }
    }

    private List<Sort> sorts = new ArrayList<Sort>();

    public List<Sort> getSorts() {
		return sorts;
	}

	/**
     * 升序
     * @param property sort property
     * @return this
     */
    public Sorter asc(String property) {
    	for (Sort sort: sorts) {
    		if (StringUtils.compare(property, sort.property)) {
    			sorts.remove(sort);
    			break;
    		}
    	}
        sorts.add(new Sort(property, Type.ASCENDING));
        return this;
    }

    /**
     * 降序
     *
     * @param property sort property
     * @return this
     */
    public Sorter desc(String property) {
    	for (Sort sort: sorts) {
    		if (StringUtils.compare(property, sort.property)) {
    			sorts.remove(sort);
    			break;
    		}
    	}
        sorts.add(new Sort(property, Type.DESCENDING));
        return this;
    }
    /**
     * 根据关键字查询 [测试通过 - 使用 solr内部转换机制]
     * @param <T>
     * @param server    solr客户端
     * @param keyword    搜索关键字
     * @param pageNum    当前页码
     * @param pageSize    每页显示的大小
     * @param clzz        对象类型
     * @return
     */
    public static  void queryHighter(SolrServer server,String solrql,
            int pageNum,int pageSize,List<String> hlField, String preTag,String postTag,Class<T> clzz,String idName){
        SolrQuery query = new SolrQuery();
        query.setQuery(solrql);
        //设置高亮显示
        query.setHighlight(true);
        //添加高亮域
        for(String hlf : hlField){
            query.addHighlightField(hlf);
        }
        //渲染标签
        query.setHighlightSimplePre(preTag);
        query.setHighlightSimplePost(postTag);
        //分页查询
        query.setStart((pageNum-1)*pageSize);
        query.setRows(pageSize);
        QueryResponse response = null;
        try {
            response = server.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
            //return null;
        }
        
        //查询到的记录总数
        long totalRow = Long.valueOf(response.getResults().getNumFound()).intValue();
        //查询结果集
        List<T> items = new ArrayList<T>();
        
        //查询结果集
        SolrDocumentList solrDocuments = response.getResults();
        try {
            Object obj = null;
            Method m = null;
            Class<?> fieldType = null;
            Map<String,Map<String,List<String>>> highlightMap=response.getHighlighting();
            for(SolrDocument solrDocument : solrDocuments) {
                    obj = clzz.newInstance();
                    Collection<String> fieldNames = solrDocument.getFieldNames();            //得到所有的属性名
                    for (String fieldName : fieldNames) {
                        //需要说明的是返回的结果集中的FieldNames()比类属性多
                        Field[] filedArrays = clzz.getDeclaredFields();                        //获取类中所有属性
                        for (Field f : filedArrays) {    
                            //如果实体属性名和查询返回集中的字段名一致,填充对应的set方法
                            if(f.getName().equals(fieldName)){
                                
                                //获取到的属性名
                                //private java.lang.String com.test.model.Article.id
                                f = clzz.getDeclaredField(fieldName);    
                                
                                //属性类型
                                //private java.lang.String com.test.model.Article.id
                                fieldType = f.getType();    
                                
                                //构造set方法名  setId
                                String dynamicSetMethod = dynamicMethodName(f.getName(), "set");
                                
                                //获取方法
                                //public void com.test.model.Article.setId(java.lang.String)
                                m = clzz.getMethod(dynamicSetMethod, fieldType);
                                
                                //获取到的值
                               // LOG.info(f.getName() + "-->" + dynamicSetMethod+ "=" + fieldType.cast(solrDocument.getFieldValue(fieldName)));
                                
                                //获取fieldType类型
                                fieldType = getFileType(fieldType);
                                
                                //获取到的属性
                                m.invoke(obj, fieldType.cast(solrDocument.getFieldValue(fieldName)));
                                
                                for(String hl : hlField){
                                    if(hl.equals(fieldName)){
                                        String idv = solrDocument.getFieldValue(idName).toString();
                                        List<String> hlfList=highlightMap.get(idv).get(fieldName);
                                        if(null!=hlfList && hlfList.size()>0){
                                            //高亮添加
                                            m.invoke(obj, fieldType.cast(hlfList.get(0)));
                                        }else{
                                            //正常添加
                                            m.invoke(obj, fieldType.cast(solrDocument.getFieldValue(fieldName)));
                                        }
                                    }
                                }
                                
                                
                            }
                            
                        }
                        
                    }
                    items.add(clzz.cast(obj));
            }
            
        } catch (Exception e) {
           // LOG.error("highlighter query error." + e.getMessage(), e);
            e.printStackTrace();
        }

        //填充page对象
      //  return new Page<T>(pageNum, pageSize, totalRow, items);
    }
    
    public static String dynamicMethodName(String name, String dynamic) {
    	String methodName ="";
    	if(name.length() > 0){
    		methodName = dynamic+((name.charAt(0)+"").toUpperCase())+name.substring(1);
    	}else if(name.length() == 0){
    		methodName = dynamic+((name.charAt(0)+"").toUpperCase());
    	}
		return methodName;
	}

	public static Class<?> getFileType(Class<?> fieldType){
        // 如果是 int, float等基本类型，则需要转型
        if (fieldType.equals(Integer.TYPE)) {
            return Integer.class;
        } else if (fieldType.equals(Float.TYPE)) {
            return Float.class;
        } else if (fieldType.equals(Double.TYPE)) {
            return  Double.class;
        } else if (fieldType.equals(Boolean.TYPE)) {
            return  Boolean.class;
        } else if (fieldType.equals(Short.TYPE)) {
            return  Short.class;
        } else if (fieldType.equals(Long.TYPE)) {
            return  Long.class;
        } else if(fieldType.equals(String.class)){
            return  String.class;
        }else if(fieldType.equals(Collection.class)){
            return  Collection.class;
        }
        return null;
    }
}
