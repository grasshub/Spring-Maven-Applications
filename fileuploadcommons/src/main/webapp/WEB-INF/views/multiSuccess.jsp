<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>File Upload Success</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
    <div class="success">
            <c:forEach var="fileName" items="${fileNames}">
                File  <strong>${fileName}</strong> uploaded successfully<br/>
            </c:forEach>
            <br/>
        <a href="<c:url value='/welcome' />">Home</a>
    </div>
</body>
</html>