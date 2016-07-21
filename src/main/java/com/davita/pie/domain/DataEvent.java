package com.davita.pie.domain;

import com.davita.pie.boot.SupportedMediaType;
import com.davita.pie.kafka.TaskType;

public class DataEvent {

	private TaskType type;

	private SupportedMediaType mediaType;

	private Physician physician;

	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

	

	public Physician getPhysician() {
		return physician;
	}

	public void setPhysician(Physician physician) {
		this.physician = physician;
	}

	public SupportedMediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(SupportedMediaType mediaType) {
		this.mediaType = mediaType;
	}

	@Override
	public String toString() {
		return "DataEvent [type=" + type + ", mediaType=" + mediaType + ", physician=" + physician + "]";
	}

	
}
