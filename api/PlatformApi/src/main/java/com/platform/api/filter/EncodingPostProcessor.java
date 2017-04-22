package com.platform.api.filter;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/20/16.
 */
public class EncodingPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String name)
            throws BeansException
    {
        if (bean instanceof RequestMappingHandlerAdapter) {
            List<HttpMessageConverter<?>> convs = ((RequestMappingHandlerAdapter) bean).getMessageConverters();
            for (HttpMessageConverter<?> conv : convs) {
                if (conv instanceof StringHttpMessageConverter) {
                    ((StringHttpMessageConverter) conv).setSupportedMediaTypes(
                            Arrays.asList(new MediaType("text", "html",
                                    Charset.forName("UTF-8"))));
                }
            }
        }
        return bean;
    }


    public Object postProcessAfterInitialization(Object bean, String name)
            throws BeansException
    {
        return bean;
    }
}
