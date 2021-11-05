package icu.junyao.fileservice.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger接口文档配置
 *
 * @author johnson
 * @date 2021-10-02
 */
@Configuration
@MapperScan("icu.junyao.fileservice.mapper")
public class Knife4jConfig {
    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("junyao")
                        .description("文件服务器API")
                        .termsOfServiceUrl("https://996.icu/")
                        .contact(new Contact("junyao", "https://996.icu", "wjssazpjya@gmail.com"))
                        .version("9.9.6")
                        .build())
                //分组名称
                .groupName("S.X版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("icu.junyao"))
                .paths(PathSelectors.any())
                .build();
    }
}
