package test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.videoClub.model.Action;
import com.videoClub.model.Film;
import com.videoClub.model.Notification;
import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.enumeration.AgeCategory;
import com.videoClub.model.enumeration.Genre;
import com.videoClub.model.enumeration.Rank;
import com.videoClub.template.FreeMinutes;
import com.videoClub.template.GenreAgeRestrictionTemplate;
import com.videoClub.template.UserAgeCategoryTemplate;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.drools.template.ObjectDataCompiler;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;


public class TemplatesTest {
	
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	private static List<RegisteredUser> users = generateUsers();
	
	@Test
    public void testUserAgeRestrictionTemplate(){
        
        InputStream template = TemplatesTest.class.getResourceAsStream("/templates/classifyUserByAge.drt");
        
        List<UserAgeCategoryTemplate> data = new ArrayList<UserAgeCategoryTemplate>();
        
        data.add(new UserAgeCategoryTemplate(0, 11, AgeCategory.CHILD));
        data.add(new UserAgeCategoryTemplate(12, 18, AgeCategory.TEEN));
        data.add(new UserAgeCategoryTemplate(19, 35, AgeCategory.YOUNG_ADULT));
        data.add(new UserAgeCategoryTemplate(36, 65, AgeCategory.ADULT));
        data.add(new UserAgeCategoryTemplate(66, 150, AgeCategory.ELDER));
        
        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);
        
        System.out.println(drl);
        KieSession ksession = createKieSessionFromDRL(drl);
        for(RegisteredUser user : users){
        	ksession.insert(user);
        }
        ksession.fireAllRules();
        ksession.dispose();
        
