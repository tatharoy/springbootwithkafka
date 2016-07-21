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

import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.http.MediaType;

import com.davita.pie.boot.SupportedMediaType;




/** A class supporting the reading and writing of JSON. */
public final class JsonpSupport {

   

    /** Name of the JSON property carrying the resource's ID. */
    public static final String ID_PROP = "id";


    /** Name of the JSON property carrying the resource reference type. */
    public static final String TYPE_PROP = "type";

    /** Name of the JSON property carrying an i18n "name" map. */
    public static final String NAME_PROP = "name";
    
    /** Name of the JSON property carrying language property. */
    public static final String LANGUAGE_PROP = "lang";
    
    /** Name of the JSON property carrying text property. */
    public static final String TEXT_PROP = "text";

    /** Name of the JSON property carrying an i18n "description" map. */
    public static final String DESCRIPTION_PROP = "description";

    /** Name of the JSON property carrying the child resource references of a parent. */
    public static final String CHILDREN_PROP = "children";

    /** Name of the JSON property carrying the HTTP status. */
    public static final String HTTP_STATUS_PROP = "status";

    /** Name of the JSON property carrying the API status code. */
    public static final String API_STATUS_CODE_PROP = "apiStatusCode";

    /** Name of the JSON property carrying the API message. */
    public static final String API_MESSAGE_PROP = "apiMessage";

    /** Constructor. */
    private JsonpSupport() {
    }

    /**
     * Checks if the given JSON value is an array.
     * 
     * @param value the value to check
     * 
     * @return true if the JSON value is an array
     */
    public static boolean isArray( JsonValue value) {
        return ValueType.ARRAY.equals(value.getValueType());
    }

    /**
     * Checks if the given JSON value is a null.
     * 
     * @param value the value to check
     * 
     * @return true if the JSON value is a null
     */
    public static boolean isNull( JsonValue value) {
        return ValueType.NULL.equals(value.getValueType());
    }

    /**
     * Checks if the given JSON value is an number.
     * 
     * @param value the value to check
     * 
     * @return true if the JSON value is an number
     */
    public static boolean isNumber( JsonValue value) {
        return ValueType.NUMBER.equals(value.getValueType());
    }

    /**
     * Checks if the given JSON value is an object.
     * 
     * @param value the value to check
     * 
     * @return true if the JSON value is an object
     */
    public static boolean isObject( JsonValue value) {
        return ValueType.OBJECT.equals(value.getValueType());
    }

    /**
     * Checks if the given JSON value is an string.
     * 
     * @param value the value to check
     * 
     * @return true if the JSON value is an string
     */
    public static boolean isString( JsonValue value) {
        return ValueType.STRING.equals(value.getValueType());
    }

    /**
     * Adds a property to the given JSON builder if its value is not null.
     * 
     * @param jsonBuilder the builder to which the property will be added
     * @param propertyName the name of the property to add
     * @param propertyValue the value to be added if not null
     */
    public static void addPropertyIfNonnullValue( JsonObjectBuilder jsonBuilder,  String propertyName,
             String propertyValue) {

        final String trimmedValue = org.springframework.util.StringUtils.trimWhitespace(propertyValue);

        if (trimmedValue != null) {
            jsonBuilder.add(propertyName, trimmedValue);
        }
    }

    /**
     * Check if json string property is empty or null.
     * 
     * @param property the value of the property to check.
     * @return returns false if property null or empty else returns true.
     */
    public static boolean isJsonStringPropertyEmptyOrNull( JsonString property) {
        if (property != null && property.getString() != null) {
            return false;
        }
        return true;
    }
    
    public static boolean isMediaTypeSame(SupportedMediaType mt1, MediaType mt2) {
        return mt1.value().equals(mt2.toString());
    }
}
