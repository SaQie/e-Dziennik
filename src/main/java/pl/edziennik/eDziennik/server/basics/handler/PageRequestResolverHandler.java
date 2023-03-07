package pl.edziennik.eDziennik.server.basics.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import pl.edziennik.eDziennik.server.basics.dto.PageRequest;

import java.util.logging.Logger;

/**
 *
 */
@SuppressWarnings("all")
public class PageRequestResolverHandler implements HandlerMethodArgumentResolver {


    private static final Logger LOGGER = Logger.getLogger(PageRequestResolverHandler.class.getName());
    private String contextPath;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(PageRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        contextPath = webRequest.getDescription(false);

        String paramPage = webRequest.getParameter("page");
        String paramSize = webRequest.getParameter("size");

        int size = checkPageableParameterValue(paramSize);
        int page = checkPageableParameterValue(paramPage);

        // if size is zero (parameter for page size not provided) set to default value (20)
        size = size == 0 ? size = 20 : size;
        // if size is zero (parameter for page not provided) set to default value (1)
        page = page == 0 ? page = 1 : page;

        return new PageRequest(page, size);
    }

    /**
     * Check pageable parameter value
     *
     * @param parameter
     * @return
     */
    private int checkPageableParameterValue(String parameter) {
        int value = 0;
        if (parameter != null && !parameter.isEmpty() && !parameter.isBlank()) {
            try {
                value = Integer.parseInt(parameter);
            } catch (NumberFormatException e) {
                LOGGER.warning(contextPath + " runned with wrong pageable parameters");
            }
        }
        return value;
    }
}
