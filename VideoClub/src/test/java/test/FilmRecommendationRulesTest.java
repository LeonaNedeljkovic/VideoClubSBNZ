package test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ObjectFilter;

import com.videoClub.model.Action;
import com.videoClub.model.Artist;
import com.videoClub.model.Film;
import com.videoClub.model.Notification;
import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.TimeInterval;
import com.videoClub.model.drl.RecommendedFilm;
import com.videoClub.model.enumeration.AgeCategory;
import com.videoClub.model.enumeration.Genre;
import com.videoClub.model.enumeration.Rank;

public class FilmRecommendationRulesTest {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	private static List<Film> films = createListOfFilms();
	
	/*
	 * Testiramo preporuke kada korisnik nema preglede
	 * (ocekujemo praznu listu preporuka)
	 */
	@Test
	public void test1(){
		System.out.println("\nNo reviews test:");
		List<RecommendedFilm> recommendations = new ArrayList<RecommendedFilm>();
		RegisteredUser user = createRegisteredUser(1L);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("filmRecommendationRulesSession");
		kieSession.insert(user);
		for(Artist a : createListOfArtists()){
			kieSession.insert(a);
		}
		insertGenres(kieSession);
		kieSession.getAgenda().getAgendaGroup("user-flags").setFocus();
		kieSession.fireAllRules();
		for(Film f : films){
			kieSession.insert(f);
		}
		kieSession.getAgenda().getAgendaGroup("flags-recommendation").setFocus();
		kieSession.fireAllRules();
		Collection<?> output = kieSession.getObjects(new RecommendedFilmObjectFilter());
		for(Object o : output){
			RecommendedFilm rf = (RecommendedFilm) o; 
			recommendations.add(rf);
		}
		kieSession.dispose();
		for(RecommendedFilm rf : recommendations){
			assertTrue(0 >= rf.getRecommendPoints());
		}
	}
	
