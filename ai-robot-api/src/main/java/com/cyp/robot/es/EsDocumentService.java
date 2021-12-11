package com.cyp.robot.es;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortOrder;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * @Author :      luyijun
 * @Date :        2021/11/5 23:47
 * @Description :
 */
@Slf4j
public class EsDocumentService {
    public RestHighLevelClient client;

    public EsDocumentService(RestHighLevelClient client) {
        this.client = client;
    }


    /**
     * 文档创建
     * 自动生成id
     * index+id 如果文档已经存在，则更新文档数据
     */
    public void documentCreate(String indeces) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Random random = new Random(System.currentTimeMillis());
        int nextInt = random.nextInt(50);
        jsonObject.put("name", "name-" + nextInt);
        jsonObject.put("title", "title-" + nextInt);
        jsonObject.put("age", nextInt);
        jsonObject.put("address", "sh");
//        jsonObject.put("sex", random.nextBoolean());
        jsonObject.put("salary", random.nextFloat() * 10000);
        jsonObject.put("createdDate", LocalDateTime.now());
        jsonObject.put("updatedDate", LocalDateTime.now());
        jsonObject.put("timestamp", System.currentTimeMillis());
        // 自动生成文档id
        IndexRequest indexRequest = new IndexRequest(indeces).source(jsonObject);
        log.info("创建文档参数= {}", indexRequest);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        log.info("创建文档返回值= {}", response);
    }

    /**
     * 文档创建
     * 指定id
     * index+id 如果文档已经存在，则更新文档数据
     */
    public void documentCreate(String indeces, String id) throws Exception {
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
    public GetResponse documentGet(String indeces, String id, @Nullable String[] includes, @Nullable String[] excludes) throws IOException {
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
     * @param sortName 排序的字段
     * @param order    排序方式（正序、倒序）
     * @return
     * @throws IOException
     */
    public SearchResponse documentGet(@NotNull String indeces, @Nullable String[] includes, @Nullable String[] excludes,
                                      int from, int size, String sortName, SortOrder order) throws IOException {
        //创建请求参数
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //分页并排序(第一页是从0开始的，所以上面的start-1)
        builder.from(from).size(size)
                //排序
//                .sort(sortName, order)
                //查全部数据(如果不写或者写false当总记录数超过10000时会返回总数10000,配置为true就会返回真实条数)
                .trackTotalHits(true)
                //指定返回字段
//                .fetchSource(includes, excludes)
                //精确匹配
                /**
                 * 使用QueryBuilder
                 * termQuery("key", obj) 完全匹配
                 * termsQuery("key", obj1, obj2..)   一次匹配多个值
                 * matchQuery("key", Obj) 单个匹配, field不支持通配符, 前缀具高级特性
                 * multiMatchQuery("text", "field1", "field2"..);  匹配多个字段, field有通配符忒行
                 * matchPhraseQuery()
                 * matchPhrasePrefixQuery()
                 * matchAllQuery();         匹配所有文件
                 */
//                .query(QueryBuilders.multiMatchQuery("title", "title-36","title-46"));
                .query(QueryBuilders.matchPhraseQuery("title", "title-36"));
//                .query(QueryBuilders.termQuery("title", "title-36"));
        log.info("文档-精确匹配参数 :{} ", builder);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.searchType(SearchType.DEFAULT);
        searchRequest.indices(indeces);
        searchRequest.source(builder);

        // 同步查询
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);

/*        // 异步查询
        ActionListener actionListener = new ActionListener() {
            @Override
            public void onResponse(Object o) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        client.searchAsync(searchRequest, RequestOptions.DEFAULT,actionListener);*/
        log.info("文档-精确匹配结果:\n{}", search);
        return search;
    }

}
