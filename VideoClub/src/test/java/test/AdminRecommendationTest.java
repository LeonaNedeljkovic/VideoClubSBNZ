package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.videoClub.dto.MessageDto;
import com.videoClub.model.Artist;
import com.videoClub.model.Film;
import com.videoClub.model.Review;
import com.videoClub.model.drl.ArtistFlag;
import com.videoClub.model.enumeration.AgeCategory;
import com.videoClub.model.enumeration.Genre;

public class AdminRecommendationTest {

	/*
	 * Slucaj kada film nece biti popularan jer nema dobrog reditelja, scenaristu
	 * niti glumce (ne postoje ArtistReviewBadges ni ArtistRateBadges ni za jednog
	 * od njih)
	 */

	@Test
	public void test_filmWontBePopular() {
		Artist actor = new Artist(1L, "Brad", "Pitt", new ArrayList<Film>(), new ArrayList<Film>(),
				new ArrayList<Film>());
		Artist scenarist = new Artist(2L, "John", "Smith", new ArrayList<Film>(), new ArrayList<Film>(),
				new ArrayList<Film>());
		Artist director = new Artist(3L, "Sammuel", "Vein", new ArrayList<Film>(), new ArrayList<Film>(),
				new ArrayList<Film>());
		MessageDto messageDto = new MessageDto();
		
		Film film = new Film(new Long(1), "New movie", "Best movie ever", Genre.ACTION, 100, 2020, 0.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>(), new ArrayList<AgeCategory>());
		actor.getRoles().add(film);
		scenarist.getWritten().add(film);
		director.getDirected().add(film);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("adminRecommendationRulesSession");
		kieSession.setGlobal("usersNumber", 4);
		kieSession.insert(film);
		kieSession.insert(messageDto);
		kieSession.fireAllRules();
		assertEquals("Film wont be successfull", messageDto.getMessage());
		assertEquals(
				"Neither director nor scenarist nor actors are popular on this platform, so, don't expect too much from this film.",
				messageDto.getResult());
	}

	/*
	 * Slucaj kada film nece biti popularan jer glumci, scenarista i reditelj nisu
	 * popularni tj broj ArtistReviewBadges za njih je manji od 50% ukupnog broja
	 * korisnika
	 */
	@Test
	public void test_filmWontBePopular2() {
		Artist actor = new Artist(1L, "Brad","Pitt",new ArrayList<Film>(),new ArrayList<Film>(),new ArrayList<Film>());
		Artist scenarist = new Artist(2L, "John","Smith",new ArrayList<Film>(),new ArrayList<Film>(),new ArrayList<Film>());
		Artist director = new Artist(3L, "Sammuel","Vein",new ArrayList<Film>(),new ArrayList<Film>(),new ArrayList<Film>());
		MessageDto messageDto = new MessageDto();
		Film film = new Film(new Long(1), "New movie", "Best movie ever", Genre.ACTION, 100, 2020, 0.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>(), new ArrayList<AgeCategory>());
		ArtistFlag actorReviewBadge = new ArtistFlag(actor, 1, 1);
		ArtistFlag scenaristReviewBadge = new ArtistFlag(scenarist, 1, 1);
		ArtistFlag directorReviewBadge = new ArtistFlag(director, 1, 1);
		actor.getRoles().add(film);
		scenarist.getWritten().add(film);
		director.getDirected().add(film);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("adminRecommendationRulesSession");
		kieSession.setGlobal("usersNumber", 4);
		kieSession.insert(film);
		kieSession.insert(messageDto);
		kieSession.insert(actorReviewBadge);
		kieSession.insert(scenaristReviewBadge);
		kieSession.insert(scenaristReviewBadge);
		kieSession.insert(directorReviewBadge);
		kieSession.fireAllRules();
		assertEquals("Film wont be successfull",messageDto.getMessage());
		assertEquals("Neither director nor scenarist nor actors are popular on this platform, so, don't expect too much from this film.", messageDto.getResult());
		
	}

