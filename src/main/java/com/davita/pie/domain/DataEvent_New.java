package com.davita.pie.domain;

import com.davita.pie.boot.SupportedMediaType;
import com.davita.pie.kafka.TaskType;

public class DataEvent_New {

	private TaskType type;

	private SupportedMediaType mediaType;

	private BaseEntity entity;

	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

	public BaseEntity getEntity() {
		return entity;
	}

	public void setEntity(BaseEntity entity) {
		this.entity = entity;
	}

	public SupportedMediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(SupportedMediaType mediaType) {
		this.mediaType = mediaType;
	}

	@Override
	public String toString() {
		return "DataEvent [type=" + type + ", mediaType=" + mediaType + ", entity=" + entity + "]";
	}

	
}