	@Test
	public void test2(){
		System.out.println("\n1 unwatched film test:");
		List<RecommendedFilm> recommendedFilms = new ArrayList<RecommendedFilm>();
		RegisteredUser user = createRegisteredUser(1L);
		Review review = createUnwatchedReview(1L, user, films.get(0));
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("filmRecommendationRulesSession");
		kieSession.insert(user);
		for(Artist a : films.get(0).getActors()){
			kieSession.insert(a);
		}
		insertGenres(kieSession);
		kieSession.insert(review.getFilm().getDirector());
		kieSession.insert(review.getFilm().getWrittenBy());
		kieSession.getAgenda().getAgendaGroup("user-flags").setFocus();
		kieSession.fireAllRules();
		for(Film f : films){
			if(f.getId() != 1L){
				kieSession.insert(f);
			}
		}
		kieSession.getAgenda().getAgendaGroup("flags-recommendation").setFocus();
		kieSession.fireAllRules();
		Collection<?> output = kieSession.getObjects(new RecommendedFilmObjectFilter());
		for(Object o : output){
			RecommendedFilm rf = (RecommendedFilm) o; 
			recommendedFilms.add(rf);
		}
		kieSession.dispose();
		
		List<Long> notRecommendedFilms = new ArrayList<Long>();
		notRecommendedFilms.add(1L);
		notRecommendedFilms.add(2L);
		notRecommendedFilms.add(3L);
		notRecommendedFilms.add(7L);
		notRecommendedFilms.add(9L);
		
		for(RecommendedFilm rf : recommendedFilms){
			if(notRecommendedFilms.contains(rf.getFilm().getId())){
				assertTrue(0 > rf.getRecommendPoints());
			}
			else{
				assertTrue(0 > rf.getRecommendPoints());
			}
		}
	}
	
	
	@Test
	public void test3(){
		System.out.println("\n1 watched film test:");
		RegisteredUser user = createRegisteredUser(1L);
		Review review = createWatchedReview(1L, user, films.get(0));
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("filmRecommendationRulesSession");
		kieSession.insert(user);
		for(Artist a : films.get(0).getActors()){
			kieSession.insert(a);
		}
		kieSession.insert(review.getFilm().getDirector());
		kieSession.insert(review.getFilm().getWrittenBy());
		kieSession.getAgenda().getAgendaGroup("user-flags").setFocus();
		kieSession.fireAllRules();
		
		List<Long> recommendedFilms = new ArrayList<Long>();
		recommendedFilms.add(2L);
		recommendedFilms.add(3L);
		recommendedFilms.add(7L);
		recommendedFilms.add(9L);
		
		List<RecommendedFilm> recommendations = new ArrayList<RecommendedFilm>();
		for(Film f : films){
			if(f.getId() != 1L){
				kieSession.insert(f);
			}
		}
		kieSession.getAgenda().getAgendaGroup("flags-recommendation").setFocus();
		kieSession.fireAllRules();
		Collection<?> output = kieSession.getObjects(new RecommendedFilmObjectFilter());
		for(Object o : output){
			RecommendedFilm rf = (RecommendedFilm) o; 
			recommendations.add(rf);
		}
		kieSession.dispose();
		
		for(RecommendedFilm rf : recommendations){
			if(recommendedFilms.contains(rf.getFilm().getId())){
				assertTrue(rf.getRecommendPoints() > 0);
			}
			else{
				assertEquals(0, rf.getRecommendPoints(), 0.001);
			}
		}
	}
	
	
	@Test
	public void test4(){
		System.out.println("\n1 rated film, rate = 5, film added to favourites:");
		RegisteredUser user = createRegisteredUser(1L);
		Review review = createWatchedReview(1L, user, films.get(0));
		review.setRate(5);
		user.getReviews().add(review);
		user.getFavouriteFilms().add(review.getFilm());
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("filmRecommendationRulesSession");
		kieSession.insert(user);
		for(Artist a : films.get(0).getActors()){
			kieSession.insert(a);
		}
		kieSession.insert(review.getFilm().getDirector());
		kieSession.insert(review.getFilm().getWrittenBy());
		kieSession.getAgenda().getAgendaGroup("user-flags").setFocus();
		kieSession.fireAllRules();
		
		List<Long> recommendedFilms = new ArrayList<Long>();
		recommendedFilms.add(2L);
		recommendedFilms.add(3L);
		recommendedFilms.add(7L);
		recommendedFilms.add(9L);
		
		List<RecommendedFilm> recommendations = new ArrayList<RecommendedFilm>();
		for(Film f : films){
			if(f.getId() != 1L){
				kieSession.insert(f);
			}
		}
		kieSession.getAgenda().getAgendaGroup("flags-recommendation").setFocus();
		kieSession.fireAllRules();
		Collection<?> output = kieSession.getObjects(new RecommendedFilmObjectFilter());
		for(Object o : output){
			RecommendedFilm rf = (RecommendedFilm) o; 
			recommendations.add(rf);
		}
		kieSession.dispose();
		
		for(RecommendedFilm rf : recommendations){
			if(recommendedFilms.contains(rf.getFilm().getId())){
				assertTrue(rf.getRecommendPoints() > 0);
			}
			else{
				assertEquals(0, rf.getRecommendPoints(), 0.001);
			}
		}
	}
	
	
	@Test
	public void test5(){
		System.out.println("\n1 rated film, rate = 4, film added to favourites:");
		RegisteredUser user = createRegisteredUser(1L);
		Review review = createWatchedReview(1L, user, films.get(0));
		review.setRate(4);
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("filmRecommendationRulesSession");
		for(Artist a : films.get(0).getActors()){
			kieSession.insert(a);
		}
		kieSession.insert(user);
		kieSession.insert(review.getFilm().getDirector());
		kieSession.insert(review.getFilm().getWrittenBy());
		kieSession.getAgenda().getAgendaGroup("user-flags").setFocus();
		kieSession.fireAllRules();
		
		List<Long> recommendedFilms = new ArrayList<Long>();
		recommendedFilms.add(2L);
		recommendedFilms.add(3L);
		recommendedFilms.add(7L);
		recommendedFilms.add(9L);
		
		List<RecommendedFilm> recommendations = new ArrayList<RecommendedFilm>();
		for(Film f : films){
			if(f.getId() != 1L){
				kieSession.insert(f);
			}
		}
		kieSession.getAgenda().getAgendaGroup("flags-recommendation").setFocus();
		kieSession.fireAllRules();
		
		Collection<?> output = kieSession.getObjects(new RecommendedFilmObjectFilter());
		for(Object o : output){
			RecommendedFilm rf = (RecommendedFilm) o; 
			recommendations.add(rf);
		}
		kieSession.dispose();
		
		for(RecommendedFilm rf : recommendations){
			if(recommendedFilms.contains(rf.getFilm().getId())){
				assertTrue(rf.getRecommendPoints() > 0);
			}
			else{
				assertEquals(0, rf.getRecommendPoints(), 0.001);
			}
		}
	}
	
	
	@Test
	public void test6(){
		System.out.println("\n1 rated film, rate = 3, film added to favourites:");
		RegisteredUser user = createRegisteredUser(1L);
		Review review = createWatchedReview(1L, user, films.get(0));
		review.setRate(3);
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("filmRecommendationRulesSession");
		kieSession.insert(user);
		for(Artist a : films.get(0).getActors()){
			kieSession.insert(a);
		}
		kieSession.insert(review.getFilm().getDirector());
		kieSession.insert(review.getFilm().getWrittenBy());
		kieSession.getAgenda().getAgendaGroup("user-flags").setFocus();
		kieSession.fireAllRules();
		
		List<Long> recommendedFilms = new ArrayList<Long>();
		recommendedFilms.add(2L);
		recommendedFilms.add(3L);
		recommendedFilms.add(7L);
		recommendedFilms.add(9L);
		
		List<RecommendedFilm> recommendations = new ArrayList<RecommendedFilm>();
		for(Film f : films){
			if(f.getId() != 1L){
				kieSession.insert(f);
			}
		}
		kieSession.getAgenda().getAgendaGroup("flags-recommendation").setFocus();
		kieSession.fireAllRules();
		Collection<?> output = kieSession.getObjects(new RecommendedFilmObjectFilter());
		for(Object o : output){
			RecommendedFilm rf = (RecommendedFilm) o; 
			recommendations.add(rf);
		}
		kieSession.dispose();
		
		for(RecommendedFilm rf : recommendations){
			if(recommendedFilms.contains(rf.getFilm().getId())){
				assertTrue(rf.getRecommendPoints() < 0);
			}
			else{
				assertEquals(0, rf.getRecommendPoints(), 0.001);
			}
		}
	}
	
	
	@Test
	public void test7(){
		System.out.println("\n1 rated film, rate = 2, film added to favourites:");
		RegisteredUser user = createRegisteredUser(1L);
		Review review = createWatchedReview(1L, user, films.get(0));
		review.setRate(2);
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("filmRecommendationRulesSession");
		kieSession.insert(user);
		for(Artist a : films.get(0).getActors()){
			kieSession.insert(a);
		}
		kieSession.insert(review.getFilm().getDirector());
		kieSession.insert(review.getFilm().getWrittenBy());
		kieSession.getAgenda().getAgendaGroup("user-flags").setFocus();
		kieSession.fireAllRules();
		
		List<Long> recommendedFilms = new ArrayList<Long>();
		recommendedFilms.add(2L);
		recommendedFilms.add(3L);
		recommendedFilms.add(7L);
		recommendedFilms.add(9L);
		
		List<RecommendedFilm> recommendations = new ArrayList<RecommendedFilm>();
		for(Film f : films){
			if(f.getId() != 1L){
				kieSession.insert(f);
			}
		}
		kieSession.getAgenda().getAgendaGroup("flags-recommendation").setFocus();
		kieSession.fireAllRules();
		Collection<?> output = kieSession.getObjects(new RecommendedFilmObjectFilter());
		for(Object o : output){
			RecommendedFilm rf = (RecommendedFilm) o; 
			recommendations.add(rf);
		}
		kieSession.dispose();
		
		for(RecommendedFilm rf : recommendations){
			if(recommendedFilms.contains(rf.getFilm().getId())){
				assertTrue(rf.getRecommendPoints() < 0);
			}
			else{
				assertEquals(0, rf.getRecommendPoints(), 0.001);
			}
		}
	}
	
	
	@Test
	public void test8(){
		System.out.println("\n1 rated film, rate = 1, film added to favourites:");
		RegisteredUser user = createRegisteredUser(1L);
		Review review = createWatchedReview(1L, user, films.get(0));
		review.setRate(1);
		user.getReviews().add(review);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("filmRecommendationRulesSession");
		kieSession.insert(user);
		for(Artist a : films.get(0).getActors()){
			kieSession.insert(a);
		}
		kieSession.insert(review.getFilm().getDirector());
		kieSession.insert(review.getFilm().getWrittenBy());
		kieSession.getAgenda().getAgendaGroup("user-flags").setFocus();
		kieSession.fireAllRules();
		
		List<Long> recommendedFilms = new ArrayList<Long>();
		recommendedFilms.add(2L);
		recommendedFilms.add(3L);
		recommendedFilms.add(7L);
		recommendedFilms.add(9L);
		
		List<RecommendedFilm> recommendations = new ArrayList<RecommendedFilm>();
		for(Film f : films){
			if(f.getId() != 1L){
				kieSession.insert(f);
			}
		}
		kieSession.getAgenda().getAgendaGroup("flags-recommendation").setFocus();
		kieSession.fireAllRules();
		Collection<?> output = kieSession.getObjects(new RecommendedFilmObjectFilter());
		for(Object o : output){
			RecommendedFilm rf = (RecommendedFilm) o; 
			recommendations.add(rf);
		}
		kieSession.dispose();
		
		for(RecommendedFilm rf : recommendations){
			if(recommendedFilms.contains(rf.getFilm().getId())){
				assertTrue(rf.getRecommendPoints() < 0);
			}
			else{
				assertEquals(0, rf.getRecommendPoints(), 0.001);
			}
		}
	}
	
	
	@Test
	public void test9(){
		System.out.println("\n2 rated film, rates = 4 and 5:");
		RegisteredUser user = createRegisteredUser(1L);
		Review review1 = createWatchedReview(1L, user, films.get(0));
		Review review2 = createWatchedReview(1L, user, films.get(1));
		review1.setRate(5);
		review2.setRate(4);
		user.getReviews().add(review1);
		user.getReviews().add(review2);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("filmRecommendationRulesSession");
		kieSession.insert(user);
		for(Artist a : films.get(0).getActors()){
			kieSession.insert(a);
		}
		kieSession.insert(review1.getFilm().getDirector());
		kieSession.insert(review1.getFilm().getWrittenBy());
		kieSession.insert(review2.getFilm().getDirector());
		kieSession.insert(review2.getFilm().getWrittenBy());
		kieSession.getAgenda().getAgendaGroup("user-flags").setFocus();
		kieSession.fireAllRules();
		
		List<Long> recommendedFilms = new ArrayList<Long>();
		recommendedFilms.add(2L);
		recommendedFilms.add(3L);
		recommendedFilms.add(7L);
		recommendedFilms.add(9L);
		
		List<RecommendedFilm> recommendations = new ArrayList<RecommendedFilm>();
		for(Film f : films){
			if(f.getId() != 1L){
				kieSession.insert(f);
			}
		}
		kieSession.getAgenda().getAgendaGroup("flags-recommendation").setFocus();
		kieSession.fireAllRules();
		Collection<?> output = kieSession.getObjects(new RecommendedFilmObjectFilter());
		for(Object o : output){
			RecommendedFilm rf = (RecommendedFilm) o; 
			recommendations.add(rf);
		}
		kieSession.dispose();
		
		for(RecommendedFilm rf : recommendations){
			if(recommendedFilms.contains(rf.getFilm().getId())){
				assertTrue(rf.getRecommendPoints() > 0);
			}
			else{
				assertEquals(0, rf.getRecommendPoints(), 0.001);
			}
		}
	}
	
	
	public RegisteredUser createRegisteredUser(Long id){
		RegisteredUser user = new RegisteredUser(
				id,
				"user" + id,
				"123",
				"e@gmail.com",
				LocalDateTime.parse(sdf.format(new Date()),df),
				0,
				0,
				Rank.NONE,
				Rank.NONE,
				new ArrayList<Action>(),
				new ArrayList<Review>(),
				new ArrayList<Notification>(),
				new ArrayList<Film>(),
				new ArrayList<Purchase>(),
				30,
				null,
				true,
				null);
		return user;
	}
	
