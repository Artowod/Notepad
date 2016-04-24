<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>NotePad - Lukavenko S.</title>
</head>
<body>
<div align=center>
<h3> Dear
<% String tempStr=request.getParameter("login"); %>
<% if (tempStr!=null) out.print(tempStr); %>
<% if (tempStr==null) out.print("Guest"); %>
</h3>
<h3>Please select the theme of a note:</h3>
<form action="ThemeServlet" method="post">
  <input type="radio" name="themes" value="itSphere" checked> IT sphere
  <input type="radio" name="themes" value="javaProgramming"> Java programming
  <input type="radio" name="themes" value="general"> General info
  <input type="radio" name="themes" value="english"> English &nbsp;&nbsp;&nbsp;
  <input type="submit" name="ViewButton" value="View selected theme">
<h3> Please type the Note: </h3>
<textarea name="textArea" rows="20" cols="80"></textarea>
<br>
<input type="submit" name="AddButton" value="Submit the current Note">
</form>
</div>

</body>
</html>