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
import com.videoClub.model.enumeration.Gender;
import com.videoClub.model.enumeration.Genre;
import com.videoClub.model.enumeration.Rank;

public class AgeGenderFilmRecommendationTest {

	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	private List<Film> films = createListOfFilms();
	
	@Test
	public void test1(){
		System.out.println("\nMale animated films:");
		RegisteredUser user = createRegisteredUser(30L, 8, AgeCategory.CHILD, Gender.MALE);
		//ocekujemo da ce biti preporuceni filmovi za decake, sto su film1, film2 i film10
		List<Long> expectedFilmIds = new ArrayList<Long>();
		expectedFilmIds.add(1L);
		expectedFilmIds.add(2L);
		expectedFilmIds.add(10L);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("filmRecommendationRulesSession");
		kieSession.insert(user);
		List<RecommendedFilm> recommended = new ArrayList<RecommendedFilm>();
		for(Film f : films){
			kieSession.insert(f);
		}
		for(Film f : films){
			for(Review r : f.getReviews()){
				kieSession.insert(r);
			}
		}
		kieSession.getAgenda().getAgendaGroup("flags-recommendation").setFocus();
		kieSession.fireAllRules();
		Collection<?> output = kieSession.getObjects(new RecommendedFilmObjectFilter());
		for(Object o : output){
			RecommendedFilm rf = (RecommendedFilm) o; 
			recommended.add(rf);
		}
		kieSession.dispose();
		for(RecommendedFilm rf : recommended){
			if(expectedFilmIds.contains(rf.getFilm().getId())){
				assertTrue(rf.getRecommendPoints() > 0);
			}
			else{
				assertTrue(rf.getRecommendPoints() == 0);
			}
		}
	}
	
	@Test
	public void test2(){
		System.out.println("\nFemale animated films:");
		RegisteredUser user = createRegisteredUser(30L, 7, AgeCategory.CHILD, Gender.FEMALE);
		//ocekujemo da ce biti preporuceni filmovi za devojcice, sto su film3, film8 i film9
		List<Long> expectedFilmIds = new ArrayList<Long>();
		expectedFilmIds.add(3L);
		expectedFilmIds.add(8L);
		expectedFilmIds.add(9L);
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("filmRecommendationRulesSession");
		kieSession.insert(user);
		List<RecommendedFilm> recommended = new ArrayList<RecommendedFilm>();
		for(Film f : films){
			for(Review r : f.getReviews()){
				kieSession.insert(r);
			}
		}
		for(Film f : films){
			kieSession.insert(f);
		}
		kieSession.getAgenda().getAgendaGroup("flags-recommendation").setFocus();
		kieSession.fireAllRules();
		Collection<?> output = kieSession.getObjects(new RecommendedFilmObjectFilter());
		for(Object o : output){
			RecommendedFilm rf = (RecommendedFilm) o; 
			recommended.add(rf);
		}
		kieSession.dispose();
		for(RecommendedFilm rf : recommended){
			if(expectedFilmIds.contains(rf.getFilm().getId())){
				assertTrue(rf.getRecommendPoints() > 0);
			}
			else{
				assertTrue(rf.getRecommendPoints() == 0);
			}
		}
	}
	
	public void createReviews(List<RegisteredUser> users, List<Film> films){
		//muski - 0,1,9
		//zenski - 2,7,8
		users.get(0).getReviews().add(createWatchedReview(1L, users.get(0), films.get(0), 5));
		users.get(0).getReviews().add(createWatchedReview(2L, users.get(0), films.get(1), 5));
		users.get(0).getReviews().add(createWatchedReview(3L, users.get(0), films.get(9), 4));
		users.get(1).getReviews().add(createWatchedReview(4L, users.get(1), films.get(0), 0));
		users.get(1).getReviews().add(createWatchedReview(5L, users.get(1), films.get(1), 5));
		users.get(1).getReviews().add(createWatchedReview(6L, users.get(1), films.get(9), 4));
		users.get(2).getReviews().add(createWatchedReview(7L, users.get(2), films.get(0), 5));
		users.get(2).getReviews().add(createWatchedReview(8L, users.get(2), films.get(9), 0));
		users.get(3).getReviews().add(createWatchedReview(9L, users.get(3), films.get(0), 5));
		users.get(3).getReviews().add(createWatchedReview(10L, users.get(3), films.get(1), 5));
		users.get(3).getReviews().add(createWatchedReview(11L, users.get(3), films.get(9), 4));
		users.get(4).getReviews().add(createWatchedReview(12L, users.get(4), films.get(0), 5));
		users.get(4).getReviews().add(createWatchedReview(13L, users.get(4), films.get(1), 0));
		users.get(5).getReviews().add(createWatchedReview(14L, users.get(5), films.get(2), 5));
		users.get(5).getReviews().add(createWatchedReview(15L, users.get(5), films.get(7), 5));
		users.get(5).getReviews().add(createWatchedReview(16L, users.get(5), films.get(8), 0));
		users.get(6).getReviews().add(createWatchedReview(17L, users.get(6), films.get(2), 4));
		users.get(6).getReviews().add(createWatchedReview(18L, users.get(6), films.get(7), 5));
		users.get(6).getReviews().add(createWatchedReview(19L, users.get(6), films.get(8), 4));
		users.get(7).getReviews().add(createWatchedReview(20L, users.get(7), films.get(2), 5));
		users.get(7).getReviews().add(createWatchedReview(21L, users.get(7), films.get(7), 0));
		users.get(8).getReviews().add(createWatchedReview(22L, users.get(8), films.get(2), 5));
		users.get(8).getReviews().add(createWatchedReview(23L, users.get(8), films.get(7), 5));
		users.get(8).getReviews().add(createWatchedReview(24L, users.get(8), films.get(8), 4));
		users.get(9).getReviews().add(createWatchedReview(25L, users.get(9), films.get(2), 5));
		users.get(9).getReviews().add(createWatchedReview(26L, users.get(9), films.get(7), 0));
		updateReviewsForFilms(users, films);
	}
	
