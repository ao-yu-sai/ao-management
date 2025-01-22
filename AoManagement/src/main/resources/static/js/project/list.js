// 削除対象のチケット番号を保持する変数
let targetTicketNumber;

// 削除ボタンクリック時の処理
function deleteProject(ticketNumber) {
  targetTicketNumber = ticketNumber;
  $('#deleteConfirmModal').modal('show');
}

// 削除確認ボタンクリック時の処理
$(document).ready(function() {
  $('#confirmDeleteBtn').on('click', function() {
    if (!targetTicketNumber) return;

    // 削除APIを呼び出し
    $.ajax({
      url: '/project/delete/' + targetTicketNumber,
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      success: function() {
        // 成功時の処理
        // メッセージを表示
        $('#successMessageText').text('案件を削除しました');
        $('#successMessage').fadeIn();
        // モーダルを閉じる
        $('#deleteConfirmModal').modal('hide');
        // 画面をリロード
        setTimeout(function() {
          location.reload();
        }, 1000);
      },
      error: function(xhr) {
        // エラー時の処理
        toastr.error('案件の削除に失敗しました');
        console.error('Error deleting project:', xhr);
      }
    });
  });
}); 