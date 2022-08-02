const url = 'https://web-app-email.herokuapp.com';
let stompClient;
let selectedUser;
let newMessages = new Map();

function connectToChat(userName) {
    console.log("connecting to chat...")
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + userName, function (response) {
            let data = JSON.parse(response.body);
            if (selectedUser === data.fromLogin) {
                render(data.message, data.fromLogin, data.subject, data.createdAt);
            } else {
                newMessages.set(data.fromLogin, data.message);
                $('#userNameAppender_' + data.fromLogin).append('<span id="newMessage_' + data.fromLogin + '" style="color: red">+1</span>');
            }
        });
    });
}

function sendMsg(from, text, subject) {
    selectedUser = $('.active')[0].text
    stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
        fromLogin: from,
        message: text,
        subject: subject
    }));
}

function registration() {
    let userName = document.getElementById("userName").value;
    if(userName == "") {alert("Choose your name!")} else {
        $.get(url + "/registration/" + userName, function (response) {
            setInterval(connectToChat(userName),3000);

            var messages = response.inboxMessages;
            let usersTemplateHTML = "";
            if (messages != null) {

                messages.reverse()
                usersTemplateHTML = createTemplate(messages, usersTemplateHTML, 'From:')
                $('#usersList').html(usersTemplateHTML);
            }
            messages = response.sentMessages;
            if (messages != null) {
                messages.reverse()

                usersTemplateHTML = "";
                usersTemplateHTML = createTemplate(messages, usersTemplateHTML, 'To:')
                $('.chat-history').find('ul').html(usersTemplateHTML);
            }
            document.getElementById("login").hidden = true;
            document.getElementById("main").hidden = false;
        }).fail(function (error) {
            if (error.status === 400) {

            }
        })
    }
}

function switchElemVisibility(elem) {
    let elemSwitch = elem.firstElementChild.lastElementChild
    elemSwitch.style.display = elemSwitch.style.display === 'none' ? 'block' : 'none'

}

function createTemplate(messages, usersTemplateHTML, direction) {
    for (let i = 0; i < messages.length; i++) {
        usersTemplateHTML = usersTemplateHTML + '<li class="clearfix" onclick="switchElemVisibility(this)">\n' +
            '                <div class="about">\n' +
            '                <div><b>'+direction+' </b>' + messages[i].fromLogin + '</div>\n' +
            '                <div><b>Date: </b>' + new Date(Date.parse(messages[i].createdAt)).toLocaleString("ru-RU") + '</div>\n' +
            '                <div><b>Subject: </b>' + messages[i].subject + '</div>\n' +
            '                <div style="display: none">' + messages[i].message + '</div>\n' +
            '                </div>\n' +
            '            </li>';
    }
    return usersTemplateHTML;
}