	/*
	 * Film ce moguce biti uspesan
	 POPULARAN DIRECTOR*/
	@Test
	public void test_filmMightBeSuccessful_popularDirector() {
		Artist actor = new Artist(1L, "Brad","Pitt",new ArrayList<Film>(),new ArrayList<Film>(),new ArrayList<Film>());
		Artist scenarist = new Artist(2L, "John","Smith",new ArrayList<Film>(),new ArrayList<Film>(),new ArrayList<Film>());
		Artist director = new Artist(3L, "Sammuel","Vein",new ArrayList<Film>(),new ArrayList<Film>(),new ArrayList<Film>());
		MessageDto messageDto = new MessageDto();
		Film film = new Film(new Long(1), "New movie", "Best movie ever", Genre.ACTION, 100, 2020, 0.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>(), new ArrayList<AgeCategory>());
		actor.getRoles().add(film);
		scenarist.getWritten().add(film);
		director.getDirected().add(film);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("adminRecommendationRulesSession");
		kieSession.setGlobal("usersNumber", 2);
		kieSession.insert(film);
		kieSession.insert(messageDto);
		kieSession.insert(new ArtistFlag(director,2,0));
		kieSession.insert(new ArtistFlag(director,2,0));
		kieSession.insert(new ArtistFlag(director,2,0));
		kieSession.insert(new ArtistFlag(director,5,0));
		kieSession.insert(new ArtistFlag(director,5,0));
		kieSession.insert(new ArtistFlag(director,5,0));
		kieSession.insert(new ArtistFlag(director,5,0));
		kieSession.fireAllRules();
		assertEquals("Film might be successfull",messageDto.getMessage());
		assertEquals("Users like watching some artists in this film, so, this film may be popular.", messageDto.getResult());
		
	}
	
	/*
	 * Film ce moguce biti uspesan
	 POPULARAN SCENARISTA*/
	@Test
	public void test_filmMightBeSuccessful_popularScenarist() {
		Artist actor = new Artist(1L, "Brad","Pitt",new ArrayList<Film>(),new ArrayList<Film>(),new ArrayList<Film>());
		Artist scenarist = new Artist(2L, "John","Smith",new ArrayList<Film>(),new ArrayList<Film>(),new ArrayList<Film>());
		Artist director = new Artist(3L, "Sammuel","Vein",new ArrayList<Film>(),new ArrayList<Film>(),new ArrayList<Film>());
		MessageDto messageDto = new MessageDto();
		Film film = new Film(new Long(1), "New movie", "Best movie ever", Genre.ACTION, 100, 2020, 0.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>(), new ArrayList<AgeCategory>());
		actor.getRoles().add(film);
		scenarist.getWritten().add(film);
		director.getDirected().add(film);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("adminRecommendationRulesSession");
		kieSession.setGlobal("usersNumber", 2);
		kieSession.insert(film);
		kieSession.insert(messageDto);
		kieSession.insert(new ArtistFlag(scenarist,2,0));
		kieSession.insert(new ArtistFlag(scenarist,2,0));
		kieSession.insert(new ArtistFlag(scenarist,2,0));
		kieSession.insert(new ArtistFlag(scenarist,5,0));
		kieSession.insert(new ArtistFlag(scenarist,5,0));
		kieSession.insert(new ArtistFlag(scenarist,5,0));
		kieSession.insert(new ArtistFlag(scenarist,5,0));
		kieSession.fireAllRules();
		assertEquals("Film might be successfull",messageDto.getMessage());
		assertEquals("Users like watching some artists in this film, so, this film may be popular.", messageDto.getResult());
		
	}
	
