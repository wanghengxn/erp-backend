package tech.qdhxy.erp.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void writeAccountSubjectTpl() {
        List<String> lines = new ArrayList<>();
        lines.add("id;category;level;code;name;fast_code;direction;pid;status;created_date;created_by");
        ClassPathResource classPathResource = new ClassPathResource("db/changelog/accounting/data/a.json");
        try (InputStream in = classPathResource.getInputStream()) {
            JSONObject json = JSON.parseObject(in, Charset.defaultCharset());
            // 资产
            getLine(lines, json, "AASJ", "Assets");
            // 权益
            getLine(lines, json, "OASJ", "Equity");
            // 负债
            getLine(lines, json, "DASJ", "Liabilities");
            // 成本
            getLine(lines, json, "CASJ", "Expenses");
            // 损益
            getLine(lines, json, "IASJ", "Revenues");


            lines.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getLine(List<String> lines, JSONObject json, String key, String category) {
        JSONArray assets = json.getJSONArray(key);
        for (int i = 0; i < assets.size(); ++i) {
            JSONObject asset = assets.getJSONObject(i);
            // id;category;code;name;fast_code;direction;pid;status;created_date;created_by
            lines.add(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;now();system",
                    asset.getString("Id"),
                    category,
                    asset.getString("AL"),
                    asset.getString("Co"),// code
                    asset.getString("AN"), // name
                    asset.getString("Acr"),// fast_code
                    asset.getString("Dir"),
                    asset.getString("Pid"),
                    asset.getIntValue("S") == 0 ? "1" : "0"
            ));
        }
    }

    public static void main(String[] args) {

//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println(encoder.encode("123445!"));
        ;
        writeAccountSubjectTpl();
    }
}
