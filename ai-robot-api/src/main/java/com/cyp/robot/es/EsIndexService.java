package com.cyp.robot.es;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;

/**
 * @Author :      luyijun
 * @Date :        2021/11/5 23:43
 * @Description :
 */
@Slf4j
public class EsIndexService {
    public RestHighLevelClient client;

    public EsIndexService(RestHighLevelClient client) {
        this.client = client;
    }

    /**
     * 删除索引
     */
    public void indexDelete(String indeces) {
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
    public GetIndexResponse indexGet(String indeces) {
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
    public boolean indexExist(String indeces) {
        GetIndexRequest request = new GetIndexRequest(indeces.toLowerCase());
        boolean exist = false;
        try {
            exist = client.indices().exists(request, RequestOptions.DEFAULT);
            log.info("索引是否存在 exist= {}", exist);
        } catch (IOException e) {
            log.info("查询索引是否存在异常", e);
            e.printStackTrace();
        }
        return exist;
    }

    /**
     * 创建索引
     */
    public boolean indexCreate(String indeces) {
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
}
