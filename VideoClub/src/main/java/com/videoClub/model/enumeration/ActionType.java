package com.videoClub.model.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ActionType {
	FREE_CONTENT, FREE_MINUTES, DISCOUNT
}
