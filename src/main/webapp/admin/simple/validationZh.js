/**
 * 监听护照信息页和添加护照信息页 input blur event..
 * {
 * firstName    firstNameEn 
 * lastName     lastNameEn
 * birthAddress birthAddressEn
 * issuedPlace  issuedPlaceEn
 * }
 * 如果报错提交错误汉字至后台
*/
(function () {
    'use strict';

    var $firstName = $('#firstName');
    var $lastName = $('#lastName');
    var $firstNameEn = $('#firstNameEn');
    var $lastNameEn = $('#lastNameEn');
    var $birthAddress = $('#birthAddress');
    var $birthAddressEn = $('#birthAddressEn');
    var $issuedPlace = $('#issuedPlace');
    var $issuedPlaceEn = $('#issuedPlaceEn');

    var _url = BASE_PATH + '/admin/simple/toRecordCharacters.html';

    var inputBlur = (function () {
        return function (str) {
            if (!(/^\/{1}[a-zA-Z]+$/.test(str))) {
                var s = str.replace(/^\/{1}[A-Z]+/g, '');
                $.ajax({
                    type: 'POST',
                    data: { characterStr: s },
                    url: _url,
                    success: function (data) {
                        console.log('done..');
                        console.log(data);
                    },
                    error: function (error) {
                        console.log(error);
                    }
                });
            }
        };
    })();

    $firstName.blur(function () {
        inputBlur($firstNameEn.val());
    });

    $lastName.blur(function () {
        inputBlur($lastNameEn.val());
    });

    $firstNameEn.blur(function () {
        inputBlur($(this).val());
    });

    $lastNameEn.blur(function () {
        inputBlur($(this).val());
    });

    $birthAddress.blur(function () {
        inputBlur($birthAddressEn.val());
    });

    $birthAddressEn.blur(function () {
        inputBlur($(this).val());
    });

    $issuedPlace.blur(function () {
        inputBlur($issuedPlaceEn.val());
    });

    $issuedPlaceEn.blur(function () {
        inputBlur($(this).val());
    });
})();


