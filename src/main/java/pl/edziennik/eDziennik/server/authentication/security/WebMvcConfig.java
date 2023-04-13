package pl.edziennik.eDziennik.server.authentication.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.edziennik.eDziennik.server.converters.ids.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET");
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new SubjectIdConverter());
        registry.addConverter(new StudentIdConverter());
        registry.addConverter(new GradeIdConverter());
        registry.addConverter(new SchoolClassIdConverter());
        registry.addConverter(new SchoolLevelIdConverter());
        registry.addConverter(new SettingsIdConverter());
        registry.addConverter(new TeacherIdConverter());
        registry.addConverter(new UserIdConverter());
        registry.addConverter(new AdminIdConverter());
        registry.addConverter(new ParentIdConverter());
        registry.addConverter(new SchoolIdConverter());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
