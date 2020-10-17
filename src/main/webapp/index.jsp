<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.google.appengine.api.datastore.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Welcome to this E-Commerce App !</title>

<link rel="stylesheet" type="text/css" href="style.css">

<script src="http://code.jquery.com/jquery-3.5.1.min.js"
	integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
	crossorigin="anonymous"></script>

<script src="script.js"></script>


<script>
	let loggedIn = function() {

		$("#addToCart").css("filter", "none");
		$(".loginButton").hide();
		$(".registerButton").hide();
		//$("#searchButton").css("margin-right", "46%");
		$("#profileIcon")
				.append(
						"<span class=\"dropdown\"><span class=\"dropbtn\"  id=\"profile\">Options</span><span class=\"dropdown-content\"><a href=\"profile.jsp\">Orders</a><a href=\"logout\">Logout</a></span></span>");

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
	%>

	<h1 id="heading">
		<a href="/"><img id="logo" src="/logo1.jpg" alt="L" width="97px"></a>

		<button id="headLoginBtn" class="loginButton"
			onclick="document.location.href='login.jsp';">Login</button>
		<button id="headLoginBtn" class="registerButton"
			onclick="document.location.href='register.jsp';">Register</button>

		<a href="#" id="profileIcon"></a>

		<!--  form id="formField">
			<div id="formField" class="SearchFieldHighlight">
				<input type="submit" value="&#8981;" id="searchButton"> <input
					value="" type="text" id="searchItem">
			</div>
		</form-->

		<a id="indexPageCart" href="/checkcart.jsp"><img id="myCart"
			src="/logo.png" alt="L" width="40px"></a>

	</h1>


	<img id="bannerImg" src="banner.jpg">

	<span id="servermsg">${auth}</span>

	<%
		String user = (String) session.getAttribute("currentUser");

	if (user == null) {
		System.out.println("null");

		out.println("<script>$(\"#indexPageCart\").hide()</script>");
	} else if (user.equals("admin admin@123")) {
		out.print("<script>loggedIn(); </script>");
		out.print("<script>adminOptions(); </script>");
		String[] addedCart = getOldCart(user);

		out.print("<script>");

		for (String id : addedCart)
			out.print("addedProducts.push(\"" + id + "\");");
		//out.print("console.log(addedProducts);");
		out.print("</script>");
	}

	else if (user != null) {
		System.out.println("not null");
		out.print("<script>loggedIn(); </script>");
		String[] addedCart = getOldCart(user);

		out.print("<script>");

		for (String id : addedCart)
			out.print("addedProducts.push(\"" + id + "\");");

		//out.print("console.log(addedProducts);");
		out.print("</script>");

	}
	
	%>

	<%!public String[] getOldCart(String user) {

		System.out.println("yes loggedin");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query q = new Query("UserCart");
		PreparedQuery pq = datastore.prepare(q);

		String[] cartList = {};

		for (Entity getCartList : pq.asIterable()) {

			String getUserName = getCartList.getKey().getName();

			//System.out.println(getUserName +"--"+getCartList.toString());
			if (getUserName.toString().equals(user)) {

				String tempCartList = (String) getCartList.getProperty("ProductID");

				cartList = tempCartList.split(",");

			}

		}

		return cartList;

	}%>
	<script>
		let checkMessage = $("#servermsg").text();

		if (checkMessage == "") {
			$("#servermsg").hide();
		} else {
			$("#servermsg").fadeOut(3000);

		}

		//console.log(checkMessage)
	</script>
	<%
		session.removeAttribute("auth");
	%>

	<div id="container">


		<h3 id="labelName">Gadgets</h3>

		<%
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query q = new Query("Products");
		PreparedQuery pq = datastore.prepare(q);

		String result = "";

		for (Entity products : pq.asIterable()) {

			//<h2 id="productImg"></h2>
			//<h3 id="productName">Product Name</h3>
			//<h2 id="price">price</h2>
			//<button id="addToCart">Add Cart</button>

			//print gadget(label)

			int availableStocks = Integer.valueOf((String) products.getProperty("AvailableStock"));

			if (availableStocks >= 1) {

				out.print("<div id=\"cards\" class=" + products.getKey().getName() + ">");
				out.print("<h2 id=\"productImg\">" + "" + "</h2>");

				out.print("<div class=\"selected\" id=\"productId\">" + products.getKey().getName() + "</div>");
				out.print("<h3 id=\"productName\">" + products.getProperty("ProductName") + "</h3>");

				out.print("<h2 id=\"price\">" + products.getProperty("ProductValue") + "\u20B9</h2>");

				out.print("<button class=\"selectedButton\" id=\"addToCart\">Add Cart</button>");
				out.print("</div>");

			}

			else {
				System.out.println("Stock Not Available");
			}
		}
		%>

	</div>


	<div id="footer">- - - - Copyright @ 2020 | E-Commerce App - - -
		-</div>

</body>
</html>