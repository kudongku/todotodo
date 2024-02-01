let host = 'http://' + window.location.host;

$(document).ready(function () {
    const auth = getToken();

    if(auth === '') {
        window.location.href = host + "/user/login";
    } else {
        $('#login-true').show();
        $('#login-false').hide();
    }
})

function logout() {
    // 토큰 삭제
    Cookies.remove('Authorization', { path: '/' });
    window.location.href = host + "/user/login";
}

function getToken() {
    let auth = Cookies.get('Authorization');

    if(auth === undefined) {
        return '';
    }

    return auth;
}