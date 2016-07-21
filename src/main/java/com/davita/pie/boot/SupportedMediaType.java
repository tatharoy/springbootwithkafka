package com.davita.pie.boot;


import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by taroy on 4/28/16.
 */
public enum SupportedMediaType {


    PHYSICIAN_V1_MEDIA_TYPE("application/vnd.com.davita.pie.domain.Physician.v1+json"),
	
	FACILITY_V1_MEDIA_TYPE("application/vnd.com.davita.pie.domain.Facility.v1+json");


    /** The string representation of the media type. */
    private final String value;

    /**
     * Enum constructor.
     *
     * @param mt The media type string.
     */
    private SupportedMediaType(@NotEmpty String mt) {
        value = mt;
    }

    /**
     * Returns the string representation of this media type.
     *
     * @return Returns the string representation of this media type.
     */
    public String value() {
        return value;
    }
}
