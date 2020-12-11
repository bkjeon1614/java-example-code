package com.example.bkjeon.base.services.api.v1.data;

import io.swagger.annotations.ApiOperation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/data/json")
public class JsonParseController {

    private final static String jsonObjectString = "{"
        + "\"title\": \"Hi Bong Keun\","
        + "\"url\": \"https://bkjeon1614.tistory.com\","
        + "\"star\": 15"
    + "}";

    private final static String jsonObjectAndObjectString = "{"
        + "\"obj1\": {"
            + "\"title\": \"제목 bkjeon\","
            + "\"url\": \"https://bkjeon1614.tistory.com\","
            + "\"draft\": false"
        + " },"
        + "\"obj2\": {"
            + "\"title\": \"제목입니다2 2222\","
            + "\"url\": \"https://bkjeon1614.tistory.com\","
            + "\"draft\": false"
        + "}"
    + "}";

    private final static String jsonObjectAndArrayAndObjectString = "{"
        + "\"list\": ["
            + "{"
                + "\"title\": \"제목11\","
                + "\"url\": \"https://bkjeon1614.tistory.com\","
                + "\"type\": false"
            + "},"
            + "{"
                + "\"title\": \"제목22\","
                + "\"url\": \"https://bkjeon1614.tistory.com\","
                + "\"type\": true"
            + "},"
            + "{"
                + "\"title\": \"제목33\","
                + "\"url\": \"https://bkjeon1614.tistory.com\","
                + "\"type\": true"
            + "}"
        + "]"
    + "}";


    @ApiOperation("JSON Object Null Check")
    @GetMapping("jsonObjectNullCheck")
    public void jsonObjectNullCheck() {
        JSONObject jsonObject = new JSONObject(jsonObjectAndObjectString);

        System.out.println("title object check: " + !jsonObject.getJSONObject("obj2").isNull("title"));
        System.out.println("title2 object check: " + !jsonObject.getJSONObject("obj2").isNull("title2"));
    }

    @ApiOperation("Key-Value만 있는 JSON Parse")
    @GetMapping("baseParseObject")
    public Map<String, Object> getJsonParseBase() {
        // JSONObjet를 가져와서 key-value를 읽습니다.
        JSONObject jObject = new JSONObject(jsonObjectString);
        String title = jObject.getString("title");
        String url = jObject.getString("url");
        int star = jObject.getInt("star");

        Map<String, Object> retMap = new HashMap<>();
        retMap.put("title", title);
        retMap.put("url", url);
        retMap.put("star", star);

        return retMap;
    }

    @ApiOperation("하위에 여러 Object가 있는 JSON")
    @GetMapping("downMultiParseObject")
    public Map<String, Map<String, Object>> getJsonParseMultiObject() {
        Map<String, Map<String, Object>> retMap = new HashMap<>();

        // 가장 큰 JSONObject를 가져옵니다.
        JSONObject jObject = new JSONObject(jsonObjectAndObjectString);

        // 첫번째 JSONObject를 가져와서 key-value를 읽습니다.
        Map<String, Object> retPost1Map = new HashMap<>();
        JSONObject post1Object = jObject.getJSONObject("obj1");
        String title = post1Object.getString("title");
        String url = post1Object.getString("url");
        boolean draft = post1Object.getBoolean("draft");
        retPost1Map.put("title", title);
        retPost1Map.put("url", url);
        retPost1Map.put("draft", draft);

        // 두번째 JSONObject를 가져와서 key-value를 읽습니다.
        Map<String, Object> retPost2Map = new HashMap<>();
        JSONObject post2Object = jObject.getJSONObject("obj2");
        title = post2Object.getString("title");
        url = post2Object.getString("url");
        draft = post2Object.getBoolean("draft");
        retPost2Map.put("title", title);
        retPost2Map.put("url", url);
        retPost2Map.put("draft", draft);

        // 최종 데이터 가공
        retMap.put("obj1", retPost1Map);
        retMap.put("obj2", retPost2Map);

        return retMap;
    }

    @ApiOperation("Array 가 있는 JSON")
    @GetMapping("arrayParseObject")
    public List<Map<String, Object>> getJsonParseArrayObject() {
        List<Map<String, Object>> retListMap = new ArrayList<>();

        // 가장 큰 JSONObject를 가져옵니다.
        JSONObject jObject = new JSONObject(jsonObjectAndArrayAndObjectString);

        // 배열을 가져옵니다.
        JSONArray jArray = jObject.getJSONArray("list");

        // 배열의 모든 아이템을 출력합니다.
        for (int i = 0; i < jArray.length(); i++) {
            Map<String, Object> objMap = new HashMap<>();

            JSONObject obj = jArray.getJSONObject(i);
            String title = obj.getString("title");
            String url = obj.getString("url");
            boolean draft = obj.getBoolean("type");

            objMap.put("title", title);
            objMap.put("url", url);
            objMap.put("type", draft);
            retListMap.add(objMap);
        }

        return retListMap;
    }

}
