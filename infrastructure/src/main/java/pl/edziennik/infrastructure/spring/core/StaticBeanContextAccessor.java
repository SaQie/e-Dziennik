package pl.edziennik.infrastructure.spring.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class StaticBeanContextAccessor {
    private static ApplicationContext context;

    @Autowired
    public StaticBeanContextAccessor(ApplicationContext applicationContext) {
        context = applicationContext;
    }


    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

}
