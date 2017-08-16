var tabUsers;

$(function () {
    tabUsers = $('#tableStaffs').DataTable({
        "bProcessing": true, // 是否显示取数据时的那个等待提示
        "bServerSide": true, //这个用来指明是通过服务端来取数据
        "bPaginate": true, // 分页按钮
        "bLengthChange": true, // 改变每页显示数据数量
        "iDisplayLength": 10,// 每页显示行数
        "bInfo": true,//页脚信息
        "bAutoWidth": true,//自动宽度
        "fnServerData": funSelectStaffs, // 获取数据的处理函数
        "bFilter": false, // 隐藏筛选框
        "ordering": false,
        'bStateSave': true,
        "aoColumns": [
            {"mData": "id"},
            {"mData": "shopNo"},
            {"mData": "staffNo"},
            {"mData": "staffName"},
            {"mData": "sex"},
            {"mData": "mobile"},
            {
                "mData": "staffType",
                render: function (data, type, row) {
                    if (data == 1) {
                        return "店长";
                    } else if (data == 2) {
                        return "老师";
                    } else if (data == 3) {
                        return "客户顾问";
                    } else if (data == 4) {
                        return "职员";
                    }
                }
            },
            {
                "mData": "status",
                render: function (data, type, row) {
                    if (data == 1) {
                        return "在职";
                    } else if (data == 0) {
                        return "离职";
                    }
                }
            },
            {
                render: function (data, type, row) {
                    if (type === 'display') {
                        return '<button type="button" class="btn btn-link btn-xs" onclick="funEditGetStaffInfo(' + row.id + ')">编辑</button>' +
                            '<button type="button" class="btn btn-link btn-xs" onclick="funDeleteStaffInfo(' + row.id + ')">删除</button>';
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

    $('#editStaffBirthday').datepicker({
        todayBtn: "linked",
        keyboardNavigation: false,
        forceParse: false,
        calendarWeeks: true,
        autoclose: true
    });
});


function funSelectStaffs(sSource, aoData, fnCallback) {
    console.log("========== selectStaffs ==========");
    sSource = "/staff.action?flag=sel";

    // 添加查询条件
    var staffName = $("#staffName").val();
    var shop = $("#selectShop").val();
    aoData.push({name: "staffName", value: staffName});
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
 * @param staffId
 */
function funEditGetStaffInfo(staffId) {
    var jsondata = {
        'op': 'staff.detail',
        'token': parent.token,
        'staffId': staffId
    };

    parent.execAjaxData("/staff.action", JSON.stringify(jsondata), true
        , function (response) {
            // error
        }, function (response) {
            // success
            if (response.code == 0) {
                $('#editStaffId').val(response.data.id);
                $('#editStaffCode').val(response.data.staffNo);
                $('#editStaffName').val(response.data.staffName);
                $('#editStaffSex').text(response.data.sex);
                $('#editStaffType').text(response.data.staffType);

                $('#editShop').val(response.data.shopNo);
                $('#editStaffMobile').val(response.data.mobile);
                $('#editStaffBirthday').val(response.data.birthday);
                $('#editStaffAdsress').val(response.data.address);
                $('#editStaffStatus').val(response.data.status);

                $('#formEditTitle').text("编辑资料");
                $('#formEditStaff').modal('show');
            }
        }, function () {
            // complete
        });
}

function editSaveStaffInfo() {
    var jsondata = {
        'op': 'staff.edit',
        'token': parent.token,
        'id': $('#editStaffId').val(),
        'staffNo': $('#editStaffCode').val(),
        'staffName': $('#editStaffName').val(),
        'sex': $("#editStaffSex").val(),
        'staffType': $("#editStaffType").val(),
        'shopNo': $("#editShop").val(),
        'mobile': $('#editStaffMobile').val(),
        'birthday': $('#editStaffBirthday').val(),
        'address': $('#editStaffAdsress').val(),
        'status': $('#editStaffStatus').val()
    };

    parent.execAjaxData("/staff.action", JSON.stringify(jsondata), true
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
            $('#formEditStaff').modal('hide');
        });
}

/**
 * 删除
 * @param staffId
 */
function funDeleteStaffInfo(staffId) {
    if (confirm("确定要删除数据吗?")) {
        console.log("delete staff id:" + staffId);

        var jsondata = {
            'op': 'staff.delete',
            'token': parent.token,
            'staffId': staffId
        };

        parent.execAjaxData("/staff.action", JSON.stringify(jsondata), true
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
    $('#formEditTitle').text("新增员工");

    $('#editStaffId').val(null)
    $('#editStaffCode').val("");
    $('#editStaffName').val("");
    $('#editStaffSex').val(1);
    $('#editStaffType').val(3);

    $('#editShop').val(0);
    $('#editStaffMobile').val("");
    $('#editStaffBirthday').val("");
    $('#editStaffAdsress').val("");
    $('#editStaffStatus').val(1);

    $('#formEditStaff').modal('show');
}
