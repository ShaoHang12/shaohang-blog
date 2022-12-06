package com.xsh.blog.config.system;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: PersonBig
 * @description: Swagger增加版
 *
 **/
@Configuration
@EnableSwagger2
public class Knife4jConfiguration {

    @Bean(value = "defaultApi")
    public Docket defaultApi() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Blog")
                        .description("Person Big System")
                        .termsOfServiceUrl("https://www.baidu.com/")
                        .version("1.0")
                        .build())
                .groupName("1.0版本")
                .select()

                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
