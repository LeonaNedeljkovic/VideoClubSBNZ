package com.videoClub.comparator;

import java.util.Comparator;

import com.videoClub.model.Artist;

public class ArtistComparator implements Comparator<Artist> {

	@Override
	public int compare(Artist o1, Artist o2) {
        int res =  o1.getName().compareToIgnoreCase(o2.getName());
        if (res != 0)
            return res;
        return o1.getSurname().compareToIgnoreCase(o2.getSurname());
	}
	

}
