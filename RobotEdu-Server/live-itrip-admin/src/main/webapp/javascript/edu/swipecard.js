var tabUsers;

$(function () {
    tabUsers = $('#tableSwipeCards').DataTable({
        "bProcessing": true, // 是否显示取数据时的那个等待提示
        "bServerSide": true, //这个用来指明是通过服务端来取数据
        "bPaginate": true, // 分页按钮
        "bLengthChange": true, // 改变每页显示数据数量
        "iDisplayLength": 10,// 每页显示行数
        "bInfo": true,//页脚信息
        "bAutoWidth": true,//自动宽度
        "fnServerData": funSelectSwipeCards, // 获取数据的处理函数
        "bFilter": false, // 隐藏筛选框
        "ordering": false,
        'bStateSave': true,
        "aoColumns": [
            {"mData": "id"},
            {"mData": "shopNo"},
            {"mData": "cardNo"},
            {
                "mData": "clientCreateTime",
                render: function (data, type, row) {
                    if (data == null) {
                        return "";
                    }
                    return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss");
                }
            },
            {
                render: function (data, type, row) {
                    if (type === 'display') {
                        return '<button type="button" class="btn btn-link btn-xs" onclick="funEditGetCardInfo(' + '\'' + row.cardNo + '\'' + ')">会员卡信息</button>';
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


function funSelectSwipeCards(sSource, aoData, fnCallback) {
    console.log("========== selectSwipeCards ==========");
    sSource = "/swipe.action?flag=sel";

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
 * 查看信息
 * @param cardNo
 */
function funEditGetCardInfo(cardNo) {
    var jsondata = {
        'op': 'card.detail',
        'token': parent.token,
        'cardNo': cardNo
    };

    parent.execAjaxData("/swipe.action", JSON.stringify(jsondata), true
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

                $('#formEditTitle').text("会员卡信息");
                $('#formEditCard').modal('show');
            }
        }, function () {
            // complete
        });
}

/**
 * 刷新
 */
function funRefresh() {
    tabUsers.ajax.reload();
}
