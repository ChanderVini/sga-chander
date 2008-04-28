var pageDetailArray = new Array ();

function holdFormData () {
    var doc = document;
    var formslength = doc.forms.length;
    var formArray = new Array ();
    for (var cnt = 0; cnt < formslength; cnt++) {
        var formele = doc.forms[cnt];
        var elementsLength = formele.elements.length;
        var elementArray = new Array ();
        for (var elecnt = 0; elecnt < elementsLength; elecnt++) {
            var ele = formele.elements [elecnt];
            var elementType = ele.type;
            var elementName = ele.name;
            var elementValues = getElementValues (ele);
            var elementDetailVar = new elementDetail (elementType, elementName, elementValues);
            elementArray.push (elementDetailVar);
        }
        var formName = formele.name;
        var formAction = formele.action;
        var formMethod = formele.method;
        var formDetailVar = new formDetail (formName, formAction, formMethod, elementArray);
        formArray.push (formDetailVar);
    }
    var documentName = doc.URL;
    var linksArray = getLinks (doc);
    var pageDetailVar = new pageDetail (documentName, formArray, linksArray);
    pageDetailArray.push (pageDetailVar);
}

function getLinks (doc) {
    var linkeles = doc.anchors;
    var linksArray = new Array ();
    for (var cnt = 0; cnt < linkeles.length; cnt++) {
        var linkName = linkeles[cnt].name;
        var linkHref = linkeles[cnt].href;
        var linkDetailVar = new linkDetail (linkName, linkHref);
        linksArray.push (linkDetailVar);
    }
    return linksArray;
}

function getElementValues (ele) {
    var elementType = ele.type;
    if (elementType == 'text') {
        return handleTextfield (ele);
    }
    if(elementType == 'radio' || elementType == 'checkbox') {
        return handleCheckbox (ele);
    }
    if (elementType == 'select-one') {
        return handleSingleselect (ele);
    }
    if (elementType == 'select-multiple') {
        return handleMultiselect (ele);
    }
}

function handleTextfield (ele) {
    var valarray = new Array ();
    valarray.push (ele.value);
    return valarray;
}

function handleCheckbox (eles) {
    var eleslength = eles.length;
    var valarray = new Array ();
    for (var cnt = 0; cnt < eleslength; cnt++) {
        if (eles[cnt].checked){
            valarray.push (eles[cnt].value);
        }
    }
    return valarray;
}

function handleSingleselect (ele) {
    var optionlen = ele.options.length;
    var valarray = new Array ();
    for (var cnt = 0; cnt < optionlen; cnt++) {
        if (ele.options[cnt].selected) {
            valarray.push (ele.options[cnt].value);
            break;
        }
    }
    return valarray;
}

function handleMultiselect (ele) {
    var optionlen = ele.options.length;
    var valarray = new Array ();
    for (var cnt = 0; cnt < optionlen; cnt++) {
        if (ele.options[cnt].selected) {
            valarray.push (ele.options[cnt].value);
        }
    }
    return valarray;
}

function showDetails () {
    for (var cnt = 0; cnt < pageDetailArray.length; cnt++) {
        var pagedet = pageDetailArray [cnt];
        alert (pagedet.pageName);
        var formdets = pagedet.formDetails;
        for (var cnt = 0; cnt < formdets.length; cnt++) {
            alert (formdets[cnt].formName);
            alert (formdets[cnt].formAction);
            alert (formdets[cnt].formMethod);
            var eledets = formdets[cnt].elementDetails;
            for (var elecnt = 0; elecnt < eledets.length; elecnt++) {
                alert (eledets[elecnt].elementType);
                alert (eledets[elecnt].elementName);
                alert (eledets[elecnt].elementValues.length);
            }
        }
        var linkdets = pagedet.linkDetails;
        for (var cnt = 0; cnt < linkdets.length; cnt++) {
            alert (linkdets[cnt].linkName);
            alert (linkdets[cnt].linkHref);
        }
    }
}