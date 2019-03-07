package com.tree.search.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class TreeSearchSwaggerConfig {

    private static final String TREE_SEARCH_API_BASE_PACKAGE = "com.tree.search.controller";

    private static final String TREE_SEARCH_API_TITLE = "Tree Search Service API";
    private static final String TREE_SEARCH_API_DESCRIPTION = "Tree Search REST API";
    private static final String TREE_SEARCH_API_VERSION = "1.0";
    private static final String TREE_SEARCH_API_TERMS_OF_SERVICE = "Terms of service";
    private static final String TREE_SEARCH_API_OWNER_NAME = "Mayank Tantuway";
    private static final String TREE_SEARCH_API_OWNER_WEBSITE = "http://easemywork.in/";
    private static final String TREE_SEARCH_API_OWNER_EMAIL = "71mayank@gmail.com";
    private static final String TREE_SEARCH_API_LICENSE = "Prototype License Version 1.0";
    private static final String TREE_SEARCH_API_LICENSE_URL = "http://easemywork.in/licenses/LICENSE-1.0";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors
                        .basePackage(TREE_SEARCH_API_BASE_PACKAGE))
                .paths(PathSelectors.regex("/.*"))
                .build().apiInfo(apiEndPointsInfo());
    }


    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title(TREE_SEARCH_API_TITLE)
                .description(TREE_SEARCH_API_DESCRIPTION)
                .version(TREE_SEARCH_API_VERSION)
                .contact(new Contact(TREE_SEARCH_API_OWNER_NAME, TREE_SEARCH_API_OWNER_WEBSITE, TREE_SEARCH_API_OWNER_EMAIL))
                .termsOfServiceUrl(TREE_SEARCH_API_TERMS_OF_SERVICE)
                .license(TREE_SEARCH_API_LICENSE)
                .licenseUrl(TREE_SEARCH_API_LICENSE_URL)
                .build();
    }
}
