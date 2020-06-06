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

import com.videoClub.bean.BronzeImmunity;
import com.videoClub.bean.BronzeTitle;
import com.videoClub.bean.GoldImmunity;
import com.videoClub.bean.GoldTitle;
import com.videoClub.bean.SilverImmunity;
import com.videoClub.bean.SilverTitle;
import com.videoClub.model.Action;
import com.videoClub.model.Film;
import com.videoClub.model.Notification;
import com.videoClub.model.Purchase;
import com.videoClub.model.RegisteredUser;
import com.videoClub.model.Review;
import com.videoClub.model.enumeration.Rank;

public class TitleRulesTest {

	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	private static List<RegisteredUser> users = generateUsers();
	
	@Test
	public void test(){
		List<Purchase> purchases = generatePurchases();
		KieSession kieSession = initializeKieSession();
		for(RegisteredUser user : users){
			kieSession.insert(user);
		}
		for(Purchase purchase : purchases){
			kieSession.insert(purchase);
		}
		kieSession.fireAllRules();
		kieSession.dispose();
		
		assertEquals(Rank.BRONZE, users.get(0).getTitle());
		assertEquals(Rank.BRONZE, users.get(1).getTitle());
		assertEquals(Rank.BRONZE, users.get(2).getTitle());
		assertEquals(Rank.BRONZE, users.get(3).getTitle());
		assertEquals(Rank.BRONZE, users.get(3).getImmunity());
		assertEquals(Rank.NONE, users.get(4).getTitle());
		assertEquals(Rank.SILVER, users.get(5).getTitle());
		assertEquals(Rank.SILVER, users.get(6).getTitle());
		assertEquals(Rank.SILVER, users.get(7).getTitle());
		assertEquals(Rank.SILVER, users.get(8).getTitle());
		assertEquals(Rank.SILVER, users.get(9).getTitle());
		assertEquals(Rank.SILVER, users.get(9).getImmunity());
		assertEquals(Rank.BRONZE, users.get(10).getTitle());
		assertEquals(Rank.GOLD, users.get(11).getTitle());
		assertEquals(Rank.GOLD, users.get(12).getTitle());
		assertEquals(Rank.GOLD, users.get(13).getTitle());
		assertEquals(Rank.GOLD, users.get(14).getTitle());
		assertEquals(Rank.GOLD, users.get(15).getTitle());
		assertEquals(Rank.GOLD, users.get(15).getImmunity());
		assertEquals(Rank.SILVER, users.get(16).getTitle());
	}
	
	public static List<RegisteredUser> generateUsers(){
		List<RegisteredUser> users = new ArrayList<RegisteredUser>();
		users.add(createRegisteredUser(1L, Rank.NONE, 200, 0));
		users.add(createRegisteredUser(2L, Rank.BRONZE, 100, 0));
		users.add(createRegisteredUser(3L, Rank.BRONZE, 200, 0));
		users.add(createRegisteredUser(4L, Rank.BRONZE, 0, 20));
		users.add(createRegisteredUser(5L, Rank.BRONZE, 0, 5));
		users.add(createRegisteredUser(6L, Rank.NONE, 300, 0));
		users.add(createRegisteredUser(7L, Rank.BRONZE, 300, 0));
		users.add(createRegisteredUser(8L, Rank.SILVER, 300, 0));
		users.add(createRegisteredUser(9L, Rank.SILVER, 200, 0));
		users.add(createRegisteredUser(10L, Rank.SILVER, 0, 30));
		users.add(createRegisteredUser(11L, Rank.SILVER, 0, 0));
		users.add(createRegisteredUser(12L, Rank.SILVER, 600, 0));
		users.add(createRegisteredUser(13L, Rank.BRONZE, 600, 0));
		users.add(createRegisteredUser(14L, Rank.NONE, 600, 0));
		users.add(createRegisteredUser(15L, Rank.GOLD, 400, 0));
		users.add(createRegisteredUser(16L, Rank.GOLD, 0, 50));
		users.add(createRegisteredUser(17L, Rank.GOLD, 0, 0));
		return users;
	}
	
	public List<Purchase> generatePurchases(){
		List<Purchase> purchases = new ArrayList<Purchase>();
		purchases.add(new Purchase(1L, null, 0, 2.0, 200, null, users.get(0)));
		purchases.add(new Purchase(2L, null, 0, 1.0, 100, null, users.get(1)));
		purchases.add(new Purchase(3L, null, 0, 2.0, 200, null, users.get(2)));
		purchases.add(new Purchase(6L, null, 0, 3.0, 300, null, users.get(5)));
		purchases.add(new Purchase(7L, null, 0, 3.0, 300, null, users.get(6)));
		purchases.add(new Purchase(8L, null, 0, 3.0, 300, null, users.get(7)));
		purchases.add(new Purchase(9L, null, 0, 3.0, 200, null, users.get(8)));
		purchases.add(new Purchase(12L, null, 0, 6.0, 600, null, users.get(11)));
		purchases.add(new Purchase(13L, null, 0, 6.0, 600, null, users.get(12)));
		purchases.add(new Purchase(14L, null, 0, 6.0, 600, null, users.get(13)));
		purchases.add(new Purchase(15L, null, 0, 4.0, 400, null, users.get(14)));
		return purchases;
	}
	
	public static RegisteredUser createRegisteredUser(Long id, Rank rank, int minutes, int points){
		RegisteredUser user = new RegisteredUser(
				id,
				"user" + id,
				"123",
				"e@gmail.com",
				LocalDateTime.parse(sdf2.format(new Date()),df2),
				points,
				minutes,
				rank,
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
	
	public KieSession initializeKieSession(){
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession("titleRulesSession");
		kieSession.setGlobal("bronzeImmunity", generateBronzeImmunity());
		kieSession.setGlobal("silverImmunity", generateSilverImmunity());
		kieSession.setGlobal("goldImmunity", generateGoldImmunity());
		kieSession.setGlobal("bronzeTitle", generateBronzeTitle());
		kieSession.setGlobal("silverTitle", generateSilverTitle());
		kieSession.setGlobal("goldTitle", generateGoldTitle());
		return kieSession;
	}
	
	public BronzeImmunity generateBronzeImmunity(){
		return new BronzeImmunity(15);
	}
	
	public SilverImmunity generateSilverImmunity(){
		return new SilverImmunity(30);
	}

	public GoldImmunity generateGoldImmunity(){
		return new GoldImmunity(50);
	}
	
	public BronzeTitle generateBronzeTitle(){
		return new BronzeTitle(180, 100, 120);
	}
	
	public SilverTitle generateSilverTitle(){
		return new SilverTitle(300, 200, 180);
	}

	public GoldTitle generateGoldTitle(){
		return new GoldTitle(600, 400, 300);
	}
}
