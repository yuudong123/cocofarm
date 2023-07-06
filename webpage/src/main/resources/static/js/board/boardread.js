const btnList = document.querySelector("#btnList");
const btnModify = document.querySelector("#btnModify");
const btnDelete = document.querySelector("#btnDelete");
const btnReply = document.querySelector("#btnReply");
const btnReplyConfirm = document.querySelector("#btnReplyConfirm");
const btnReportBoard = document.querySelector("#btnReportBoard");
const replyForm = document.querySelector("#replyForm");
const replyModifyForm = document.querySelector("#replyModifyForm");
const replyList = document.querySelector("#replyList");
const replyContent = document.querySelector("#replyContent");
const reportType = document.querySelector("#reportType");
const reportNo = document.querySelector("#reportNo");
const reportedNickname = document.querySelector("#reportedNickname");
const reportRadios = document.querySelectorAll("input[name='report']");
const reportContent = document.querySelector("#reportContent");
const btnReportConfirm = document.querySelector("#btnReportConfirm");
const reportModal = M.Modal.init(document.querySelector("#reportModal"), {});

let modReplyNo = 0;
let modReplyContent = "";

onload = loadReply();

btnList.addEventListener("click", () => {
  location.href = location.href.split("/").slice(0, 5).join("/");
});
if (btnModify != null) {
  btnModify.addEventListener("click", () => {
    if (confirm("관리자 페이지로 이동하시겠습니까?")) {
      location.href = location.href.split("/").slice(0, 3).join("/") + "/admin/board/" + board_no + "/modify";
    }
  });
}
if (btnDelete != null) {
  btnDelete.addEventListener("click", () => {
    if (confirm("삭제 후 되돌릴 수 없습니다. 정말 삭제하시겠습니까?")) {
      fetchJSON("/" + board_no + "/delete", null, (data) => {
        if (data == 1) {
          alert("삭제되었습니다.");
          history.back();
        } else {
          M.toast({ html: "삭제에 실패했습니다..." });
        }
      });
    }
  });
}
if (btnReply != null) {
  btnReply.addEventListener("click", () => {
    toggleReplyBtn();
    replyForm.dataset.method = "write";
    replyContent.textContent = "";
  });
}
if (btnReplyConfirm != null) {
  btnReplyConfirm.addEventListener("click", () => {
    if (replyContent.value == "") {
      M.toast({ html: "댓글 내용을 입력해주세요.." });
      return;
    }
    let content = replyContent.value;
    let mapping = "";
    let data = {};
    let msg = "";
    if (replyForm.dataset.method == "write") {
      mapping = "/reply/write";
      data = { content: content, board_no: board_no };
      msg = "댓글 등록";
    } else if (replyForm.dataset.method == "modify") {
      mapping = "/reply/modify";
      data = { content: content, reply_no: modReplyNo };
      msg = "수정";
    }
    fetchTEXT(mapping, data, (data) => {
      if (data == 1) {
        replyContent.value = "";
        toggleReplyBtn();
        loadReply();
        M.toast({ html: msg + " 완료!" });
      } else {
        M.toast({ html: msg + "에 실패했습니다." });
      }
    });
  });
}
btnReportConfirm.addEventListener("click", () => {
  let reqBody = {
    reported_member: reportedNickname.dataset.memberNo,
    content: reportContent.value,
  };
  if (reportType.textContent == "게시글") {
    reqBody.reported_board = reportNo.textContent;
  } else if (reportType.textContent == "댓글") {
    reqBody.reported_board = board_no;
    reqBody.reported_reply = reportNo.textContent;
  }

  reportRadios.forEach((radio) => {
    if (radio.checked) {
      reqBody.reason_cd = radio.dataset.cd;
    }
  });
  console.log(reqBody);
  if (confirm("작성을 완료하시겠습니까?")) {
    fetchTEXT("/report", reqBody, (data) => {
      if (data == 1) {
        M.toast({ html: "신고되었습니다." });
        reportModal.close();
        reportContent.value = "";
      } else {
        M.toast({ html: "신고에 실패했습니다." });
      }
    });
  }
});

btnReportBoard.addEventListener("click", () => {
  reportType.innerHTML = "게시글";
  reportNo.innerHTML = board_no;
  reportedNickname.innerHTML = board_nickname;
  reportedNickname.dataset.memberNo = board_memberno;
});
function modifyReply(reply_no, content) {
  modReplyNo = reply_no;
  modReplyContent = content;
  replyForm.dataset.method = "modify";
  replyContent.textContent = modReplyContent;
  toggleReplyBtn();
}

function deleteReply(reply_no) {
  if (confirm("삭제 후 되돌릴 수 없습니다. 삭제하시겠습니까?")) {
    fetchTEXT("/reply/delete", { reply_no: reply_no }, (data) => {
      if (data == 1) {
        loadReply();
        M.toast({ html: "삭제되었습니다." });
      } else {
        M.toast({ html: "삭제에 실패했습니다." });
      }
    });
  }
}

function reportReply(reply_no, nickname, member_no) {
  reportType.innerHTML = "댓글";
  reportNo.innerHTML = reply_no;
  reportedNickname.innerHTML = nickname;
  reportedNickname.dataset.memberNo = member_no;
}

function loadReply() {
  replyList.innerHTML = "";
  fetchJSON("/reply/getlist", { board_no: board_no }, (data) => {
    if (data.length != 0) {
      data.forEach((item) => {
        replyList.innerHTML += item;
      });
    } else if (data == null) {
      replyList.innerHTML = "<p class='grey-text'>댓글을 불러오지 못했습니다..</p>";
    } else {
      replyList.innerHTML = "<p class='grey-text'>댓글이 없습니다.</p>";
    }
  });
}

function toggleReplyBtn() {
  if (replyForm.dataset.isopen == "false") {
    replyForm.style.display = "block";
    replyForm.dataset.isopen = "true";
    btnReplyConfirm.style.display = "block";
    btnReply.classList.add("red");
    btnReply.innerHTML = "취소";
  } else {
    replyForm.style.display = "none";
    replyForm.dataset.isopen = "false";
    btnReplyConfirm.style.display = "none";
    btnReply.classList.remove("red");
    btnReply.innerHTML = "댓글 쓰기";
  }
}
