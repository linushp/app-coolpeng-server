<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/tms-view.tld" prefix="tms" %>
<%@ taglib uri="/WEB-INF/tld/blog-widget.tld" prefix="blog" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% application.setAttribute("ctx", request.getScheme() + "://" + request.getHeader("host") + request.getContextPath()); %>