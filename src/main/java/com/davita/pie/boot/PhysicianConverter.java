package com.davita.pie.boot;

import com.davita.pie.domain.Physician;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * Created by taroy on 4/28/16.
 */
@Component
public class PhysicianConverter implements HttpMessageConverter<Physician> {

        ObjectMapper mapper;

        public PhysicianConverter() {
            mapper = new ObjectMapper();
        }


    public Class<Physician> getResourceType() {
        return Physician.class;
    }



    protected Physician createResource(MediaType mediaType) {
        return new Physician();
    }

    public boolean canRead(Class<?> aClass, MediaType mediaType) {
        return SupportedMediaType.PHYSICIAN_V1_MEDIA_TYPE.value().equals(mediaType.toString());
    }

    public boolean canWrite(Class<?> aClass, MediaType mediaType) {
        return SupportedMediaType.PHYSICIAN_V1_MEDIA_TYPE.value().equals(mediaType.toString());
    }

    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(
                MediaType.valueOf(SupportedMediaType.PHYSICIAN_V1_MEDIA_TYPE.value())
        );
    }

    public Physician read(Class<? extends Physician> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        httpInputMessage.getBody();
        return null;
    }

    public void write(Physician physician, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {

    }
}
