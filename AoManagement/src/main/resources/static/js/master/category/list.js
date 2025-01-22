$(document).ready(function() {
  var selectedTypeCode = null;
  var categoryToDelete = null;
  var categoryTypeToDelete = null;

  // 区分一覧を読み込む関数
  function loadCategories(typeCode) {
    if (typeCode) {
      console.log('Loading categories for type code:', typeCode);
      $('#addCategoryBtn').prop('disabled', false);
      $.get(`/master/category/${typeCode}/categories`, function(data) {
        $('#categoryList').html(data);
        $('#categoryList').removeClass('p-3');
        initializeDeleteButtons();
        initializeEditButtons();
      }).fail(function(xhr) {
        console.error('Error loading categories:', xhr);
        toastr.error('区分一覧の取得に失敗しました');
      });
    } else {
      $('#addCategoryBtn').prop('disabled', true);
      $('#categoryList').addClass('p-3');
      $('#categoryList').html('<p class="text-muted text-center">区分種別を選択してください</p>');
    }
  }

  // 区分種別行クリック時の処理
  $('.clickable-row').click(function() {
    $('.clickable-row').removeClass('selected');
    $(this).addClass('selected');
    selectedTypeCode = $(this).data('type-code');
    console.log('Selected type code:', selectedTypeCode);
    loadCategories(selectedTypeCode);
  });

  // 新規作成ボタンの処理
  $('#addCategoryBtn').click(function() {
    if (selectedTypeCode) {
      var modal = $('#categoryModal');
      modal.find('form')[0].reset();
      modal.find('#categoryId').val('');
      modal.find('#originalCategoryCode').val('');
      modal.find('#displayOrder').val(0);
      modal.find('.modal-title').text('区分新規作成');
      modal.find('form').attr('action', `/master/category/${selectedTypeCode}/categories`);
      modal.modal('show');
    } else {
      alert('区分種別を選択してください。');
    }
  });

  // 削除ボタンの初期化
  function initializeDeleteButtons() {
    $('.category-delete-btn').click(function() {
      var categoryCode = $(this).data('category-code');
      categoryToDelete = categoryCode;
      $('#deleteCategoryModal').modal('show');
    });
  }

  // 区分種別の削除ボタンのクリックハンドラ
  $('.delete-btn:not(.category-delete-btn)').click(function() {
    categoryTypeToDelete = $(this).data('category-type-code');
    $('#deleteCategoryTypeModal').modal('show');
  });

  // 削除確認ボタンのクリックハンドラ
  $('#confirmDeleteBtn').click(function() {
    if (categoryToDelete && selectedTypeCode) {
      $.post(`/master/category/${selectedTypeCode}/categories/${categoryToDelete}/delete`)
        .done(function() {
          $('#deleteCategoryModal').modal('hide');
          loadCategories(selectedTypeCode);
          toastr.success('区分を削除しました');
        })
        .fail(function() {
          toastr.error('削除に失敗しました');
        });
    }
  });

  // 削除モーダルが閉じられたときの処理
  $('#deleteCategoryModal').on('hidden.bs.modal', function() {
    categoryToDelete = null;
  });
  
  // 区分登録モーダルを表示
  function showRegistModal(categoryTypeCode) {
    // モーダルのタイトルを設定
    $('#categoryModal .modal-title').text('区分登録');
    
    // フォームの値をクリア
    $('#categoryId').val('');
    $('#originalCategoryCode').val('');
    $('#categoryCode').val('');
    $('#categoryName').val('');
    $('#description').val('');
    $('#displayOrder').val(0);
    
    // フォームのアクションを設定
    $('#categoryForm').attr('action', `/master/category/${categoryTypeCode}/regist`);
    $('#categoryForm').attr('method', 'post');
    
    // モーダルを表示
    $('#categoryModal').modal('show');
  }

  // フォーム送信処理
  $('#categoryForm').submit(function(e) {
    e.preventDefault();
    var form = $(this);
    var formData = form.serialize();
    var isEdit = $('#categoryModal .modal-title').text() === '区分編集';

    // APIエンドポイントの設定
    var url = isEdit 
      ? `/master/category/${selectedTypeCode}/categories/${$('#originalCategoryCode').val()}`
      : `/master/category/${selectedTypeCode}/categories`;

    // 送信処理
    $.ajax({
      url: url,
      type: 'POST',
      data: formData,
      success: function() {
        $('#categoryModal').modal('hide');
        loadCategories(selectedTypeCode);
        toastr.success('保存しました');
      },
      error: function(xhr) {
        console.error('Error:', xhr);
        toastr.error('保存に失敗しました');
      }
    });
  });

  // 初期表示時に選択されている行があれば区分一覧を読み込む
  var initialSelectedRow = $('.clickable-row.selected');
  if (initialSelectedRow.length > 0) {
    selectedTypeCode = initialSelectedRow.data('type-code');
    loadCategories(selectedTypeCode);
  }

  // 編集ボタンのクリックハンドラ
  $('.edit-btn').click(function() {
    const categoryTypeCode = $(this).data('category-type-code');
    $.get(`/master/category/${categoryTypeCode}`)
      .done(function(categoryType) {
        $('#categoryTypeForm').attr('data-mode', 'edit');
        $('#categoryTypeForm').attr('data-category-type-code', categoryTypeCode);
        $('#categoryTypeForm').attr('action', `/master/category/${categoryTypeCode}`);
        $('#categoryTypeCode').val(categoryType.categoryTypeCode);
        $('#categoryTypeName').val(categoryType.categoryTypeName);
        $('#description').val(categoryType.description);
        $('#isActive').prop('checked', categoryType.isActive);

        // モーダルのタイトルを変更
        $('.modal-title').text('区分種別編集');

        // 編集時は区分種別コードを編集不可に
        $('#categoryTypeCode').prop('readonly', true);

        $('#categoryTypeModal').modal('show');
      })
      .fail(function() {
        toastr.error('区分種別情報の取得に失敗しました');
      });
  });

  // モーダルが閉じられたときの処理
  $('#categoryTypeModal').on('hidden.bs.modal', function() {
    $('#categoryTypeForm').trigger('reset');
    $('#categoryTypeForm').removeAttr('data-mode');
    $('#categoryTypeForm').removeAttr('data-category-type-code');
    $('#categoryTypeForm').attr('action', '/master/category');
    $('.modal-title').text('区分種別登録');
    $('#categoryTypeCode').prop('readonly', false);
  });

  // 区分種別削除確認ボタンのクリックハンドラ
  $('#confirmDeleteTypeBtn').click(function() {
    if (categoryTypeToDelete) {
      $.post(`/master/category/${categoryTypeToDelete}/delete`)
        .done(function() {
          $('#deleteCategoryTypeModal').modal('hide');
          location.reload();
          toastr.success('区分種別を削除しました');
        })
        .fail(function(xhr) {
          toastr.error('削除に失敗しました。関連する区分が存在する可能性があります。');
        });
    }
  });

  // 区分種別削除モーダルが閉じられたときの処理
  $('#deleteCategoryTypeModal').on('hidden.bs.modal', function() {
    categoryTypeToDelete = null;
  });

  // 区分編集ボタンのクリックハンドラを初期化する関数
  function initializeEditButtons() {
    $('.category-edit-btn').click(function() {
      var modal = $('#categoryModal');
      var button = $(this);
      
      // データ属性から値を取得
      var categoryCode = button.data('category-code');
      var categoryName = button.data('category-name');
      var description = button.data('description');
      var displayOrder = button.data('display-order');
      
      // モーダルのフォームに値をセット
      modal.find('#categoryCode').val(categoryCode);
      modal.find('#originalCategoryCode').val(categoryCode);
      modal.find('#categoryName').val(categoryName);
      modal.find('#description').val(description);
      modal.find('#displayOrder').val(displayOrder);
      
      // モーダルのタイトルを設定
      modal.find('.modal-title').text('区分編集');
      
      // モーダルを表示
      modal.modal('show');
    });
  }
});