        assertEquals(AgeCategory.CHILD, users.get(0).getAgeCategory());
		assertEquals(AgeCategory.TEEN, users.get(1).getAgeCategory());
		assertEquals(AgeCategory.TEEN, users.get(2).getAgeCategory());
		assertEquals(AgeCategory.TEEN, users.get(3).getAgeCategory());
		assertEquals(AgeCategory.TEEN, users.get(4).getAgeCategory());
		assertEquals(AgeCategory.YOUNG_ADULT, users.get(5).getAgeCategory());
		assertEquals(AgeCategory.YOUNG_ADULT, users.get(6).getAgeCategory());
		assertEquals(AgeCategory.YOUNG_ADULT, users.get(7).getAgeCategory());
		assertEquals(AgeCategory.YOUNG_ADULT, users.get(8).getAgeCategory());
		assertEquals(AgeCategory.ADULT, users.get(9).getAgeCategory());
		assertEquals(AgeCategory.ADULT, users.get(10).getAgeCategory());
		assertEquals(AgeCategory.ADULT, users.get(11).getAgeCategory());
		assertEquals(AgeCategory.ADULT, users.get(12).getAgeCategory());
		assertEquals(AgeCategory.ADULT, users.get(13).getAgeCategory());
		assertEquals(AgeCategory.ELDER, users.get(14).getAgeCategory());
    }
	
	
	@Test
    public void testGenreAgeRestrictionTemplate(){
        
        InputStream template = TemplatesTest.class.getResourceAsStream("/templates/genreRestrictionByAge.drt");
        
        List<GenreAgeRestrictionTemplate> data = new ArrayList<GenreAgeRestrictionTemplate>();
        
        Film film1 = new Film();
        film1.setGenre(Genre.HORROR);
        Film film2 = new Film();
        film2.setGenre(Genre.HORROR);
        Film film3 = new Film();
        film3.setGenre(Genre.ACTION);
        
        data.add(new GenreAgeRestrictionTemplate(AgeCategory.CHILD, Genre.HORROR));
        
        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);
        
        System.out.println(drl);
        KieSession ksession = createKieSessionFromDRL(drl);
        ksession.insert(film1);
        ksession.insert(film2);
        ksession.insert(film3);
        ksession.fireAllRules();
        ksession.dispose();
        
        assertTrue(film1.getRestrictedAgeCategories().size() == 1);
        assertTrue(film2.getRestrictedAgeCategories().size() == 1);
        assertTrue(film3.getRestrictedAgeCategories().size() == 0);
        
        assertTrue(film1.getRestrictedAgeCategories().contains(AgeCategory.CHILD));
        assertTrue(film2.getRestrictedAgeCategories().contains(AgeCategory.CHILD));
    }
	
	@Test
    public void testFreeMinutesByAge(){
        
        InputStream template = TemplatesTest.class.getResourceAsStream("/templates/freeMinutesByAge.drt");
        
        List<FreeMinutes> data = new ArrayList<FreeMinutes>();
        
        data.add(new FreeMinutes(AgeCategory.CHILD, 120, "Header", "Body"));
        data.add(new FreeMinutes(AgeCategory.TEEN, 120, "Header", "Body"));
        
        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);
        
        System.out.println(drl);
        KieSession ksession = createKieSessionFromDRL(drl);
        users.get(0).setAgeCategory(AgeCategory.CHILD);
        users.get(1).setAgeCategory(AgeCategory.TEEN);
        users.get(14).setAgeCategory(AgeCategory.ELDER);
        ksession.insert(users.get(0));
        ksession.insert(users.get(1));
        ksession.insert(users.get(14));
        ksession.fireAllRules();
        ksession.dispose();
        
        assertEquals(120, users.get(0).getAvailableMinutes());
        assertEquals(120, users.get(1).getAvailableMinutes());
        assertEquals(0, users.get(14).getAvailableMinutes());
    }
	
	@Test
    public void testFreeMinutesByTitle(){
        
        InputStream template = TemplatesTest.class.getResourceAsStream("/templates/freeMinutesByTitle.drt");
        
        List<FreeMinutes> data = new ArrayList<FreeMinutes>();
        
        data.add(new FreeMinutes(Rank.GOLD, 300, "Header", "Body"));
        data.add(new FreeMinutes(Rank.SILVER, 200, "Header", "Body"));
        data.add(new FreeMinutes(Rank.BRONZE, 150, "Header", "Body"));
        
        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);
        
        System.out.println(drl);
        KieSession ksession = createKieSessionFromDRL(drl);
        users.get(8).setTitle(Rank.GOLD);
        users.get(9).setTitle(Rank.SILVER);
        users.get(10).setTitle(Rank.BRONZE);
        ksession.insert(users.get(8));
        ksession.insert(users.get(9));
        ksession.insert(users.get(10));
        ksession.fireAllRules();
        ksession.dispose();
        
        assertEquals(300, users.get(8).getAvailableMinutes());
        assertEquals(200, users.get(9).getAvailableMinutes());
        assertEquals(150, users.get(10).getAvailableMinutes());
    }
	
	@Test
    public void testFreeMinutesByTitleAndAge(){
        
        InputStream template = TemplatesTest.class.getResourceAsStream("/templates/freeMinutesByAgeAndTitle.drt");
        
        List<FreeMinutes> data = new ArrayList<FreeMinutes>();
        
        data.add(new FreeMinutes(AgeCategory.TEEN, Rank.GOLD, 300, "Header", "Body"));
        data.add(new FreeMinutes(AgeCategory.ADULT,Rank.SILVER, 200, "Header", "Body"));
        data.add(new FreeMinutes(AgeCategory.ELDER,Rank.BRONZE, 150, "Header", "Body"));
        
        ObjectDataCompiler converter = new ObjectDataCompiler();
        String drl = converter.compile(data, template);
        
        System.out.println(drl);
        KieSession ksession = createKieSessionFromDRL(drl);
        users.get(11).setTitle(Rank.GOLD);
        users.get(12).setTitle(Rank.SILVER);
        users.get(13).setTitle(Rank.BRONZE);
        users.get(11).setAgeCategory(AgeCategory.TEEN);
        users.get(12).setAgeCategory(AgeCategory.ADULT);
        users.get(13).setAgeCategory(AgeCategory.ELDER);
        ksession.insert(users.get(11));
        ksession.insert(users.get(12));
        ksession.insert(users.get(13));
        ksession.fireAllRules();
        ksession.dispose();
        
        assertEquals(300, users.get(11).getAvailableMinutes());
        assertEquals(200, users.get(12).getAvailableMinutes());
        assertEquals(150, users.get(13).getAvailableMinutes());
    }
	
	public static List<RegisteredUser> generateUsers(){
		List<RegisteredUser> users = new ArrayList<RegisteredUser>();
		users.add(createRegisteredUser(1L, 10, Rank.NONE));
		users.add(createRegisteredUser(2L, 12, Rank.BRONZE));
		users.add(createRegisteredUser(3L, 13, Rank.BRONZE));
		users.add(createRegisteredUser(4L, 15, Rank.BRONZE));
		users.add(createRegisteredUser(5L, 18, Rank.SILVER));
		users.add(createRegisteredUser(6L, 20, Rank.GOLD));
		users.add(createRegisteredUser(7L, 25, Rank.BRONZE));
		users.add(createRegisteredUser(8L, 30, Rank.SILVER));
		users.add(createRegisteredUser(9L, 35, Rank.NONE));
		users.add(createRegisteredUser(10L, 40, Rank.NONE));
		users.add(createRegisteredUser(11L, 45, Rank.GOLD));
		users.add(createRegisteredUser(12L, 50, Rank.GOLD));
		users.add(createRegisteredUser(13L, 55, Rank.SILVER));
		users.add(createRegisteredUser(14L, 65, Rank.NONE));
		users.add(createRegisteredUser(15L, 70, Rank.SILVER));
		return users;
	}
	
	public static RegisteredUser createRegisteredUser(Long id, int age, Rank rank){
		RegisteredUser user = new RegisteredUser(
				id,
				"user" + id,
				"123",
				"e@gmail.com",
				LocalDateTime.parse(sdf2.format(new Date()),df2),
				0,
				0,
				rank,
				Rank.NONE,
				new ArrayList<Action>(),
				new ArrayList<Review>(),
				new ArrayList<Notification>(),
				new ArrayList<Film>(),
				new ArrayList<Purchase>(),
				age,
				null);
		return user;
	}
	
	private KieSession createKieSessionFromDRL(String drl){
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);
        
        Results results = kieHelper.verify();
        
        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: "+message.getText());
            }
            
            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }
        
        return kieHelper.build().newKieSession();
    }
}
