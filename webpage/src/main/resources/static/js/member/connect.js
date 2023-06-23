const loginbtn = document.querySelector('#loginbtn');
const loginform = document.querySelector('#loginform');

loginbtn.addEventListener('click', function (e){
    // const postTest = (email, password) => {
    //     fetch("/member/login", {
    //         method: 'POST',
    //         headers: { 'Content-Type: 'application/json' },
    //         body: JSON.stringify({ 'email': email, 'password': password})
    //     });
    // }        
     e.preventDefault();
    var httpRequest = new XMLHttpRequest();
    var inputEmail = document.querySelector('#email').value;
    var inputPassword = document.querySelector('#password').value;
    $.ajax({
        url: "/member/login",
        method: "POST",
        data : { email:inputEmail , password:inputPassword},
        success: function(res){
            console.log(res);
        },
        fail:function(jqXhr, status , errorThrown){
        }
});
   // }
    // var reqJson = new Object();
    // reqJson.email = inputEmail;
    // reqJson.password = inputPassword;
    // console.log(reqJson);
    // httpRequest.onreadystatechange = () => {
    // if (httpRequest.readyState === XMLHttpRequest.DONE) {
    //     if (httpRequest.status === 200) {
    //         var result = httpRequest.responseText;
    //         if (result == null) {
    //             console.log('실패');
    //         } else {
    //         console.log('성공');
    //         }
    //     } else {
    //         alert('오류');
    //     }
    //     }
    // }
    // httpRequest.open("POST", "/member/login", true);
    // httpRequest.responseType = "json";
    // httpRequest.setRequestHeader("Content-Type", "application/json");
    // httpRequest.send(JSON.stringify(reqJson));
});
