package com.videoClub.model.enumeration;

public enum AgeCategory {

	CHILD,
	TEEN,
	YOUNG_ADULT,
	ADULT,
	ELDER;
	
	public AgeCategory getHigherCategory(AgeCategory category){
		if(category.equals(CHILD)){
			return TEEN;
		}
		else if(category.equals(TEEN)){
			return YOUNG_ADULT;
		}
		else if(category.equals(YOUNG_ADULT)){
			return ADULT;
		}
		else if(category.equals(ADULT)){
			return ELDER;
		}
		else{
			return ELDER;
		}
	}

	public AgeCategory getLowerCategory(AgeCategory category){
		if(category.equals(TEEN)){
			return CHILD;
		}
		else if(category.equals(YOUNG_ADULT)){
			return TEEN;
		}
		else if(category.equals(ADULT)){
			return YOUNG_ADULT;
		}
		else if(category.equals(ELDER)){
			return ADULT;
		}
		else{
			return CHILD;
		}
	}
}
