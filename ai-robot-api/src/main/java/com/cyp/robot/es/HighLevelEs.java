package com.cyp.robot.es;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortOrder;

import javax.validation.constraints.NotNull;
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

    public static void main(String[] args) throws Exception {
        client();
//        indexExist(indexName);
//        indexDelete(indexName);
//        indexCreate(indexName);
//        indexGet(indexName);

//        documentCreate(indexName);
        documentGet(indexName, "1", new String[]{"name", "title"}, Strings.EMPTY_ARRAY);
        documentGet(indexName, new String[]{"title", "name"}, Strings.EMPTY_ARRAY, 0, 20, "name", SortOrder.DESC);
        client.close();
    }


    public static void client() {
        if (client == null) {
            HttpHost http = new HttpHost("127.0.0.1", 9200, "http");
            // 配置多个ES节点
            HttpHost[] nodes = {http};
            client = new RestHighLevelClient(RestClient.builder(nodes));
            log.info("es 连接成功 client:{}", client.toString());
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
            log.info("删除索引异常", e);
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
            // 索引数据第一条就是本索引最先的存储的数据
            String[] indexs = getIndexResponse.getIndices();
            log.info("索引查询结果= {}", indexs);
        } catch (IOException e) {
            log.info("索引查询异常");
            e.printStackTrace();
        }
        return getIndexResponse;
    }

    /**
     * 索引是否存在
     */
    public static boolean indexExist(String indeces) {
        GetIndexRequest request = new GetIndexRequest(indeces.toLowerCase());
        boolean exist = false;
        try {
            exist = client.indices().exists(request, RequestOptions.DEFAULT);
            log.info("索引是否存在 exist= {}", exist);
        } catch (IOException e) {
            log.info("查询索引是否存在异常", exist);
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
            CreateIndexResponse response = client.indices().create(index, RequestOptions.DEFAULT);
            log.info("创建索引 response= {}", JSONObject.toJSONString(response));
            return response.isAcknowledged();
        } catch (IOException e) {
            log.info("创建索引异常");
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 文档创建
     * 自动生成id
     * index+id 如果文档已经存在，则更新文档数据
     */
    public static void documentCreate(String indeces) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "name999");
        jsonObject.put("title", "title999");
        jsonObject.put("age", 18);
        jsonObject.put("address", "sh");
        // 自动生成文档id
        IndexRequest indexRequest = new IndexRequest(indeces).source(jsonObject);
        log.info("创建文档参数= {}", indexRequest);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        // 获取结果
        String index = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        long version = response.getVersion();
        log.info("索引是: " + index);
        log.info("类型是: " + type);
        log.info("文档id是: " + id);
        log.info("版本是: " + version);
        log.info("创建文档返回值= {}", response);
    }

    /**
     * 文档创建
     * 指定id
     * index+id 如果文档已经存在，则更新文档数据
     */
    public static void documentCreate(String indeces, String id) throws Exception {
        // 指定文档id
        IndexRequest indexRequest = new IndexRequest(indeces).id(id).source("name", "name456", "price", 13999, "title", "title456");
        log.info("创建文档参数= {}", indexRequest);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        log.info("创建文档返回值= {}", response);
    }

    /**
     * 文档查询
     * 索引、文档id是必填参数
     */
    public static GetResponse documentGet(String indeces, String id, @Nullable String[] includes, @Nullable String[] excludes) throws IOException {
        GetResponse getResponse = null;
        if (StringUtils.isNoneEmpty(indeces) && StringUtils.isNoneEmpty(id)) {
            GetRequest getRequest = new GetRequest(indeces, id);   //索引名称 , 文档id
            FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
            getRequest.fetchSourceContext(fetchSourceContext); //为特定字段配置源包含
            getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            log.info("文档查询结果getResponse:{}", getResponse);
        }
        return getResponse;
    }

    /**
     * 文档查询
     *
     * @param indeces  索引
     * @param includes 包含的字段
     * @param excludes 不包含的字段
     * @param from     分页起始页，第一页从0开始
     * @param size     分页条数
     * @param name     排序的字段
     * @param order    排序方式（正序、倒序）
     * @return
     * @throws IOException
     */
    public static SearchResponse documentGet(@NotNull String indeces, @Nullable String[] includes, @Nullable String[] excludes,
                                             int from, int size, String name, SortOrder order) throws IOException {
        //创建请求参数
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        //分页并排序(第一页是从0开始的，所以上面的start-1)
        ssb.from(from).size(size)
                //排序
                .sort(name, order)
                //查全部数据(如果不写或者写false当总记录数超过10000时会返回总数10000,配置为true就会返回真实条数)
                .trackTotalHits(true)
                //指定返回字段
                .fetchSource(includes, excludes);
        //精确匹配
//        ssb.query(new BoolQueryBuilder().filter(QueryBuilders.termQuery("name", "name123")));
        log.info("文档-精确匹配参数:{}", ssb);
        SearchRequest request = new SearchRequest();
        request.indices(indeces);
        request.source(ssb);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        log.info("文档-精确匹配结果:{}", search);
        return search;
    }


}
