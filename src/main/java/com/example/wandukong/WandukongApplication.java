package com.example.wandukong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class WandukongApplication {

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
	}

	public static void main(String[] args) {

		SpringApplication.run(WandukongApplication.class, args);
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void resetTodayVisit() {
		redisTemplate.delete("viewKey");
	}

}