	public static List<Artist> createListOfArtists(){
		List<Artist> artists = new ArrayList<Artist>();
		for(long i = 1; i <= 20; i++){
			Artist artist = new Artist(
					i,
					"name"+i,
					"surname"+i,
					new ArrayList<Film>(),
					new ArrayList<Film>(),
					new ArrayList<Film>());
			artists.add(artist);
		}
		return artists;
	}
	
	public static List<Film> createListOfFilms(){
		List<Artist> artists = createListOfArtists();
		Artist artist1 = artists.get(15);
		Artist artist2 = artists.get(16);
		Artist artist3 = artists.get(17);
		Artist artist4 = artists.get(18);
		Artist artist5 = artists.get(19);
		
		List<Artist> artistsFilm1 = new ArrayList<Artist>();
		artistsFilm1.add(artists.get(0));
		artistsFilm1.add(artists.get(1));
		artistsFilm1.add(artists.get(2));
		
		List<Artist> artistsFilm2 = new ArrayList<Artist>();
		artistsFilm2.add(artists.get(1));
		artistsFilm2.add(artists.get(2));
		artistsFilm2.add(artists.get(3));
		
		List<Artist> artistsFilm3 = new ArrayList<Artist>();
		artistsFilm3.add(artists.get(0));
		artistsFilm3.add(artists.get(1));
		artistsFilm3.add(artists.get(3));
		
		List<Artist> artistsFilm4 = new ArrayList<Artist>();
		artistsFilm4.add(artists.get(4));
		artistsFilm4.add(artists.get(5));
		artistsFilm4.add(artists.get(6));
		
		List<Artist> artistsFilm5 = new ArrayList<Artist>();
		artistsFilm5.add(artists.get(4));
		artistsFilm5.add(artists.get(7));
		artistsFilm5.add(artists.get(6));
		
		List<Artist> artistsFilm6 = new ArrayList<Artist>();
		artistsFilm6.add(artists.get(5));
		artistsFilm6.add(artists.get(8));
		artistsFilm6.add(artists.get(7));
		
		List<Artist> artistsFilm7 = new ArrayList<Artist>();
		artistsFilm7.add(artists.get(8));
		artistsFilm7.add(artists.get(9));
		artistsFilm7.add(artists.get(10));
		
		List<Artist> artistsFilm8 = new ArrayList<Artist>();
		artistsFilm8.add(artists.get(9));
		artistsFilm8.add(artists.get(10));
		artistsFilm8.add(artists.get(11));
		
		List<Artist> artistsFilm9 = new ArrayList<Artist>();
		artistsFilm9.add(artists.get(11));
		artistsFilm9.add(artists.get(12));
		artistsFilm9.add(artists.get(13));
		
		List<Artist> artistsFilm10 = new ArrayList<Artist>();
		artistsFilm10.add(artists.get(12));
		artistsFilm10.add(artists.get(13));
		artistsFilm10.add(artists.get(14));
		
		Film film1 = createFilm(1L, artist1, artist1, artistsFilm1, Genre.ACTION);
		Film film2 = createFilm(2L, artist1, artist1, artistsFilm2, Genre.ACTION);
		Film film3 = createFilm(3L, artist1, artist1, artistsFilm3, Genre.WESTERN);
		Film film4 = createFilm(4L, artist2, artist3, artistsFilm4, Genre.THRILLER);
		Film film5 = createFilm(5L, artist2, artist4, artistsFilm5, Genre.HORROR);
		Film film6 = createFilm(6L, artist2, artist5, artistsFilm6, Genre.THRILLER);
		Film film7 = createFilm(7L, artist1, artist2, artistsFilm7, Genre.DRAMA);
		Film film8 = createFilm(8L, artist2, artist5, artistsFilm8, Genre.COMEDY);
		Film film9 = createFilm(9L, artist1, artist1, artistsFilm9, Genre.DRAMA);
		Film film10 = createFilm(10L, artist2, artist4, artistsFilm10, Genre.ACTION);
		
		List<Film> films = new ArrayList<Film>();
		films.add(film1);
		films.add(film2);
		films.add(film3);
		films.add(film4);
		films.add(film5);
		films.add(film6);
		films.add(film7);
		films.add(film8);
		films.add(film9);
		films.add(film10);
		
		return films;
	}
	
