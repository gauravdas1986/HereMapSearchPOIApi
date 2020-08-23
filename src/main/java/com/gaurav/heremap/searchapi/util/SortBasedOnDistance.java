package com.gaurav.heremap.searchapi.util;

import java.util.Comparator;
import java.util.Map;

import org.json.JSONException;

import net.minidev.json.JSONObject;

public class SortBasedOnDistance implements Comparator<Map> {
	@Override
	public int compare(Map lhs, Map rhs) {
		Integer i = (Integer) lhs.get("distance");
		try {
			return (int)(lhs.get("distance")) > (int)(rhs.get("distance")) ? 1
					: ((int)(lhs.get("distance")) < (int)(rhs.get("distance")) ? -1 : 0);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0;

	}
}