let css =  document.getElementById("theme");
let toggler =  document.getElementById("themeSwitch");
updateTheme();

function toggle(){
    if (toggler.checked){
        document.cookie = "theme=dark";
        updateTheme();
    } else {
        document.cookie = "theme=light";
        updateTheme();
    }
}

function updateTheme() {
    if (getCookieValue("theme")==="light"){
        toggler.checked = false;
        css.href = "/resources/css/blackjack.css";
    }else {
        toggler.checked = true;
        css.href = "/resources/css/blackjack_dark.css";
    }
}

function getCookieValue(a) {
    let b = document.cookie.match('(^|[^;]+)\\s*' + a + '\\s*=\\s*([^;]+)');
    return b ? b.pop() : '';
}