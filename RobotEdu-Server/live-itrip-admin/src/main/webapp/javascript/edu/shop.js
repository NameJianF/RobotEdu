var tabUsers;

$(function () {
    tabUsers = $('#tableShops').DataTable({
        "bProcessing": true, // 是否显示取数据时的那个等待提示
        "bServerSide": true, //这个用来指明是通过服务端来取数据
        "bPaginate": true, // 分页按钮
        "bLengthChange": true, // 改变每页显示数据数量
        "iDisplayLength": 10,// 每页显示行数
        "bInfo": true,//页脚信息
        "bAutoWidth": true,//自动宽度
        "fnServerData": funSelectShops, // 获取数据的处理函数
        "bFilter": false, // 隐藏筛选框
        "ordering": false,
        'bStateSave': true,
        "aoColumns": [
            {"mData": "id"},
            {"mData": "code"},
            {"mData": "name"},
            {"mData": "province"},
            {"mData": "city"},
            {"mData": "address"},
            {"mData": "tel"},
            {"mData": "manager"},
            {"mData": "mobile"},
            {
                "mData": "level",
                render: function (data, type, row) {
                    if (data == 1) {
                        return "普通";
                    } else if (data == 2) {
                        return "VIP";
                    } else {
                        return "未知";
                    }
                }
            },
            {
                "mData": "status",
                render: function (data, type, row) {
                    if (data == 1) {
                        return "有效";
                    } else if (data == 0) {
                        return "无效";
                    } else {
                        return "未知";
                    }
                }
            },
            //{
            //    "mData": "createTime",
            //    render: function (data, type, row) {
            //        if (data == null) {
            //            return "";
            //        }
            //        return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss");
            //    }
            //},
            //{
            //    "mData": "updateTime",
            //    render: function (data, type, row) {
            //        if (data == null) {
            //            return "";
            //        }
            //        return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss");
            //    }
            //},
            {
                render: function (data, type, row) {
                    if (type === 'display') {
                        return '<button type="button" class="btn btn-link btn-xs" onclick="funEditGetShopInfo(' + row.id + ')">编辑</button>' +
                            '<button type="button" class="btn btn-link btn-xs" onclick="funDeleteShopInfo(' + row.id + ')">删除</button>';
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


function funSelectShops(sSource, aoData, fnCallback) {
    console.log("========== selectShops ==========");
    sSource = "/shop.action?flag=sel";

    // 添加查询条件
    var name = $("#shopName").val();
    var province = $("#selectProvince").val();
    var city = $("#selectCity").val();
    aoData.push({name: "name", value: name});
    aoData.push({name: "province", value: province});
    aoData.push({name: "city", value: city});

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
 * @param shopId
 */
function funEditGetShopInfo(shopId) {
    var jsondata = {
        'op': 'shop.detail',
        'token': parent.token,
        'shopId': shopId
    };

    parent.execAjaxData("/shop.action", JSON.stringify(jsondata), true
        , function (response) {
            // error
        }, function (response) {
            // success
            if (response.code == 0) {
                $('#editShopId').val(response.data.id);
                $('#editShopCode').val(response.data.code);
                $('#editShopName').val(response.data.name);
                $('#editShopProvince').text(response.data.province);
                $('#editShopCity').text(response.data.city);

                $('#editShopAddress').val(response.data.address);
                $('#editShopTel').val(response.data.tel);
                $('#editShopManager').val(response.data.manager);
                $('#editShopMobile').val(response.data.mobile);
                $('#editShopLevel').val(response.data.level);

                $('#editShopStatus').val(response.data.status);

                $('#formEditTitle').text("编辑门店");
                $('#formEditShop').modal('show');
            }
        }, function () {
            // complete
        });
}

// 省份选择
function provinceChangeEvent(event, flag) {
    //alert('You like ' + event.target.value + ' ice cream.');
    if (flag == 1) {
        $('#selectCity').empty();
    } else {
        $('#editShopCity').empty();
    }

    var jsondata = {
        'op': 'city.selectByProvinceCode',
        'token': parent.token,
        'provinceCode': event.target.value
    };
    parent.execAjaxData("/sysCfg.action", JSON.stringify(jsondata), true
        , function (response) {
            // error
        }, function (response) {
            // success
            if (response.code == 0) {
                if (flag == 1) {
                    var ops = "";
                    var jsonarray = eval(response.data);
                    for (var i = 0; i < jsonarray.length; i++) {
                        ops += '<option value="' + jsonarray[i].code + '">' + jsonarray[i].name + '</option>';
                    }
                    $('#selectCity').append(ops);
                } else {
                    var ops = "";
                    var jsonarray = eval(response.data);
                    for (var i = 0; i < jsonarray.length; i++) {
                        ops += '<option value="' + jsonarray[i].code + '">' + jsonarray[i].name + '</option>';
                    }
                    $('#editShopCity').append(ops);
                }
            }
        }, function () {
            // complete
        });
}

function editSaveShopInfo() {
    var jsondata = {
        'op': 'shop.edit',
        'token': parent.token,
        'id': $('#editShopId').val(),
        'code': $('#editShopCode').val(),
        'name': $('#editShopName').val(),
        'province': $("#editShopProvince").find("option:selected").text(),
        'city': $("#editShopCity").find("option:selected").text(),
        'address': $('#editShopAddress').val(),
        'tel': $('#editShopTel').val(),
        'mobile': $('#editShopMobile').val(),
        'manager': $('#editShopManager').val(),
        'level': $('#editShopLevel').val(),
        'status': $('#editShopStatus').val()
    };

    parent.execAjaxData("/shop.action", JSON.stringify(jsondata), true
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
            $('#formEditShop').modal('hide');
        });
}

/**
 * 删除
 * @param shopId
 */
function funDeleteShopInfo(shopId) {
    if (confirm("确定要删除数据吗?")) {
        console.log("delete shop id:" + shopId);

        var jsondata = {
            'op': 'shop.delete',
            'token': parent.token,
            'shopId': shopId
        };

        parent.execAjaxData("/shop.action", JSON.stringify(jsondata), true
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
    $('#formEditTitle').text("新增门店");

    $('#editShopId').val(null)
    $('#editShopCode').val("");
    $('#editShopName').val("");
    $('#editShopProvince').val(0);
    $('#editShopCity').val(0);

    $('#editShopAddress').val("");
    $('#editShopTel').val("");
    $('#editShopManager').val("");
    $('#editShopMobile').val("");
    $('#editShopLevel').val(1);

    $('#editShopStatus').val(1);

    $('#formEditShop').modal('show');
}
