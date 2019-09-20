package com.dyl.utils.area_code;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Date 2019/9/18 16:03
 * @Author Dong YL
 * @Email silentself@126.com
 */
public class CodeUtil {
    private static final String LNG_LAT_URL = "http://www.atool88.com/city_%s.html";

    /**
     * 爬取经纬度
     *
     * @param code 行政区划
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unused")
    private static Map<String, String> lngAndLat(Object code) throws IOException {
        Document doc = Jsoup.connect(String.format(LNG_LAT_URL, code)).post();
        Element table = doc.getElementsByTag("table").get(0);
        Element tr = table.child(0).child(3).child(2);
        String[] lngAndLat = tr.text().split("\\(|\\)")[1].split(",");
        Map<String, String> map = new HashMap<>(2);
        map.put("lng", lngAndLat[0]);
        map.put("lat", lngAndLat[1]);

        return map;

    }
}
