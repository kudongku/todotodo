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
                let title = message['title'];
                let state = message['todoState']
                addHTML(id, title, state);
            }
        }
    })
}

// 메모 하나를 HTML로 만들어서 body 태그 내 원하는 곳에 붙입니다.
function addHTML(id, title, state) {
    // 1. HTML 태그를 만듭니다.
    let tempHtml =
        `<div class="card" onclick="showDetails('${id}')">
            <div id="${id}-title" class="title">
                ${title}
            </div>
            <div id="${id}-state" class="title">
                ${state}
            </div>
        </div>`;
    // 2. #cards-box 에 HTML을 붙인다.
    $('#cards-box').append(tempHtml);
}

// 일정 세부사항 보기 + 수정, 삭제하기
function showDetails(id) {
    $('#detail-box').empty();
    $.ajax({
        type: 'GET',
        url: `/todos/${id}`,
        success: function (response) {
            let id = response['id'];
            let modifiedAt = response['modifiedAt'];
            let title = response['title'];
            let username = response['username']
            let content = response['content'];
            let state = response['todoState']
            showDetailHTML(id, title, content, username, state, modifiedAt);
        }
    })
}

// 세부사항을 html 중간에 보여줍니다.
function showDetailHTML(id, title, content, username, state, modifiedAt) {
    // 1. HTML 태그를 만듭니다.
    let tempHtml =
        `<div class="card">
                <div class="content">
                    <div id="${id}-date" class="string">
                        ${modifiedAt}
                    </div>
                    <br>
                    <div id="${id}-state" class="string">
                        ${state}
                    </div>
                    <br>
                    <div id="${id}-username" class="string">
                        ${username}
                    </div>
                    <br>
                    <div id="${id}-title" class="string">
                        ${title}
                    </div>
                    <br>
                    <div id="${id}-content" class="string">
                        ${content}
                    </div>

                    <div id="${id}-editarea" class="edit">
                        <textarea id="${id}-titleArea" class="te-edit" name="" id="22" cols="30" rows="1"></textarea>
                        <textarea id="${id}-contentArea" class="te-edit" name="" id="32" cols="30" rows="3"></textarea>
                    </div>
                </div>

                <!-- 버튼 영역-->
                <div class="footer">
                    <button onclick="doingTodo('${id}')">하는중</button>
                    <button onclick="doneTodo('${id}')">완료</button>
                    <button onclick="editPost('${id}')">수정하기</button>
                    <button onclick="deleteOne('${id}')">삭제하기</button>
                    <button onclick="submitEdit('${id}')"> 제출하기</button>
                </div>
        </div>`;

    // 2. #cards-box 에 HTML을 붙인다.
    $('#detail-box').append(tempHtml);
}

// 수정 버튼을 눌렀을 때, 기존 작성 내용을 textarea 에 전달합니다.
function editPost(id) {
    showEdits(id);
    let date = $(`#${id}-date`).text().trim();
    let writer = $(`#${id}-username`).text().trim();
    let title = $(`#${id}-title`).text().trim();
    let content = $(`#${id}-content`).text().trim();
    $(`#${id}-titleArea`).val(title);
    $(`#${id}-contentArea`).val(content);
    $(`#${id}-writerArea`).val(writer);
    $(`#${id}-dateArea`).val(date);
}

// 숨길 버튼을 숨기고, 나타낼 버튼을 나타냅니다.
function showEdits(id) {
    $(`#${id}-editarea`).show();
    $(`#${id}-submit`).show();
    $(`#${id}-delete`).show();

    $(`#${id}-content`).hide();
    $(`#${id}-edit`).hide();
}

// 메모를 수정합니다.
function submitEdit(id) {
    let title = $(`#${id}-titleArea`).val();
    let content = $(`#${id}-contentArea`).val();


    // 3. 전달할 data JSON으로 만듭니다.
    let data = {'content': content, 'title': title};

    // 4. PUT /api/memos/{id} 에 data를 전달합니다.
    $.ajax({
        type: "PUT",
        url: `/todos/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert('메시지 변경에 성공하였습니다.');
            window.location.reload();
        }
    });
}

//api 를 통해 삭제합니다.
function deleteOne(id) {
    $.ajax({
        type: "DELETE",
        url: `/todos/${id}`,
        contentType: "application/json",
        success: function (response) {
            alert('메시지 삭제에 성공하였습니다.');
            window.location.reload();
        }
    })
}

function doingTodo(id) {
    $.ajax({
        type: "GET",
        url: `/todos/doing/${id}`,
        contentType: "application/json",
        success: function (response) {
            alert('메시지 수정에 성공하였습니다.');
            window.location.reload();
        }
    })
}

function doneTodo(id) {
    $.ajax({
        type: "GET",
        url: `/todos/done/${id}`,
        contentType: "application/json",
        success: function (response) {
            alert('메시지 수정에 성공하였습니다.');
            window.location.reload();
        }
    })
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