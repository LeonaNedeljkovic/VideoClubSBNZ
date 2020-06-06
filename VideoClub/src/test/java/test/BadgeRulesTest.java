package test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.videoClub.model.Action;
import com.videoClub.model.Artist;
import com.videoClub.model.Film;
import com.videoClub.model.Notification;
import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.TimeInterval;
import com.videoClub.model.drl.ArtistRateBadge;
import com.videoClub.model.drl.ArtistReviewBadge;
import com.videoClub.model.drl.Badge;
import com.videoClub.model.drl.GenreBadge;
import com.videoClub.model.drl.UserConclusion;
import com.videoClub.model.enumeration.AgeCategory;
import com.videoClub.model.enumeration.Genre;
import com.videoClub.model.enumeration.Rank;

public class BadgeRulesTest {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	private static List<Film> films = createListOfFilms();
	
	/*
	 * Testiramo da li ce korisniku biti dodeljeni neki bedzevi ukoliko nema ni jedan pregled
	 */
	@Test
	public void test1(){
		System.out.println("\nNo reviews test:");
		RegisteredUser user = createRegisteredUser(1L);
		UserConclusion conclusion = new UserConclusion(user, new ArrayList<Badge>());
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("badgeRulesSession");
		kieSession.insert(conclusion);
		for(Artist a : createListOfArtists()){
			kieSession.insert(a);
		}
		kieSession.fireAllRules();
		kieSession.dispose();
		
		assertEquals(conclusion.getBadges().size(), 0);
	}
	
	/*
	 * Testiramo koji ce bedzevi biti dodeljeni korisniku kada je napravio jedan pregled
	 * i nije odgledao taj film do kraja
	 */
	@Test
	public void test2(){
		System.out.println("\n1 unwatched film test:");
		RegisteredUser user = createRegisteredUser(1L);
		Review review = createUnwatchedReview(1L, user, films.get(0));
		user.getReviews().add(review);
		UserConclusion conclusion = new UserConclusion(user, new ArrayList<Badge>());
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("badgeRulesSession");
		kieSession.insert(conclusion);
		int artistsNum = 0;
		for(Artist a : films.get(0).getActors()){
			kieSession.insert(a);
			artistsNum++;
		}
		kieSession.insert(review.getFilm().getDirector());
		kieSession.insert(review.getFilm().getWrittenBy());
		kieSession.insert(review);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		assertEquals(artistsNum+1, conclusion.getBadges().size()); //+1 za umetnika koji je rezirao film i pisao scenario
		int artistReviewBadges = 0;
		int otherBadges = 0;
		for(Badge badge : conclusion.getBadges()){
			if(badge instanceof ArtistReviewBadge){
				artistReviewBadges++;
				assertEquals(1, ((ArtistReviewBadge) badge).getUnwatchedNumber());
				assertEquals(0, ((ArtistReviewBadge) badge).getWatchedNumber());
			}
			else{
				otherBadges++;
			}
		}
		assertEquals(artistsNum+1, artistReviewBadges);
		assertEquals(otherBadges, 0);
	}
	
	
	/*
	 * Testiramo koji ce bedzevi biti dodeljeni korisniku kada je napravio jedan pregled
	 * i odgledao je taj film do kraja
	 */
	@Test
	public void test3(){
		System.out.println("\n1 watched film test:");
		RegisteredUser user = createRegisteredUser(1L);
		Review review = createWatchedReview(1L, user, films.get(0));
		user.getReviews().add(review);
		UserConclusion conclusion = new UserConclusion(user, new ArrayList<Badge>());
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("badgeRulesSession");
		kieSession.insert(conclusion);
		int artistsNum = 0;
		for(Artist a : films.get(0).getActors()){
			kieSession.insert(a);
			artistsNum++;
		}
		insertGenres(kieSession);
		
		kieSession.insert(review.getFilm().getDirector());
		kieSession.insert(review.getFilm().getWrittenBy());
		kieSession.insert(review);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		assertEquals(17, conclusion.getBadges().size()); //+1 za umetnika koji je rezirao film i pisao scenario
		int artistReviewBadges = 0;
		int genreBadges = 0;
		int otherBadges = 0;
		for(Badge badge : conclusion.getBadges()){
			if(badge instanceof ArtistReviewBadge){
				artistReviewBadges++;
				assertEquals(0, ((ArtistReviewBadge) badge).getUnwatchedNumber());
				assertEquals(1, ((ArtistReviewBadge) badge).getWatchedNumber());
			}
			else if(badge instanceof GenreBadge){
				genreBadges++;
				if(((GenreBadge) badge).getGenre().equals(Genre.ACTION)){
					assertEquals(0, ((GenreBadge) badge).getUnwatchedTime());
					assertEquals(1, ((GenreBadge) badge).getWatchedTime());
					assertEquals(0, ((GenreBadge) badge).getAverageRate(), 0.001);
				}
				else{
					assertEquals(0, ((GenreBadge) badge).getUnwatchedTime());
					assertEquals(0, ((GenreBadge) badge).getWatchedTime());
					assertEquals(0, ((GenreBadge) badge).getAverageRate(), 0.001);
				}
			}
			else{
				otherBadges++;
			}
		}
		assertEquals(artistsNum+1, artistReviewBadges);
		assertEquals(otherBadges, 0);
		assertEquals(13, genreBadges);
	}
	
	
	/*
	 * Testiramo koji ce bedzevi biti dodeljeni korisniku kada je jedan film odgledao do kraja
	 * i kada drugi nije odgledao do kraja
	 */
	@Test
	public void test4(){
		System.out.println("\n1 watched film, 1 unwatched film test:");
		RegisteredUser user = createRegisteredUser(1L);
		Review review1 = createWatchedReview(1L, user, films.get(0));
		Review review2 = createUnwatchedReview(2L, user, films.get(7));
		user.getReviews().add(review1);
		user.getReviews().add(review2);
		UserConclusion conclusion = new UserConclusion(user, new ArrayList<Badge>());
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("badgeRulesSession");
		kieSession.insert(conclusion);
		for(Artist a : films.get(0).getActors()){
			kieSession.insert(a);
		}
		for(Artist a : films.get(7).getActors()){
			kieSession.insert(a);
		}
		kieSession.insert(review1.getFilm().getDirector());
		kieSession.insert(review1.getFilm().getWrittenBy());
		kieSession.insert(review1);
		kieSession.insert(review2.getFilm().getDirector());
		kieSession.insert(review2.getFilm().getWrittenBy());
		kieSession.insert(review2);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		List<Long> watchedArtistsIds = new ArrayList<Long>();
		watchedArtistsIds.add(1L);
		watchedArtistsIds.add(2L);
		watchedArtistsIds.add(3L);
		watchedArtistsIds.add(16L);
		
		assertEquals(9, conclusion.getBadges().size());
		int artistReviewBadges = 0;
		int otherBadges = 0;
		for(Badge badge : conclusion.getBadges()){
			if(badge instanceof ArtistReviewBadge){
				artistReviewBadges++;
				if(watchedArtistsIds.contains(((ArtistReviewBadge) badge).getArtist().getId())){
					assertEquals(0, ((ArtistReviewBadge) badge).getUnwatchedNumber());
					assertEquals(1, ((ArtistReviewBadge) badge).getWatchedNumber());
				}
				else{
					assertEquals(1, ((ArtistReviewBadge) badge).getUnwatchedNumber());
					assertEquals(0, ((ArtistReviewBadge) badge).getWatchedNumber());
				}
			}
			else{
				otherBadges++;
			}
		}
		assertEquals(9, artistReviewBadges);
		assertEquals(otherBadges, 0);
	}
	
