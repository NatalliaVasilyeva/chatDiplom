package com.bsu.nvasilyeva.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Locale;
import java.util.Properties;

/**
 * Create different beans for application
 */

@Configuration
@EnableAspectJAutoProxy
@EnableWebMvc
@EnableAsync
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.bsu.nvasilyeva"})
public class BeansConfig implements WebMvcConfigurer {

    /**
     * The jsp view resolver is spring built-in view resolver, it will return jsp pages saved in webapp/WEB-INF/views folder.
     * And spring will use the return string to build the jsp page file path with below format webapp/WEB-INF/views/return_string.jsp
     * @return  returned string will be the jsp file name.
     */

    @Bean
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    /**
     * MessageSource bean is spring built-in bean name which will manipulate internationalization messages.
     * All message files is saved in resources/Resource Bundle/ folder
     * Each message file is a properties file, the file base name is messages and suffix with the language or country ISO code, such as messages_en, messages_ru.
     * @return bundleMessage
     */

    @Bean(name ="messageSource")
    public ReloadableResourceBundleMessageSource getBundleMessage(){
        ReloadableResourceBundleMessageSource bundleMessage = new ReloadableResourceBundleMessageSource();
        bundleMessage.setBasename("classpath:messages");
        bundleMessage.setCacheSeconds(1);
        bundleMessage.setDefaultEncoding("UTF-8");
        bundleMessage.setUseCodeAsDefaultMessage(true);
        return bundleMessage;
    }

    /**
     *  The localeResolver is used to resolve user locale data. The CookieLocaleResolver object is used to save user locale information in browser cookie.
     * This way user locale info can be transferred between request. If user disable cookie then you can use SessionLocaleResolver instead.
     * @return localeResolver
     */

    @Bean(name = "localeResolver")
    public CookieLocaleResolver getCookieLocaleResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setCookieName("cookie-locale-info");
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        localeResolver.setCookieMaxAge(3600);
        return localeResolver;
    }


    /**
     *  If user disable cookie then you can use SessionLocaleResolver instead.
     *  @return localeResolver
     */

   @Bean(name = "localeResolver")
    public SessionLocaleResolver getSessionLocaleResolver(){
      SessionLocaleResolver localeResolver = new SessionLocaleResolver();
      localeResolver.setDefaultLocale(Locale.ENGLISH);
      return localeResolver;
    }

    /**
     * The LocaleChangeInterceptor is a interceptor that will intercept user locale change by a parameter value.
     * @return interceptor with parameter"lang"
     */

    @Bean(name="localeInterceptor")
    public LocaleChangeInterceptor getLocaleInterceptor(){
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    /**
     *  Register locale interceptor in spring then it will intercept user request url and parse out the lang query string to get user request locale.
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(getLocaleInterceptor());
    }


    /**
     * Stores registrations of resource handlers for serving static resources such as images, css files and others
     *
     * @param registry use for add information about resource storage to the application
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("resources/**").addResourceLocations("/resources/");
    }

    /**
     * Setting file size 10 mb and encoding of file - "utf-8". Can be changes
     *
     * @return resolver. File upload configuration
     */

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxUploadSize(10485760);
        resolver.setMaxInMemorySize(10485760);
        return resolver;
    }

    /**
     * Send message from this mail to somebody through smpt protocol
     *
     * @return email sender
     */
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        senderImpl.setHost("smtp.gmail.com");
        senderImpl.setPort(587);

        senderImpl.setUsername("natali1111@tut.by");
        senderImpl.setPassword("dremlin1987");

        Properties props = senderImpl.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", "true");

        return senderImpl;

    }

}
