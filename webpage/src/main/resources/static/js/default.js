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

const btnLogout = document.querySelector("#btnHeaderLogout");

if (btnLogout != null) {
  btnLogout.addEventListener("click", (e) => {
    e.preventDefault();
    logout();
  });
}

function logout() {
  fetchTEXT("/member/logout", null, (data) => {
    console.log(data);
    location.href = data;
    // location.href = "/";
  });
}
