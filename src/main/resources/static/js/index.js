window.console.log("Javascript works");
function load() {
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/dupa";
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

