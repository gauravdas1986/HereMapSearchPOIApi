package com.gaurav.heremap.searchapi.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.gaurav.heremap.searchapi.service.FetchPOIService;

@RestController
@RequestMapping("/search")
public class ExtactPOIController {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	FetchPOIService poiService;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@GetMapping("/poi")
	public List callMe(@RequestParam(value = "position", required = true) @NotBlank String at) {
		System.out.println("Searching for position: " + at);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		List resp = poiService.getPlaceInfo(at);
		
		return resp;
	}
}
