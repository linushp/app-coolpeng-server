package com.coolpeng.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class TMSPropertyConfigurer extends PropertyPlaceholderConfigurer
{
  private static Map<String, Object> ctxPropMap;
  private static Logger logger = LoggerFactory.getLogger(TMSPropertyConfigurer.class);

  public static Object getContextProperty(String name)
  {
    return ctxPropMap.get(name);
  }

  public static Map<String, Object> getConfigurer() {
    return ctxPropMap;
  }

  protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
    throws BeansException
  {
    super.processProperties(beanFactory, props);

    ctxPropMap = new HashMap();
    for (Iterator localIterator = props.keySet().iterator(); localIterator.hasNext(); ) { Object key = localIterator.next();
      String keyStr = key.toString();
      String value = props.getProperty(keyStr);
      ctxPropMap.put(keyStr, value);
    }
  }
}