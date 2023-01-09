package com.lt.stock;

import com.lt.stock.pojo.vo.InnerMarketResponseVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/9 11:53
 */
@SpringBootTest
public class TestRestTemplate {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test1() {
        String url = "http://localhost:8081/api/quot/index/all";
        ResponseEntity<List> result = restTemplate.getForEntity(url, List.class);
        // 获取响应头
        HttpHeaders headers = result.getHeaders();
        System.out.println(headers.toString());
        // 获取响应状态码
        int statusCode = result.getStatusCodeValue();
        System.out.println(statusCode);
        // 获取响应数据
        List respData = result.getBody();
        System.out.println(respData);
    }

    @Test
    public void test2() {
        String url = "http://localhost:8081/api/quot/index/all";
        // 设置请求头参数
        HttpHeaders headers = new HttpHeaders();
        headers.add("userName", "zhangsan");
        // 请求头填充到请求对象下
        HttpEntity<Map> entity = new HttpEntity<>(headers);
        // 发送请求
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        // 获取响应数据
        String body = result.getBody();
        System.out.println(body);
    }

    @Test
    public void test3() {
        String url = "http://localhost:6666/account/addAccount";
        // 设置请求头，指定请求数据方式
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded");
        // 组装模拟form表单提交数据
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("id", "10");
        map.add("userName", "itheima");
        map.add("address", "shanghai");
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);
        /*
            参数1：请求url地址
            参数2：请求方式 POST
            参数3：请求体对象，携带了请求头和请求体相关的参数
            参数4：响应数据类型
         */
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String body = exchange.getBody();
        System.out.println(body);
    }

    @Test
    public void test4() {
        String url = "http://localhost:6666/account/updateAccount";
        //设置请求头的请求参数类型
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json; charset=utf-8");
        //组装json格式数据
//        HashMap<String, String> reqMap = new HashMap<>();
//        reqMap.put("id","1");
//        reqMap.put("userName","zhangsan");
//        reqMap.put("address","上海");
//        String jsonReqData = new Gson().toJson(reqMap);
        String jsonReq = "{\"address\":\"上海\",\"id\":\"1\",\"userName\":\"zhangsan\"}";
        //构建请求对象
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonReq, headers);
          /*
            发送数据
            参数1：请求url地址
            参数2：请求方式
            参数3：请求体对象，携带了请求头和请求体相关的参数
            参数4：响应数据类型
         */
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        //或者
        // String account=restTemplate.postForObject(url,httpEntity,String.class);
        String body = responseEntity.getBody();
        System.out.println(body);
    }

    @Test
    public void test05() {
        String url = "http://localhost:6666/account/getCookie";
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        //获取cookie
        List<String> cookies = result.getHeaders().get("Set-Cookie");
        //获取响应数据
        String resStr = result.getBody();
        System.out.println(resStr);
        System.out.println(cookies);
    }
}
