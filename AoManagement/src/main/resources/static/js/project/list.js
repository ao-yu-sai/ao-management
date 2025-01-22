// 案件削除の確認ダイアログを表示
function deleteProject(ticketNumber) {
  if (confirm('案件を削除してもよろしいですか？')) {
    // 削除処理を実行
    fetch(`/project/delete/${ticketNumber}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .then(response => {
      if (response.ok) {
        // 削除成功時は画面をリロード
        location.reload();
      } else {
        throw new Error('削除に失敗しました');
      }
    })
    .catch(error => {
      alert('削除処理に失敗しました');
      console.error('Error:', error);
    });
  }
} 