package com.gaurav.heremap.searchapi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gaurav.heremap.searchapi.service.async.AsyncPOIInfoService;
import com.gaurav.heremap.searchapi.util.SortBasedOnDistance;
import com.jayway.jsonpath.JsonPath;


@Service
public class FetchPOIService {

	@Autowired
	AsyncPOIInfoService riService;

	@Value("#{'${poi.app.search-catagory.list}'.split(',')}")
	private List<String> searchCatTypes;
	
	List<Map> finalList = new ArrayList<Map>();
	
	@Cacheable("POI")
	public List getPlaceInfo(String at) {
		
		System.out.println("Inside service with position : "+at);
		
		final CompletableFuture<String> atmBankInfo = riService.getPlaceInfo(at, searchCatTypes.get(0));
		final CompletableFuture<String> petrolStationInfo = riService.getPlaceInfo(at, searchCatTypes.get(1));
		final CompletableFuture<String> restaurantInfo = riService.getPlaceInfo(at, searchCatTypes.get(2));
		
		try {
		CompletableFuture.allOf(atmBankInfo, petrolStationInfo, restaurantInfo).join();
		List<Map> atmBankInfoList = JsonPath.parse(atmBankInfo.get()).read("$.results.items");
		List<Map> petrolStationInfoList = JsonPath.parse(petrolStationInfo.get()).read("$.results.items");
		List<Map> restaurantInfoList = JsonPath.parse(restaurantInfo.get()).read("$.results.items");
		
		Collections.sort(atmBankInfoList, new SortBasedOnDistance());
		Collections.sort(petrolStationInfoList, new SortBasedOnDistance());
		Collections.sort(restaurantInfoList, new SortBasedOnDistance());
		
		List<Map> closestAtmBankList = new ArrayList<Map>();
		List<Map> closestPetrolStationList = new ArrayList<Map>();
		List<Map> closestRestaurantList = new ArrayList<Map>();
		
		for(int i = 0; i<3; i++) {
			closestAtmBankList.add(atmBankInfoList.get(i));
			closestPetrolStationList.add(petrolStationInfoList.get(i));
			closestRestaurantList.add(restaurantInfoList.get(i));
		}
		
		finalList = new ArrayList<Map>();
		finalList.addAll(closestAtmBankList);
		finalList.addAll(closestPetrolStationList);
		finalList.addAll(closestRestaurantList);
		
		}
		catch(CompletionException | InterruptedException | ExecutionException ce) {
			ce.printStackTrace();
		}
		return finalList;
		
	}

}
