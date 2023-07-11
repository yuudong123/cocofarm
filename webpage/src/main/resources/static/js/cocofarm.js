// 서버통신 XMLHttpRequest();
var sendServerData = function(method, url, type, sendData, callBack){

    const xhr = new XMLHttpRequest();
      xhr.onload = function () {
        console.error("Request failed. Status code: " + xhr.status);
        if (xhr.status === 200) {

          // 요청이 성공적으로 완료됐을 때 실행할 코드
          
          var response = xhr.responseText;
          console.log(response);
          if(callBack){
            console.log(response);
                     callBack(response);
                     
                 }
         // var a = JSON.parse(response);

        //   if (a.status == "OK") {
        //     if(callBack){
        //         callBack(a);
        //     }
        //   }
        //   //console.log(response);
        // } else {
        //   console.log(response);
        //   callBack(response);
        //   // 요청이 실패했을 때 실행할 코드
       //console.error("Request failed. Status code: " + xhr.status);
        // }
      }else{
       // console.error("Request failed. Status code: " + xhr.status);
      }
    }
      // 서버에 POST 요청 보내기
      xhr.open(method, url, true);
      xhr.setRequestHeader("Content-Type", type);
      xhr.send(sendData);

};