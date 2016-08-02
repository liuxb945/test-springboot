package com.abcd.test.springboot;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.Future;

import javax.sql.DataSource;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

import com.abcd.test.springboot.dao.CustomerMongoRepository;
import com.abcd.test.springboot.model.Customer;
import com.abcd.test.springboot.model.User;
import com.abcd.test.springboot.service.BookingService;
import com.abcd.test.springboot.service.GitHubLookupService;

/**
 * Hello world!
 *
 */
@SpringBootApplication
//@EnableScheduling
@ComponentScan
@EnableAsync
public class Application implements CommandLineRunner{
	 private static final Logger log = LoggerFactory.getLogger(Application.class);
	 public static String ROOT = "upload-dir";
	 
//	 @Bean
		BookingService bookingService() {
			return new BookingService();
		}

//		@Bean
		JdbcTemplate jdbcTemplate(DataSource dataSource) {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			log.info("Creating tables");
			jdbcTemplate.execute("drop table BOOKINGS if exists");
			jdbcTemplate.execute("create table BOOKINGS("
					+ "ID serial, FIRST_NAME varchar(5) NOT NULL)");
			return jdbcTemplate;
		}
		
		/*@Autowired
	    GitHubLookupService gitHubLookupService;
		
		@Bean
		CommandLineRunner ruanAsyn(){
			return (String[] args) -> {
			// Start the clock
	        long start = System.currentTimeMillis();

	        // Kick of multiple, asynchronous lookups
	        Future<User> page1 = gitHubLookupService.findUser("PivotalSoftware");
	        Future<User> page2 = gitHubLookupService.findUser("CloudFoundry");
	        Future<User> page3 = gitHubLookupService.findUser("Spring-Projects");

	        // Wait until they are all done
	        while (!(page1.isDone() && page2.isDone() && page3.isDone())) {
	            Thread.sleep(10); //10-millisecond pause between each check
	        }

	        // Print results, including elapsed time
	        System.out.println("Elapsed time: " + (System.currentTimeMillis() - start));
	        System.out.println(page1.get());
	        System.out.println(page2.get());
	        System.out.println(page3.get());
			};
		}*/
	 
    public static void main(String[] args) throws InterruptedException {  
        ConfigurableApplicationContext context =  
        SpringApplication.run(Application.class, args);  
  
        System.out.println("hohoho");  
        String[] names = context.getBeanDefinitionNames();  
        Arrays.sort(names);  
        for (String string : names) {  
        System.err.println(string);  
        } 
//        book(args,context);
        //----redis message begin------------
        /*StringRedisTemplate template = context.getBean(StringRedisTemplate.class);
		CountDownLatch latch = context.getBean(CountDownLatch.class);

		log.info("Sending message...");
		template.convertAndSend("chat", "Hello from Redis!");

		latch.await();

		System.exit(0);*/
		//----redis message end------------
    }
    
    /*@Autowired
    JdbcTemplate jdbcTemplate;*/
  
    @Override  
    public void run(String... args) throws Exception {  
        /*RestTemplate template = new RestTemplate();  
        Greeting greeting = template.getForObject("http://localhost:8080/greeting?name=323", Greeting.class);  
        System.err.println(greeting);*/ 
    	
    	/*RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        log.info(quote.toString());*/
    	
    	/*log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        // Split up the array of whole names into an array of first/last names
        List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        // Use a Java 8 stream to print out each tuple of the list
        splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);

        log.info("Querying for customer records where first_name = 'Josh':");
        jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers WHERE first_name = ?", new Object[] { "Josh" },
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
        ).forEach(customer -> log.info(customer.toString()));*/
    } 
    
    @Bean
    CommandLineRunner init() {
        return (String[] args) -> {
            File f=new File(ROOT);
			if (!f.exists())
				f.mkdir();
            System.out.println(f.getAbsolutePath());
        };
    }
    
    public static void book(String[] args,ApplicationContext ctx){
//    	ApplicationContext ctx = SpringApplication.run(Application.class, args);

		BookingService bookingService = ctx.getBean(BookingService.class);
		bookingService.book("Alice", "Bob", "Carol");
		Assert.assertEquals("First booking should work with no problem", 3,
				bookingService.findAllBookings().size());

		try {
			bookingService.book("Chris", "Samuel");
		}
		catch (RuntimeException e) {
			log.info("v--- The following exception is expect because 'Samuel' is too big for the DB ---v");
			log.error(e.getMessage());
		}

		for (String person : bookingService.findAllBookings()) {
			log.info("So far, " + person + " is booked.");
		}
		log.info("You shouldn't see Chris or Samuel. Samuel violated DB constraints, and Chris was rolled back in the same TX");
		Assert.assertEquals("'Samuel' should have triggered a rollback", 3,
				bookingService.findAllBookings().size());

		try {
			bookingService.book("Buddy", null);
		}
		catch (RuntimeException e) {
			log.info("v--- The following exception is expect because null is not valid for the DB ---v");
			log.error(e.getMessage());
		}

		for (String person : bookingService.findAllBookings()) {
			log.info("So far, " + person + " is booked.");
		}
		log.info("You shouldn't see Buddy or null. null violated DB constraints, and Buddy was rolled back in the same TX");
		Assert.assertEquals("'null' should have triggered a rollback", 3, bookingService
				.findAllBookings().size());

    }
    
//    @Bean
	public CommandLineRunner demoMongo(CustomerMongoRepository repository) {
    	return (args) -> {
    		repository.deleteAll();

    		// save a couple of customers
    		Customer c1=new Customer("Alice", "Smith");
    		Customer c2=new Customer("Bob", "Smith");
    		c1.setId(1l);
    		c2.setId(2l);
    		repository.save(c1);
    		repository.save(c2);

    		// fetch all customers
    		System.out.println("Customers found with findAll():");
    		System.out.println("-------------------------------");
    		for (Customer customer : repository.findAll()) {
    			System.out.println(customer);
    		}
    		System.out.println();

    		// fetch an individual customer
    		System.out.println("Customer found with findByFirstName('Alice'):");
    		System.out.println("--------------------------------");
    		System.out.println(repository.findByFirstName("Alice"));

    		System.out.println("Customers found with findByLastName('Smith'):");
    		System.out.println("--------------------------------");
    		for (Customer customer : repository.findByLastName("Smith")) {
    			System.out.println(customer);
    		}
    	};
    }
    
    /*@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Customer("Jack", "Bauer"));
			repository.save(new Customer("Chloe", "O'Brian"));
			repository.save(new Customer("Kim", "Bauer"));
			repository.save(new Customer("David", "Palmer"));
			repository.save(new Customer("Michelle", "Dessler"));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
            log.info("");

			// fetch an individual customer by ID
			Customer customer = repository.findOne(1L);
			log.info("Customer found with findOne(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
            log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			for (Customer bauer : repository.findByLastName("Bauer")) {
				log.info(bauer.toString());
			}
            log.info("");
		};
	}*/
  
}
