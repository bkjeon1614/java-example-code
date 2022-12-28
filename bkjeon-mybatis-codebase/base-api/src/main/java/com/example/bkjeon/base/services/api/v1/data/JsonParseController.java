package com.example.bkjeon.base.services.api.v1.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/data/json")
public class JsonParseController {

    private final static String JSON_OBJECT_STRING = "{"
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

    private final static String JSON_OBJECT_AND_ARRAY_AND_OBJECT_STRING = "{"
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

    private final static String ARRAY_AND_OBJECT_STRING = "["
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
    + "]";

    @ToString
    @Getter
    public static class JsonTest {
        private String title;
        private String url;
        private boolean type;
    }

    // TODO: 여기서부터 옮겨야돼
    @ApiOperation("[Jackson] Convert JSON to JsonNode")
    @GetMapping("isJsonStrToJsonNode")
    public void isJsonStrToJsonNode() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(JSON_OBJECT_STRING);
        log.info(
            ">>>>>>>>>>>>>>>>>>>> jsonNode1: title: {}, url: {}, star: {}",
            jsonNode.get("title").asText(),
            jsonNode.get("url").asText(),
            jsonNode.get("star").asText()
        );
    }

    @ApiOperation("[jackson] Convert JSON Array String to Java List")
    @GetMapping("isJsonArrayStrToList")
    public void isJsonArrayStrToList() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<JsonTest> jsonTestList = objectMapper.readValue(ARRAY_AND_OBJECT_STRING, new TypeReference<>(){});
        log.info(">>>>>>>>>>>>>>>>>>>> jsonTestList: {}", jsonTestList.toString());
    }

    @ApiOperation("[Jackson] Convert JSON to Java Map")
    @GetMapping("isJsonToJavaMap")
    public void isJsonToJavaMap() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(JSON_OBJECT_STRING, new TypeReference<>(){});
        log.info(">>>>>>>>>>>>>>>>>>>> isJsonToJavaMap: {}", jsonMap.get("title").toString());
    }

    @ApiOperation("[Jackson] JSON 에는 있지만 Mapping 될 Object 에는 없는 필드를 무시해야하는 경우")
    @GetMapping("isJsonNotMappingIgnore")
    public void isJsonNotMappingIgnore() throws IOException {
        String json = "{\"title\":\"Ryan\",\"age\":30,\"url\":\"www.test.com\"}";
        ObjectMapper objectMapper = new ObjectMapper();

        // JsonTest Object 에서는 "age" 항목이 없습니다. 아래 설정을 안하게되면 익셉션이 발생합니다.
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonTest user = objectMapper.readValue(json, JsonTest.class);
        log.info(">>>>>>>>>>>>>>>>>>>> isJsonNotMappingIgnore: {}", user.title);
    }

    @ApiOperation("[Jackson] JSON 에 있는 Property 가 Mapping 될 Object 에 Primitive 인데 null 값이 전달을 무시해야하는 경우")
    @GetMapping("isJsonPropertyNullIgnore")
    public void isJsonPropertyNullIgnore() throws IOException {
        String json = "{\"title\":\"Ryan\",\"type\":null,\"url\":\"www.test.com\"}";
        ObjectMapper objectMapper = new ObjectMapper();

        // 기본적으로 FAIL_ON_NULL_FOR_PRIMITIVES 옵션은 false 상태이다. 의도적으로 옵션을 설정해서 테스트를 하였다.
        // 옵션이 true 가 되게되면, type 이 boolean 인 primitive 자료형이 null 인 json 이 전달되는 경우 익셉션을 발생시킨다.
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        JsonTest user = objectMapper.readValue(json, JsonTest.class);
        log.info(">>>>>>>>>>>>>>>>>>>> isJsonNotMappingIgnore: {}", user.title);
    }

    @ApiOperation("JSON Object Null Check")
    @GetMapping("jsonObjectNullCheck")
    public void jsonObjectNullCheck() throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonObjectAndObjectString);

        System.out.println("title object check: " + !jsonObject.getJSONObject("obj2").isNull("title"));
        System.out.println("title2 object check: " + !jsonObject.getJSONObject("obj2").isNull("title2"));
    }

    @ApiOperation("Key-Value만 있는 JSON Parse")
    @GetMapping("baseParseObject")
    public Map<String, Object> getJsonParseBase() throws JSONException {
        // JSONObjet를 가져와서 key-value를 읽습니다.
        JSONObject jObject = new JSONObject(JSON_OBJECT_STRING);
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
    public Map<String, Map<String, Object>> getJsonParseMultiObject() throws JSONException {
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
    public List<Map<String, Object>> getJsonParseArrayObject() throws JSONException {
        List<Map<String, Object>> retListMap = new ArrayList<>();

        // 가장 큰 JSONObject를 가져옵니다.
        JSONObject jObject = new JSONObject(JSON_OBJECT_AND_ARRAY_AND_OBJECT_STRING);

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
