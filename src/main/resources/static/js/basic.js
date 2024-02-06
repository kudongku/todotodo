// HTML 문서를 로드할 때마다 실행합니다.
$(document).ready(function () {
    getMessages();
})

// 메모를 불러와서 보여줍니다.
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
                ${title} : ${state}
            </div>
        </div>`;
    // 2. #cards-box 에 HTML을 붙인다.
    $('#cards-box').append(tempHtml);
}


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

    $.ajax({
        type: 'GET',
        url: `/comments/${id}`,
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let commentId = response[i]['commentId']
                let content = response[i]['content'];
                createCommentHTML(commentId, content);
            }
        }
    })
}
function createCommentHTML(commentId, content) {
    // 1. HTML 태그를 만듭니다.
    let tempHtml =
        `<div class="card">
            <div id="${commentId}-comment">${content}</div>
            <textarea id="${commentId}-comment-editArea" cols="30" rows="10"></textarea>
            <button id="${commentId}-comment-edit" onclick="editComment('${commentId}')">댓글 수정</button>
            <button id="${commentId}-comment-submit" onclick="submitCommentEdit('${commentId}')">수정완료</button>
            <button id="${commentId}-comment-delete" onclick="deleteComment('${commentId}')"> 댓글 삭제</button>
        </div>`;

    $('#comment-box').append(tempHtml);
    $(`#${commentId}-comment-editArea`).hide();
    $(`#${commentId}-comment-submit`).hide();
}
function editComment(commentId) {
    showCommentEdits(commentId);
    let title = $(`#${commentId}-title`).text().trim();
    let content = $(`#${commentId}-content`).text().trim();
    $(`#${commentId}-titleArea`).val(title);
    $(`#${commentId}-contentArea`).val(content);
}

// 숨길 버튼을 숨기고, 나타낼 버튼을 나타냅니다.
function showCommentEdits(commentId) {
    $(`#${commentId}-comment-editArea`).show();
    $(`#${commentId}-comment-submit`).show();
    $(`#${commentId}-comment-edit`).hide();
    $(`#${commentId}-comment`).hide();
}

// 메모를 수정합니다.
function submitCommentEdit(commentId) {
    let content = $(`#${commentId}-comment-editArea`).val();


    // 3. 전달할 data JSON으로 만듭니다.
    let data = {'content': content};

    // 4. PUT /api/memos/{id} 에 data를 전달합니다.
    $.ajax({
        type: "PUT",
        url: `/comments/${commentId}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function () {
            alert('메시지 변경에 성공하였습니다.');
            window.location.reload();
        }
    });
}

//api 를 통해 삭제합니다.
function deleteComment(commentId) {
    $.ajax({
        type: "DELETE",
        url: `/comments/${commentId}`,
        contentType: "application/json",
        success: function () {
            alert('메시지 삭제에 성공하였습니다.');
            window.location.reload();
        }
    })
}

// 세부사항을 html 중간에 보여줍니다.
function showDetailHTML(id, title, content, username, state, modifiedAt) {
    // 1. HTML 태그를 만듭니다.
    let tempHtml =
        `<div class="card">
                <div class="content" id="${id}-contents">
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
                        <textarea id="${id}-titleArea" class="te-edit" placeholder="일정 제목을 입력해주세요" name="" id="22" cols="30" rows="3"></textarea>
                        <br>
                        <textarea id="${id}-contentArea" class="te-edit" placeholder="일정 내용을 입력해주세요" name="" id="32" cols="30" rows="5"></textarea>
                        <br>
                    </div>
                </div>

                <!-- 버튼 영역-->
                <div class="footer">
                    <button onclick="doingTodo('${id}')">하는중</button>
                    <button onclick="doneTodo('${id}')">완료</button>
                    <button onclick="editPost('${id}')" id="${id}-edit">수정하기</button>
                    <button onclick="deleteOne('${id}')">삭제하기</button>
                    <button onclick="submitEdit('${id}')" id="${id}-submit"> 제출하기</button>
                </div>
                
                <div class="comment-area">
                    <textarea id="${id}-comment-textarea" placeholder="댓글을 입력해주세요" cols="30" rows="1"></textarea>
                    <button onclick="postComment('${id}')">전송하기</button>
                    
                    <div id="comment-box">
                    
                    </div>
                </div>
        </div>`;

    // 2. #cards-box 에 HTML을 붙인다.
    $('#detail-box').append(tempHtml);
    $(`#${id}-editarea`).hide();
    $(`#${id}-submit`).hide();
}
function postComment(id) {
    let content = $(`#${id}-comment-textarea`).val();
    let data = {'content': content};

    $.ajax({
        type: "POST",
        url: `/comments/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert('댓글 달기에 성공하였습니다.');
            window.location.reload();
        }
    });
}

// 수정 버튼을 눌렀을 때, 기존 작성 내용을 textarea 에 전달합니다.
function editPost(id) {
    showEdits(id);
    let title = $(`#${id}-title`).text().trim();
    let content = $(`#${id}-content`).text().trim();
    $(`#${id}-titleArea`).val(title);
    $(`#${id}-contentArea`).val(content);
}

// 숨길 버튼을 숨기고, 나타낼 버튼을 나타냅니다.
function showEdits(id) {
    $(`#${id}-editarea`).show();
    $(`#${id}-submit`).show();
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
        success: function () {
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
        success: function () {
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
        success: function () {
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
        success: function () {
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