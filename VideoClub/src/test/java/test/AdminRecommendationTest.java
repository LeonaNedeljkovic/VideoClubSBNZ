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
import com.videoClub.model.enumeration.AgeCategory;
import com.videoClub.model.enumeration.Genre;

public class AdminRecommendationTest {

	/*
	 * Slucaj kada ce film biti popularan jer
	 * je barem jedan od artista bio uspesan scenarista nekog od filmova u sistemu ili je
	 * bar jedan reditelj je bio uspesan reditelj nekog od filmova u sitemu ili je
	 * bar jedan od glumaca je bio uspesan u nekom od filmova sistema
	 * i zanr je visoko ocenjen ili gledan
	 */

	@Test
	public void test_filmHasGoodElements_allGood() {
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("adminRecommendationRulesSession");
		Artist actor = new Artist(1L, "Brad", "Pitt", new ArrayList<Film>(), new ArrayList<Film>(),
				new ArrayList<Film>());
		Artist scenarist = new Artist(2L, "John", "Smith", new ArrayList<Film>(), new ArrayList<Film>(),
				new ArrayList<Film>());
		Artist director = new Artist(3L, "Sammuel", "Vein", new ArrayList<Film>(), new ArrayList<Film>(),
				new ArrayList<Film>());
		Film film1 = new Film(new Long(1), "Film1", "Best movie ever", Genre.ACTION, 100, 2020, 5.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Film film2 = new Film(new Long(2), "Film2", "Best movie ever", Genre.ACTION, 100, 2020, 2.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Film film3 = new Film(new Long(3), "Film3", "Best movie ever", Genre.ACTION, 100, 2020, 2.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Film film4 = new Film(new Long(4), "Film4", "Best movie ever", Genre.ACTION, 100, 2020, 5.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Film film5 = new Film(new Long(5), "Film5", "Best movie ever", Genre.ACTION, 100, 2020, 4.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Film film6 = new Film(new Long(6), "Film6", "Best movie ever", Genre.ACTION, 100, 2020, 4.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Review review1 = new Review(1L,film1,null,null,false,null,0);
		Review review2 = new Review(2L,film1,null,null,false,null,0);
		Review review3 = new Review(3L,film1,null,null,true,null,5);
		Review review4 = new Review(4L,film1,null,null,true,null,5);
		Review review5 = new Review(2L,film1,null,null,true,null,5);
		kieSession.insert(actor);
		kieSession.insert(scenarist);
		kieSession.insert(director);
		kieSession.insert(film1);
		kieSession.insert(film2);
		kieSession.insert(film3);
		kieSession.insert(film4);
		kieSession.insert(film5);
		kieSession.insert(film6);
		kieSession.insert(review1);
		kieSession.insert(review2);
		kieSession.insert(review3);
		kieSession.insert(review4);
		kieSession.insert(review5);
		MessageDto messageDTO = new MessageDto();
		kieSession.insert(messageDTO);
		kieSession.insert(Genre.ACTION);
		kieSession.fireAllRules();
		assertEquals("Film has good elements",messageDTO.getMessage());
		assertEquals("Film has good elements", messageDTO.getResult());	
	}
	
	/*Slucaj kada film nece biti popularan jer nema popularan zanr niti je barem jedan od artista bio uspesan scenarista 
	 * nekog od filmova u sistemu niti je
	 * bar jedan reditelj je bio uspesan reditelj nekog od filmova u sitemu niti je
	 * bar jedan od glumaca je bio uspesan u nekom od filmova sistema
	 */
	@Test
	public void test_filmDoesntHaveGoodElements_nothingGood() {
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("adminRecommendationRulesSession");
		Artist actor = new Artist(1L, "Brad", "Pitt", new ArrayList<Film>(), new ArrayList<Film>(),
				new ArrayList<Film>());
		Artist scenarist = new Artist(2L, "John", "Smith", new ArrayList<Film>(), new ArrayList<Film>(),
				new ArrayList<Film>());
		Artist director = new Artist(3L, "Sammuel", "Vein", new ArrayList<Film>(), new ArrayList<Film>(),
				new ArrayList<Film>());
		Film film1 = new Film(new Long(1), "Film1", "Best movie ever", Genre.ACTION, 100, 2020, 2.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Film film2 = new Film(new Long(2), "Film2", "Best movie ever", Genre.ACTION, 100, 2020, 2.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Film film3 = new Film(new Long(3), "Film3", "Best movie ever", Genre.ACTION, 100, 2020, 2.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Film film4 = new Film(new Long(4), "Film4", "Best movie ever", Genre.ACTION, 100, 2020, 2.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Film film5 = new Film(new Long(5), "Film5", "Best movie ever", Genre.ACTION, 100, 2020, 2.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Film film6 = new Film(new Long(6), "Film6", "Best movie ever", Genre.ACTION, 100, 2020, 3.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Review review1 = new Review(1L,film1,null,null,false,null,0);
		Review review2 = new Review(2L,film1,null,null,false,null,0);
		Review review3 = new Review(3L,film1,null,null,true,null,5);
		Review review4 = new Review(4L,film1,null,null,true,null,5);
		Review review5 = new Review(2L,film1,null,null,true,null,5);
		kieSession.insert(actor);
		kieSession.insert(scenarist);
		kieSession.insert(director);
		kieSession.insert(film1);
		kieSession.insert(film2);
		kieSession.insert(film3);
		kieSession.insert(film4);
		kieSession.insert(film5);
		kieSession.insert(film6);
		kieSession.insert(review1);
		kieSession.insert(review2);
		kieSession.insert(review3);
		kieSession.insert(review4);
		kieSession.insert(review5);
		MessageDto messageDTO = new MessageDto();
		kieSession.insert(messageDTO);
		kieSession.insert(Genre.COMEDY);
		kieSession.fireAllRules();
		assertEquals("Film doesn't have good elements",messageDTO.getMessage());
		assertEquals("Film doesn't have good elements", messageDTO.getResult());	
	}
	
	/*Zanr je popularan ali artisti nisu*/
	@Test
	public void test_filmHasGoodElements_genrePopularArtistsNot() {
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("adminRecommendationRulesSession");
		Artist actor = new Artist(1L, "Brad", "Pitt", new ArrayList<Film>(), new ArrayList<Film>(),
				new ArrayList<Film>());
		Artist scenarist = new Artist(2L, "John", "Smith", new ArrayList<Film>(), new ArrayList<Film>(),
				new ArrayList<Film>());
		Artist director = new Artist(3L, "Sammuel", "Vein", new ArrayList<Film>(), new ArrayList<Film>(),
				new ArrayList<Film>());
		Artist random = new Artist(4L, "Paper", "Vaj", new ArrayList<Film>(), new ArrayList<Film>(),
				new ArrayList<Film>());
		Film film1 = new Film(new Long(1), "Film1", "Best movie ever", Genre.ACTION, 100, 2020, 5.0, "poster",
				new ArrayList<Artist>(Arrays.asList(random)),random,random, new ArrayList<Review>());
		Film film2 = new Film(new Long(2), "Film2", "Best movie ever", Genre.ACTION, 100, 2020, 4.0, "poster",
				new ArrayList<Artist>(Arrays.asList(random)),random,random, new ArrayList<Review>());
		Film film3 = new Film(new Long(3), "Film3", "Best movie ever", Genre.ACTION, 100, 2020, 5.0, "poster",
				new ArrayList<Artist>(Arrays.asList(random)),random,random, new ArrayList<Review>());
		Film film4 = new Film(new Long(4), "Film4", "Best movie ever", Genre.ACTION, 100, 2020, 5.0, "poster",
				new ArrayList<Artist>(Arrays.asList(random)),random,random, new ArrayList<Review>());
		Film film5 = new Film(new Long(5), "Film5", "Best movie ever", Genre.ACTION, 100, 2020, 4.0, "poster",
				new ArrayList<Artist>(Arrays.asList(random)),random,random, new ArrayList<Review>());
		Film film6 = new Film(new Long(6), "Film6", "Best movie ever", Genre.ACTION, 100, 2020, 3.0, "poster",
				new ArrayList<Artist>(Arrays.asList(random)),random,random, new ArrayList<Review>());
		Review review1 = new Review(1L,film1,null,null,false,null,0);
		Review review2 = new Review(2L,film1,null,null,false,null,0);
		Review review3 = new Review(3L,film1,null,null,true,null,5);
		Review review4 = new Review(4L,film1,null,null,true,null,5);
		Review review5 = new Review(2L,film1,null,null,true,null,5);
		kieSession.insert(actor);
		kieSession.insert(scenarist);
		kieSession.insert(director);
		kieSession.insert(film1);
		kieSession.insert(film2);
		kieSession.insert(film3);
		kieSession.insert(film4);
		kieSession.insert(film5);
		kieSession.insert(film6);
		kieSession.insert(review1);
		kieSession.insert(review2);
		kieSession.insert(review3);
		kieSession.insert(review4);
		kieSession.insert(review5);
		MessageDto messageDTO = new MessageDto();
		kieSession.insert(messageDTO);
		kieSession.insert(Genre.ACTION);
		kieSession.fireAllRules();
		assertEquals("Film has good elements",messageDTO.getMessage());
		assertEquals("Film has good elements", messageDTO.getResult());	
	}
	
	
	/*Artisti su popularni zanr nije
	 */
	@Test
	public void test_filmHasGoodElements_popularArtistsNotGenre() {
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("adminRecommendationRulesSession");
		Artist actor = new Artist(1L, "Brad", "Pitt", new ArrayList<Film>(), new ArrayList<Film>(),
				new ArrayList<Film>());
		Artist scenarist = new Artist(2L, "John", "Smith", new ArrayList<Film>(), new ArrayList<Film>(),
				new ArrayList<Film>());
		Artist director = new Artist(3L, "Sammuel", "Vein", new ArrayList<Film>(), new ArrayList<Film>(),
				new ArrayList<Film>());
		Film film1 = new Film(new Long(1), "Film1", "Best movie ever", Genre.ACTION, 100, 2020, 5.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Film film2 = new Film(new Long(2), "Film2", "Best movie ever", Genre.ACTION, 100, 2020, 5.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Film film3 = new Film(new Long(3), "Film3", "Best movie ever", Genre.ACTION, 100, 2020, 4.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Film film4 = new Film(new Long(4), "Film4", "Best movie ever", Genre.ACTION, 100, 2020, 5.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Film film5 = new Film(new Long(5), "Film5", "Best movie ever", Genre.ACTION, 100, 2020, 4.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Film film6 = new Film(new Long(6), "Film6", "Best movie ever", Genre.ACTION, 100, 2020, 3.0, "poster",
				new ArrayList<Artist>(Arrays.asList(actor)),director,scenarist, new ArrayList<Review>());
		Review review1 = new Review(1L,film1,null,null,false,null,0);
		Review review2 = new Review(2L,film1,null,null,false,null,0);
		Review review3 = new Review(3L,film1,null,null,true,null,5);
		Review review4 = new Review(4L,film1,null,null,true,null,5);
		Review review5 = new Review(2L,film1,null,null,true,null,5);
		kieSession.insert(actor);
		kieSession.insert(scenarist);
		kieSession.insert(director);
		kieSession.insert(film1);
		kieSession.insert(film2);
		kieSession.insert(film3);
		kieSession.insert(film4);
		kieSession.insert(film5);
		kieSession.insert(film6);
		kieSession.insert(review1);
		kieSession.insert(review2);
		kieSession.insert(review3);
		kieSession.insert(review4);
		kieSession.insert(review5);
		MessageDto messageDTO = new MessageDto();
		kieSession.insert(messageDTO);
		kieSession.insert(Genre.COMEDY);
		kieSession.fireAllRules();
		assertEquals("Film has good elements",messageDTO.getMessage());
		assertEquals("Film has good elements", messageDTO.getResult());	
	}
	
	
	

}
