package com.abcd.test.springboot;

import java.io.File;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@SpringBootApplication
//@EnableScheduling
public class Application implements CommandLineRunner{
	 private static final Logger log = LoggerFactory.getLogger(Application.class);
	 public static String ROOT = "upload-dir";
	 
    public static void main(String[] args) {  
        ConfigurableApplicationContext context =  
        SpringApplication.run(Application.class, args);  
  
        System.out.println("hohoho");  
        String[] names = context.getBeanDefinitionNames();  
        Arrays.sort(names);  
        for (String string : names) {  
        System.err.println(string);  
        }  
    }
    
    @Autowired
    JdbcTemplate jdbcTemplate;
  
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
  
}
