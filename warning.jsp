<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div align=center>
<h1> Dear guest, you have typed incorrect credentials. Please try again.</h1>
<br>
<h3> Detected error: </h3>
<% out.print(request.getAttribute("warningText")); %>
</div>
</body>
</html>