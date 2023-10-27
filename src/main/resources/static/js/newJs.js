function showDanhGia(){
    var form = document.querySelector('.danhGiaForm');
    // Kiểm tra trạng thái hiện tại của div
  if (form.style.display === "none") {
    // Nếu đang ẩn, thì hiển thị div
    form.style.display = "block";
  } else {
    // Nếu đang hiển thị, thì ẩn div
    form.style.display = "none";
  }
}

function showFormTaoCourse(){
    window.location.href = "http://localhost:8080/teacher/createCourse";
}

setInterval(function() {
  var ele = document.querySelector(".title-pageCheckout");
  var element = document.querySelector(".editColor");
  if(element === null){
      ele.classList.add("editColor");
  } else
    ele.classList.remove("editColor");
}, 500)