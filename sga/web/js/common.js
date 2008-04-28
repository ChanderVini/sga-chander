function handleshipBillAdd () {
    if (document.getElementById('shipbillsame').checked) {
        document.getElementById ('billheaderid').style.display = 'none';
        document.getElementById ('billcontentid').style.display = 'none';
    } else {
        document.getElementById ('billheaderid').style.display = 'inline';
        document.getElementById ('billcontentid').style.display = 'inline';
    }
}

function addreview () {
    var ele = document.getElementById ('addreview');
    if (ele && ele != 'undefined') {
        if (ele.style.display == 'none') {
            ele.style.display = 'inline';
        } else {
            ele.style.display = 'none';
        }
    }
}

function viewreviews () {
    var ele = document.getElementById ('viewreviews');
    if (ele && ele != 'undefined') {
        if (ele.style.display == 'none') {
            ele.style.display = 'inline';
        } else {
            ele.style.display = 'none';
        }
    }
}