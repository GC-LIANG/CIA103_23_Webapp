<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.catalog.model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% CatalogVO catalogVO = (CatalogVO) request.getAttribute("catalogVO"); %>

<html>
<head>
<meta charset="UTF-8">
<title>類別資料 listOneCatalog.jsp</title>
<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>類別資料 - listOneCatalog.jsp</h3>
		 <h4><a href="${pageContext.request.contextPath}/back-end/member/select.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<c:if test="${empty catalogVO}">
    <p style="color:red;">查無類別資料，請返回重新嘗試。</p>
</c:if>


<c:if test="${not empty catalogVO}">
<table>
	<tr>
		<th>類別編號</th>
		<th>類別名稱</th>
	</tr>
	<tr>
		<td><%=catalogVO.getId()%></td>
		<td><%=catalogVO.getName()%></td>
	</tr>
</table>
</c:if>

</body>
</html>