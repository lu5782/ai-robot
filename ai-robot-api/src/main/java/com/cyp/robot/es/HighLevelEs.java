package com.cyp.robot.es;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.search.sort.SortOrder;

/**
 * @Author :      luyijun
 * @Date :        2021/7/18 18:40
 * @Description :
 */
@Slf4j
public class HighLevelEs {

    //    @Value("${es.ip:192.168.66.3}")
    static String esIp = "192.168.0.101";

    public static RestHighLevelClient client;
    private static String indexName = "customer-abcd";

    public static void main(String[] args) throws Exception {
        connect();
        EsIndexService esIndexService = new EsIndexService(client);
        EsDocumentService esDocumentService = new EsDocumentService(client);

        boolean exist = esIndexService.indexExist(indexName);
        if (!exist) {
            esIndexService.indexCreate(indexName);
        }
//        indexDelete(indexName);
//        esIndexService.indexGet(indexName);

//        esDocumentService.documentCreate(indexName);
//        esDocumentService.documentGet(indexName, "1", new String[]{"name", "title"}, Strings.EMPTY_ARRAY);
        esDocumentService.documentGet(indexName, new String[]{"title", "name","age"}, Strings.EMPTY_ARRAY, 0, 20, "name", SortOrder.DESC);
        client.close();
    }


    public static void connect() {
        if (client == null) {
            HttpHost http = new HttpHost(esIp, 9200, "http");
            // 配置多个ES节点
            HttpHost[] nodes = {http};
            client = new RestHighLevelClient(RestClient.builder(nodes));
        }
    }


}
