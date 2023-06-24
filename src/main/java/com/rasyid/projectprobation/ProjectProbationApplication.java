package com.rasyid.projectprobation;

import com.rasyid.projectprobation.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.rasyid.projectprobation.base.mapper")
public class ProjectProbationApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProjectProbationApplication.class, args);
	}
	@Autowired
	private RedisService redisService;
	/**
	 * Initialize the inventory quantity and information of the product in Redis
	 * @param args
	 * @throws Exception
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		redisService.put("watch", 10, 20);
	}
}