	public static Film createFilm(Long id, Artist director, Artist scenarist, List<Artist> actors, Genre genre){
		Film film = new Film(
				id,
				"film"+id,
				"description",
				genre, 
				100,
				2000,
				0,
				"",
				actors, 
				director,
				scenarist, 
				new ArrayList<Review>(),
				new ArrayList<AgeCategory>());
		return film;
	}
	
	public Review createWatchedReview(Long id, RegisteredUser user, Film film){
		Review review = new Review(
				id,
				film,
				user, 
				new ArrayList<TimeInterval>(),
				true,
				LocalDateTime.parse(sdf.format(new Date()),df),
				0);
		review.getTimeIntervals().add(new TimeInterval(id, 0, 100, review));
		return review;
	}
	
	public Review createUnwatchedReview(Long id, RegisteredUser user, Film film){
		Review review = new Review(
				id,
				film,
				user, 
				new ArrayList<TimeInterval>(),
				false,
				LocalDateTime.parse(sdf.format(new Date()),df),
				0);
		review.getTimeIntervals().add(new TimeInterval(id, 0, 50, review));
		return review;
	}
	
	public void insertGenres(KieSession kieSession){
		kieSession.insert(Genre.ACTION);
		kieSession.insert(Genre.ADVENTURE);
		kieSession.insert(Genre.ANIMATED);
		kieSession.insert(Genre.COMEDY);
		kieSession.insert(Genre.DOCUMENTARY);
		kieSession.insert(Genre.DRAMA);
		kieSession.insert(Genre.HISTORICAL);
		kieSession.insert(Genre.HORROR);
		kieSession.insert(Genre.MUSIC);
		kieSession.insert(Genre.SCIFI);
		kieSession.insert(Genre.THRILLER);
		kieSession.insert(Genre.WESTERN);
		kieSession.insert(Genre.FAMILY);
	}
	
	class RecommendedFilmObjectFilter implements ObjectFilter {
        @Override
        public boolean accept(Object object) {
            String className = object.getClass().getName();
            return className.contains("RecommendedFilm");
        }
    }
}
