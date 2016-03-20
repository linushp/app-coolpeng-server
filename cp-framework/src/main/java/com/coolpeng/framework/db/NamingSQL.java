package com.coolpeng.framework.db;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class NamingSQL {

	private static Logger logger = LoggerFactory.getLogger(NamingSQL.class);

	private static final Map<String, String> SQL_MAP = new HashMap<String, String>();

	public static String getNamingSqlById(String namingSqlId) {
		if (SQL_MAP.isEmpty()) {
			loadNamingSql();
		}
		return SQL_MAP.get(namingSqlId);
	}

	private static void loadNamingSql() {

		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		Resource[] resources = null;
		try {
			resources = resolver.getResources("classpath*:**/*-naming-sql.xml");
		} catch (IOException e1) {
			logger.error("filed to load classpath,", e1);
		}

		if (resources != null) {
			for (Resource resource : resources) {
				File file = null;
				try {
					file = resource.getFile();
					if (file == null) {
						logger.error("filed to load file,file is null");
					} else {
						loadNamingSql(file);
					}
				} catch (IOException e) {
					logger.error("filed to load file " + file.getAbsolutePath(), e);
				}

			}
		} else {
			logger.error("filed to load classpath, resources is null");
		}

	}

	private static void loadNamingSql(File file) {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			logger.error("filed to load file " + file.getAbsolutePath(), e);
			return;
		}

		Document document = null;
		try {
			document = db.parse(file);
		} catch (SAXException | IOException e) {
			logger.error("filed to load file " + file.getAbsolutePath(), e);
			return;
		}

		NodeList list = document.getElementsByTagName("sql");

		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);

			String id = element.getAttribute("id");

			String sqlContent = element.getFirstChild().getNodeValue();

			if (!StringUtils.isEmpty(sqlContent)) {
				SQL_MAP.put(id, sqlContent.trim());
			}
		}
	}

}
