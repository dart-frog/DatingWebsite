/**
 * 
 */

var req;
var searchField;
var usersList;


function init() {
	searchField = document.getElementById("searchForm");
	usersList = document.getElementById("listOfUsers");
	document.getElementById("submitButton").setAttribute("onClick", "return doCompletion()");
}

function doCompletion() {
	var url = "SearchHandler?";
	var field;
	for(var i = 0; i < 4; i++) {
		field = searchField[i];
		url += escape(field.name) + "=" + escape(field.value) + "&";
	}
	url = url.substring(0, url.length-1);
	req = initRequest();
	req.open("GET", url, false);
	req.onreadystatechange = callback;
	req.send(null);
}

function doTest() {
	document.getElementsByName("FirstName")[0].setAttribute("value", "John");
	return doCompletion();
}

function initRequest() {
	return new XMLHttpRequest();
}

function callback() {
	clearUsers();
	if(req.readyState == 4) {
		if(req.status == 200) {
			console.log(req);
			parseUsers(req.responseXML);
		}
	}
}

function clearUsers() {
	if(usersList.getElementsByTagName("div").length > 0) {
		for(var loop = usersList.childNodes.length - 1; loop >= 0; loop--) {
			usersList.removeChild(usersList.childNodes[loop]);
		}
	}
}

function addUser(id, firstName, lastName, Class, gender) {
	var user = document.createElement("div");
	var link = document.createElement("a");
	link.setAttribute("href", "Users.jsp?user=" + id);
	link.innerHTML = firstName + " " + lastName;
	user.innerHTML = link.outerHTML;
	user.innerHTML += "<br>Class: " + Class + "<br>Gender: " + gender + "<br>";
	usersList.appendChild(user);
}

function parseUsers(responseXML) {
	var users = responseXML.getElementsByTagName("users")[0];
	for(var loop = 0; loop < users.childNodes.length; loop++) {
		var user = users.childNodes[loop];
		var firstName = user.getElementsByTagName("firstName")[0].innerHTML;
		var lastName = user.getElementsByTagName("lastName")[0].innerHTML;
		var id = user.getElementsByTagName("id")[0].innerHTML;
		var Class = user.getElementsByTagName("class")[0].innerHTML;
		var gender = user.getElementsByTagName("gender")[0].innerHTML;
		addUser(id, firstName, lastName, Class, gender);
	}
}
