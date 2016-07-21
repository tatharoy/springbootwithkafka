/* Copyright (C) 2013 Covisint. All Rights Reserved. */

package com.davita.pie.boot;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractView;



/**
 * A {@link ViewResolver} that returns a view that renders using a {@link MediaTypeModelMessageConverter}. The idea
 * being that this message converter is used to read in message bodies and convert them to models.
 */

public class MediaTypeModelMessageConverterViewResolver implements ViewResolver {

    /** The view that gets returned by this resolver. */
    private final MessageConverterModelView view;
    
   
    /**
     * Constructor.
     * 
     * @param messageConverter the message converter used to generate the outbound responses
     */
    public MediaTypeModelMessageConverterViewResolver(MappingJackson2HttpMessageConverter converter) {
        view = new MessageConverterModelView(converter);
    }

    /** {@inheritDoc} */
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return view;
    }

    /**
     * A view that supports a model populated using {@link ModelSupport} and whose body is written out using a
     * {@link HttpMessageConverter}.
     */
    private static class MessageConverterModelView extends AbstractView {

        

        /** Class logger. */
        private final Logger log = LoggerFactory.getLogger(MessageConverterModelView.class);

        /** Converter used to write out the message body. */
        private final MappingJackson2HttpMessageConverter converter;

        

        /**
         * Constructor. Set the {@link #defaultCacheAge} to 5 minutes (300 seconds).
         * 
         * @param bodyConverter converter used to write out the message body
         */
        public MessageConverterModelView(final MappingJackson2HttpMessageConverter bodyConverter) {
            converter = bodyConverter;
            
        }

        /** {@inheritDoc} */
        protected void renderMergedOutputModel(final Map<String, Object> model,
                final HttpServletRequest request, final HttpServletResponse response)
                throws Exception {
            try {
                
            	MediaType mediaType = MediaType.parseMediaType("application/json");
                    renderModel(mediaType, model, request, response);
                
            } catch (Throwable e) {
                log.error("Unable to write out response", e);
                response.setStatus(500);
            }
        }



        /**
         * Sets the HTTP status on the response based on {@link ModelSupport#getStatusCode(Map)}.
         * 
         * @param model the mode
         * @param request the request
         * @param response the response
         */
        private void setHttpStatus(final Map<String, Object> model, final HttpServletRequest request,
                final HttpServletResponse response) {
            final Integer statusCode = (Integer)model.get("status");
            if (statusCode != null) {
                log.debug("Setting HTTP status code to {}", statusCode);
                response.setStatus(statusCode);
            } else {
                log.debug("Setting HTTP status code to {}", 200);
                response.setStatus(200);
            }
        }

        /**
         * Sets the body for the response based on {@link ModelSupport#getBody(Map)}.
         * 
         * @param model the mode
         * @param responseMediaType the media type of the response
         * @param request the request
         * @param response the response
         * 
         * @throws IOException thrown if there is a problem writing to the HTTP response
         */
        private void setBody(final Map<String, Object> model, final MediaType responseMediaType,
                final HttpServletRequest request, final HttpServletResponse response)
                throws IOException {
            final Object body = model.get("body");
            if (body == null) {
                log.debug("No body was available in the model to be sent in the response");
                return;
            }
            //response.setCharacterEncoding(Charsets.UTF_8.toString());
            try {
                converter.write(body, responseMediaType, new ServletServerHttpResponse(response));
            } catch (HttpMessageNotWritableException e) {
               log.error("error publishing response ",e);
            }
            response.flushBuffer();
        }

     

       

        /**
         * Render the model.
         * 
         * @param mediaType the media type of the response
         * @param model the model
         * @param request the request
         * @param response the response
         */
        protected void renderModel(final MediaType mediaType, final Map<String, Object> model,
                 final HttpServletRequest request, final HttpServletResponse response) {
            try {
                setHttpStatus(model, request, response);
                setBody(model, mediaType, request, response);
            } catch (Throwable e) {
                log.error("Unable to write out response", e);
                response.setStatus(500);
            }
        }

        
    }
}