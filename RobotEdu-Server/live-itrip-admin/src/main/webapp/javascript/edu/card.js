var tabUsers;

$(function () {
    tabUsers = $('#tableCards').DataTable({
        "bProcessing": true, // 是否显示取数据时的那个等待提示
        "bServerSide": true, //这个用来指明是通过服务端来取数据
        "bPaginate": true, // 分页按钮
        "bLengthChange": true, // 改变每页显示数据数量
        "iDisplayLength": 10,// 每页显示行数
        "bInfo": true,//页脚信息
        "bAutoWidth": true,//自动宽度
        "fnServerData": funSelectCards, // 获取数据的处理函数
        "bFilter": false, // 隐藏筛选框
        "ordering": false,
        'bStateSave': true,
        "aoColumns": [
            {"mData": "id"},
            {"mData": "shopNo"},
            {"mData": "cardNo"},
            {
                "mData": "cardType",
                render: function (data, type, row) {
                    if (data == 1) {
                        return "次卡";
                    }
                }
            },
            {"mData": "totalTimes"},
            {"mData": "usedTimes"},
            {"mData": "price"},
            {"mData": "discount"},
            {"mData": "adviser"},
            {
                "mData": "flag",
                render: function (data, type, row) {
                    if (data == 1) {
                        return "正常";
                    } else if (data == 0) {
                        return "不可用";
                    }
                }
            },
            {
                render: function (data, type, row) {
                    if (type === 'display') {
                        return '<button type="button" class="btn btn-link btn-xs" onclick="funEditGetCardInfo(' + row.id + ')">编辑</button>' +
                            '<button type="button" class="btn btn-link btn-xs" onclick="funDeleteCardInfo(' + row.id + ')">删除</button>';
                    }
                    return data;
                }
            }
        ],
        "language": {  //语言设置
            'sSearch': '筛选:',
            "sLengthMenu": "每页显示  _MENU_ 条记录",
            "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "前一页",
                "sNext": "后一页",
                "sLast": "尾页"
            },
            "sZeroRecords": "抱歉， 没有数据",
            "sInfoEmpty": "没有数据"
        }

    });

});


function funSelectCards(sSource, aoData, fnCallback) {
    console.log("========== selectCards ==========");
    sSource = "/card.action?flag=sel";

    // 添加查询条件
    var cardNo = $("#cardNo").val();
    var shop = $("#selectShop").val();
    aoData.push({name: "cardNo", value: cardNo});
    aoData.push({name: "shop", value: shop});

    aoData.push({name: "token", value: parent.token});
    aoData = JSON.stringify(aoData);

    parent.execAjaxData(sSource, aoData, false
        , function (response) {
            // error
        }, function (response) {
            // success
            fnCallback(response);
        }, function () {
            // complete
        });
}

/**
 * 修改
 * @param cardId
 */
function funEditGetCardInfo(cardId) {
    var jsondata = {
        'op': 'card.detail',
        'token': parent.token,
        'cardId': cardId
    };

    parent.execAjaxData("/card.action", JSON.stringify(jsondata), true
        , function (response) {
            // error
        }, function (response) {
            // success
            if (response.code == 0) {
                $('#editCardId').val(response.data.id);
                $('#editCardNo').val(response.data.cardNo);
                $('#editShop').val(response.data.shopNo);
                $('#editCardType').val(response.data.cardType);
                $('#editTotalTimes').val(response.data.totalTimes);

                $('#editUsedTimes').val(response.data.usedTimes);
                $('#editPrice').val(response.data.price);
                $('#editDiscount').val(response.data.discount);
                $('#editAdviser').val(response.data.adviser);
                $('#editCardFlag').val(response.data.flag);

                $('#formEditTitle').text("会员卡编辑");
                $('#formEditCard').modal('show');
            }
        }, function () {
            // complete
        });
}

function editSaveCardInfo() {
    var jsondata = {
        'op': 'card.edit',
        'token': parent.token,
        'id': $('#editCardId').val(),
        'cardNo': $('#editCardNo').val(),
        'shopNo': $('#editShop').val(),
        'cardType': $("#editCardType").val(),
        'totalTimes': $("#editTotalTimes").val(),
        'usedTimes': $("#editUsedTimes").val(),
        'price': $('#editPrice').val(),
        'discount': $('#editDiscount').val(),
        'adviser': $('#editAdviser').val(),
        'flag': $('#editCardFlag').val()
    };

    parent.execAjaxData("/card.action", JSON.stringify(jsondata), true
        , function (response) {
            // error
            parent.notifyDanger('保存失败', response);
        }, function (response) {
            // success
            if (response.code == 0) {
                parent.notifySuccess('保存成功', '');
                funRefresh();
            } else {
                parent.notifyDanger('保存失败', response.msg);
            }
        }, function () {
            // complete
            $('#formEditCard').modal('hide');
        });
}

/**
 * 删除
 * @param cardId
 */
function funDeleteCardInfo(cardId) {
    if (confirm("确定要删除数据吗?")) {
        console.log("delete Card id:" + cardId);

        var jsondata = {
            'op': 'card.delete',
            'token': parent.token,
            'cardId': cardId
        };

        parent.execAjaxData("/card.action", JSON.stringify(jsondata), true
            , function (response) {
                // error
            }, function (response) {
                // success
                if (response.code == 0) {
                    parent.notifySuccess('删除成功', '');
                    funRefresh();
                } else {
                    parent.notifyDanger('删除失败', response.msg);
                }
            }, function () {
                // complete
            });
    }
}

/**
 * 刷新
 */
function funRefresh() {
    tabUsers.ajax.reload();
}

/**
 * 新增
 */
function funClickAddRow() {
    console.log(" fnClickAddRow click ");
    // clear
    $('#formEditTitle').text("新增会员卡");

    $('#editCardId').val(null)
    $('#editCardNo').val("");
    $('#editShop').val(0);
    $('#editCardType').val(1);
    $('#editTotalTimes').val("");

    $('#editUsedTimes').val("");
    $('#editPrice').val("");
    $('#editDiscount').val("");
    $('#editAdviser').val("");
    $('#editCardFlag').val(1);

    $('#formEditCard').modal('show');
}
