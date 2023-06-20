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