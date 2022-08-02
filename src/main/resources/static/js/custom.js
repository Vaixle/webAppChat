let $chatHistory;
let $button;
let $textarea;
let $chatHistoryList;
let $messageHistory;
let $subject;
let $to;
let $peopleList;

function init() {
    cacheDOM();
    bindEvents();
}

function bindEvents() {
    $button.on('click', addMessage.bind(this));
    $textarea.on('keyup', addMessageEnter.bind(this));
}

function cacheDOM() {
    $chatHistory = $('.chat-history');
    $button = $('#sendBtn');
    $textarea = $('#message-to-send');
    $chatHistoryList = $chatHistory.find('ul');
    $messageHistory = $('#usersList');
    $subject = $('#subject');
    $to = $('.basicAutoComplete');
    $peopleList = $('.people-list ul');
}

function render(message, userName, subject, createdAt) {
    scrollToTopSent();
    // responses
    var templateResponse = Handlebars.compile($("#message-response-template").html());
    var contextResponse = {
        response: message,
        time: getCurrentTime(),
        userName: userName,
        subject: subject
    };

    setTimeout(function () {
        $messageHistory.prepend(templateResponse(contextResponse));
        scrollToTopInbox();
    }.bind(this), 1500);
}

function sendMessage(message) {
    let username = $('#userName').val();
    let subject = $('#subject').val() == '' ? 'No subject' : $('#subject').val();
    let createdAt = getCurrentTime();
    console.log(username)
    sendMsg(username, message, subject);
    scrollToTopSent();
    if (message.trim() !== '') {
        var template = Handlebars.compile($("#message-template").html());
        var context = {
            messageOutput: message,
            time: getCurrentTime(),
            toUserName: selectedUser,
            subject: subject,
            createdAt: createdAt,

        };
        $chatHistoryList.prepend(template(context));
        scrollToTopSent();
        $textarea.val('');
        $subject.val('');
        $to.val('');
    }
}

function scrollToTopSent() {
    $chatHistory.scrollTop(-$chatHistory[0].scrollHeight);
}

function scrollToTopInbox() {
    $peopleList.scrollTop(-$peopleList[0].scrollHeight);
}

function getCurrentTime() {
    return new Date().toLocaleString("ru-RU")
}

function addMessage() {
    sendMessage($textarea.val());
}

function addMessageEnter(event) {
    // enter was pressed
    if (event.keyCode === 13) {
        addMessage();
    }
}

init();

