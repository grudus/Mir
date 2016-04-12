
console.log("Javascript works");
var mainURL = "/dupa";
var allMessagesDiv = document.getElementById("all_messages");

var actualLenght = 0;

function load() {

    var request = new XMLHttpRequest();
    request.onreadystatechange = function() {
        if (request.readyState === 4 && request.status === 200) {
            console.log("Response: " + request.responseText);
            refreshMessages(JSON.parse(request.responseText), actualLenght);
        }
    };
    request.open("GET", mainURL, true);
    request.send();
}

function sendMessage() {
    var messageForm = document.getElementById("adding_message");
    var message = messageForm.children[0].value;

    var arr = {"message":message, "author":"anon"};

    var request = new XMLHttpRequest();
    var params = "message=" + encodeURIComponent(message) +
        "&author=anon";
    request.open("POST", mainURL, true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send(params);
    load();

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
