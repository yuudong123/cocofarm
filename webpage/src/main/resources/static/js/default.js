function fetchJSON(url, data, callback) {
  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  })
    .then((response) => {
      if (response.ok) {
        return response.json();
      }
      throw new Error("Network response was not ok.");
    })
    .then((data) => {
      if (data) {
        callback(data);
      }
    })
    .catch((error) => console.error("Error : " + error));
}

function fetchTEXT(url, data, callback) {
  fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  })
    .then((response) => {
      if (response.ok) {
        return response.text();
      }
      throw new Error("Network response was not ok.");
    })
    .then((data) => {
      if (data != null) {
        callback(data);
      }
    })
    .catch((error) => console.error("Error : " + error));
}

btnLogout = document.querySelectorAll(".btnHeaderLogout");
btnLogout.forEach((btn) => {
  if (btn != null) {
    btn.addEventListener("click", (e) => {
      e.preventDefault();
      logout();
    });
  }
});

function logout() {
  fetchTEXT("/member/logout", null, (data) => {
    console.log(data);
    location.href = data;
    // location.href = "/";
  });
}

function toast(message) {
  M.toast({ html: message });
}

dropdown = M.Dropdown.init(document.querySelector(".dropdown-trigger"), {});
mobileBurger = M.Sidenav.init(document.querySelector("#mobileBurger"), {});
