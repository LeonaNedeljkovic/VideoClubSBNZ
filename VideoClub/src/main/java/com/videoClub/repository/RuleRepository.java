package com.videoClub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.videoClub.model.EntityDefiner;
import com.videoClub.model.enumeration.DefinerType;
import com.videoClub.model.enumeration.Rank;

@Repository
public interface RuleRepository extends JpaRepository<EntityDefiner, Long>{

	@Query("SELECT DISTINCT ed.value FROM EntityDefiner ed " +
		    "WHERE ed.type = ?2 AND ed.rank = ?1")
	public List<Integer> getPoints(Rank immunityRank, DefinerType type);
	
	@Transactional
    @Modifying
	@Query("UPDATE EntityDefiner ed " +
			"SET ed.value = ?3 " +
		    "WHERE ed.type = ?2 AND ed.rank = ?1")
	public Integer updatePoints(Rank immunityRank, DefinerType type, int value);
}