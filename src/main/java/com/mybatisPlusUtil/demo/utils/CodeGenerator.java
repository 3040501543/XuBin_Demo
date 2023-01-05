package com.mybatisPlusUtil.demo.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {
    @Test
    public void startGenerator() {
        //1、全局配置
        GlobalConfig config = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        config.setActiveRecord(true)//开启AR模式
                .setDateType(DateType.ONLY_DATE)
                .setAuthor("xubin")//设置作者
                .setOutputDir(projectPath + "/src/main/java")//生成路径(一般在此项目的src/main/java下)
                .setFileOverride(true)//第二次生成会把第一次生成的覆盖掉
                .setOpen(true)//生成完毕后是否自动打开输出目录
                .setSwagger2(true)//实体属性 Swagger2 注解
                //.setIdType(IdType.AUTO)//主键策略
                .setServiceName("%sService")//生成的service接口名字首字母是否为I，这样设置就没有I
//                .setServiceName("Service")//生成的service接口名字首字母是否为I，这样设置就没有I
                .setBaseResultMap(true)//生成resultMap
                .setBaseColumnList(true);//在xml中生成基础列

        //2、数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)//数据库类型
                .setDriverName("com.mysql.jdbc.Driver")
                .setUrl("jdbc:mysql://124.222.49.29:3306/db_health_online")
                .setUsername("root")
                .setPassword("XuBin2020@");
        //3、策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)//开启全局大写命名
                .setNaming(NamingStrategy.underline_to_camel)//表名映射到实体的命名策略(下划线到驼峰)
                //表字段映射属性名策略(未指定按naming)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                //.setTablePrefix("tb_")//表名前缀
                //.setSuperEntityClass("你自己的父类实体,没有就不用设置!")
                //.setSuperEntityColumns("id");//写于父类中的公共字段
                //.setSuperControllerClass("自定义继承的Controller类全称，带包名,没有就不用设置!")
                .setRestControllerStyle(true) //生成 @RestController 控制器
                .setEntityLombokModel(true)//使用lombok
                .setInclude("oh_examination_email");//逆向工程使用的表

        //4、包名策略配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.hyit.health")//设置包名的parent
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("domain")
                .setXml("mapper");//设置xml文件的目录


        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // 自定义vo，ro，qo等数据模型
                Map<String, String> customFile = new HashMap<>();
                customFile.put("VO.java", "templates/addDto.java.vm");
                customFile.put("RO.java", "templates/model/ro.java.vm");
                customFile.put("QO.java", "templates/model/qo.java.vm");
                customFile.put("URO.java", "templates/model/uro.java.vm");
                // 自定义MapStruct
                customFile.put("Converter.java", "templates/converter/converter.java.vm");

                // 自定义配置对象
                Map<String, Object> customMap = new HashMap<>();
                customMap.put("vo", "VO");
                customMap.put("ro", "RO");
                customMap.put("qo", "QO");
                customMap.put("uro", "URO");

//                builder.customFile(customFile) // 自定义模板
//                        .customMap(customMap); // 自定义map
            }
        };
        //5、整合配置
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig);
        //6、执行
        autoGenerator.execute();
    }
}