	/*
	 * Testiramo koji ce bedzevi biti dodeljeni korisniku kada je jedan film odgledao do kraja
	 * i ocenio ga ocenom 5
	 */
	@Test
	public void test5(){
		System.out.println("\n1 rated film test:");
		RegisteredUser user = createRegisteredUser(1L);
		Review review = createWatchedReview(1L, user, films.get(0));
		review.setRate(5);
		user.getReviews().add(review);
		UserConclusion conclusion = new UserConclusion(user, new ArrayList<Badge>());
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("badgeRulesSession");
		kieSession.insert(conclusion);
		int artistsNum = 0;
		for(Artist a : films.get(0).getActors()){
			kieSession.insert(a);
			artistsNum++;
		}
		kieSession.insert(review.getFilm().getDirector());
		kieSession.insert(review.getFilm().getWrittenBy());
		kieSession.insert(review);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		assertEquals(artistsNum+1, conclusion.getBadges().size()); //+1 za umetnika koji je rezirao film i pisao scenario
		int artistRateBadges = 0;
		int otherBadges = 0;
		for(Badge badge : conclusion.getBadges()){
			if(badge instanceof ArtistRateBadge){
				artistRateBadges++;
				int favourites = ((ArtistRateBadge) badge).getFavourites();
				assertEquals(0, favourites);
				assertEquals(5.0, ((ArtistRateBadge) badge).getAverageRate(), 0.001);
				
			}
			else{
				otherBadges++;
			}
		}
		assertEquals(artistsNum+1, artistRateBadges);
		assertEquals(otherBadges, 0);
	}
	
