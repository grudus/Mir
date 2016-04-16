function load() {

    var request = new XMLHttpRequest();
    request.onreadystatechange = function() {
        if (request.readyState === 4) {
            if (request.status === 200) {
                console.log("Response: " + request.responseText);
                if (request.responseText.localeCompare("ok") !== 0) {
                    var d = document.createElement("h2");
                    d.innerHTML = "NO I CHUJ NO I CZES";
                    document.body.appendChild(d);
                }
            }
        }
    };
    request.open("GET", "/login", true);
    request.send();
}

function signUp() {
    console.log("Kliklo");
    window.location = "/create";
}
load();
