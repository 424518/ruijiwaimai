package com.itheima.reggie.config;

import com.itheima.reggie.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.text.ExtendedMessageFormat;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.smile.MappingJackson2SmileHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author shkstart
 * @create 2022-08-01 14:04
 */
@Slf4j
@Configuration
//使其成为一个配置类
class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 是指静态资源映射
     *
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射........");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
        //ResourceHandler：资源处理器，用来解决静态资源映射路径问题
    }

    /**
     * 扩展mvc框架的消息转换器
     * 因为long的精度只能达到16位，而id有19位，会失去精度
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器.......");
        //创建一个新的消息转换器对象，
        //消息转换器作用：将controller的返回结果转换成json对象。通过输出流输出
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置兑换转换器，底层使用Jackson将java对象转为JSON对象
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //就爱那个上面的消息转换器对象追加到mvc框架的转换器集合中
        converters.add(0,messageConverter);
    }
}
