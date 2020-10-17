<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="content-type"
	content="application/xhtml+xml; charset=UTF-8" />
<title>Login - Welcome to this E-Commerce App !</title>

<link rel="stylesheet" type="text/css" href="style.css">
<script src="http://code.jquery.com/jquery-3.5.1.min.js"
	integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
	crossorigin="anonymous"></script>
<script src="script.js"></script>

<script>
	let loggedIn = function() {

		$(".loginButton").hide();
		$(".registerButton").hide();
		$("#searchButton").css("margin-right", "62%");
	}
</script>

<script>
	$(document).ready(function() {
		$("#goButton").click(goRequest);
	});

	function goRequest() {

		var AUTH_ENDPOINT = "https://accounts.google.com/o/oauth2/v2/auth";
		var RESPONSE_TYPE = "code";
		var CLIENT_ID = "628485492305-clc02nguilt3cn9aimatqolcgd8ivc7c.apps.googleusercontent.com";
		var REDIRECT_URI = "http://localhost:8080/oauth";
		var SCOPE = "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/calendar.events.readonly";

		var requestEndpoint = AUTH_ENDPOINT + "?" + "response_type="
				+ encodeURIComponent(RESPONSE_TYPE) + "&" + "client_id="
				+ encodeURIComponent(CLIENT_ID) + "&" + "redirect_uri="
				+ encodeURIComponent(REDIRECT_URI) + "&" + "scope="
				+ encodeURIComponent(SCOPE) + "&access_type=offline";

		window.location.href = requestEndpoint;
	}
</script>

<script>
	$(document).ready(function() {
		$("#fbButton").click(fbRequest);
	});

	function fbRequest() {

		var AUTH_ENDPOINT = "https://www.facebook.com/dialog/oauth";
		var RESPONSE_TYPE = "code";
		var CLIENT_ID = "326846368586443";
		var REDIRECT_URI = "http://localhost:8080/oauth1";
		var SCOPE = "public_profile email user_posts";

		var requestEndpoint = AUTH_ENDPOINT + "?" + "response_type="
				+ encodeURIComponent(RESPONSE_TYPE) + "&" + "client_id="
				+ encodeURIComponent(CLIENT_ID) + "&" + "redirect_uri="
				+ encodeURIComponent(REDIRECT_URI) + "&" + "scope="
				+ encodeURIComponent(SCOPE);

		window.location.href = requestEndpoint;
	}
</script>

</head>

<body>



	<h1 id="heading">
		<a href="/"><img src="/logo1.jpg" alt="L" width="97px"></a>

		<button id="headLoginBtn"
			onclick="document.location.href='register.jsp';">Register</button>
	</h1>

	<span id="servermsg">${message}</span>

	<%
		String user = (String) session.getAttribute("currentUser");

	if (user != null) {
		System.out.println("not null");
		out.print("<script>loggedIn(); </script>");

	} else {
		System.out.println("null");
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

	<form action="login" method="POST">

		<h3 id="actionPage">Login</h3>
		<div id="loginform">
			<input type="text" class="field" id="linput1"
				placeholder="Enter MailID" name="mailID" required> <br>
			<input class="field" id="linput2" type="password"
				placeholder="Enter Password" name="password" required> <br>
			<input value="Login" id="click" type="submit">
		<span style="color:grey;">&nbsp; - - Or - -</span>
		<br>
		

	<button id="fbButton" type="button">Fb Login</button>
	<button id="goButton" type="button">Google Login</button>
		
		</div>
	</form>


	

	<!-- button id="newAccountButton" onclick="document.location.href='register.jsp';">Create New Account</button -->

	<script>
		let checkMessage = $("#servermsg").text();

		if (checkMessage == "") {
			$("#servermsg").hide();
		}

		console.log(checkMessage)
	</script>

	<%
		session.removeAttribute("message");
	%>


</body>
</html>