	/*
	 * Film ce moguce biti uspesan
	 POPULARAN GLUMAC*/
	@Test
	public void test_filmMightBeSuccessful_popularActors() {
		Artist actor = new Artist(1L, "Brad","Pitt",new ArrayList<Film>(),new ArrayList<Film>(),new ArrayList<Film>());
		Artist scenarist = new Artist(2L, "John","Smith",new ArrayList<Film>(),new ArrayList<Film>(),new ArrayList<Film>());
		Artist director = new Artist(3L, "Sammuel","Vein",new ArrayList<Film>(),new ArrayList<Film>(),new ArrayList<Film>());
		MessageDto messageDto = new MessageDto();
		Film film = new Film(new Long(1), "New movie", "Best movie ever", Genre.ACTION, 100, 2020, 0.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>(), new ArrayList<AgeCategory>());
		actor.getRoles().add(film);
		scenarist.getWritten().add(film);
		director.getDirected().add(film);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("adminRecommendationRulesSession");
		kieSession.setGlobal("usersNumber", 2);
		kieSession.insert(film);
		kieSession.insert(messageDto);
		kieSession.insert(new ArtistFlag(actor,2,0));
		kieSession.insert(new ArtistFlag(actor,2,0));
		kieSession.insert(new ArtistFlag(actor,2,0));
		kieSession.insert(new ArtistFlag(actor,5,0));
		kieSession.insert(new ArtistFlag(actor,5,0));
		kieSession.insert(new ArtistFlag(actor,5,0));
		kieSession.insert(new ArtistFlag(actor,5,0));
		kieSession.fireAllRules();
		assertEquals("Film might be successfull",messageDto.getMessage());
		assertEquals("Users like watching some artists in this film, so, this film may be popular.", messageDto.getResult());
		
	}
	
	/*
	 * Film ce moguce biti uspesan
	 POPULARAN GLUMAC, REDITELJ I SCENARISTA*/
	@Test
	public void test_filmWillBeSuccessful() {
		Artist actor = new Artist(1L, "Brad","Pitt",new ArrayList<Film>(),new ArrayList<Film>(),new ArrayList<Film>());
		Artist scenarist = new Artist(2L, "John","Smith",new ArrayList<Film>(),new ArrayList<Film>(),new ArrayList<Film>());
		Artist director = new Artist(3L, "Sammuel","Vein",new ArrayList<Film>(),new ArrayList<Film>(),new ArrayList<Film>());
		MessageDto messageDto = new MessageDto();
		Film film = new Film(new Long(1), "New movie", "Best movie ever", Genre.ACTION, 100, 2020, 0.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>(), new ArrayList<AgeCategory>());
		actor.getRoles().add(film);
		scenarist.getWritten().add(film);
		director.getDirected().add(film);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("adminRecommendationRulesSession");
		kieSession.setGlobal("usersNumber", 2);
		kieSession.insert(film);
		kieSession.insert(messageDto);
		kieSession.insert(new ArtistFlag(actor,2,0));
		kieSession.insert(new ArtistFlag(actor,2,0));
		kieSession.insert(new ArtistFlag(actor,2,0));
		kieSession.insert(new ArtistFlag(actor,5,0));
		kieSession.insert(new ArtistFlag(actor,5,0));
		kieSession.insert(new ArtistFlag(actor,5,0));
		kieSession.insert(new ArtistFlag(actor,5,0));
		
		
		kieSession.insert(new ArtistFlag(scenarist,2,0));
		kieSession.insert(new ArtistFlag(scenarist,2,0));
		kieSession.insert(new ArtistFlag(scenarist,2,0));
		kieSession.insert(new ArtistFlag(scenarist,5,0));
		kieSession.insert(new ArtistFlag(scenarist,5,0));
		kieSession.insert(new ArtistFlag(scenarist,5,0));
		kieSession.insert(new ArtistFlag(scenarist,5,0));
		
		
		kieSession.insert(new ArtistFlag(director,2,0));
		kieSession.insert(new ArtistFlag(director,2,0));
		kieSession.insert(new ArtistFlag(director,2,0));
		kieSession.insert(new ArtistFlag(director,5,0));
		kieSession.insert(new ArtistFlag(director,5,0));
		kieSession.insert(new ArtistFlag(director,5,0));
		kieSession.insert(new ArtistFlag(director,5,0));

		kieSession.fireAllRules();
		assertEquals("Film will be successfull",messageDto.getMessage());
		assertEquals("Film will be successfull", messageDto.getResult());
		
	}

}
