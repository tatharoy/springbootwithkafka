/* Copyright (C) 2013 Covisint. All Rights Reserved. */

package com.davita.pie.boot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpResponse;



import com.davita.pie.http.EntityReader;
import com.davita.pie.http.EntityWriter;


//TODO metrics

/**
 * A {@link HttpMessageConverter} that delegates to {@link MediaTypeMarshaller} and {@link MediaTypeUnmarshaller}
 * instances for reading and writing data to/from data streams.
 * 
 * @param <T> the type of objects marshalled and unmarshalled
 */
public class MediaTypeModelMessageConverter<T> implements HttpMessageConverter<T> {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(MediaTypeModelMessageConverter.class);

    /** The collection of readers supported by this converter. */
    private final List<EntityReader<T>> entityReaders;

    /** The collection of writers supported by this converter. */
    private final List<EntityWriter<T>> entityWriters;



    

    /**
     * Constructor.
     *
     * @param readers the readers supported by this converter
     * @param writers the writers supported by this converter

     */
    public MediaTypeModelMessageConverter(List<EntityReader<T>> readers, List<EntityWriter<T>> writers
            ) {
        if (readers == null) {
            entityReaders = Collections.EMPTY_LIST;
        } else {
            entityReaders = readers;
        }

        if (writers == null) {
            entityWriters = Collections.EMPTY_LIST;
        } else {
            entityWriters = writers;
        }

       
    }

    /** {@inheritDoc} */
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.EMPTY_LIST;
    }

    /** {@inheritDoc} */
    public boolean canRead( final Class<?> clazz,  final MediaType mediaType) {
        final EntityReader reader = getEntityReader(toMediaType(mediaType));
        return reader != null && reader.getResourceType().isAssignableFrom(clazz);
    }

    /** {@inheritDoc} */
    public boolean canWrite( final Class<?> clazz,  final MediaType mediaType) {
        final EntityWriter writer = getEntityWriter(clazz, toMediaType(mediaType));
        return writer != null
                && (Collection.class.isAssignableFrom(clazz) || writer.getResourceType().isAssignableFrom(clazz));
    }

    /** {@inheritDoc} */
    
    public T read( final Class<? extends T> clazz,  final HttpInputMessage inputMessage)
            throws IOException {
        final HttpHeaders httpHeaders = inputMessage.getHeaders();
        final MediaType requestMediaType = toMediaType(httpHeaders.getContentType());

        final EntityReader reader = getEntityReader(requestMediaType);
        if (reader == null) {
            log.debug("No entity reader available for the media type {}", requestMediaType);
            throw new HttpMessageNotReadableException("Unable to read message body");
        }

        try {
            log.debug("Beginning to read entity of content type {} with entity reader of type {}", requestMediaType,
                    reader.getClass().getName());
            final T object = (T) reader.read(requestMediaType, inputMessage.getBody());
            

            
            return object;
        } catch (Exception e) {
            log.error("Unable to read inputstream into an object of type {} ",
                    clazz.getName(), e);
            
        }
        return null;
    }

    /** {@inheritDoc} */
    public void write( final T source,  final MediaType contentType,
             final HttpOutputMessage outputMessage) throws IOException {
        final MediaType requestedMediaType = toMediaType(contentType);

        final EntityWriter writer = getEntityWriter(source.getClass(), requestedMediaType);
        if (writer == null) {
            log.debug("No entity writer available for media type {}", requestedMediaType.toString());
            throw new HttpMessageNotWritableException("Unable to write out message body");
        }

        try {
            log.debug("Beginning to write entity for source object of type {} with entity writer of type {}", source
                    .getClass().getName(), writer.getClass().getName());

            // TODO Using a BAOS to hold the response until after we get the MediaType since the content type needs to
            // be set before anything is written to the response output stream. Need to revisit
            final ByteArrayOutputStream body = new ByteArrayOutputStream();
            final MediaType mediaType = toMediaType(writer.write(requestedMediaType, source, body));

            if (mediaType != null) {
                ((ServletServerHttpResponse) outputMessage).getServletResponse().setContentType(mediaType.toString());
            }

            // Now write to the body after the content type header has been set
            body.writeTo(outputMessage.getBody());

            log.debug("Completed writing entity for source object of type {} with entity writer of type {}", source
                    .getClass().getName(), writer.getClass().getName());
        } catch (Exception e) {
            
            log.error("Unable to write object of type {} ", source.getClass().getName(),e);
            throw new HttpMessageNotWritableException("Unable to write out message body");
        }
    }


    
    private MediaType toMediaType(MediaType mediaType) {
        return MediaType.parseMediaType(mediaType.toString());
    }

    
    private EntityReader getEntityReader(MediaType mediaType) {
        for (EntityReader reader : entityReaders) {
            if (reader.isReadable(mediaType)) {
                return reader;
            }
        }

        return null;
    }

    
    private EntityWriter getEntityWriter(Class<?> clazz, MediaType mediaType) {
        for (EntityWriter writer : entityWriters) {
            if (writer.isWritable(clazz, mediaType)) {
                return writer;
            }
        }

        return null;
    }

    
}