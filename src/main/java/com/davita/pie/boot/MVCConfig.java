/* Copyright (C) 2015 Covisint. All Rights Reserved. */

package com.davita.pie.boot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;



/**
 * MVC Configuration file ..
 */
@EnableWebMvc
@Configuration("mvcConfig")
public class MVCConfig extends WebMvcConfigurerAdapter {

	/** env property of the {@link MVCConfig} resource. */
	@Autowired
	private Environment env;

	/*private static final Resource[] DEV_PROPERTIES = new ClassPathResource[] {
			new ClassPathResource("config.properties") };

	private static final Resource[] NONDEV_PROPERTIES = new ClassPathResource[] {
			new ClassPathResource("config.properties") };*/


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}


	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
		converters.add(converter());
        //converters.add(phyconverter());
	}

	/**
	 * converter method of the {@link MVCConfig} resource .
	 * 
	 * @return converter .
	 */
	@Bean
	MappingJackson2HttpMessageConverter converter() {
		final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		return converter;
	}

    @Bean
    PhysicianConverter phyconverter() {
        final PhysicianConverter converter = new PhysicianConverter();
        return converter;
    }


    @Bean
	MediaTypeModelMessageConverterViewResolver viewResolver() {
		final MediaTypeModelMessageConverterViewResolver viewResolver = new MediaTypeModelMessageConverterViewResolver(converter());		
		return viewResolver;
	}

   




	/**
	 * getAsyncExecutor method of the {@link MVCConfig} resource .
	 * 
	 * @return executor .
	 */
	@Bean
	public Executor getAsyncExecutor() {
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(10);
		executor.initialize();
		return executor;
	}

//	/**
//	 * properties method of the {@link MVCConfig} resource .
//	 *
//	 * @return PropertySourcesPlaceholderConfigurer .
//	 */
//	@Bean
//	public static PropertySourcesPlaceholderConfigurer properties() {
//		return new PropertySourcesPlaceholderConfigurer();
//	}



}
