package com.machine.controller;

/**
 * @author moubin.mo
 * @date: 2021/8/24 23:15
 */

import com.machine.controller.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
@Configuration
public class HelloWorldApplication {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "hello")
	@ResponseBody
	public String hello() {
		return orderService.pay(1);
	}

	public static void main(String[] args) {

		SpringApplication.run(HelloWorldApplication.class, args);

	}

}