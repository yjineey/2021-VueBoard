package kr.co.board;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class SpringFoxConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
//				.apis(RequestHandlerSelectors.basePackage("kr.co.board.controller"))
				.paths(PathSelectors.any())
				.build();
	}

}
//.consume()과 .produces()는 각각 Request Content-Type, Response Content-Type에 대한 설정입니다.(선택)
//.apiInfo()는 Swagger API 문서에 대한 설명을 표기하는 메소드입니다. (선택)
//.apis()는 Swagger API 문서로 만들기 원하는 basePackage 경로입니다. (필수)
//.path()는 URL 경로를 지정하여 해당 URL에 해당하는 요청만 Swagger API 문서로 만듭니다.(필수)

