package com.videoClub.model.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Rank {
	NONE, BRONZE, SILVER, GOLD
}
