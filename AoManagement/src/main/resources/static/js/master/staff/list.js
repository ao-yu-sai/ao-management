$(document).ready(function() {
    var selectedStaffId = null;

    // 行クリック時の処理
    $('.clickable-row').click(function() {
        $('.clickable-row').removeClass('selected');
        $(this).addClass('selected');
        selectedStaffId = $(this).find('td:first').text();
        
        // 担当者情報を取得
        $.get(`/api/staff/${selectedStaffId}`, function(staff) {
            $('#staffId').val(staff.staffId);
            $('#staffName').val(staff.staffName);
            $('#email').val(staff.email);
            $('#department').val(staff.department);
            $('#isActive').prop('checked', staff.isActive);
        });
    });

    // 新規作成ボタンの処理
    $('#addStaffBtn').click(function() {
        $('#staffForm')[0].reset();
        $('#staffId').val('');
        $('.clickable-row').removeClass('selected');
        selectedStaffId = null;
    });

    // フォーム送信の処理
    $('#staffForm').submit(function(e) {
        e.preventDefault();
        var url = selectedStaffId ? `/api/staff/${selectedStaffId}` : '/api/staff';
        
        // フォームデータを作成
        var formData = {
            staffName: $('#staffName').val(),
            email: $('#email').val(),
            department: $('#department').val(),
            isActive: $('#isActive').prop('checked')
        };
        
        if (selectedStaffId) {
            formData.staffId = selectedStaffId;
        }
        
        $.ajax({
            url: url,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function() {
                location.reload();
            },
            error: function() {
                toastr.error('保存に失敗しました。');
            }
        });
    });

    // 削除ボタンの処理
    $('#deleteBtn').click(function() {
        if (selectedStaffId) {
            $('#deleteConfirmModal').modal('show');
        }
    });

    // 削除確認ボタンの処理
    $('#confirmDeleteBtn').click(function() {
        $.post(`/api/staff/${selectedStaffId}/delete`)
            .done(function() {
                $('#deleteConfirmModal').modal('hide');
                location.reload();
            })
            .fail(function() {
                $('#deleteConfirmModal').modal('hide');
                toastr.error('削除に失敗しました。');
            });
    });
});