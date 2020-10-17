<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.google.appengine.api.datastore.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Profile | Welcome to this E-Commerce App !</title>

<link rel="stylesheet" type="text/css" href="style.css">

<script src="http://code.jquery.com/jquery-3.5.1.min.js"
	integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
	crossorigin="anonymous"></script>

<script src="script.js"></script>

<script>
	let loggedIn = function() {

		$(".loginButton").hide();
		$(".registerButton").hide();
		//$("#searchButton").css("margin-right", "46%");
		$("#profileIcon")
				.append(
						"<span class=\"dropdown\"><span class=\"dropbtn\"  id=\"profile\">Options</span><span class=\"dropdown-content\"><a href=\"logout\">Logout</a></span></span>");

	}
</script>

<script>
	if (window.location.hash == "#_=_")
		window.location.hash = "";

	let adminOptions = function() {

		$(document).ready(
				function() {

					$(".dropdown-content").append(
							"<a href=\"adminpage.jsp\">Console</a>")
				})

	}
</script>

</head>

<body>

	<%
		response.setHeader("cache-control", "no-cache, no-store, must-revalidate");

	String message = (String) session.getAttribute("currentUser");

	if (message == null) {

		//session.setAttribute("auth", "Something Went Wrong");

		response.sendRedirect("index.jsp");

	}
	%>

	<h1 id="heading">
		<a href="/"><img id="logo" src="/logo1.jpg" alt="L" width="97px"></a>

		<button id="headLoginBtn" class="loginButton"
			onclick="document.location.href='login.jsp';">Login</button>
		<button id="headLoginBtn" class="registerButton"
			onclick="document.location.href='register.jsp';">Register</button>

		<a href="#" id="profileIcon"></a> <a href="/checkcart.jsp"><img
			id="myCart" src="/logo.png" alt="L" width="40px"></a>

		<!--  form id="formField">
			<div id="formField" class="SearchFieldHighlight">
				<input type="submit" value="&#8981;" id="searchButton"> <input
					value="" type="text" id="searchItem">
			</div>
		</form-->

	</h1>


	<!-- ${currentUser} -->

	<div id="cartContainer">
		<h3 id="labelName">Your Orders</h3>


		<table>
			<tbody>

				<%
					DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

				Query q = new Query("UserOrders");
				PreparedQuery pq = datastore.prepare(q);

				String yourOrders = "";
				String Status = "";

				for (Entity e : pq.asIterable()) {

					if (e.getProperty("userMailID").toString().equals(message)) {

						yourOrders = yourOrders + e.getProperty("Orders") + ",";

					}

				}

				if (yourOrders != "") {
					yourOrders = yourOrders.substring(0, yourOrders.length() - 1);
				}

				if (yourOrders.contains(",")) {
					String[] yourOrder = yourOrders.split(",");

					for (String orders : yourOrder) {

						Query q1 = new Query("Products");

						PreparedQuery pq1 = datastore.prepare(q1);

						for (Entity e2 : pq1.asIterable()) {

					//System.out.println(e2.getKey().getName());
					if (e2.getKey().getName().toString().equals(orders)) {

						String productName = (String) e2.getProperty("ProductName");
						String productValue = (String) e2.getProperty("ProductValue");
						//System.out.println("many"+productName + "==" + productValue);

						//out.print("<h2 id=\"productImg\">" + "" + "</h2>");

						/**
						out.print("<div id=\"cartCards\">");
						out.print("<h3 id=\"productId\">" + e2.getKey().getName() + "</h3>");
						out.print("<h3 id=\"productName\">" + productName + "</h3>");
						out.print("<div id=\"productImgCart\"></div>");
						//out.print("<div class=\"selected\" id=\"productId\">" + products.getKey().getName() + "</div>");
						out.print("<h2 id=\"price\">" + productValue + "\u20B9</h2>");
						out.print("<button  id=\"deleteCart\">Remove</button>");
						out.print("</div>");
						*/

						out.print("<tr>");

						out.print(
								"<td><a href=\"#\"><img id=\"cartImgs\" src=\"https://www.flaticon.com/svg/static/icons/svg/1573/1573307.svg\"></a></td>");

						out.print("<td id=\"productNameInCart\"><a href=\"#\">" + productName + "</a></td>");

						//out.print("<div class=\"selected\" id=\"productId\">" + products.getKey().getName() + "</div>");
						out.print("<td><div id=\"price\">" + productValue + "\u20B9</div></td>");
						out.print("<td><div id=\"quantity\">1</div></td>");
						//out.print("<td><button onclick=\"eee()\" id=\"deleteCart\">Remove</button></td>");
						out.print("<td><div id=\"price\">" + productValue + "\u20B9</div></td>");

						out.print("</tr>");

					}

						}

					}
				} else {

					Query q1 = new Query("Products");

					PreparedQuery pq1 = datastore.prepare(q1);

					for (Entity e2 : pq1.asIterable()) {

						//System.out.println(e2.getKey().getName());
						if (e2.getKey().getName().toString().equals(yourOrders)) {

					String productName = (String) e2.getProperty("ProductName");
					String productValue = (String) e2.getProperty("ProductValue");

					out.print("<tr>");

					out.print(
							"<td><a href=\"#\"><img id=\"cartImgs\" src=\"https://www.flaticon.com/svg/static/icons/svg/1573/1573307.svg\"></a></td>");

					out.print("<td id=\"productNameInCart\"><a href=\"#\">" + productName + "</a></td>");

					//out.print("<div class=\"selected\" id=\"productId\">" + products.getKey().getName() + "</div>");
					out.print("<td><div id=\"price\">" + productValue + "\u20B9</div></td>");
					out.print("<td><div id=\"quantity\">1</div></td>");
					//out.print("<td><button onclick=\"eee()\" id=\"deleteCart\">Remove</button></td>");
					out.print("<td><div id=\"price\">" + productValue + "\u20B9</div></td>");

					out.print("</tr>");

						}

					}
				}
				%>

			</tbody>
		</table>
	</div>


	<span id="servermsg">${auth}</span>

	<%
		String user = (String) session.getAttribute("currentUser");

	if (user == null) {
		System.out.println("null");
	} else if (user.equals("admin admin@123")) {
		out.print("<script>loggedIn(); </script>");
		out.print("<script>adminOptions(); </script>");
	}

	else if (user != null) {
		System.out.println("not null");
		out.print("<script>loggedIn(); </script>");

	}
	%>
	<script>
		let checkMessage = $("#servermsg").text();

		if (checkMessage == "") {
			$("#servermsg").hide();
		} else {
			$("#servermsg").fadeOut(3000);

		}

		console.log(checkMessage)
	</script>
	<%
		session.removeAttribute("auth");
	%>


</body>
</html>