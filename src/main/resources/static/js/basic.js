// 캘린더 작성하기
function writePost() {
    // 1. 작성한 메모를 불러옵니다.
    let title = $('#title').val();
    let content = $('#content').val();

    // 3. 전달할 data JSON으로 만듭니다.
    let data = {'title': title, 'content': content};

    // 5. POST /api/memos 에 data를 전달합니다.
    $.ajax({
        type: "POST",
        url: "/todos",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function () {
            alert('메시지가 성공적으로 작성되었습니다.');
            window.location.reload();
        }
    });
}

// // HTML 문서를 로드할 때마다 실행합니다.
$(document).ready(function () {
    getMessages();
})
//
// // 메모를 불러와서 보여줍니다.
function getMessages() {
    // 1. 기존 메모 내용을 지웁니다.
    $('#cards-box').empty();
    // 2. 메모 목록을 불러와서 HTML로 붙입니다.
    $.ajax({
        type: 'GET',
        url: '/todos',
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let message = response[i];
                let id = message['id'];
                let modifiedAt = message['modifiedAt'];
                let title = message['title'];
                let content = message['content'];

                addHTML(id, title, content, modifiedAt);
            }
        }
    })
}

// 메모 하나를 HTML로 만들어서 body 태그 내 원하는 곳에 붙입니다.
function addHTML(id, title) {
    // 1. HTML 태그를 만듭니다.
    let tempHtml =
        `<div class="card">
            <div id="${id}-title" class="title">
                ${title}
            </div>
        </div>`;
    // 2. #cards-box 에 HTML을 붙인다.
    $('#cards-box').append(tempHtml);
}

let host = 'http://' + window.location.host;

$(document).ready(function () {
    const auth = getToken();
    if (auth === '') {
        $('#login-true').show();
        $('#login-false').hide();
        // window.location.href = host + "/user/login-page";
    } else {
        $('#login-true').show();
        $('#login-false').hide();
    }
})

function logout() {
    // 토큰 삭제
    Cookies.remove('Authorization', {path: '/'});
    window.location.href = host + "/user/login-page";
}

function getToken() {
    let auth = Cookies.get('Authorization');

    if (auth === undefined) {
        return '';
    }

    return auth;
}