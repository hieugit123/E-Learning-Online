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

$(document).ready(function() {
  var arrSao = document.querySelectorAll(".tbsao");
  var saoClass = document.querySelectorAll(".saoShow");
  var size = arrSao.length;

  for (var i = 0; i < size; i++) {
      var soSao = parseFloat(arrSao[i].textContent);
      var saoHTML = ''; // Chuỗi HTML cho hình ngôi sao

      if (soSao <= 1.0) {
          if (soSao == 1.0)
              saoHTML = `<i class="bi bi-star-fill star"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i>`;
          else if (soSao < 0.5)
              saoHTML = `<i class="bi bi-star star2"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i>`;
          else
              saoHTML = `<i class="bi bi-star-half star1"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i>`;
      } else if (soSao <= 2.0) {
          if (soSao == 2.0)
              saoHTML = `<i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i>`;
          else if (soSao < 1.5)
              saoHTML = `<i class="bi bi-star-fill star"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i>`;
          else
              saoHTML = `<i class="bi bi-star-fill star"></i><i class="bi bi-star-half star1"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i>`;
      } else if (soSao <= 3.0) {
          if (soSao == 3.0)
              saoHTML = `<i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i>`;
          else if (soSao < 2.5)
              saoHTML = `<i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i>`;
          else
              saoHTML = `<i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star-half star1"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i>`;
      } else if (soSao <= 4.0) {
          if (soSao == 4.0)
              saoHTML = `<i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star star2"></i>`;
          else if (soSao < 3.5)
              saoHTML = `<i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star star2"></i><i class="bi bi-star star2"></i>`;
          else
              saoHTML = `<i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star-half star1"></i><i class="bi bi-star star2"></i>`;
      } else if (soSao <= 5.0) {
          if (soSao == 5.0)
              saoHTML = `<i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i>`;
          else if (soSao < 4.5)
              saoHTML = `<i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star star2"></i>`;
          else
              saoHTML = `<i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star-fill star"></i><i class="bi bi-star-half star1"></i>`;
      }

      saoClass[i].innerHTML = saoHTML; // Gán chuỗi HTML cho hình ngôi sao
  }
});
