package test;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ObjectFilter;

import com.videoClub.comparator.AgeCategoryReportComparator;
import com.videoClub.model.Action;
import com.videoClub.model.Artist;
import com.videoClub.model.Film;
import com.videoClub.model.Notification;
import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.TimeInterval;
import com.videoClub.model.drl.FinalReport;
import com.videoClub.model.enumeration.AgeCategory;
import com.videoClub.model.enumeration.Gender;
import com.videoClub.model.enumeration.Genre;
import com.videoClub.model.enumeration.Rank;

public class AdminRecommendationTest {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	@Test
	public void test1() throws InstantiationException, IllegalAccessException{
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("adminRecommendationRulesSession");
		
		List<Artist> artists = createArtists();
		List<Film> films = createFilms(artists);
		List<RegisteredUser> users = this.createUsers(films);
		
		Film film = createFilm(1L,artists.get(7),artists.get(7),Arrays.asList(artists.get(0), artists.get(1)), Genre.ACTION);
		kieSession.insert(film);
		
		for(RegisteredUser user : users){
			kieSession.insert(user);
		}
		kieSession.insert(AgeCategory.CHILD);
		kieSession.insert(AgeCategory.TEEN);
		kieSession.insert(AgeCategory.YOUNG_ADULT);
		kieSession.insert(AgeCategory.ADULT);
		kieSession.insert(AgeCategory.ELDER);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		Collection<?> types = kieSession.getObjects(new FinalReportObjectFilter());
		for(Object o : types){
			FinalReport report = (FinalReport) o;
			Collections.sort(report.getAgeCategoryReports(), new AgeCategoryReportComparator());
			assertEquals(50, report.getPercentage(),0);
			assertEquals(5, report.getUserRecommendationsNumber());
			assertEquals(3, report.getAgeCategoryReports().get(0).getUserRecommendationNumber()); //TEEN
			assertEquals(1, report.getAgeCategoryReports().get(1).getUserRecommendationNumber()); //ADULT
			assertEquals(1, report.getAgeCategoryReports().get(2).getUserRecommendationNumber()); //CHILD
			assertEquals(0, report.getAgeCategoryReports().get(3).getUserRecommendationNumber()); //ELDER
			assertEquals(0, report.getAgeCategoryReports().get(4).getUserRecommendationNumber()); //YOUNG_ADULT
		}
	}
	
	@Test
	public void test2() throws InstantiationException, IllegalAccessException{
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("adminRecommendationRulesSession");
		
		List<Artist> artists = createArtists();
		List<Film> films = createFilms(artists);
		List<RegisteredUser> users = this.createUsers(films);
		
		Film film = createFilm(1L,artists.get(7),artists.get(7),Arrays.asList(artists.get(0), artists.get(1)), Genre.ACTION);
		kieSession.insert(film);
		users.get(0).getReviews().get(0).setRate(5);
		users.get(0).getReviews().get(0).setWatched(false);
		for(RegisteredUser user : users){
			kieSession.insert(user);
		}
		kieSession.insert(AgeCategory.CHILD);
		kieSession.insert(AgeCategory.TEEN);
		kieSession.insert(AgeCategory.YOUNG_ADULT);
		kieSession.insert(AgeCategory.ADULT);
		kieSession.insert(AgeCategory.ELDER);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		Collection<?> types = kieSession.getObjects(new FinalReportObjectFilter());
		for(Object o : types){
			FinalReport report = (FinalReport) o;
			Collections.sort(report.getAgeCategoryReports(), new AgeCategoryReportComparator());
			assertEquals(50, report.getPercentage(),0);
			assertEquals(5, report.getUserRecommendationsNumber());
			assertEquals(3, report.getAgeCategoryReports().get(0).getUserRecommendationNumber()); //TEEN
			assertEquals(1, report.getAgeCategoryReports().get(1).getUserRecommendationNumber()); //ADULT
			assertEquals(1, report.getAgeCategoryReports().get(2).getUserRecommendationNumber()); //CHILD
			assertEquals(0, report.getAgeCategoryReports().get(3).getUserRecommendationNumber()); //ELDER
			assertEquals(0, report.getAgeCategoryReports().get(4).getUserRecommendationNumber()); //YOUNG_ADULT
		}
	}
	
