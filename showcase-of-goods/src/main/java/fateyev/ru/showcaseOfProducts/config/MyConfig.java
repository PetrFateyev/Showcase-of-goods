package fateyev.ru.showcaseOfProducts.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

@Configuration
public class MyConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SpecificationArgumentResolver());
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Showcase of goods")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .name("Fateyev Petr")
                                )
                );
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
