package com.peisp.exportest;

import java.util.*;

public class exportTest {
    public static void main(String[] args) throws Exception {


        // 建立连接，查数据
        List<Map<String, Object>> maps = demoData();
        String excelByData = ExportUtil.createExcelByData(maps);
        System.out.println(excelByData);

        // 连接数据库导出时，需要依赖对应的驱动包
        DataSource dataSource = new DataSource("com.mysql.jdbc.Driver",
                "jdbc:mysql://192.168.1.165:4000/dcdgnew_changsha_pro4test?useUnicode=true&characterEncoding=UTF-8&useSSL=true&allowMultiQueries=true&serverTimezone=UTC",
                "root",
                "1qaz!QAZ",
                "SELECT `id`, `measure_code`, `measure_difin`, `measure_org`, `measure_chrild`, `measure_support`, `measure_date`, `measure_referto`, `measure_evaluate`, `measure_algorithm`, `measure_treeid`, `measure_name`,`measure_status`,`create_id`,`create_time`,`update_time`FROM `dcdgnew_changsha_pro4test`.`asset_measure`");

        String excelByData1 = ExportUtil.createExcelByData(DBUtils.getSqlData(dataSource));
        System.out.println(excelByData1);

    }

    public static List<Map<String, Object>> demoData(){
        List<Map<String, Object>> maps = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>(5);
            map.put("name","张" + i);
            map.put("年龄",10 + i);
            map.put("体重",20 + i);
            map.put("生日",new Date());
            map.put("double",0.56 + i);
            maps.add(map);
        }
        return maps;
    }
}
