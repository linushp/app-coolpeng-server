package com.coolpeng.framework.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LStringBuffer {

	private List<Object> objectList1 = null;
	private List<String> objectList2 = null;
	private String slim;
	private boolean doubleMode = true;

	public LStringBuffer(String slim) {
		this(slim, true);
	}

	public LStringBuffer(String slim, boolean doubleMode) {
		this.slim = slim;
		this.doubleMode = doubleMode;
		this.objectList1 = new ArrayList<Object>();
		if (doubleMode) {
			this.objectList2 = new ArrayList<String>();
		}

	}

	public void append(String object) {
		objectList1.add(object);
		if (doubleMode) {
			objectList2.add(object);
		}
	}

	public void append(LStringBuffer object, String str2) {
		objectList1.add(object);
		if (doubleMode) {
			objectList2.add(str2);
		}
	}

	public String join2() {
		if (doubleMode) {
			return this.joinCommon(this.slim, this.objectList2);
		}
		return null;
	}

	public String join1() {
		return this.joinCommon(this.slim, this.objectList1);
	}

	private String joinCommon(String slim, List<?> objectList) {
		StringBuffer sb = new StringBuffer();
		Iterator<?> it = objectList.iterator();
		while (it.hasNext()) {
			Object str = it.next();
			if (str instanceof LStringBuffer) {
				str = ((LStringBuffer) str).join1();
			}
			sb.append(str);

			if (it.hasNext()) {
				sb.append(slim);
			}
		}
		return sb.toString();
	}

	public String toString() {
		return this.join1();
	}

	public String toString2() {
		return this.join2();
	}
}
