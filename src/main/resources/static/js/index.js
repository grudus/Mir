
console.log("Javascript works");
var mainURL = "/dupa";
var allMessagesDiv = document.getElementById("all_messages");
var user = "anonymous";

var actualLenght = 0;

function load() {

    var request = new XMLHttpRequest();
    request.onreadystatechange = function() {
        if (request.readyState === 4 && request.status === 200) {
            var userAndMessages = (JSON.parse(request.responseText));
            console.log("Response: " + request.responseText);
            user = userAndMessages.user;
            aboutUserStuff();
            refreshMessages(userAndMessages.messages, actualLenght);
        }
    };
    request.open("GET", mainURL, true);
    request.send();
}

function aboutUserStuff() {
    if (user.localeCompare("anonymous") === 0) console.log("NIe ma usera");
    else {
        console.log("Witam, " + user);
        var loginA = document.getElementById("login_link");
        loginA.innerHTML = "LOGOUT";
        loginA.setAttribute("href", "/logout");
        document.getElementById("my_profile_link").setAttribute("href", "/" + user);
    }
}

function sendMessage() {
    var messageForm = document.getElementById("adding_message");
    var message = messageForm.children[0].value;


    var request = new XMLHttpRequest();
    var params = "message=" + encodeURIComponent(message) + "&author=" +
        (user.localeCompare("anonymous") == 0 ? "anon" : encodeURIComponent(user));
    params += "&" + document.getElementById("csrf").getAttribute("name") + "=" + document.getElementById("csrf").value;
    console.log("Przed postem mam " + params);
    request.open("POST", "/dupa", true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send(params);
    //load();

}


function getDateFromJavaObject(timeObject) {
    console.log("Mam objekt; " + timeObject);
    var time = "";
    time += timeObject.dayOfMonth + ".";
    time += timeObject.monthValue + ".";
    time += timeObject.year + " ";
    time += timeObject.hour + ":";
    time += timeObject.minute;
    return time;
}

function refreshMessages(messagesArray, howMany) {
    howMany = howMany | 0;
    if (!messagesArray) return;
    var length = messagesArray.length;
    if (length === 0) return;
    actualLenght = length;
    for (var i = howMany; i < length; i++) {
        var object = messagesArray[i];

        var mainDiv = document.createElement("div");
        mainDiv.setAttribute("class", "one_message");

        var infoDiv = document.createElement("div");
        infoDiv.setAttribute("class", "who_and_when");

        var p1 = document.createElement("p");
        p1.innerHTML = object.author;
        infoDiv.appendChild(p1);

        var p2 = document.createElement("p");
        p2.innerHTML = getDateFromJavaObject(object.date);
        infoDiv.appendChild(p2);

        mainDiv.appendChild(infoDiv);

        var pointsDiv = document.createElement("div");
        pointsDiv.setAttribute("class", "points");

        var pluses = document.createElement("div");
        pluses.setAttribute("class", "plus");
        pluses.innerHTML = object.plus;

        var minuses = document.createElement("div");
        minuses.setAttribute("class", "minus");
        minuses.innerHTML = object.minus;

        pointsDiv.appendChild(pluses);
        pointsDiv.appendChild(minuses);
        mainDiv.appendChild(pointsDiv);

        var messageDiv = document.createElement("div");
        messageDiv.setAttribute("class", "actual_message");

        var p3 = document.createElement("p");
        p3.innerHTML = object.message;

        messageDiv.appendChild(p3);

        mainDiv.appendChild(messageDiv);

        allMessagesDiv.insertBefore(mainDiv,
            allMessagesDiv.children[0]);

    }
}
