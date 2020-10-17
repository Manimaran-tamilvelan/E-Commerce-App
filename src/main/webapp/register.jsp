<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="content-type"
	content="application/xhtml+xml; charset=UTF-8" />
<title>Register - Welcome to this E-Commerce App !</title>

<link rel="stylesheet" type="text/css" href="style.css">

<script src="http://code.jquery.com/jquery-3.5.1.min.js"
	integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
	crossorigin="anonymous"></script>
<script src="script.js"></script>

</head>

<body>

	<h1 id="heading">
		<a href="/"><img src="/logo1.jpg" alt="L" width="97px"></a>

		<button id="headLoginBtn"
			onclick="document.location.href='login.jsp';">Login</button>
	</h1>


	<form action="register" method="POST">

		<h3 id="actionPage">Register</h3>
		<div id="loginform">
			
			<input type="text" class="field" pattern="^[A-Za-z0-9](.)?[\S]{5,15}" placeholder="Enter Username"
				name="userName" required> <br>
			 <input class="field" type="password"
				placeholder="Enter Password" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%#.*?&])[A-Za-z\d@$#.!%*?&]{8,}$" name="password" required> <br>
			
			<input type="text" class="field" pattern="\d\d-\d\d-\d{4}" placeholder="Enter DOB"
				name="dOB" required> <br>
			<input type="text" class="field" pattern="[A-Za-z0-9]+[.]?[A-Za-z0-9]*@[A-Za-z]+\.[A-Za-z]+(\.[A-Za-z]*)?[A-Za-z]" placeholder="Enter MailID"
				name="mailID" required> <br>
				
			
				<input type="text" pattern="(\+91)?\s?\d{10}" class="field" placeholder="Enter Mobile No"
				name="mobileNo" required> <br>
				
				
				
				 <input
				value="Register" id="click" type="submit">
		</div>
	</form>

</body>
</html>