var url = "http://localhost:8080/dupa";

window.console.log("Javascript works");
function load() {
    var request = new XMLHttpRequest();
    request.onreadystatechange = function() {
        if (request.readyState === 4) {
            if (request.status === 200) {
                alert(request.responseText);
            }
        }
    };
    request.open("GET", url, true);
    request.send();
}

function sendMessage() {
    var messageForm = document.getElementById("adding_message");
    var message = messageForm.children[0].value;
    var tags = [];
    print("Mamy wiadomosc: " + message);


    var form = document.createElement("form");
    form.setAttribute("method", "post");

    var arr = {"message":message, "author":"anon"};

    for (var key in arr) {
        var messageToSend = document.createElement("input");
        messageToSend.setAttribute("type", "hidden");
        messageToSend.setAttribute("name", key);
        messageToSend.setAttribute("value", arr[key]);
        form.appendChild(messageToSend);
    }

    document.body.appendChild(form);
    form.submit();

    //
    //var request = new XMLHttpRequest();
    //var params = "message=dupa&author=gej";
    //request.open("POST", url, true);
    //request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    //request.send(params);


}

function print(message) {
    window.console.log(message);

}

//for (var i = 0; i < message.length; i++) {
//    if (message.charAt(i) === '#') {
//        for (var j = ++i; j < message.length; j++) {
//            if (/\s/.test(message.charAt(j))) {
//                tags.push(message.substr(i, j-i));
//                break;
//            }
//            else if  (j === message.length-1) {
//                tags.push(message.substr(i, j-i+1));
//            }
//        }
//    }
//}
//
//for (i = 0; i < tags.length; i++) print(tags[i]);