let deleteTargetId = null;

// ページサイズ変更時の処理
$('#pageSizeSelect').change(function() {
  const size = $(this).val();
  const keyword = $('input[name="keyword"]').val();
  const params = new URLSearchParams({
    page: '1',
    size: size
  });
  if (keyword) {
    params.append('keyword', keyword);
  }
  window.location.href = `/master/functions?${params.toString()}`;
});

function showDescription(element) {
  const description = element.getAttribute('data-description');
  document.getElementById('fullDescription').textContent = description || '説明なし';
  $('#descriptionModal').modal('show');
}

$(document).ready(function() {
  // 編集ボタンの処理
  $('.edit-btn').click(function() {
    const functionId = $(this).data('function-id');
    $.get(`/master/functions/${functionId}`)
      .done(function(func) {
        $('#functionForm').attr('data-mode', 'edit');
        $('#functionForm').attr('data-function-id', functionId);
        $('#serviceKbnCode').val(func.serviceKbnCode);
        $('#functionCode').val(func.functionCode);
        $('#functionCode').prop('readonly', true);
        $('#functionName').val(func.functionName);
        $('#description').val(func.description);
        $('#isActive').prop('checked', func.isActive);

        $('.modal-title').text('機能編集');
        $('#functionModal').modal('show');
      })
      .fail(function(xhr) {
        toastr.error('機能情報の取得に失敗しました: ' + xhr.responseText);
      });
  });

  // フォーム送信処理
  $('#functionForm').submit(function(e) {
    e.preventDefault();
    const mode = $(this).attr('data-mode');
    const functionId = $(this).attr('data-function-id');

    const url = mode === 'edit'
      ? `/master/functions/${functionId}`
      : '/master/functions';

    $.post(url, $(this).serialize())
      .done(function() {
        $('#functionModal').modal('hide');
        toastr.success('保存しました');
        location.reload();
      })
      .fail(function(xhr) {
        toastr.error('保存に失敗しました：' + xhr.responseText);
      });
  });

  // 削除ボタンの処理
  $('.delete-btn').click(function() {
    deleteTargetId = $(this).data('function-id');
    $('#deleteConfirmModal').modal('show');
  });

  // 削除確認ボタンの処理
  $('#confirmDeleteBtn').click(function() {
    if (deleteTargetId) {
      $.post(`/master/functions/${deleteTargetId}/delete`)
        .done(function() {
          $('#deleteConfirmModal').modal('hide');
          toastr.success('機能を削除しました');
          location.reload();
        })
        .fail(function(xhr) {
          $('#deleteConfirmModal').modal('hide');
          toastr.error('削除に失敗しました：' + xhr.responseText);
        });
    }
  });

  // モーダルが閉じられたときの処理
  $('#functionModal').on('hidden.bs.modal', function() {
    $('#functionForm').trigger('reset');
    $('#functionForm').removeAttr('data-mode');
    $('#functionForm').removeAttr('data-function-id');
    $('#functionCode').prop('readonly', false);
    $('.modal-title').text('機能登録');
  });

  // 削除モーダルが閉じられたときの処理
  $('#deleteConfirmModal').on('hidden.bs.modal', function() {
    deleteTargetId = null;
  });
});