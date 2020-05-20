package user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*"); // TODO: Change to something else
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//        registry
//                .addResourceHandler("/assets/images/**")
//                .setCachePeriod(0)
//                .addResourceLocations("classpath:/static/assets/images/");
//    }
}