	public void createUsers(List<Film> films){
		List<RegisteredUser> users = new ArrayList<RegisteredUser>();
		users.add(createRegisteredUser(1L, 7, AgeCategory.CHILD, Gender.MALE));
		users.add(createRegisteredUser(2L, 8, AgeCategory.CHILD, Gender.MALE));
		users.add(createRegisteredUser(3L, 9, AgeCategory.CHILD, Gender.MALE));
		users.add(createRegisteredUser(4L, 6, AgeCategory.CHILD, Gender.MALE));
		users.add(createRegisteredUser(5L, 6, AgeCategory.CHILD, Gender.MALE));
		users.add(createRegisteredUser(7L, 7, AgeCategory.CHILD, Gender.FEMALE));
		users.add(createRegisteredUser(8L, 8, AgeCategory.CHILD, Gender.FEMALE));
		users.add(createRegisteredUser(9L, 9, AgeCategory.CHILD, Gender.FEMALE));
		users.add(createRegisteredUser(10L, 6, AgeCategory.CHILD, Gender.FEMALE));
		users.add(createRegisteredUser(11L, 6, AgeCategory.CHILD, Gender.FEMALE));
		createReviews(users, films);
	}
	
	public void updateReviewsForFilms(List<RegisteredUser> users, List<Film> films){
		for(RegisteredUser user : users){
			for(Review r : user.getReviews()){
				for(Film f : films){
					if(f.getId() == r.getFilm().getId()){
						f.getReviews().add(r);
					}
				}
			}
		}
	}
	
	public RegisteredUser createRegisteredUser(Long id, int age, AgeCategory category, Gender gender){
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
				age,
				category,
				true,
				gender);
		return user;
	}
	
	public List<Artist> createListOfArtists(){
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
	
	public List<Film> createListOfFilms(){
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
		
		Film film1 = createFilm(1L, artist1, artist1, artistsFilm1, Genre.ANIMATED);//muski crtani
		Film film2 = createFilm(2L, artist1, artist1, artistsFilm2, Genre.ANIMATED);//muski crtani
		Film film3 = createFilm(3L, artist1, artist1, artistsFilm3, Genre.ANIMATED);//zenski crtani
		Film film4 = createFilm(4L, artist2, artist3, artistsFilm4, Genre.DRAMA);
		Film film5 = createFilm(5L, artist2, artist4, artistsFilm5, Genre.HORROR);
		Film film6 = createFilm(6L, artist2, artist5, artistsFilm6, Genre.THRILLER);
		Film film7 = createFilm(7L, artist1, artist2, artistsFilm7, Genre.DRAMA);
		Film film8 = createFilm(8L, artist1, artist1, artistsFilm8, Genre.ANIMATED);//zenski crtani
		Film film9 = createFilm(9L, artist1, artist1, artistsFilm9, Genre.ANIMATED);//zenski crtani
		Film film10 = createFilm(10L, artist2, artist4, artistsFilm10, Genre.ANIMATED);//muski crtani
		
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
		createUsers(films);
		return films;
	}
	
	public Film createFilm(Long id, Artist director, Artist scenarist, List<Artist> actors, Genre genre){
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
				new ArrayList<Review>());
		return film;
	}
	
	public Review createWatchedReview(Long id, RegisteredUser user, Film film, int rate){
		Review review = new Review(
				id,
				film,
				user, 
				new ArrayList<TimeInterval>(),
				true,
				LocalDateTime.parse(sdf.format(new Date()),df),
				0);
		review.setRate(rate);
		review.getTimeIntervals().add(new TimeInterval(id, 0, 100, review));
		return review;
	}
	
	class RecommendedFilmObjectFilter implements ObjectFilter {
        @Override
        public boolean accept(Object object) {
            String className = object.getClass().getName();
            return className.contains("RecommendedFilm");
        }
    }
}
