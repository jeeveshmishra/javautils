package my.utils;

import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;

public class JsonUtilsTest {

    @Test
    public void test_serialize() throws Exception {
        TestJsonModel jsonModel = new TestJsonModel("JsonModel", "description");

        String expectedResult = "{\"name\":\"JsonModel\",\"description\":\"description\"}";
        String result = JsonUtils.serialize(jsonModel);

        assertEquals(result, expectedResult);
    }

    @Test
    public void test_serialize_list() throws Exception {
        TestJsonModel one = new TestJsonModel("one", "description");
        TestJsonModel two = new TestJsonModel("two", "description");

        List<TestJsonModel> jsonModel = new ArrayList<>();
        jsonModel.add(one);
        jsonModel.add(two);

        String expectedResult = "[{\"name\":\"one\",\"description\":\"description\"},{\"name\":\"two\",\"description\":\"description\"}]";
        String result = JsonUtils.serialize(jsonModel);

        assertEquals(result, expectedResult);
    }

    @Test
    public void test_serialize_nestedObject() throws Exception {
        TestJsonModel one = new TestJsonModel("one", "description");
        TestJsonModel two = new TestJsonModel("two", "description");
        TestListJsonModel jsonModel = new TestListJsonModel(one, two);

        String expectedResult = "{\"data\":[{\"name\":\"one\",\"description\":\"description\"},{\"name\":\"two\",\"description\":\"description\"}]}";
        String result = JsonUtils.serialize(jsonModel);

        assertEquals(result, expectedResult);
    }

    @Test
    public void test_serialize_multivaluedMap() throws Exception {
        String expectedJson = "{\"secondary\":\"purple\",\"primary\":[\"red\",\"green\",\"blue\"]}";

        List<String> empty = new ArrayList<>();
        List<String> single = Arrays.asList("purple");
        List<String> multiple = Arrays.asList("red", "green", "blue");

        Map<String, List<String>> multivaluedMap = new HashMap<>();
        multivaluedMap.put("primary", multiple);
        multivaluedMap.put("secondary", single);
        multivaluedMap.put("dark", empty);
        multivaluedMap.put("light", null);

        String json = JsonUtils.serialize(multivaluedMap);

        assertEquals(json, expectedJson);
    }

    @Test
    public void test_deserialize() throws Exception {
        String expectedResult = "{\"name\":\"JsonModel\",\"description\":\"description\"}";
        TestJsonModel result = JsonUtils.deserialize(expectedResult, TestJsonModel.class);

        assertNotNull(result);
        assertEquals(result.getName(), "JsonModel");
        assertEquals(result.getDescription(), "description");
    }

    @Test
    public void test_deserialize_strict() throws Exception {
        String json = "{\"name\":\"JsonModel\",\"description\":\"description\"}";
        TestJsonModel result = JsonUtils.deserialize(json, TestJsonModel.class, true);

        assertNotNull(result);
        assertEquals(result.getName(), "JsonModel");
        assertEquals(result.getDescription(), "description");
    }

    @Test
    public void test_deserialize_list() throws Exception {
        String json = "[{\"name\":\"one\",\"description\":\"description\"},{\"name\":\"two\",\"description\":\"description\"}]";
        List<TestJsonModel> result = JsonUtils.deserializeList(json, TestJsonModel.class);

        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getName(), "one");
        assertEquals(result.get(1).getName(), "two");
    }

    @Test
    public void test_deserialize_nestedObject() throws Exception {
        String json = "{\"data\":[{\"name\":\"one\",\"description\":\"description\"},{\"name\":\"two\",\"description\":\"description\"}]}";
        TestListJsonModel result = JsonUtils.deserialize(json, TestListJsonModel.class);

        assertNotNull(result);
        assertEquals(result.getData().size(), 2);
        assertEquals(result.getData().get(0).getName(), "one");
        assertEquals(result.getData().get(1).getName(), "two");
    }


    @Test(expectedExceptions = RuntimeException.class)
    public void test_deserialize_invalidJson() throws Exception {
        String expectedResult = "{\"name\":\"JsonModel\"[]\"description\":\"description\"}";
        JsonUtils.deserialize(expectedResult, TestJsonModel.class);
    }

    @Test
    public void test_deserialize_invalidJson_withDefaultValue() throws Exception {
        TestJsonModel defaultValue = new TestJsonModel("Donald Duck", "A cartoon character");
        String expectedResult = "{\"name\":\"JsonModel\"[]\"description\":\"description\"}";
        TestJsonModel result = JsonUtils.deserialize(expectedResult, TestJsonModel.class, defaultValue);

        assertEquals(result, defaultValue);
    }

    static class TestJsonModel {
        private String name;
        private String description;

        public TestJsonModel() {
        }

        public TestJsonModel(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }

    static class TestListJsonModel {
        private List<TestJsonModel> data;

        public TestListJsonModel() {
        }

        public TestListJsonModel(TestJsonModel... data) {
            this.data = Arrays.asList(data);
        }

        public TestListJsonModel(List<TestJsonModel> data) {
            this.data = data;
        }

        public List<TestJsonModel> getData() {
            return data;
        }
    }
}