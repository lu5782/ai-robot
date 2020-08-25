package com.cyp.robot.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;
import java.util.Map;

public class MapUtils {

    private static RestTemplate restTemplate;

    static {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = null;
        try {
            sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectionRequestTimeout(30000);
        requestFactory.setReadTimeout(5000);//ms
        requestFactory.setConnectTimeout(3000);//ms
        restTemplate = new RestTemplate(requestFactory);
    }

    /**
     * 腾讯位置服务：地址解析接口URL
     */
    private static String ADDRESS_TO_LOCATION_URL = "https://apis.map.qq.com/ws/geocoder/v1/?address={0}&key={1}&sig={2}";

    /**
     * 开发密钥
     */
    private static String key = "PORBZ-HQ5KO-WAJWG-SOJKK-IXLTZ-AZFTS";
    private static String key1 = "LQUBZ-GDUC4-C7AUN-D7FOM-UAYD7-P5BKT";

    /**
     * 签名 SK
     */
    private static String sk = "5w8brijYH3UP01WgPTgNSI948TetP3W";
    private static String sk1 = "O9q2QFpcDaKraXNHnKevUp4kEfIsHiql";

    /**
     * 根据地址计算经纬度
     *
     * @param address
     * @return
     */
    public static Map<String, Object> addressResolution(String address) {
        try {
            //签名计算：请求路径+”?”+请求参数+SK进行拼接，并计算拼接后字符串md5值，即为签名(sig)
            //请求参数必须升序
            String param = "/ws/geocoder/v1/?address=" + address + "&key=" + key + sk;
            String sig = Md5Utils.getMd5(param);

            String str = restTemplate.getForObject(ADDRESS_TO_LOCATION_URL, String.class, address, key, sig);
            Map<String, Object> map = ObjectMapperUtils.fromJson(str, new TypeReference<Map<String, Object>>() {
            });
            //解析数据
            String status = map.get("status").toString();
            if( "0".equals(status)){
                Map<String,Object> resultMap = (Map<String,Object>)map.get("result");
                Map<String,Object> locationMap= ( Map<String,Object>)resultMap.get("location");
                System.out.println("纬度："+locationMap.get("lat")+ ",经度："+locationMap.get("lng"));
                return locationMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        MapUtils.addressResolution("上海市杨浦区淞沪路77号(近邯郸路)");
    }
}