	/*
	 * Testiramo koji ce bedzevi biti dodeljeni korisniku kada je jedan film odgledao do kraja
	 * i ocenio ga ocenom 5 i dodao u omiljene
	 */
	@Test
	public void test6(){
		System.out.println("\n1 rated film and added to favourites test:");
		RegisteredUser user = createRegisteredUser(1L);
		Review review = createWatchedReview(1L, user, films.get(0));
		review.setRate(5);
		user.getReviews().add(review);
		user.getFavouriteFilms().add(review.getFilm());
		UserConclusion conclusion = new UserConclusion(user, new ArrayList<Badge>());
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("badgeRulesSession");
		kieSession.insert(conclusion);
		int artistsNum = 0;
		for(Artist a : films.get(0).getActors()){
			kieSession.insert(a);
			artistsNum++;
		}
		kieSession.insert(review.getFilm().getDirector());
		kieSession.insert(review.getFilm().getWrittenBy());
		kieSession.insert(review);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		assertEquals(artistsNum+1, conclusion.getBadges().size()); //+1 za umetnika koji je rezirao film i pisao scenario
		int artistRateBadges = 0;
		int otherBadges = 0;
		for(Badge badge : conclusion.getBadges()){
			if(badge instanceof ArtistRateBadge){
				artistRateBadges++;
				int favourites = ((ArtistRateBadge) badge).getFavourites();
				assertEquals(1, favourites);
				assertEquals(5.0, ((ArtistRateBadge) badge).getAverageRate(), 0.001);
				
			}
			else{
				otherBadges++;
			}
		}
		assertEquals(artistsNum+1, artistRateBadges);
		assertEquals(otherBadges, 0);
	}
	
	
	/*
	 * Testiramo koji ce bedzevi biti dodeljeni korisniku kada je 2 filma
	 * od istog autora odgledao do kraja i jedan ocenio ocenom 5 i dodao ga u omiljene,
	 * a drugi ocenom 4
	 */
	@Test
	public void test7(){
		System.out.println("\n1 rated film, 1 rated and added to favourites test:");
		RegisteredUser user = createRegisteredUser(1L);
		Review review1 = createWatchedReview(1L, user, films.get(0));
		Review review2 = createWatchedReview(2L, user, films.get(1));
		review1.setRate(5);
		user.getFavouriteFilms().add(review1.getFilm());
		user.getReviews().add(review1);
		user.getReviews().add(review2);
		review2.setRate(4);
		UserConclusion conclusion = new UserConclusion(user, new ArrayList<Badge>());
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("badgeRulesSession");
		kieSession.insert(conclusion);
		for(Artist a : review1.getFilm().getActors()){
			kieSession.insert(a);
		}
		for(Artist a : review2.getFilm().getActors()){
			kieSession.insert(a);
		}
		kieSession.insert(review1.getFilm().getDirector());
		kieSession.insert(review1.getFilm().getWrittenBy());
		kieSession.insert(review1);
		kieSession.insert(review2.getFilm().getDirector());
		kieSession.insert(review2.getFilm().getWrittenBy());
		kieSession.insert(review2);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		List<Long> favouriteArtistsIds = new ArrayList<Long>();
		favouriteArtistsIds.add(2L);
		favouriteArtistsIds.add(3L);
		favouriteArtistsIds.add(16L);
		
		assertEquals(5, conclusion.getBadges().size()); //+1 za umetnika koji je rezirao film i pisao scenario
		int artistRateBadges = 0;
		int otherBadges = 0;
		for(Badge badge : conclusion.getBadges()){
			if(badge instanceof ArtistRateBadge){
				artistRateBadges++;
				int favourites = ((ArtistRateBadge) badge).getFavourites();
				if(((ArtistRateBadge) badge).getArtist().getId() == 1){
					assertEquals(1, favourites);
					assertEquals(5.0, ((ArtistRateBadge) badge).getAverageRate(), 0.001);
				}
				else if(favouriteArtistsIds.contains(((ArtistRateBadge) badge).getArtist().getId())){
					assertEquals(1, favourites);
					assertEquals(4.5, ((ArtistRateBadge) badge).getAverageRate(), 0.001);
				}
				else{
					assertEquals(0, favourites);
					assertEquals(4.0, ((ArtistRateBadge) badge).getAverageRate(), 0.001);
				}
			}
			else{
				otherBadges++;
			}
		}
		assertEquals(5, artistRateBadges);
		assertEquals(otherBadges, 0);
	}
	
