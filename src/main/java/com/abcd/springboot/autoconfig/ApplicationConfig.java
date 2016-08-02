package com.abcd.springboot.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class ApplicationConfig {

	/*@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic("chat"));

		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Bean
	Receiver receiver(CountDownLatch latch) {
		return new Receiver(latch);
	}

	@Bean
	CountDownLatch latch() {
		return new CountDownLatch(1);
	}

	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}
	
	@Bean
	RedisConnectionFactory connectionFactory(){
		JedisConnectionFactory f=new JedisConnectionFactory();
		f.setHostName("101.201.77.49");
		f.setPassword("kk_11");
		f.setPort(6379);
		return f;
	}*/
	
	/*@Bean
	RedisConnectionFactory connectionFactory(){
		JedisConnectionFactory f=new JedisConnectionFactory();
		f.setHostName("101.201.77.49");
		f.setPassword("kk_11");
		f.setPort(6379);
		return f;
	}*/
}
