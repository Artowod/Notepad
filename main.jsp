<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>NotePad - Lukavenko S.</title>
</head>
<style>
body {text-align:center; padding: 20px;}

#outer {
margin: 0 auto;
text-align:left;
border: 1px solid black;
background: #fc3;
width: 800px;
padding: 20px;
}

#inner {
margin: 0 auto;
text-align:left;
border: 1px solid black;
background: #ac3;
width: 700px;
padding: 20px;
}

</style>	
<body>
<div id="outer">
<h3> Dear
<% String tempStr=(String)request.getAttribute("sendRealNameToJspPage"); %>
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
<h3> Please type the new Note: </h3>
<div id="inner">Please call the Note:&nbsp;<input type="text" name="noteName" value="" size=50></div>
<div id="inner">
<br>Content:<br>
<textarea name="textArea" rows="20" cols="95"></textarea>
</div>
<br>
<input type="submit" name="AddButton" value="Save the current Note">
</form>
<form action="AuthorizationServlet" method="get">
<input type="submit" name="LogOut" value="Log out">
</form>
</div> 

</body>
</html>