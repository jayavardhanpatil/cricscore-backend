package com.cpp.mscs.cricscore;

import com.cpp.mscs.cricscore.services.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableCaching
public class CrickscoreApplication {

	@Autowired
	CacheService cachingService;

	public static void main(String[] args) {
		SpringApplication.run(CrickscoreApplication.class, args);
	}

	@Scheduled(fixedRate = 600000000)
	public void evictAllcachesAtIntervals() {
		cachingService.evictAllCaches();
	}

}