	@Test
	public void test8(){
		System.out.println("\n1 rated film, 1 rated and added to favourites, 1 watched, 1 unwatched test:");
		RegisteredUser user = createRegisteredUser(1L);
		Review review1 = createWatchedReview(1L, user, films.get(0));
		Review review2 = createWatchedReview(2L, user, films.get(1));
		Review review3 = createWatchedReview(3L, user, films.get(2));
		Review review4 = createUnwatchedReview(4L, user, films.get(3));
		
		review1.setRate(5);
		review2.setRate(4);
		user.getFavouriteFilms().add(review1.getFilm());
		user.getReviews().add(review1);
		user.getReviews().add(review2);
		user.getReviews().add(review3);
		user.getReviews().add(review4);
		
		UserConclusion conclusion = new UserConclusion(user, new ArrayList<Badge>());
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("badgeRulesSession");
		kieSession.insert(conclusion);
		for(Artist a : review1.getFilm().getActors()){
			kieSession.insert(a);
		}
		for(Artist a : review2.getFilm().getActors()){
			kieSession.insert(a);
		}
		for(Artist a : review3.getFilm().getActors()){
			kieSession.insert(a);
		}
		for(Artist a : review4.getFilm().getActors()){
			kieSession.insert(a);
		}
		kieSession.insert(review1.getFilm().getDirector());
		kieSession.insert(review1.getFilm().getWrittenBy());
		kieSession.insert(review1);
		kieSession.insert(review2.getFilm().getDirector());
		kieSession.insert(review2.getFilm().getWrittenBy());
		kieSession.insert(review2);
		kieSession.insert(review3.getFilm().getDirector());
		kieSession.insert(review3.getFilm().getWrittenBy());
		kieSession.insert(review3);
		kieSession.insert(review4.getFilm().getDirector());
		kieSession.insert(review4.getFilm().getWrittenBy());
		kieSession.insert(review4);
		kieSession.fireAllRules();
		kieSession.dispose();
		
		List<Long> favouriteArtistsIds = new ArrayList<Long>();
		favouriteArtistsIds.add(1L);
		favouriteArtistsIds.add(2L);
		favouriteArtistsIds.add(3L);
		favouriteArtistsIds.add(16L);
		
		long idRate5 = 1;
		long idRate4 = 4;
		
		List<Long> ratedArtistsIds = new ArrayList<Long>();
		ratedArtistsIds.add(2L);
		ratedArtistsIds.add(3L);
		ratedArtistsIds.add(16L);
		
		List<Long> watchedArtistsIds = new ArrayList<Long>();
		watchedArtistsIds.add(1L);
		watchedArtistsIds.add(2L);
		watchedArtistsIds.add(4L);
		watchedArtistsIds.add(16L);
		
		List<Long> unwatchedArtistsIds = new ArrayList<Long>();
		unwatchedArtistsIds.add(5L);
		unwatchedArtistsIds.add(6L);
		unwatchedArtistsIds.add(7L);
		unwatchedArtistsIds.add(17L);
		unwatchedArtistsIds.add(18L);
		
		assertEquals(14, conclusion.getBadges().size()); //+1 za umetnika koji je rezirao film i pisao scenario
		int artistRateBadges = 0;
		int artistReviewBadges = 0;
		for(Badge badge : conclusion.getBadges()){
			if(badge instanceof ArtistRateBadge){
				artistRateBadges++;
				int favourites = ((ArtistRateBadge) badge).getFavourites();
				if(favouriteArtistsIds.contains(((ArtistRateBadge) badge).getArtist().getId())){
					assertEquals(1, favourites);
				}
				if(ratedArtistsIds.contains(((ArtistRateBadge) badge).getArtist().getId())){
					assertEquals(4.5, ((ArtistRateBadge) badge).getAverageRate(), 0.001);
				}
				if(((ArtistRateBadge) badge).getArtist().getId() == idRate5){
					assertEquals(5.0, ((ArtistRateBadge) badge).getAverageRate(), 0.001);
				}
				if(((ArtistRateBadge) badge).getArtist().getId() == idRate4){
					assertEquals(4.0, ((ArtistRateBadge) badge).getAverageRate(), 0.001);
				}
			}
			else{
				artistReviewBadges++;
				if(watchedArtistsIds.contains(((ArtistReviewBadge) badge).getArtist().getId())){
					assertEquals(1, ((ArtistReviewBadge) badge).getWatchedNumber());
				}
				else if(unwatchedArtistsIds.contains(((ArtistReviewBadge) badge).getArtist().getId())){
					assertEquals(1, ((ArtistReviewBadge) badge).getUnwatchedNumber());
				}
			}
		}
		assertEquals(5, artistRateBadges);
		assertEquals(9, artistReviewBadges);
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
				true);
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
}
