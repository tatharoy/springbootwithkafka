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

import java.io.InputStream;

import org.springframework.http.MediaType;



/**
 * An {@link EntityReader} reads an HTTP entity and converts it into an object. Such readers are generally specific to a
 * given (set of) media types.
 * 
 * @param <T> the type of object produced by this reader
 */

public interface EntityReader<T> {

    /**
     * Checks whether this reader could be used to operate upon an HTTP message with the given media type and headers.
     * 
     * <p>
     * Implementations of this method should never throw a exception or error; simply return <code>false</code>
     * indicating that the reader can not process the message.
     * </p>
     * 
     * @param mediaType the media type of the HTTP entity to be read
     * 
     * @return true if this reader can operate upon an HTTP message, with the given media type and headers, false
     *         otherwise
     */
    boolean isReadable(MediaType mediaType);

    /**
     * Reads from the given {@link InputStream}, which represents an HTTP entity, and produces an object from the data.
     * 
     * <p>
     * Implementers should assume that {@link #isReadable(MediaType)} was already invoked and returned <code>true</code>
     * prior to this method being called.
     * </p>
     * <p>
     * Implementers should throw {@link RuntimeException}s if the message could not be processed.
     * </p>
     * 
     * @param mediaType the media type of the HTTP entity
     * @param input the stream from which the HTTP entity is read
     * 
     * @return the constructed object
     */

    T read(MediaType mediaType, InputStream input);

    /**
     * Gets the type of resource being read.
     * 
     * @return the type of resource being read.
     */
    
    Class<T> getResourceType();

}