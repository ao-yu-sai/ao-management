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
                        .data('function-code', func.functionCode)
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

    // 機能行クリック時の処理
    $('#functionList').on('click', 'tr', function() {
        const selectedRow = $('.table tbody tr.table-active');
        const serviceKbnCode = selectedRow.data('service-kbn-code');
        const ticketNumber = selectedRow.find('td:first').text();
        const functionCode = $(this).data('function-code');

        // 案件機能別タスク情報を取得
        $.get('/task-manage/function-task-info', {
            serviceKbnCode: serviceKbnCode,
            ticketNumber: ticketNumber,
            functionCode: functionCode
        })
        .done(function(taskInfoList) {
            // 取得したデータを表示
            const tbody = $('#taskInfoTable tbody');
            tbody.empty(); // 既存の行をクリア

            taskInfoList.forEach(function(taskInfo) {
                const row = $('<tr>');
                row.data('task-kbn-code', taskInfo.taskKbnCode);
                row.append($('<td>').text(taskInfo.taskKbnCode));
                row.append($('<td>').text(taskInfo.personInCharge));
                row.append($('<td>').text(taskInfo.plannedStartDate));
                row.append($('<td>').text(taskInfo.plannedEndDate));
                row.append($('<td>').text(taskInfo.plannedManHours));
                tbody.append(row);
            });
        })
        .fail(function(xhr) {
            console.error('Error fetching project function task info:', xhr);
            toastr.error('案件機能別タスク情報の取得に失敗しました');
        });
    });

    // タスク追加ボタンクリック時の処理
    $('#addTaskBtn').on('click', function() {
        // タスク区分を取得
        $.get('/task-manage/task-categories')
        .done(function(categories) {
            const categoryList = $('#taskCategoryList');
            categoryList.empty();

            categories.forEach(function(category) {
                const checkbox = $('<div>').addClass('form-check');
                checkbox.append(
                    $('<input>').addClass('form-check-input')
                        .attr('type', 'checkbox')
                        .attr('id', 'taskCategory_' + category.categoryCode)
                        .attr('value', category.categoryCode)
                );
                checkbox.append(
                    $('<label>').addClass('form-check-label')
                        .attr('for', 'taskCategory_' + category.categoryCode)
                        .text(category.categoryName)
                );
                categoryList.append(checkbox);
            });

            $('#addTaskModal').modal('show');
        })
        .fail(function(xhr) {
            console.error('Error fetching task categories:', xhr);
            toastr.error('タスク区分の取得に失敗しました');
        });
    });

    // タスク追加フォームの送信処理
    $('#addTaskForm').on('submit', function(event) {
        event.preventDefault();

        const selectedCategories = [];
        $('#taskCategoryList input:checked').each(function() {
            selectedCategories.push($(this).val());
        });

        const taskData = {
            serviceKbnCode: $('.table tbody tr.table-active').data('service-kbn-code'),
            ticketNumber: $('.table tbody tr.table-active').find('td:first').text(),
            functionCode: $('#functionList tr.table-active').data('function-code'),
            taskKbnCodes: selectedCategories,
            personInCharge: $('#personInCharge').val(),
            plannedStartDate: $('#plannedStartDate').val(),
            plannedEndDate: $('#plannedEndDate').val(),
            plannedManHours: $('#plannedManHours').val()
        };

        $.ajax({
            url: '/task-manage/tasks/add',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(taskData)
        })
        .done(function() {
            toastr.success('タスクを追加しました');
            $('#addTaskModal').modal('hide');
            // タスク情報を再読み込み
        })
        .fail(function(xhr) {
            console.error('Error adding task:', xhr);
            toastr.error('タスクの追加に失敗しました');
        });
    });
});
