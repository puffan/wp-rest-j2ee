package com.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import de.ailis.pherialize.MixedArray;
import de.ailis.pherialize.Pherialize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestService {

    @Test
    public void testFastJSON() {
        List<String> idList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            idList.add(String.valueOf(i + 1));
        }

        String jsonStr = JSONObject.toJSONString(idList);
        List<String> json = JSONArray.parseArray(jsonStr, String.class);

        assertTrue(json.size() == idList.size());
    }

    @Test
    public void testPherialize() {
        String data = "a:5:{s:5:\"width\";i:700;s:6:\"height\";i:464;s:4:\"file\";s:27:\"2018/01/everbright-bank.jpg\";s:5:\"sizes\";a:2:{s:9:\"thumbnail\";a:4:{s:4:\"file\";s:27:\"everbright-bank-150x150.jpg\";s:5:\"width\";i:150;s:6:\"height\";i:150;s:9:\"mime-type\";s:10:\"image/jpeg\";}s:6:\"medium\";a:4:{s:4:\"file\";s:27:\"everbright-bank-300x199.jpg\";s:5:\"width\";i:300;s:6:\"height\";i:199;s:9:\"mime-type\";s:10:\"image/jpeg\";}}s:10:\"image_meta\";a:12:{s:8:\"aperture\";s:1:\"0\";s:6:\"credit\";s:0:\"\";s:6:\"camera\";s:0:\"\";s:7:\"caption\";s:0:\"\";s:17:\"created_timestamp\";s:1:\"0\";s:9:\"copyright\";s:0:\"\";s:12:\"focal_length\";s:1:\"0\";s:3:\"iso\";s:1:\"0\";s:13:\"shutter_speed\";s:1:\"0\";s:5:\"title\";s:0:\"\";s:11:\"orientation\";s:1:\"0\";s:8:\"keywords\";a:0:{}}}";
        MixedArray list= Pherialize.unserialize(data).toArray();

        System.out.println(list.getArray("sizes").getArray("medium").getString("file"));

        assertTrue(true);
    }

}
