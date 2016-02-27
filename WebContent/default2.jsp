<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="searchengine.Stats" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Results for your query</title>
</head>
<body>

<h4>Query is:
<%=Stats.query%> <br />
</h4>
<h5>Page 1:</h5>
	<%
		String url = Stats.getUrl(0);
		String title = Stats.getTitles(0);
		out.println(title);
		out.println("<br />");
		out.println("<a href=" + url + "> " + url + " </a>");
		out.println("<br />");
	%>
<h5>Page 2:</h5>
	<%
		url = Stats.getUrl(1);
		title = Stats.getTitles(1);
		out.println(title);
		out.println("<br />");
		out.println("<a href=" + url + "> " + url + " </a>");
		out.println("<br />");
	%>	
<h5>Page 3:</h5>
	<%
		url = Stats.getUrl(2);
		title = Stats.getTitles(2);
		out.println(title);
		out.println("<br />");
		out.println("<a href=" + url + "> " + url + " </a>");
		out.println("<br />");
	%>	
<h5>Page 4:</h5>
	<%
		url = Stats.getUrl(3);
		title = Stats.getTitles(3);
		out.println(title);
		out.println("<br />");
		out.println("<a href=" + url + "> " + url + " </a>");
		out.println("<br />");
	%>	
<h5>Page 5:</h5>
	<%
		url = Stats.getUrl(4);
		title = Stats.getTitles(4);
		out.println(title);
		out.println("<br />");
		out.println("<a href=" + url + "> " + url + " </a>");
		out.println("<br />");
		Stats.urls.clear();
		Stats.titles.clear();
	%>	
</body>
</html>