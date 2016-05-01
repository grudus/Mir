function load() {

    var request = new XMLHttpRequest();
    request.onreadystatechange = function() {
        if (request.readyState === 4) {
            if (request.status === 200) {
                console.log("Response: " + request.responseText);
                if (request.responseText.localeCompare("ok") !== 0) {
                    var div = document.getElementById("bad_login_info");
                    div.setAttribute("class", "bad_login_info");
                }
            }
        }
    };
    request.open("GET", "/login.json", true);
    request.send();
}

function signUp() {
    console.log("Kliklo");
    window.location = "/create";
}
load();
