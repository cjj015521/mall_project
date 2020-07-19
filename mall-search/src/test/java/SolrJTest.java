import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {
	
	@Test
	public void testAddDocument() throws Exception {
		//创建一个solrServer
		String url = "http://192.168.64.133:8080/solr";
		HttpSolrServer solrServer = new HttpSolrServer(url);

		//创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		// 注意：id的域不能少
        document.addField("id", "c0002");
        document.addField("item_title", "测试商品2");
        document.addField("item_price", "2352345");
        
        //把文档对象写入索引库
        solrServer.add(document);
        solrServer.commit();

	}
	
	@Test
	public void deleteDocument() throws Exception {
		//创建一个solrServer
		String url = "http://192.168.64.133:8080/solr";
		HttpSolrServer solrServer = new HttpSolrServer(url);
		
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}

}
