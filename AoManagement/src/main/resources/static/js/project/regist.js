$(document).ready(function () {
  // チケット番号入力フィールドのフォーカスアウトイベント
  $('#ticketNumber').blur(function() {
    var ticketNumber = $(this).val();
    if (ticketNumber) {
      // チケット情報を取得
      $.get('/project/ticket/' + ticketNumber)
        .done(function(response) {
          if (response && response.issue) {
            // 案件名を設定
            $('#projectName').val(response.issue.subject);
            
            // 説明を設定
            $('#description').val(response.issue.description);
            
            // ステータスを設定（ステータス名が一致する場合）
            if (response.issue.status) {
              $('#status option').each(function() {
                if ($(this).text() === response.issue.status.name) {
                  $('#status').val($(this).val());
                }
              });
            }
            
            // 優先度を設定（優先度名が一致する場合）
            if (response.issue.priority) {
              $('#priority option').each(function() {
                if ($(this).text() === response.issue.priority.name) {
                  $('#priority').val($(this).val());
                }
              });
            }
          }
        })
        .fail(function() {
          toastr.error('チケット情報の取得に失敗しました');
        });
    }
  });

  // フォームのバリデーション設定
  $('#projectForm').validate({
    rules: {
      ticketNumber: {
        required: true,
        pattern: /^[A-Za-z0-9_-]+$/
      },
      projectName: {
        required: true
      },
      status: {
        required: true
      },
      priority: {
        required: true
      },
      progressRate: {
        min: 0,
        max: 100
      }
    },
    messages: {
      ticketNumber: {
        required: "チケット番号を入力してください",
        pattern: "半角英数字、ハイフン、アンダースコアのみ使用可能です"
      },
      projectName: {
        required: "案件名を入力してください"
      },
      status: {
        required: "ステータスを選択してください"
      },
      priority: {
        required: "優先度を選択してください"
      },
      progressRate: {
        min: "0以上の値を入力してください",
        max: "100以下の値を入力してください"
      }
    },
    errorElement: 'span',
    errorPlacement: function (error, element) {
      error.addClass('invalid-feedback');
      element.closest('.form-group').append(error);
    },
    highlight: function (element, errorClass, validClass) {
      $(element).addClass('is-invalid');
    },
    unhighlight: function (element, errorClass, validClass) {
      $(element).removeClass('is-invalid');
    }
  });
}); 