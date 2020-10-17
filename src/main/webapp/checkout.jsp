<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Billing Checkout | Welcome to this E-Commerce App !</title>

<link rel="stylesheet" type="text/css" href="style.css">

<script src="http://code.jquery.com/jquery-3.5.1.min.js"
	integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
	crossorigin="anonymous"></script>
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
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

		<a href="#" id="profileIcon"></a> <a href="/checkcart.jsp"><img
			id="myCart" src="/logo.png" alt="L" width="40px"></a>

		<!--  form id="formField">
			<div id="formField" class="SearchFieldHighlight">
				<input type="submit" value="&#8981;" id="searchButton"> <input
					value="" type="text" id="searchItem">
			</div>
		</form-->

	</h1>


	<div id="myContainer">
		<div id="shippingDetails">
			<h3 id="checkOutHeading">Shipping Address</h3>
			<span id="shippingAddressField"> <label id="checkoutlabel">First
					Name</label> <input class="field" id="firstName" type="text" required><span>&emsp;</span>
				<label id="checkoutlabel">Last Name</label> <input id="lastName"
				class="field" type="text" required> <br> <br> <label
				id="checkoutlabel">Street Address</label> <input type="text"
				class="field" id="address"
				placeholder="House No, Street Name, Landmark" style="width: 350px;"
				required> <br> <br> <label id="checkoutlabel">Town/City</label>
				<input id="townOrCity" class="field" type="text" required>
			</span><span>&emsp;</span> <label id="checkoutlabel">Area Code</label> <input
				id="pincode" class="field" type="text" required> <br> <br>
			<span id="checkoutlabel">Country/State :</span><br> <span
				id="checkoutlabel" style="font-weight: bold;">India/TamilNadu
				*</span><br> <br>

			<h3 id="checkOutHeading">Contact Detail</h3>
			<label id="checkoutlabel">Mobile No</label> <input id="mobileNo"
				class="field" type="text" required> <span>&emsp;&emsp;</span>
			<label id="checkoutlabel">Mail ID</label> <input class="field"
				id="mailId" type="text" required> <br> <br> <br>
			<input type="submit" id="confirmButtonAddress" value="Update/Confirm"
				onclick="hello()">
				<button id="payButton" onclick="pay()" disabled="disabled"
			style="background-color: grey;">Click to Pay</button><br>
			<span id="getResult"style="padding-left:20px; color:grey;"></span>
		</div>
	</div>



	<script>getAddress();</script>

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

	<script>

	let pay = function() {

		
		var options = {
			"key": "rzp_test_mXDh3dstoySxhS", // Enter the Key ID generated from the Dashboard
			"amount": ${overAllCost} *100, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
			"currency": "INR",
			"name": "Byte",
			"description": "Test Transaction",
			"image": "/logo.png",

			"handler": function(response) {
				
				$.ajax({
					url: "payment",
					data: {
						"orderId": response.razorpay_payment_id,
						"ref": "cart",
					},
					success: function(result){
						console.log(result)
						console.log("saved");
						
					},
					error: function(){
						console.log("Not saved::Err")
					}
					
				})
				alert("Transaction Successful !")
				document.location.href='profile.jsp';
				//alert(response.razorpay_payment_id);
				//alert(response.razorpay_order_id);
				//alert(response.razorpay_signature)
				
				//save razorpay_payment_id with usermail id with their cart
			},
			"prefill": {
				
				"email": mailID,
				"contact": mobileNo
			},
			"notes": {
				"address": "Razorpay Corporate Office"
			},
			"theme": {
				"color": "#F37254"
			}
		};
		var rzp1 = new Razorpay(options);
		rzp1.open();

	}

	
	</script>
	<%
		session.removeAttribute("auth");
	%>


	<div id="checkCartFooter">- - - - Copyright @ 2020 | E-Commerce
		App - - - -</div>

</body>
</html>