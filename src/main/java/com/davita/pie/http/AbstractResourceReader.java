package com.davita.pie.http;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.stream.JsonParsingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.davita.pie.domain.BaseEntity;

/**
 * An implementation of {@link EntityReader} that operates on
 * {@link AbstractJpaResource} objects.
 * <p>
 * This base implementation will operate either on a single instance of a
 * resource or a collection of them. When reading in a collection of resource,
 * the root-level JSON structure must be a JSON array containing zero or more
 * resource objects.
 * </p>
 * <p>
 * Implementations of this class must implement the
 * {@link #createResource(MediaType)} and override
 * {@link #readResource(MediaType, JsonObject)}. Be sure to invoke
 * <code>super()</code> when overriding the latter.
 * </p>
 * 
 * @param <T>
 *            the type of resource
 */
public abstract class AbstractResourceReader<T extends BaseEntity> implements EntityReader {

	/** Class logger. */
	private final Logger log = LoggerFactory.getLogger(AbstractResourceReader.class);

	/**
	 * Creates an empty instance of the resource.
	 * 
	 * @param mediaType
	 *            the media type of the HTTP entity being read
	 * 
	 * @return the newly created, unpopulated, resource
	 */

	protected abstract T createResource(MediaType mediaType);

	/**
	 * Reads a JSON array and converts it into a collection of resources.
	 * 
	 * @param mediaType
	 *            the media type of the HTTP entity being read
	 * @param jsonArray
	 *            array of JSON objects being read
	 * 
	 * @return the collection of resources
	 */

	protected List<T> readResourceArray(MediaType mediaType, JsonArray jsonArray) {
		final ArrayList resources = new ArrayList<>();

		for (JsonValue jsonElement : jsonArray) {
			if (JsonpSupport.isObject(jsonElement)) {
				resources.add(readResource(mediaType, (JsonObject) jsonElement));
			} else if (JsonpSupport.isNull(jsonElement)) {
				log.debug("JSON array contained a null element, ignoring it");
			} else {
				log.debug("{} JSON structure is not supported within the root-level array",
						jsonElement.getValueType().toString());
				throw new RuntimeException(jsonElement.getValueType().toString()
						+ " JSON structure of is not supported in root-level array.");
			}
		}

		return resources;
	}

	/**
	 * Reads the information from a JSON object into a resource.
	 * <p>
	 * The default implementation of this method calls
	 * {@link #createResource(MediaType)} to create the empty resource object
	 * and then
	 * {@link #populateAbstractLegacyResourceData(MediaType, JsonObject, AbstractJpaResource)}
	 * to populate all the {@link AbstractJpaResource} fields.
	 * </p>
	 * 
	 * @param mediaType
	 *            the media type of the HTTP entity being read
	 * @param json
	 *            the JSON object being read
	 * 
	 * @return the constructed resource
	 */
	protected T readResource(MediaType mediaType, JsonObject json) {
		final T resource = createResource(mediaType);
		log.debug("Created a resource of type {} into which the JSON data will be read", resource.getClass().getName());

		populateAbstractLegacyResourceData(mediaType, json, resource);

		return resource;
	}

	/**
	 * Populates the fields related to {@link AbstractJpaResource}.
	 * 
	 * @param mediaType
	 *            the media type of the HTTP entity being read
	 * @param json
	 *            the JSON object being read
	 * @param resource
	 *            the resource that will be populated
	 */
	protected void populateAbstractLegacyResourceData(MediaType mediaType, JsonObject json, T resource) {
		JsonString stringValue = json.getJsonString(JsonpSupport.ID_PROP);
		if (!JsonpSupport.isJsonStringPropertyEmptyOrNull(stringValue)) {
			resource.setId(Long.valueOf(stringValue.getString()));
		}

	}

	/** {@inheritDoc} */
	public final Object read(MediaType mediaType, InputStream input) {

		log.debug("Preparing to parse JSON data from inputstream");

		try (final JsonReader jsonReader = Json.createReader(input)) {
			final JsonStructure json = jsonReader.read();
			log.debug("Parsed a JSON structure of type {}", json.getValueType().toString());

			if (JsonpSupport.isArray(json)) {
				return readResourceArray(mediaType, (JsonArray) json);
			} else if (JsonpSupport.isObject(json)) {
				return readResource(mediaType, (JsonObject) json);
			} else {
				log.debug("Root-level JSON structure of type {} is not supported by this reader",
						json.getValueType().toString());
				throw new RuntimeException(
						"Root-level JSON structure of " + json.getValueType().toString() + " is not supported.");
			}
		} catch (JsonParsingException e) {
			log.debug("Invalid JSON, unable to parse", e);
			throw new RuntimeException("Unable to parse JSON", e);
		} catch (JsonException e) {
			// If request JSON body is empty, return empty resource.
			try (final Scanner scanner = new Scanner(input)) {
				if (!scanner.hasNext()) {
					return createResource(mediaType);
				}
			}
			log.debug("Unable to parse JSON", e);
			throw new RuntimeException("Unable to parse JSON", e);
		} catch (IllegalStateException | ClassCastException e) {
			log.debug("Unable to parse JSON", e);
			throw new RuntimeException("Unable to parse JSON", e);
		}
	}
}
