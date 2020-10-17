
let addedProducts = [];

let mobileNo;
let mailID;

$(document).ready(function() {


	let userNamecheck = false;


	$("#rinput1").on("keyup", function() {

		let userName = $("#rinput1").val();

		checkUserName(userName);

		//user regex here for every input

	});



	let checkUserName = function(a) {
		var str = a;
		var patt = /^[A-Za-z0-9](.)?[\S]{5,15}/g;

		//var patt = /[A-Za-z0-9]+[.]?[A-Za-z0-9]*@[A-Za-z]+\.[A-Za-z]+(\.[A-Za-z]*)?[A-Za-z]/g;
		var result = str.match(patt);


		if (result == null || str.length >= 15) {
			$("#rinput1").css("border-bottom", "1px solid red");
			//console.log("not satisfied");
			userNamecheck = false;
		}
		else {
			$("#rinput1").css("border-bottom", "1px solid green");
			//console.log("Satis");
			userNamecheck = true;
		}

	}


	$("#rinput2").on("keyup", function() {

		let password = $("#rinput2").val();

		checkPassword(password);

		//user regex here for every input

	});

	let checkPassword = function(a) {
		var str = a;
		//var patt = /^[A-Za-z0-9](.)?[\S]{5,15}/g;

		var patt = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%#.*?&])[A-Za-z\d@$#.!%*?&]{8,}$/g;
		var result = str.match(patt);

		if (result == null) {
			$("#rinput2").css("border-bottom", "1px solid red");
			//console.log("not satisfied");
		}
		else {
			$("#rinput2").css("border-bottom", "1px solid green");
			//console.log("Satis");
		}

	}



	$("#rinput3").on("keyup", function() {

		let mailID = $("#rinput3").val();

		checkMailID(mailID);

		//user regex here for every input

	});

	let checkMailID = function(a) {
		var str = a;
		//var patt = /^[A-Za-z0-9](.)?[\S]{5,15}/g;

		var patt = /[A-Za-z0-9]+[.]?[A-Za-z0-9]*@[A-Za-z]+\.[A-Za-z]+(\.[A-Za-z]*)?[A-Za-z]/g;
		var result = str.match(patt);

		if (result == null) {
			$("#rinput3").css("border-bottom", "1px solid red");
			//console.log("not satisfied");
		}
		else {
			$("#rinput3").css("border-bottom", "1px solid green");
			//console.log("Satis");
		}

	}


	$("#rinput4").on("keyup", function() {

		let mobileNo = $("#rinput4").val();

		checkMobileNo(mobileNo);

		//user regex here for every input

	});

	let checkMobileNo = function(a) {
		var str = a;
		//var patt = /^[A-Za-z0-9](.)?[\S]{5,15}/g;

		var patt = /(\+91)?\s?\d{10}/g;
		var result = str.match(patt);

		if (result == null || str.length > 14) {
			$("#rinput4").css("border-bottom", "1px solid red");
			//console.log("not satisfied");
		}
		else {
			$("#rinput4").css("border-bottom", "1px solid green");
			//console.log("Satis");
		}

	}


});



let getUser = function() {

	//$("#userList").click(function() {
	//	$("#users").show();
	//})

	$.ajax({
		url: "fetchuserorders",
		success: function(result) {
			//console.log(result)

			$("tr").remove()
			$("#users").append("<tr><th>Order ID</th><th>Product ID</th><th>Status</th><th>Mail ID</th></tr>")
			$("#rows").append(result);
			$("#userList").css("background-color", "red").attr("disabled", "disabled");
			$("h3").text("Change Status")
			$("#getProductName").attr("placeholder", "Enter OrderID")
			$("#getProductValue").attr("placeholder", "Status: Started to Deliver / Delivered")
			$("#clickProduct").attr("onclick", "changeStatus()")
			$("#clickProduct").attr("value", "Change")
			$("#ProductList").css("background-color", "white").removeAttr("disabled");
			$("#getProductCount").hide();


		},

		error: function(xhr) {
			console.log(xhr.status)
		}
	})


}


let getProducts = function() {


	$.ajax({
		url: "getProducts",
		success: function(result) {

			//console.log(result)

			$("tr").remove()
			$("#users").append("<tr><th>Product ID</th><th>Product Name</th><th>Price</th><th>Stock's</th></tr>")
			$("#rows").append(result);

			$("h3").text("Add Products")
			$("#getProductName").attr("placeholder", "Enter Product Name")
			$("#getProductValue").attr("placeholder", "Enter Product Value")
			$("#clickProduct").attr("onclick", "AddProducts()")
			$("#clickProduct").attr("value", "Add")
			$("#getProductCount").show();
			//document.location.href='adminpage.jsp'

			$("#ProductList").css("background-color", "red").attr("disabled", "disabled");
			$("#userList").css("background-color", "white").removeAttr("disabled");


		},

		error: function(xhr) {
			console.log(xhr.status)
		}



	})



}



