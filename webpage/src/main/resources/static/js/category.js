const categorys = document.querySelectorAll("#category a");
const selectBar = document.getElementById("categorySelectBar");

if (categorys != null) {
  categorys.forEach((category) => {
    if (category.dataset.category != null) {
      category.addEventListener("click", () => {
        reqDiv.setAttribute("action", "/board/" + category.dataset.category);
        inputPage.setAttribute("value", "1");
        reqDiv.submit();
      });
    }
    var width = category.offsetWidth;
    var height = category.offsetBottom;
    var offsetLeft = category.offsetLeft;
    category.addEventListener("mouseenter", function () {
      selectBar.style.width = width + "px";
      selectBar.style.transform = "translateX(" + offsetLeft + "px)";
    });

    category.addEventListener("mouseleave", function () {
      selectBar.style.width = "0";
      selectBar.style.transform = "translateX(" + (offsetLeft + width / 2) + "px)";
    });
  });
}
