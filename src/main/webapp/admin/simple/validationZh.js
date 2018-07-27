/**
 * 监听 firstNameEn and lastNameEn input Event 
 * 如果报错提交错误汉字至后台
*/
(function() {
    'use strict';

    var $firstName 	 = $('#firstName');
    var $lastName 	 = $('#lastName');
    var $firstNameEn = $('#firstNameEn');
    var $lastNameEn  = $('#lastNameEn');

    var _url = BASE_PATH + '/admin/simple/toRecordCharacters.html';

    var inputBlur = (function() {
        return function(str) {
            console.log("**************************");
            if (!(/^\/{1}[a-zA-Z]+$/.test(str))) {
                var s = str.replace(/^\/{1}[A-Z]+/g, '');
                console.log(s);
                $.ajax({
                    type: 'POST',
                    data : {characterStr: s},
                    url: _url,
                    success: function(data) {
                        console.log('done..');
                        console.log(data);
                    },
                    error: function(error) {
                        console.log(error);
                    }
                });
            }
        };
    })();

    $firstName.blur(function() {
        inputBlur($firstNameEn.val());
    });

    $lastName.blur(function() {
        inputBlur($lastNameEn.val());
    });
    
    $firstNameEn.blur(function() {
        inputBlur($(this).val());
    });

    $lastNameEn.blur(function() {
        inputBlur($(this).val());
    });

})();