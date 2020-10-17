<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.google.appengine.api.datastore.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Cart - Welcome to this E-Commerce App !</title>

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

		<a href="#" id="profileIcon"></a>

		<!-- form id="formField">
			<div id="formField" class="SearchFieldHighlight">
				<input type="submit" value="&#8981;" id="searchButton"> <input
					value="" type="text" id="searchItem">
			</div>
		</form -->

	</h1>





	<span id="servermsg">${auth}</span>

	<%
		String user = (String) session.getAttribute("currentUser");

	if (user == null) {
		response.sendRedirect("index.jsp");
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

	<div id="cartContainer">


		<h3 id="labelName">Your Cart</h3>


		<table>
			<tbody>

				<tr>
					<th></th>
					<th id="productHeading">Product</th>
					<th>Price</th>
					<th>Quantity</th>
					<th>Subtotal</th>
					<th></th>
				</tr>

				<%
					DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

				Key key;

				try {

					key = KeyFactory.createKey("UserCart", user);

					System.out.println(datastore.get(key));

					Entity e = datastore.get(key);

					int total = 0;

					String cartIds = (String) e.getProperty("ProductID");

					if (e.getProperty("ProductID") == "") {

						//not working for no user and empty cart
						System.out.println("Empty");
					}

					else if (cartIds.contains(",")) {

						String[] cartId = cartIds.split(",");

						for (String productId : cartId) {
					//System.out.println(productId);

					Query q = new Query("Products");

					PreparedQuery pq = datastore.prepare(q);

					for (Entity e2 : pq.asIterable()) {

						//System.out.println(e2.getKey().getName());
						if (e2.getKey().getName().toString().equals(productId)) {

							String productName = (String) e2.getProperty("ProductName");
							String productValue = (String) e2.getProperty("ProductValue");
							//System.out.println("many"+productName + "==" + productValue);

							total = total + Integer.valueOf(productValue.replaceAll(",", ""));
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
							out.print("<td><span id=\"productId\">" + e2.getKey().getName()
									+ "</span><a id=\"deleteFromCartList\">&#10006;</a></td>");

							out.print("</tr>");

						}

					}

						}

					} else {

						String cartId = cartIds;

						//need implementation

						Query q = new Query("Products");

						PreparedQuery pq = datastore.prepare(q);

						for (Entity e2 : pq.asIterable()) {

					//System.out.println(e2.getKey().getName());
					if (e2.getKey().getName().toString().equals(cartId)) {

						String productName = (String) e2.getProperty("ProductName");
						String productValue = (String) e2.getProperty("ProductValue");
						//System.out.println("only one"+productName+"=="+productValue);

						total = total + Integer.valueOf(productValue.replaceAll(",", ""));

						/**	out.print("<div id=\"cartCards\">");
							out.print("<h3 id=\"productId\">" + e2.getKey().getName() + "</h3>");
							out.print("<h3 id=\"productName\">" + productName + "</h3>");
							out.print("<div id=\"productImgCart\"></div>");
							//out.print("<div class=\"selected\" id=\"productId\">" + products.getKey().getName() + "</div>");
							out.print("<h2 id=\"price\">" + productValue + "\u20B9</h2>");
							out.print("<button  id=\"deleteCart\">Remove</button>");
							out.print("</div><br>");*/

						out.print("<tr>");

						out.print(
								"<td><a href=\"#\"><img id=\"cartImgs\" src=\"https://www.flaticon.com/svg/static/icons/svg/1573/1573307.svg\"></a></td>");

						out.print("<td id=\"productNameInCart\"><a href=\"#\">" + productName + "</a></td>");

						//out.print("<div class=\"selected\" id=\"productId\">" + products.getKey().getName() + "</div>");
						out.print("<td><div id=\"price\">" + productValue + "\u20B9</div></td>");
						//out.print("<td><input id=\"quantity\" value=\"1\" type=\"number\"></td>");
						out.print("<td><div id=\"quantity\">1</div></td>");
						//out.print("<td><button onclick=\"eee()\" id=\"deleteCart\">Remove</button></td>");
						out.print("<td><div id=\"price\">" + productValue + "\u20B9</div></td>");
						out.print("<td><span id=\"productId\">" + e2.getKey().getName()
								+ "</span><a id=\"deleteFromCartList\">&#10006;</a></td>");

						out.print("</tr>");

					}

						}

					}

					out.print("<div id=\"totalAmt\"><span id=\"myCartAmt\">" +total +"</span>\u20B9</div>");
					System.out.println(total);

				} catch (EntityNotFoundException e) {
					System.out.println("entity not found");
				//	out.println(
				//	"<script>$(\"table\").hide(); $(document).ready(function(){$(\"#confirmCart\").css(\"background\",\"gray\").attr(\"disabled\",\"true\");}) </script>");
					//out.print("<div id=\"noCart\">Nothing Found</div>");
					
				} catch (IllegalArgumentException e) {
					System.out.println("user not found ");
				}
				%>

			</tbody>
		</table>

	</div>

	<div id="verifyCart">

		Total: <span id="totalRs"></span> <br>
		<form action="checkout">
			<input type="text" name="ref" value="cart" style="display: none;">
			<input type="submit" id="confirmCart" value="proceed to checkout">
		</form>
	</div>



	<script>
		let total = $("#totalAmt").text();
		let checkTotal = $("#myCartAmt").text();
		if(checkTotal == 0){
			$("tbody").hide(); 
			$("table").replaceWith("<div id=\"noCart\">Nothing Found</div><button id=\"noCartFound\" onclick=\"document.location.href='/'\">Return to Home</button>");
			$("#confirmCart").css("background","gray").attr("disabled","true");
			
		}
		$("#totalRs").append(total);
	</script>

	<div id="checkCartFooter">- - - - Copyright @ 2020 | E-Commerce
		App - - - -</div>

</body>
</html>