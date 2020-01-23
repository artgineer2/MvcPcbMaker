package com.mvcpcbmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class MvcPcbMakerApplication {

	
	public static void main(String[] args) {
	
		//try
		{
			SpringApplication.run(MvcPcbMakerApplication.class, args);
		}
		/*catch(Exception e)
		{
			e.printStackTrace();
		}*/
	}

}