let AddProducts = function() {


	let productName = $("#getProductName").val();
	let productValue = $("#getProductValue").val();
	let stockCount = $("#getProductCount").val();



	if (productName == "" || productValue == "" || stockCount == "") {
		console.log("empty");
		return "";
	}

	$.ajax({
		url: "addToCart",
		data: {
			productName: $("#getProductName").val(),
			productValue: $("#getProductValue").val(),
			stockValue: $("#getProductCount").val()
		},
		success: function(result) {

			$("#rows").append(result);

		},

		error: function(xhr) {
			console.log(xhr.status)
		}

	})

	$("#getProductName").val("");
	$("#getProductValue").val("");
	$("#getProductCount").val("");


}



$(document).ready(function() {
	$('div .selectedButton').click(function() {


		let value = $(this).siblings(".selected").text();
		console.log(value)

		$(this).css("background", "green");
		$(this).attr("disabled", "disabled");


		//let id = $(this).children(".selected").text();



		$.ajax({
			url: "addProductToCart",
			data: {
				productID: $(this).siblings(".selected").text()
			},
			success: function(result) {

				if (result == "added") {

					console.log(result)
				}

				else if (result == "not added") {

					$(".selectedButton").css("background", "brown")
					$(".selectedButton").removeAttr("disabled")
					//console.log(result)
					//temp cart until logout
				}


			},
			error: function(xhr) {

				console.log(xhr.status)

			}


		})




	})


});


$(document).ready(function() {

	let makeExistingCart = function() {

		let oldCartSize = addedProducts.length;

		for (var i = 0; i < oldCartSize; i++) {
			let id = addedProducts[i];

			$("#cartCount").append(oldCartSize);
			$("." + id).children(".selectedButton").css("background", "green")
			$("." + id).children(".selectedButton").attr("disabled", "disabled")
		}

	}

	makeExistingCart();

})


$(document).ready(function() {

	$("td #deleteFromCartList").click(function() {

		let value = $(this).siblings().text();
		//console.log(value)

		$.ajax({
			url: "deleteFromCart",
			data: {
				"id": value,
			},
			success: function(result) {
				console.log(result)

				location.reload();
			},
			error: function(xhr) {
				console.log(xhr.status)
			}

		})

	})

})


function hello() {



	if (validateForm()) {


		mobileNo = $("#mobileNo").val()
		mailID = $("#mailId").val()


		$.ajax({
			url: "getShippingAddress",
			data: {
				"firstName": $("#firstName").val(),
				"lastName": $("#lastName").val(),
				"address": $("#address").val(),
				"townOrCity": $("#townOrCity").val(),
				"pincode": $("#pincode").val(),
				"mobileNo": $("#mobileNo").val(),
				"mailId": $("#mailId").val(),

			},
			success: function(result) {

				if (result == "added")
					$("#payButton").css("background-color", "#a46497").removeAttr("disabled")
				//alert("Address Confirmed !")
				$("#getResult").text("")
				$("#getResult").append("Address Confirmed, You could pay now!")
				
			}


		})


	}


	else
		console.log("not")
		
}


function validateForm() {
	
	var isValid = true;
	$('.field').each(function() {
		$(this).css("background", "whitesmoke")
		if ($(this).val() === '') {
			$(this).css("border-bottom", "1px solid red")
			
			isValid = false;
		}


	});
	return isValid;
}



let getAddress = function() {

	$.ajax({
		url: "getAddress",
		success: function(result) {

			if (result == "") {
				console.log("new ship")
			} else {
				console.log(result)
				let details = result.split("|");

				let firstName = details[0];
				let lastName = details[1];
				let address = details[2];
				let town = details[3];
				let pincode = details[4];
				let mobileNo = details[5];
				let mailId = details[6];

				$("#firstName").attr("value", firstName);
				$("#lastName").attr("value", lastName);
				$("#address").attr("value", address);
				$("#townOrCity").attr("value", town);
				$("#pincode").attr("value", pincode);
				$("#mobileNo").attr("value", mobileNo);
				$("#mailId").attr("value", mailId);


			}

		},
		error: function(xhr) {
			console.log(xhr.status)
		}

	})

}



let changeStatus = function(){
	let productId = $("#getProductName").val();
	let status = $("#getProductValue").val();
	
	//console.log(productId+"-"+status)
	
	$.ajax({
		url: "changeStatus",
		data:{
			"orderID": productId,
			"status":status
		},
		success: function(){
			
			location.reload();
		},
		error: function(xhr){
			console.log(xhr.status)
		}
		
		
	})
}
