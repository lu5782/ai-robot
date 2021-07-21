package com.cyp.robot.es;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;

/**
 * @Author :      luyijun
 * @Date :        2021/7/18 18:40
 * @Description :
 */
@Slf4j
public class HighLevelEs {

    public static RestHighLevelClient client;
    private static String indexName = "customer-abcd";
    private static String indexType = "customer-type";

    public static void main(String[] args) throws Exception {
        client();
        indexExist("customer");
        indexDelete("customer-abcd");
        indexCreate("customer-abcd");

        indexGet("customer-abcd");

        documentCreate("customer-abcd");
        documentGet("customer-abcd");
        client.close();
    }


    public static void client() {
        if (client == null) {
            client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
        }
    }

    /**
     * 删除索引
     */
    public static void indexDelete(String indeces) {
        DeleteIndexRequest request = new DeleteIndexRequest(indeces.toLowerCase());
        try {
            AcknowledgedResponse deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
            boolean acknowledged = deleteIndexResponse.isAcknowledged();
            log.info("删除索引：{}", acknowledged);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 索引查询
     */
    public static GetIndexResponse indexGet(String indeces) {
        GetIndexResponse getIndexResponse = null;
        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest(indeces);
            getIndexResponse = client.indices().get(getIndexRequest, RequestOptions.DEFAULT);
            log.info("索引查询结果= {}", getIndexResponse);
            // 索引数据第一条就是本索引最先的存储的数据
            String[] indexs = getIndexResponse.getIndices();
        } catch (IOException e) {
            log.info("索引查询indexGet 异常");
            e.printStackTrace();
        }
        return getIndexResponse;
    }


    /**
     * 索引是否存在
     */
    public static Boolean indexExist(String indeces) {
        GetIndexRequest request = new GetIndexRequest(indeces.toLowerCase());
        Boolean exist = null;
        try {
            exist = client.indices().exists(request, RequestOptions.DEFAULT);
            log.info("索引是否存在 exist= {}", exist);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exist;
    }

    /**
     * 创建索引
     */
    public static boolean indexCreate(String indeces) {
        try {
            CreateIndexRequest index = new CreateIndexRequest(indeces);

//            XContentBuilder builder = JsonXContent.contentBuilder();
//            builder.startObject()
//                    .startObject("mappings")
//                    .startObject("properties")
//                    .field("type", "text")
//                    .endObject()
//                    .endObject();
//
//            builder.startObject("settings")
//                    //分片数
//                    .field("number_of_shards", 1)
//                    //副本数,1台机器设为0
//                    .field("number_of_replicas", 0)
//                    .endObject()
//                    .endObject();
//            index.source(builder);

//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("title", "title123");
//            jsonObject.put("name", "name123");
//            index.source(jsonObject.toString(), XContentType.JSON);

            CreateIndexResponse response = client.indices().create(index, RequestOptions.DEFAULT);
            log.info("创建索引 response= {}", JSONObject.toJSONString(response));
            return response.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void documentCreate(String indeces) throws Exception {
//        JSONObject source = new JSONObject();
//        source.put("name", "name123");
//        source.put("price", 13999);
//        source.put("title", "title123");
//        IndexRequest indexRequest = new IndexRequest(indeces).id("1").source(source);
        IndexRequest indexRequest = new IndexRequest(indeces).id("1").source("name", "name123", "price", 13999, "title", "title123");
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

        // 获取结果
        String index = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        long version = response.getVersion();
//        boolean created = response.isCreated();

        System.out.println("索引是: " + index);
        System.out.println("类型是: " + type);
        System.out.println("文档id是: " + id);
        System.out.println("版本是: " + version);
//        System.out.println("是否创建: " + created);
        log.info("创建文档response= {}", response);
    }

    /**
     * 索引查询
     */
    public static SearchResponse  documentGet(String indeces) throws IOException {
        GetRequest getRequest = new GetRequest(indeces, "1");   //索引名称 , 文档id
        String[] includes = new String[]{"name", "title"};
        String[] excludes = Strings.EMPTY_ARRAY;
        FetchSourceContext fetchSourceContext =
                new FetchSourceContext(true, includes, excludes);
        getRequest.fetchSourceContext(fetchSourceContext); //为特定字段配置源包含
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        log.info("查询文档结果getResponse:{}", getResponse);


        //创建请求参数
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        //分页并排序(第一页是从0开始的，所以上面的start-1)
        ssb.from(1).size(10)
//                    .sort("name", SortOrder.DESC) //排序
                .trackTotalHits(true);//查全部数据(如果不写或者写false当总记录数超过10000时会返回总数10000,配置为true就会返回真实条数)
        //指定返回字段
        ssb.fetchSource(new String[]{"title", "name"}, new String[]{});

        //精确匹配
        ssb.query(new BoolQueryBuilder().filter(QueryBuilders.termQuery("name", "name123")));
        log.info("查询文档参数:{}", ssb);

        SearchRequest request = new SearchRequest();
        request.indices(indeces);
        request.source(ssb);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        log.info("查询文档结果:{}", search);
        return search;
    }


}
