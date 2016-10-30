package com.cloudcode.framework.utils;

//import java.io.IOException;

//import org.apache.solr.client.solrj.SolrServer;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.client.solrj.impl.CloudSolrServer;
//import org.apache.solr.client.solrj.impl.HttpSolrServer;
//import org.apache.solr.client.solrj.request.CoreAdminRequest;
//import org.apache.solr.client.solrj.response.CoreAdminResponse;
//import org.apache.solr.common.cloud.ZkStateReader;

public class SolrUtil {
//	private static HttpSolrServer httpSolrServer;
//	private static final String DEFAULT_URL = "http://localhost:8080/solr/";
//
//	public static HttpSolrServer getSolrServer() {
//		if (httpSolrServer == null) {
//			httpSolrServer = new HttpSolrServer(DEFAULT_URL);
//			httpSolrServer.setSoTimeout(1000);
//			httpSolrServer.setConnectionTimeout(200);
//			httpSolrServer.setDefaultMaxConnectionsPerHost(100);
//			httpSolrServer.setMaxTotalConnections(100);
//			httpSolrServer.setFollowRedirects(false);
//
//			httpSolrServer.setAllowCompression(true);
//			httpSolrServer.setMaxRetries(1);
//		}
//
//		return httpSolrServer;
//	}
//
//	public static void deleteByQuery() {
//		try {
//			getSolrServer().deleteByQuery("*:*");
//		} catch (SolrServerException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void showDown() {
//		if (getSolrServer() != null)
//			getSolrServer().shutdown();
//	}
//
//	public static void createCollection(String collectionName) {
//		SolrServer aServer = new HttpSolrServer(DEFAULT_URL);
//		CoreAdminResponse aResponse;
//		try {
//			aResponse = CoreAdminRequest.getStatus(collectionName, aServer);
//			if (aResponse.getCoreStatus().size() < 1) {
//				CoreAdminRequest.Create aCreateRequest = new CoreAdminRequest.Create();
//				aCreateRequest.setCoreName(collectionName);
//				aCreateRequest.setInstanceDir(collectionName);
//				aCreateRequest.process(aServer);
//			}
//		} catch (SolrServerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	private static CloudSolrServer cloudSolrServer;  
//    
//    private  static synchronized CloudSolrServer getCloudSolrServer(final String zkHost) {  
//        if(cloudSolrServer == null) {  
//            try {  
//                cloudSolrServer = new CloudSolrServer(zkHost);  
//            }catch(Exception e) {  
//                e.printStackTrace();                  
//            }  
//        }  
//          
//        return cloudSolrServer;  
//    }  
//    public static void main(String[] args) {          
//        final String zkHost = "localhost:8080";       
//        final String  defaultCollection = "collection2";  
//        final int  zkClientTimeout = 20000;  
//        final int zkConnectTimeout = 1000;  
//          
//        CloudSolrServer cloudSolrServer = getCloudSolrServer(zkHost);         
//        System.out.println("The Cloud SolrServer Instance has benn created!");  
//          
//        cloudSolrServer.setDefaultCollection(defaultCollection);  
//        cloudSolrServer.setZkClientTimeout(zkClientTimeout);  
//        cloudSolrServer.setZkConnectTimeout(zkConnectTimeout);    
//          
//          
//        cloudSolrServer.connect();  
//        System.out.println("The cloud Server has been connected !!!!");  
//          
//        /*ZkStateReader zkStateReader = cloudSolrServer.getZkStateReader();  
//        CloudState cloudState  = zkStateReader.getCloudState();  
//        System.out.println(cloudState);  
//          
//        //测试实例！  
//        TestCloudSolr test = new TestCloudSolr();         
//        System.out.println("测试添加index！！！");       
//        //添加index  
//        test.addIndex(cloudSolrServer);  
//          
//        System.out.println("测试查询query！！！！");  
//        test.search(cloudSolrServer, "id:*");  
//          
//        System.out.println("测试删除！！！！");  
//        test.deleteAllIndex(cloudSolrServer);  
//        System.out.println("删除所有文档后的查询结果：");  
//        test.search(cloudSolrServer, "*:*");      */
//          
//          
//                  
//         // release the resource   
//        cloudSolrServer.shutdown();  
//   
//    }   
}