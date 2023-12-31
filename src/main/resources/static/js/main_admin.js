const fetchDepartment = () => {
    $.ajax({
        url: "/course/departments",
        method: "GET",
        success: function (data) {
            $("#departmentId").html("");
            let isFirst = true;
            if (data){
                let modalAddDepartments = data.map(item => {
                    if (isFirst === true){
                        isFirst = false;
                        return `<option value="${item.departmentId}" selected>${item.departmentName}</option>`
                    }
                    else {
                        return `<option value="${item.departmentId}">${item.departmentName}</option>`
                    }
                })
                $("#departmentId").html(modalAddDepartments);
                $("#editDepartmentId").html(modalAddDepartments);
            }
        }
    })
}

$('#formCreateCourse').submit(function(event) {
    event.preventDefault();
    let formData = new FormData();
    formData.append("courseAvt", $("#courseAvt")[0].files[0]);
    formData.append("courseName", $("#courseName").val())
    formData.append("courseDes", $("#courseDes").val())
    formData.append("departmentId", $("#departmentId").val())
    $.ajax({
        url: '/course/add',
        type: 'POST',
        data: formData,
        dataType: 'json',
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        success: function(response) {
            // handle success response
            console.log(response);
            Swal.fire({
                icon: 'success',
                title: 'Khóa học đã được thêm',
                showConfirmButton: false,
                timer: 1500
            }).then(()=>{
                location.reload();
            })
        },
        error: function(xhr, status, error) {
            Swal.fire('Có lỗi khi thêm!', '', 'error');
        }
    });
});

// $('#formEditAccount').submit(function(event) {
//     event.preventDefault();
//     var passOld = $("#input-old-password").val();
//     var passNew = $("#input-new-password").val();
//     var passNewConfirm = $("#input-confirm-password").val();
    
//       $.ajax({
//         url: '/account_info1',
//         type: 'POST',
//         data: {
//             passOld: passOld,
//             passNew: passNew,
//             passNewConfirm: passNewConfirm
//         },
//         success: function(response) {
//             // handle success response
//             console.log(response);
//             Swal.fire({
//                 icon: 'success',
//                 title: 'Đã thay đổi thành công',
//                 showConfirmButton: false,
//                 timer: 1500
//             }).then(()=>{
//                 location.reload();
//             })
//         },
//         error: function(xhr, status, error) {
//             Swal.fire('Có lỗi khi sửa!', '', 'error');
//         }
//     });
// });

$('#formAddCourse').submit(function(event) {
    event.preventDefault();
    let formData = new FormData();
    var gia = Number($("#courseGia").val());
    if(isNaN(gia) || gia < 0 || $("#courseGia").val() == ""){
        Swal.fire('Giá không hợp lệ!', '', 'error');
        return;
    }
    formData.append("courseAvt", $("#courseAvt")[0].files[0]);
    formData.append("courseName", $("#courseName").val());
    formData.append("courseDes", $("#courseDes").val());
    formData.append("departmentId", $("#departmentId").val());
    formData.append("gia", gia);
    $.ajax({
        url: '/course/add',
        type: 'POST',
        data: formData,
        dataType: 'json',
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        success: function(response) {
            // handle success response
            console.log(response);
            Swal.fire({
                icon: 'success',
                title: 'Khóa học đã được thêm',
                showConfirmButton: false,
                timer: 1500
            }).then(()=>{
                location.reload();
            })
        },
        error: function(xhr, status, error) {
            Swal.fire('Có lỗi khi thêm!', '', 'error');
        }
    });
});

$(".course-item").each((index)=>{

})

const removeCourse = (courseId) =>{
    Swal.fire({
        title: 'Bạn muốn xóa khóa học này ?',
        showDenyButton: true,
        confirmButtonText: 'Có',
        denyButtonText: `Không`,
    }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            $.ajax({
                url: `/course/delete/${courseId}`,
                method: "POST",
                success: function (){
                    Swal.fire('Đã xóa thành công!', '', 'success').then(()=> location.reload());
                },
                error: function (){
                    Swal.fire('Có lỗi khi xóa!', '', 'error');
                }
            })
        }
    })
}
const openEditCourseModal = (courseId) => {
    $('#editCourseModal').modal('show');
    $.ajax({
        url: `/course/detail/${courseId}`,
        method: 'GET',
        success: function (res){
           $("#editCourseName").val(res.courseName);
           $("#editCourseDes").val(res.courseDes);
           $("#editDepartmentId").val(res.department.departmentId);
           $("#editCourseId").val(res.courseID);
           $("#editGia").val(res.gia);
        }
    })
}

$("#btnEditCourse").on("click", function(event){
    event.preventDefault();
    let formData = new FormData();
    let file = $("#editCourseAvt")[0].files[0];
    if (file){
        formData.append("courseAvt", file);
    }
    var gia = Number($("#editGia").val());
    if(isNaN(gia) || gia < 0 || $("#editGia").val() == ""){
        Swal.fire('Giá không hợp lệ!', '', 'error');
        return;
    }
    formData.append("gia", gia);
    formData.append("courseName", $("#editCourseName").val());
    formData.append("courseDes", $("#editCourseDes").val());
    formData.append("departmentId", $("#editDepartmentId").val());
    formData.append("courseID", $("#editCourseId").val());
    $.ajax({
        url: '/course/edit',
        type: 'POST',
        data: formData,
        dataType: 'json',
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        success: function(response) {
            // handle success response
            console.log(response);
            Swal.fire({
                icon: 'success',
                title: 'Đã sửa thành công!',
                showConfirmButton: false,
                timer: 1500
            }).then(()=>{
                location.reload();
            })
        },
        error: function(xhr, status, error) {
            Swal.fire('Có lỗi khi sửa!', '', 'error');
        }
    });
})

fetchDepartment();