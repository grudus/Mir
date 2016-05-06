
function load() {
    var request = new XMLHttpRequest();
    request.onreadystatechange = function() {
        if (request.readyState === 4 && request.status === 200) {
            console.log("Response: " + request.responseText);
        }
        else console.log("status " + request.status);
    };
    request.open("GET", window.location.pathname + ".json", true);
    request.send();
}

load();

