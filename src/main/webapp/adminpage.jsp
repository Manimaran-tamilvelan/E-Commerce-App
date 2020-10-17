<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.google.appengine.api.datastore.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin | Welcome back !</title>

<script src="http://code.jquery.com/jquery-3.5.1.min.js"
	integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
	crossorigin="anonymous"></script>
<script src="script.js"></script>

<link rel="stylesheet" type="text/css" href="style.css">


</head>
<body>

	<%
		response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
	%>

	<%
		String message = (String) session.getAttribute("currentUser");

	if (message == null) {

		//session.setAttribute("auth", "Something Went Wrong");

		response.sendRedirect("index.jsp");

	} else if (!message.equals("admin admin@123")) {

		session.setAttribute("auth", "Not Authorized Person");

		response.sendRedirect("index.jsp");
	}
	%>

	<h1 id="heading">
		<a href="/"><img id="logo" src="/logo1.jpg" alt="L" width="97px"></a> <span
			class="dropdown"> <span class="dropbtn" id="profile">Options</span><span
			class="dropdown-content"><a href="profile.jsp">Orders</a><a
				href="checkcart.jsp">Cart</a><a href="logout">Logout</a></span></span>

	</h1>


	<div id="productAdd">
		<h3 id="productAddTitle">Change Status</h3>
		<input type="text" id="getProductName" class="productField"
			placeholder="Enter OrderID"><br> <input type="text"
			id="getProductValue" class="productField"
			placeholder="Status: Started to Deliver / Delivered"><br> <input
			id="clickProduct" onclick="changeStatus()" value="Change"
			type="submit"> <input type="number" style="display: none;"
			placeholder="Stock" id="getProductCount">
	</div>


	<div id="adminOptions">

		<button class="adminOptions" id="userList" onclick="getUser()"
			type="button">User's Orders</button>
		<button class="adminOptions" id="ProductList" onclick="getProducts()"
			type="button">Product List</button>

	</div>

	<script>
		getUser()
	</script>

	<table id="users">

		<tbody id="rows">

		</tbody>
	</table>


</body>
</html>