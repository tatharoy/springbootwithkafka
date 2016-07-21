package com.davita.pie.http;



import java.util.HashMap;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.davita.pie.boot.SupportedMediaType;
import com.davita.pie.domain.Physician;



public class PhysicianReader {

    /** Logger for this reader to use. */
    private final Logger log = LoggerFactory.getLogger(PhysicianReader.class);

    /** {@inheritDoc} */
    //@Override
    
  /*  public Class<Physician> getResourceType() {
        return Physician.class;
    }

    *//** {@inheritDoc} *//*
    @Override
    protected Physician createResource( MediaType mediaType) {
        return new Physician();
    }

    *//** {@inheritDoc} *//*
    @Override
    public boolean isReadable( MediaType mediaType) {
        return JsonpSupport.isMediaTypeSame(SupportedMediaType.PHYSICIAN_V1_MEDIA_TYPE, mediaType);
    }

    *//** {@inheritDoc} *//*
    @Override
    public Physician readResource( MediaType mediaType,  JsonObject json) {

        // Read the core resource fields.
        final Physician resource = super.readResource(mediaType, json);

        
        return resource;
    }*/

    /**
     * Read a JSON array of objects containing key/value properties, into a map.
     * 
     * @param json The JSON parent object.
     * @param mapPropertyName The name of the JSON property containing the array.
     * @param keyPropertyName The name of the key property.
     * @param valuePropertyName The name of the value property.
     * @return Returns the map read from the JSON array.
     */
   /* private Map<String, String> readMap( JsonObject json,   String mapPropertyName,
              String keyPropertyName,   String valuePropertyName) {

        final Map<String, String> map = new HashMap<>();

        final JsonArray jsonArray = json.getJsonArray(mapPropertyName);

        if (jsonArray == null) {
            return map;
        }

        for (final JsonValue value : jsonArray) {

            if (!JsonpSupport.isObject(value)) {
                log.debug("Found array item not of type 'object', but {}.  Skipping it.", value.getValueType());
                continue;
            }

            final JsonObject jsonObject = (JsonObject) value;

            JsonString jsonString = jsonObject.getJsonString(keyPropertyName);

            if (JsonpSupport.isJsonStringPropertyEmptyOrNull(jsonString)) {
                log.warn("Expected key property '{}'.  Skipping entry.", keyPropertyName);
                continue;
            }

            final String lang = jsonString.getString();

            jsonString = jsonObject.getJsonString(valuePropertyName);

            if (JsonpSupport.isJsonStringPropertyEmptyOrNull(jsonString)) {
                log.warn("Expected value property '{}'.  Skipping entry.", valuePropertyName);
                continue;
            }

            final String text = jsonString.getString();

            map.put(lang, text);

        }

        return map;
    }*/

}
