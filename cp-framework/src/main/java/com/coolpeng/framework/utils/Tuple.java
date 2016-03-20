package com.coolpeng.framework.utils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class Tuple {
	private List<Object> data = new ArrayList<Object>(5);

	public Tuple(Object... dataArray) {
		for (Object d : dataArray) {
			data.add(d);
		}
	}
	
	public static Tuple tuple(Object... dataArray){
		return new Tuple(dataArray);
	}
	
	public <T> T first() {
		return get(0);
	}

	public <T> T second() {
		return get(1);
	}

	public <T> T third() {
		return get(2);
	}
	
	public <T> T forth() {
		return get(3);
	}
	
	

	public <T> T get(int index) {
		return (T) this.data.get(index);
	}

}