	public List<Artist> createArtists(){
		List<Artist> artists = new ArrayList<Artist>();
		artists.add(new Artist(1L, "Brad", "Pitt", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(2L, "Angelina", "Jolie", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(3L, "Tom", "Cruise", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(4L, "Nicholas", "Hoult", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(5L, "Samuel", "Jackson", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(6L, "Michael", "Moore", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(7L, "Jessica", "Chanstain", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(8L, "Quentin", "Tarantino", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(9L, "Uma", "Thurman", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(10L, "Harvey", "Keitel", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(11L, "Tim", "Roth", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(12L, "Amanda", "Plummer", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(13L, "Eric", "Stoltz", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(14L, "Rosanna", "Arquette", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(15L, "Christopher", "Walken", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(16L, "Jamie", "Foxx", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(17L, "Christoph", "Waltz", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(18L, "Kerry", "Washington", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(19L, "Dennis", "Christopher", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		artists.add(new Artist(20L, "James", "Remar", new ArrayList<Film>(), new ArrayList<Film>(), new ArrayList<Film>()));
		return artists;
	}
	
	public List<Film> createFilms(List<Artist> artists){
		List<Film> films = new ArrayList<Film>();
		films.add(createFilm(1L,artists.get(7),artists.get(7),Arrays.asList(artists.get(0), artists.get(5), artists.get(6)), Genre.ACTION));
		films.add(createFilm(2L,artists.get(8),artists.get(9),Arrays.asList(artists.get(1), artists.get(10), artists.get(11)), Genre.DRAMA));
		films.add(createFilm(2L,artists.get(15),artists.get(16),Arrays.asList(artists.get(14), artists.get(13), artists.get(12)), Genre.MUSIC));
		return films;
	}
	
	public List<RegisteredUser> createUsers(List<Film> films){
		List<RegisteredUser> users = new ArrayList<RegisteredUser>();
		users.add(createRegisteredUser(1L, 7, AgeCategory.CHILD, Gender.MALE));
		users.add(createRegisteredUser(2L, 8, AgeCategory.CHILD, Gender.FEMALE));
		users.add(createRegisteredUser(3L, 14, AgeCategory.TEEN, Gender.MALE));
		users.add(createRegisteredUser(4L, 16, AgeCategory.TEEN, Gender.MALE));
		users.add(createRegisteredUser(5L, 16, AgeCategory.TEEN, Gender.MALE));
		users.add(createRegisteredUser(6L, 17, AgeCategory.TEEN, Gender.FEMALE));
		users.add(createRegisteredUser(7L, 28, AgeCategory.YOUNG_ADULT, Gender.FEMALE));
		users.add(createRegisteredUser(8L, 39, AgeCategory.ADULT, Gender.FEMALE));
		users.add(createRegisteredUser(9L, 26, AgeCategory.YOUNG_ADULT, Gender.FEMALE));
		users.add(createRegisteredUser(10L, 66, AgeCategory.ELDER, Gender.FEMALE));
		
		users.get(0).getReviews().add(createReview(1L, users.get(0), films.get(0), true, 5));
		users.get(0).getReviews().add(createReview(2L, users.get(0), films.get(1), true, 0));
		users.get(1).getReviews().add(createReview(3L, users.get(1), films.get(0), false, 0));
		users.get(2).getReviews().add(createReview(4L, users.get(2), films.get(1), true, 4));
		users.get(2).getReviews().add(createReview(5L, users.get(2), films.get(0), true, 5));
		users.get(3).getReviews().add(createReview(6L, users.get(3), films.get(1), true, 0));
		users.get(3).getReviews().add(createReview(7L, users.get(3), films.get(0), true, 0));
		users.get(4).getReviews().add(createReview(8L, users.get(4), films.get(2), true, 5));
		users.get(5).getReviews().add(createReview(9L, users.get(5), films.get(1), true, 5));
		users.get(6).getReviews().add(createReview(10L, users.get(6), films.get(2), false, 0));
		users.get(7).getReviews().add(createReview(11L, users.get(7), films.get(0), true, 0));
		return users;
	}
	
	public Review createReview(Long id, RegisteredUser user, Film film, boolean watched, int rate){
		Review review = new Review(
				id,
				film,
				user, 
				new ArrayList<TimeInterval>(),
				watched,
				LocalDateTime.parse(sdf.format(new Date()),df),
				rate);
		review.setRate(rate);
		return review;
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
	
	public Film createFilm(Long id, Artist director, Artist scenarist, List<Artist> actors, Genre genre){
		return new Film(id,
				"Film"+id,
				"Best movie ever",
				genre,
				100, 2020,
				0.0,
				"poster",
				actors,
				director,
				scenarist,
				new ArrayList<Review>());
	}
	
	class FinalReportObjectFilter implements ObjectFilter {
        @Override
        public boolean accept(Object object) {
            String className = object.getClass().getName();
            return className.contains("FinalReport");
        }
    }
}
