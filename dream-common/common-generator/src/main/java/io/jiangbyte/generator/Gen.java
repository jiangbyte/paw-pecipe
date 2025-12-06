package io.jiangbyte.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.model.ClassAnnotationAttributes;
import io.jiangbyte.framework.pojo.BaseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangJiangHu
 * @version v1.0
 * @date 28/09/2025
 * @description TODO
 */
public class Gen {
    public static void execute(List<Module> modules, String dataBaseUrl, String dataBaseUserName, String dataBasePassword, String author, String outputDir, Boolean addBackend, Boolean addApiAndView) {

        DataSourceConfig.Builder datasourceBuilder = new DataSourceConfig.Builder(dataBaseUrl, dataBaseUserName, dataBasePassword);

        for (Module module : modules) {
            FastAutoGenerator.create(datasourceBuilder)
                    /**
                     * 全局配置
                     * 可以配置作者、目录这些
                     */
                    .globalConfig(builder -> {
                        builder.author(author) // 作者
                                .disableOpenDir() // 禁止打开输出目录
                                .enableSpringdoc()
                                .outputDir(outputDir); // 指定输出目录
                    })

                    /**
                     * 包配置
                     */
                    .packageConfig(builder -> builder
                            .parent(module.getModuleName()) // 设置父包名
                    )

                    /**
                     * 策略配置
                     */
                    .strategyConfig(builder -> builder
                            .addInclude(module.getTables()) // 设置需要生成的表名

                            /**
                             * Entity
                             */
                            .entityBuilder()
//                            .enableFileOverride() // 覆盖已生成文件
                            .superClass(BaseEntity.class) // 父类
                            .enableLombok(new ClassAnnotationAttributes("@Data", "lombok.Data")) // 开启 lombok
                            .naming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                            .formatFileName("%s")

                            /**
                             * Service
                             */
                            .serviceBuilder()
                            .disable()

                            /**
                             * Mapper
                             */
                            .mapperBuilder()
                            .mapperAnnotation(Mapper.class)
                            .formatMapperFileName("%sMapper")
                            .formatXmlFileName("%sMapper")

                            /**
                             * Controller
                             */
                            .controllerBuilder()
                            .enableHyphenStyle()
                            .enableRestStyle() // @RestController
                            .formatFileName("%sController")
                    )
                    .injectionConfig(injectConfig -> {
                        List<CustomFile> build = new ArrayList<>();

                        if (addBackend) {
                            List<CustomFile> build1 = List.of(
                                    /* ====================================================== 参数 ====================================================== */
                                    new CustomFile.Builder()
                                            .fileName("Dto.java") // 文件名称
                                            .templatePath("/backend/Dto.java.ftl") // 生成模板路径
                                            .packageName("dto")
                                            .enableFileOverride() // 覆盖已生成文件
                                            .build(),
                                    new CustomFile.Builder()
                                            .fileName("PageQuery.java") // 文件名称
                                            .templatePath("/backend/PageQuery.java.ftl") // 生成模板路径
                                            .packageName("dto")
                                            .enableFileOverride() // 覆盖已生成文件
                                            .build(),

                                    /* ====================================================== 控制器 ====================================================== */
                                    new CustomFile.Builder()
                                            .fileName("Controller.java")
                                            .templatePath("/backend/Controller.java.ftl")
                                            .packageName("controller")
                                            .enableFileOverride() // 覆盖已生成文件
                                            .build(),

                                    /* ====================================================== 映射器 ====================================================== */
                                    new CustomFile.Builder()
                                            .fileName("Mapper.java")
                                            .templatePath("/backend/Mapper.java.ftl")
                                            .packageName("mapper")
                                            .enableFileOverride() // 覆盖已生成文件
                                            .build(),

                                    /* ====================================================== 实体类 ====================================================== */
                                    new CustomFile.Builder()
                                            .fileName(".java")
                                            .templatePath("/backend/IEntity.java.ftl")
                                            .packageName("entity")
                                            .enableFileOverride() // 覆盖已生成文件
                                            .build(),

                                    /* ====================================================== 服务类 ====================================================== */
                                    new CustomFile.Builder()
                                            .fileName("Service.java")
                                            .formatNameFunction(TableInfo::getEntityName)
                                            .templatePath("/backend/Service.java.ftl")
                                            .packageName("service")
                                            .enableFileOverride() // 覆盖已生成文件
                                            .build(),
                                    new CustomFile.Builder()
                                            .fileName("ServiceImpl.java")
                                            .formatNameFunction(TableInfo::getEntityName)
                                            .templatePath("/backend/ServiceImpl.java.ftl")
                                            .enableFileOverride() // 覆盖已生成文件
                                            .packageName("service.impl")
                                            .build()
                            );
                            build.addAll(build1);
                        }

                        if (addApiAndView) {
                            List<CustomFile> build2 = List.of(
                                    /* ====================================================== API ====================================================== */
                                    new CustomFile.Builder()
                                            .fileName("Api.ts")
                                            .formatNameFunction(tableInfo -> tableInfo.getEntityName().toLowerCase() + "/")
                                            .templatePath("/api/Api.ts.ftl")
                                            .enableFileOverride() // 覆盖已生成文件
                                            .packageName("vue.api")
                                            .build(),

                                    /* ====================================================== API VUE 文件 ====================================================== */
                                    new CustomFile.Builder()
                                            .fileName("constant.ts")
                                            .formatNameFunction(tableInfo -> tableInfo.getEntityName().toLowerCase() + "/")
                                            .templatePath("/vue/v1/constant.ts.ftl")
                                            .enableFileOverride() // 覆盖已生成文件
                                            .packageName("vue")
                                            .build(),
                                    new CustomFile.Builder()
                                            .fileName("index.vue")
                                            .formatNameFunction(tableInfo -> tableInfo.getEntityName().toLowerCase() + "/")
                                            .templatePath("/vue/v1/index.vue.ftl")
                                            .enableFileOverride() // 覆盖已生成文件
                                            .packageName("vue")
                                            .build(),
                                    new CustomFile.Builder()
                                            .fileName("form.vue")
                                            .formatNameFunction(tableInfo -> tableInfo.getEntityName().toLowerCase() + "/")
                                            .templatePath("/vue/v1/form.vue.ftl")
                                            .enableFileOverride() // 覆盖已生成文件
                                            .packageName("vue")
                                            .build(),
                                    new CustomFile.Builder()
                                            .fileName("detail.vue")
                                            .formatNameFunction(tableInfo -> tableInfo.getEntityName().toLowerCase() + "/")
                                            .templatePath("/vue/v1/detail.vue.ftl")
                                            .enableFileOverride() // 覆盖已生成文件
                                            .packageName("vue")
                                            .build()
                            );
                            build.addAll(build2);
                        }


                        injectConfig
                                .customMap(Map.of("GModule", module.getGModule()))
                                .customMap(Map.of("ModuleName", module.getModuleName()))
                                .customFile(build);
                    })
                    .templateEngine(new FreemarkerTemplateEngine())
                    .execute();
        }
    }
}
