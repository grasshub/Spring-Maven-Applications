<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Home</title>
	<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
	<link href="${bootstrapCss}" rel="stylesheet" />
</head>

<body>

	<h2>Spring MVC - View (JSON, XML, PDF, Excel, JSP)</h2>
	<div style="margin: 10px; width: 700px; text-align: center">
		<a href="<c:url value='/books.json' />" class="label label-info">JSON</a> 
		<a href="<c:url value='/books.xml' />" class="label label-info">XML</a> 
		<a href="<c:url value='/books.pdf' />" class="label label-info">PDF</a> 
		<a href="<c:url value='/books.xlsx' />" class="label label-info">Excel</a>
	</div>
	
</body>

</html>