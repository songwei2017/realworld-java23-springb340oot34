package com.world;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器
 *
 */
public class CodeGenerator {
    public static void main(String[] args) {
        // 数据源配置
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/world?serverTimezone=GMT%2B8", "root", "root")
                .globalConfig(builder -> {
                    builder.author("ergou")        // 设置作者
                            .disableOpenDir()       // 禁止打开输出目录 默认值:true
                            .commentDate("yyyy-MM-dd") // 注释日期
                            .dateType(DateType.ONLY_DATE)   //定义生成的实体类中日期类型 DateType.ONLY_DATE 默认值: DateType.TIME_PACK
                            .outputDir(System.getProperty("user.dir") + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.world") // 父包模块名
                            .controller("controller")   //Controller 包名 默认值:controller
                            .entity("entity")           //Entity 包名 默认值:entity
                            .service("service")         //Service 包名 默认值:service
                            .mapper("mapper")           //Mapper 包名 默认值:mapper
                            //.moduleName("xxx")        // 设置父包模块名 默认值:无
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/mapper")); // 设置mapperXml生成路径
                    //默认存放在mapper的xml下
                })
                .injectionConfig(consumer -> {
                    Map<String, String> customFile = new HashMap<>();
                    consumer.customFile(customFile);
                })
                .strategyConfig(builder -> {
                    builder.addInclude("article,article_comment,article_favorite,article_tag,tag,user_follow,user") // 设置需要生成的表名 可边长参数“user”, “user1”
                            .addTablePrefix("tb_", "gms_") // 设置过滤表前缀
                            .serviceBuilder()//service策略配置
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .entityBuilder()// 实体类策略配置
                            .idType(IdType.AUTO)//主键策略
                            .enableLombok() //开启lombok
                            .logicDeleteColumnName("is_deleted")// 说明逻辑删除是哪个字段
                            .enableTableFieldAnnotation()// 属性加上注解说明
                            .controllerBuilder() //controller 策略配置
                            .formatFileName("%sController")
                            .enableRestStyle() // 开启RestController注解
                            .mapperBuilder()// mapper策略配置
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation()//@mapper注解开启
                            .formatXmlFileName("%sMapper");
                })
                //默认的是Velocity引擎模板
                .templateEngine(new VelocityTemplateEngine())
                .execute();
    }
}
