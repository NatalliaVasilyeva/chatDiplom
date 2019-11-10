package com.bsu.nvasilyeva.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;

/**
 * Register configuration in spring context
 */

public class DispatcherServletConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Max size of upload file
     */

    private int maxUploadSizeInMb = 5 * 1024 * 1024;

    /**
     * Add configurations which init beans
     *
     * @return configure class
     */

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{};
    }

    /**
     * Add configuration which init ViewResolver
     *
     * @return configure class
     */

    @Override
    protected Class<?>[] getServletConfigClasses() {

        return new Class[]{BeansConfig.class};
    }

    /**
     * Set path af application
     *
     * @return path
     */

    @Override
    protected String[] getServletMappings() {

        return new String[]{"/"};
    }

    /**
     * Set upload directory and register a MultipartConfigElement
     *
     * @param registration use to set multipart config
     */

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);
        registration.setMultipartConfig(multipartConfigElement);

    }
}
