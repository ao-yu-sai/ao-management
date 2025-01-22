
// メニューの状態を管理するスクリプト
$(document).ready(function() {
    // アクティブなメニューを展開
    $('.nav-link.active').parents('.nav-treeview').show();
    $('.nav-link.active').parents('.nav-item').addClass('menu-open');
    
    // 現在のURLに基づいてメニューをアクティブ化
    var currentUrl = window.location.pathname;
    $('.nav-link').each(function() {
        if ($(this).attr('href') === currentUrl) {
            $(this).addClass('active');
            $(this).parents('.nav-item').addClass('menu-open');
            $(this).parents('.nav-treeview').show();
        }
    });

    // メニューの開閉状態を保存
    $('.nav-item').on('click', function() {
        setTimeout(function() {
            var menuState = {};
            $('.nav-item').each(function() {
                var menuId = $(this).index();
                menuState[menuId] = $(this).hasClass('menu-open');
            });
            localStorage.setItem('menuState', JSON.stringify(menuState));
        }, 100);
    });

    // 保存されたメニューの状態を復元
    var savedMenuState = localStorage.getItem('menuState');
    if (savedMenuState) {
        var menuState = JSON.parse(savedMenuState);
        Object.keys(menuState).forEach(function(menuId) {
            if (menuState[menuId]) {
                $('.nav-item').eq(menuId).addClass('menu-open')
                    .find('.nav-treeview').show();
            }
        });
    }
});