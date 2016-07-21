/* 
 * Copyright 2016 Covisint
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.davita.pie.http;

import java.io.OutputStream;

import org.springframework.http.MediaType;



/**
 * An {@link EntityWriter} writes an object out as an HTTP entity. Such writers are generally specific to a given (set
 * of) media types.
 * 
 * @param <T> the type of object produced by this writer
 */

public interface EntityWriter<T> {

    /**
     * Checks whether this writer could be used to operates upon an object with the given media type and headers.
     * 
     * <p>
     * Implementations of this method should never throw a exception or error; simply return <code>false</code>
     * indicating that the writer can not process the source.
     * </p>
     * 
     * @param clazz the type of the object that will be written out
     * @param mediaType the media type of the HTTP entity to be produced
     * 
     * @return true if this writer can operate upon an object with the given media type and headers, false otherwise
     */
    boolean isWritable( final Class<?> clazz, MediaType mediaType);

    /**
     * Writes the given object out to the given {@link OutputStream}, which represents an HTTP entity.
     * 
     * <p>
     * Implementers should assume that {@link #isWritable(MediaType)} was already invoked and returned <code>true</code>
     * prior to this method being called.
     * </p>
     * <p>
     * Implementers should throw {@link RuntimeException}s if the source could not be processed or message written.
     * </p>
     * 
     * @param mediaType the media type of the HTTP entity being produced
     * @param source the object to be written out as an HTTP entity
     * @param output the stream to which the HTTP entity is written
     * 
     * @return the media type that was produced, including any additional parameters
     */
    MediaType write( MediaType mediaType,  T source,  OutputStream output);
    
    /**
     * Gets the type of resource being write.
     * 
     * @return the type of resource being write.
     */

    Class<T> getResourceType();
}