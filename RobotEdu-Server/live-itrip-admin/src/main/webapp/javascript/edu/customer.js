var tabUsers;

$(function () {
    tabUsers = $('#tableCustomers').DataTable({
        "bProcessing": true, // 是否显示取数据时的那个等待提示
        "bServerSide": true, //这个用来指明是通过服务端来取数据
        "bPaginate": true, // 分页按钮
        "bLengthChange": true, // 改变每页显示数据数量
        "iDisplayLength": 10,// 每页显示行数
        "bInfo": true,//页脚信息
        "bAutoWidth": true,//自动宽度
        "fnServerData": funSelectCustomers, // 获取数据的处理函数
        "bFilter": false, // 隐藏筛选框
        "ordering": false,
        'bStateSave': true,
        "aoColumns": [
            {"mData": "id"},
            {"mData": "shopNo"},
            {"mData": "cardNo"},
            {"mData": "childName"},
            {
                "mData": "childSex",
                render: function (data, type, row) {
                    if (data == 1) {
                        return "男";
                    } else if (data == 2) {
                        return "女";
                    }
                }
            },
            {"mData": "birthday"},
            {"mData": "adviser"},
            {
                render: function (data, type, row) {
                    if (type === 'display') {
                        return '<button type="button" class="btn btn-link btn-xs" onclick="funEditGetCustomerInfo(' + row.id + ')">详细信息</button>';
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

    $('#editChildBirthday').datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        calendarWeeks: true,
        autoclose: true
    });
});


function funSelectCustomers(sSource, aoData, fnCallback) {
    console.log("========== selectCustomers ==========");
    sSource = "/customer.action?flag=sel";

    // 添加查询条件
    var customerName = $("#customerName").val();
    var shop = $("#selectShop").val();
    aoData.push({name: "customerName", value: customerName});
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
 * 查询详细信息
 * @param customerId
 */
function funEditGetCustomerInfo(customerId) {
    var jsondata = {
        'op': 'customer.detail',
        'token': parent.token,
        'customerId': customerId
    };

    parent.execAjaxData("/customer.action", JSON.stringify(jsondata), true
        , function (response) {
            // error
        }, function (response) {
            // success
            if (response.code == 0) {
                $('#editCustomerId').val(response.data.id);
                $('#editShop').val(response.data.shopNo);
                $('#editAdviser').val(response.data.adviser);
                $('#editCardNo').val(response.data.cardNo);
                $('#editChildName').val(response.data.childName);

                $('#editChildSex').val(response.data.childSex);
                $('#editChildBirthday').val(response.data.birthday);
                $('#editMomName').val(response.data.momName);
                $('#editMomMobile').val(response.data.momMobile);
                $('#editDadName').val(response.data.dadName);

                $('#editDadMobile').val(response.data.dadMobile);
                $('#editAddress').val(response.data.address);
                $('#editRemarks').val(response.data.remarks);

                $('#formEditTitle').text("详细信息");
                $('#formEditCustomer').modal('show');
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
