const btnNewBoard = document.querySelector("#btnNewBoard");

if (btnNewBoard != null) {
  btnNewBoard.addEventListener("click", () => {
    if (confirm("새 페이지로 이동합니다.")) {
      location.href = "/admin/board/write";
    }
  });
}
