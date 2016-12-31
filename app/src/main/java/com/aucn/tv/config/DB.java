package com.aucn.tv.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB {

	public DB(){
		pls = new ArrayList<DisplayBase>();
		plDtls = new HashMap<String, List<DisplayBase>>();
		livePre = new ArrayList<DisplayBase>();
		vips = new ArrayList<DisplayBase>();
	}

	public List<DisplayBase> pls;

	public List<DisplayBase> livePre;

	public Map<String , List<DisplayBase>> plDtls;

	public List<DisplayBase> vips;

}
