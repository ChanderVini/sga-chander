var xmlHttp = false;

function createRequestObject () {
	try {
        xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (someexp) {
        try {
            xmlHttp = new ActiveXObject ("Microsoft.XMLHTTP");
        } catch (somethingelse) {
            xmlHttp = false;
        }
    }
    if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
        xmlHttp = new XMLHttpRequest ();
    }
}

function checkpass (obj) {
    var userpass = document.register.userPassword.value;
    var url = document.register.action;
    var loc = url.lastIndexOf ("/");
    url = url.substring (0, loc) + 'checkstrength';
    createRequestObject ();
    if (xmlHttp) {
        xmlHttp.open ("POST", url, true);
        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        xmlHttp.send ('userpass='+userpass);
        xmlHttp.onreadystatechange = rewriteData;
    }
}

function rewriteData () {
    if (xmlHttp.readyState == 4) {
        var responsetxt = xmlHttp.responseText;
        if (responsetxt == 'S') {
        
        }
        if (responsetxt == 'S') {
        
        }
        if (responsetxt == 'S') {
        
        }
    }
}