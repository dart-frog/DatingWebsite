/**
 * 
 */

var req;

function init() {
	searchField = document.getElementById("searchForm");
	usersList = document.getElementById("listOfUsers");
	document.getElementById("searchForm").setAttribute("onSubmit", "return doCompletion()");
}

function doCompletion() {
	var url = "MatchMaker.jsp?" + escape(searchField.values);
	req = initRequest();
	req.open("GET", url, true);
	req.onreadystatechange = callback;
	req.send(null);
}

function initRequest() {
	return new XMLHttpRequest();
}

function callback() {
	clearUsers();
	if(req.readyState == 4) {
		if(req.status == 200) {
			parseUsers(req.responseXML);
		}
	}
}

function addUser(id, firstName, lastName, Class, gender) {
	var user = document.createElement("div");
	user.innerHTML = firstName + " " + lastName + "<br>" + Class;
	usersList.appendChild(user);
}

function parseUsers(responseXML) {
	var users = responseXML.getElementsByTagName("users")[0];
	for(var loop = 0; loop < users.childNodes.length; loop++) {
		var user = users.childNodes[loop];
		var firstName = user.getElementsByTagName("firstName")[0];
		var lastName = user.getElementsByTagName("lastName")[0];
		var id = user.getElementsByTagName("id")[0];
		var Class = user.getElementsByTagName("Class")[0];
		var gender = user.getElementsByTagName("gender")[0];
		addUser(id, firstName, lastName, Class, gender);
	}
}

function clearUsers() {
	if(usersList.getElementsByTagName("div").length > 0) {
		for(var loop = usersList.childNodes.length - 1; loop >= 0; loop--) {
			usersList.removeChild(usersList.childNodes[loop]);
		}
	}
}
