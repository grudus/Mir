var btn = document.getElementById("button");

//[0]-login [1]-password [2]-confirm [3]-email
var inputsState = [false, false, false, false];


function checkConditions(id) {
    switch (id) {
        case "login": checkLogin(); break;
        case "password": checkPassword(); break;
        case "confirm": checkConfirmedPassword(); break;
        case "email": checkEmail(); break;
        default:
            checkLogin();
            checkPassword();
            checkConfirmedPassword();
            checkEmail();
            break;
    }
    var ok = true;
    for (var i = 0; i < inputsState.length; i++) if (!inputsState[i]) ok = false;

    btn.disabled = !ok;
}


function checkLogin() {
    var loginInput = document.getElementById("login");
    var login = loginInput.value;

    //for empty input
    if (!login) {
        inputsState[0] = false;
        colorInput("login", false, "You must have login!");
        return;
    }
    //changing all whitespaces to '_'
    if (/\s/g.test(login)) {
        login = login.trim().replace(/\s/g, "_");
        loginInput.value = login;
    }

    //checking if login contains special characters
    if (/[~`!#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?]/g.test(login)) {
        colorInput("login", false, "Your login contains forbidden characters!");
        inputsState[0] = false;
        return;
    }

    //sending request to check if login isn't already taken
    var request = new XMLHttpRequest();
    var params = "login=" + encodeURIComponent(login);
    console.log("Parameters to send: " + params);

    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            if (request.responseText === "ok") {
                inputsState[0] = true;
                colorInput("login", true);
            } else {
                inputsState[0] = false;
                colorInput("login", false, "This login is already taken");
            }
        }
    };

    request.open("GET", "/checkUsers?" + params, true);
    request.send();
}

function checkPassword() {
    var passwordInput = document.getElementById("password");
    var password = passwordInput.value;
    if (password.length < 8) {
        inputsState[1] = false;
        colorInput("password", false, "Your input must have at least 8 characters");
        return;
    }
    inputsState[1] = true;
    colorInput("password", true);
}

function checkConfirmedPassword() {
    var confirmedPassword = document.getElementById("confirm").value;

    if (inputsState[1] && confirmedPassword === document.getElementById("password").value) {
        inputsState[2] = true;
        colorInput("confirm", true);
    }
    else {
        inputsState[2] = false;
        colorInput("confirm", false, "Password isn't confirmed!");
    }
}

function checkEmail() {
    var email = document.getElementById("email").value;
    if (/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email)) {
        inputsState[3] = true;
        colorInput("email", true);
    }
    else {
        inputsState[3] = false;
        colorInput("email", false, "Your email address is incorrect!");
    }
}


function createAccount() {
    checkConditions("all");
    //last validation
    for (var i = 0; i < inputsState.length; i++) if (!inputsState[i]) return;
    var form = document.getElementById("form");
    var params = [];
    for (i = 0; i < form.children.length; i+=2) {
        params.push(form.children[i].value);
    }
    send(params);
}

function send(parameters) {
    console.log("I am sending " + parameters);
    var request = new XMLHttpRequest();
    var params = "login=" + encodeURIComponent(parameters[0]) + "&password=" + encodeURIComponent(parameters[1])
    + "&email=" + encodeURIComponent(parameters[3]);

    params += "&" + document.getElementById("csrf").getAttribute("name") + "=" + document.getElementById("csrf").value;

    request.open("POST", "/create", true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send(params);
}


function colorInput(id, isOk, text) {
    var input = document.getElementById(id);
    if (isOk) input.setAttribute("class", "good_login");
    else {
        input.setAttribute("class", "bad_login");
        console.log(text);
    }
}

