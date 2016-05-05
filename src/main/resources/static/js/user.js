var userName;

function load() {

    var url = window.location.pathname;
    var request = new XMLHttpRequest();
    request.onreadystatechange = function() {
        if (request.readyState === 4 && request.status === 200) {
            console.log("Response: " + request.responseText);
            var json = JSON.parse(request.responseText);
            getMessages(json.messages, json.logged);
            userName = json.user;
            document.getElementById("login_text").innerHTML =
                userName;
            console.log(json.logged);
          }
    };
    request.open("GET", url + ".json", true);
    request.send();
    console.log(url + ".json");
}

function getMessages(messagesArray, isLogged) {
    if (!messagesArray) return;
    var length = messagesArray.length;
    if (length === 0) return;
    var allMessagesDiv = document.getElementById("all_messages")
    for (var i = 0; i < length; i++) {
        var object = messagesArray[i];

        var mainDiv = document.createElement("div");
        mainDiv.setAttribute("class", "one_message");

        var infoDiv = document.createElement("div");
        infoDiv.setAttribute("class", "who_and_when");

        if (isLogged) {
            var removeDiv = document.createElement("div");
            removeDiv.setAttribute("class", "remove_div");
            removeDiv.innerHTML = "X";
            const id = object._id;
            removeDiv.onclick = function () {
                removeMessage(id);
            };
            infoDiv.appendChild(removeDiv);
        }

        var p2 = document.createElement("p");
        p2.innerHTML = getDateFromJavaObject(object.date);
        infoDiv.appendChild(p2);

        mainDiv.appendChild(infoDiv);
        
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

function removeMessage(id) {
    var user = window.location.pathname;
    var request = new XMLHttpRequest();
    var params = "id=" + encodeURIComponent(id);

    params += "&" + document.getElementById("csrf").getAttribute("name") + "=" + document.getElementById("csrf").value;
    //TODO change to delete
    request.open("POST", user, true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send(params);
    window.location = user;
}

function getDateFromJavaObject(timeObject) {
    var time = "";
    time += timeObject.dayOfMonth + ".";
    time += timeObject.monthValue + ".";
    time += timeObject.year + " ";
    time += timeObject.hour + ":";
    time += timeObject.minute;
    return time;
}

function removeUser() {
    document.getElementById("login_text").innerHTML
        = "Do you really want to delete your account?";
    var div = document.getElementById("user_info");
    div.children[0].onclick = ohNo;
    var submit = document.createElement("button");
    submit.innerHTML = "YES";
    submit.onclick = ohYesRemove;
    div.appendChild(submit);
    var ohno = document.createElement("button");
    ohno.innerHTML = "NO";
    ohno.onclick = ohNo;
    div.appendChild(ohno);
}

function ohNo() {
    document.getElementById("login_text").innerHTML
        = userName;
    var div = document.getElementById("user_info");
    div.children[0].onclick = removeUser;
    for (var i = 0; i < 2; i++) div.removeChild(document.getElementsByTagName("button")[0]);
}

function ohYesRemove() {
    var request = new XMLHttpRequest();
    var user = window.location.pathname;

    var params = document.getElementById("csrf").getAttribute("name") + "=" + document.getElementById("csrf").value;
    //TODO change to delete
    request.open("POST", user + "/removeAccount", true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send(params);
    window.location = user;
}


load();
