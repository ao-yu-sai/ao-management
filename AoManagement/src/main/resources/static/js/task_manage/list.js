$(document).ready(function() {
    // チケット番号クリック時の処理
    $('.table tbody tr td:first-child').on('click', function(e) {
        e.stopPropagation();  // 親要素のクリックイベントを停止
        const ticketNumber = $(this).text();
        
        // チケット詳細を取得
        $.get('/task-manage/ticket/' + ticketNumber)
        .done(function(ticket) {
            // モーダルに詳細を表示
            $('#modalTicketNumber').text(ticket.ticketNumber);
            $('#modalProjectName').text(ticket.projectName);
            $('#modalStatus').text(ticket.statusName);
            $('#modalPriority').text(ticket.priorityName);
            $('#modalDescription').html(ticket.description?.replace(/\n/g, '<br>') || '');
            
            // モーダルを表示
            $('#ticketDetailModal').modal('show');
        })
        .fail(function(xhr) {
            console.error('Error fetching ticket details:', xhr);
            toastr.error('チケット詳細の取得に失敗しました');
        });
    });

    // タスク行クリック時の処理
    $('.table tbody tr').on('click', function() {
        const ticketNumber = $(this).find('td:first').text();
        const serviceKbnCode = $(this).data('service-kbn-code');
        
        // 選択行のハイライト
        $('.table tbody tr').removeClass('table-active');
        $(this).addClass('table-active');
        
        // 機能一覧を取得
        $.get('/task-manage/functions', {
            ticketNumber: ticketNumber,
            serviceKbnCode: serviceKbnCode
        })
        .done(function(functions) {
            // 機能一覧を表示
            const tbody = $('#functionList');
            tbody.empty();
            
            functions.forEach(function(func) {
                tbody.append(
                    $('<tr>')
                        .append($('<td>').text(func.functionName))
                );
            });
        })
        .fail(function(xhr) {
            console.error('Error fetching functions:', xhr);
            toastr.error('機能一覧の取得に失敗しました');
        });
    });

    // 機能追加ボタンクリック時の処理
    $('#addFunctionBtn').on('click', function() {
        const selectedRow = $('.table tbody tr.table-active');
        if (!selectedRow.length) {
            toastr.warning('案件を選択してください');
            return;
        }

        const serviceKbnCode = selectedRow.data('service-kbn-code');
        
        // 利用可能な機能一覧を取得
        loadAvailableFunctions(serviceKbnCode);
        
        // モーダルを表示
        $('#functionSelectModal').modal('show');
    });

    // 機能検索フィルター
    $('#functionFilter').on('input', function() {
        const serviceKbnCode = $('.table tbody tr.table-active').data('service-kbn-code');
        loadAvailableFunctions(serviceKbnCode, $(this).val());
    });

    // 全選択/解除の処理
    $('#selectAllFunctions').on('change', function() {
        const isChecked = $(this).prop('checked');
        $('#availableFunctionList input[type="checkbox"]').prop('checked', isChecked);
    });

    // 機能追加処理
    $('#saveFunctionsBtn').on('click', function() {
        const selectedRow = $('.table tbody tr.table-active');
        const ticketNumber = selectedRow.find('td:first').text();
        const serviceKbnCode = selectedRow.data('service-kbn-code');
        
        // 選択された機能を取得
        const selectedFunctions = $('#availableFunctionList input[type="checkbox"]:checked').map(function() {
            return $(this).val();
        }).get();
        
        if (!selectedFunctions.length) {
            toastr.warning('機能を選択してください');
            return;
        }
        
        // 機能を追加
        $.ajax({
            url: '/task-manage/functions/add',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                ticketNumber: ticketNumber,
                serviceKbnCode: serviceKbnCode,
                functionCodes: selectedFunctions
            })
        })
        .done(function() {
            toastr.success('機能を追加しました');
            $('#functionSelectModal').modal('hide');
            
            // 機能一覧を再読み込み
            $.get('/task-manage/functions', {
                ticketNumber: ticketNumber,
                serviceKbnCode: serviceKbnCode
            })
            .done(function(functions) {
                // 機能一覧を表示
                const tbody = $('#functionList');
                tbody.empty();
                
                functions.forEach(function(func) {
                    tbody.append(
                        $('<tr>')
                            .append($('<td>').text(func.functionName))
                    );
                });
            })
            .fail(function(xhr) {
                console.error('Error reloading functions:', xhr);
                toastr.error('機能一覧の更新に失敗しました');
            });
        })
        .fail(function(xhr) {
            console.error('Error adding functions:', xhr);
            toastr.error('機能の追加に失敗しました');
        });
    });

    function loadAvailableFunctions(serviceKbnCode, keyword = '') {
        const selectedRow = $('.table tbody tr.table-active');
        const ticketNumber = selectedRow.find('td:first').text();
        
        $.get('/task-manage/available-functions', {
            serviceKbnCode: serviceKbnCode,
            ticketNumber: ticketNumber,
            keyword: keyword
        })
        .done(function(functions) {
            const tbody = $('#availableFunctionList');
            tbody.empty();
            
            functions.forEach(function(func) {
                tbody.append(
                    $('<div>').addClass('function-item')
                        .append(
                            $('<div>').addClass('custom-control custom-checkbox')
                                .append($('<input>')
                                    .addClass('custom-control-input')
                                    .attr({
                                        type: 'checkbox',
                                        id: 'func_' + func.functionCode,
                                        value: func.functionCode
                                    }))
                                .append($('<label>')
                                    .addClass('custom-control-label')
                                    .attr('for', 'func_' + func.functionCode))
                        )
                        .append($('<div>').addClass('function-name').text(func.functionName))
                );
            });
        })
        .fail(function(xhr) {
            console.error('Error fetching available functions:', xhr);
            toastr.error('機能一覧の取得に失敗しました');
        });
    }
});
