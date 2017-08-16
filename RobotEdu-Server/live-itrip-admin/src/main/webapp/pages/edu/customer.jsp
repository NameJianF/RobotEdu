<%--
  Created by IntelliJ IDEA.
  User: Feng
  Date: 2016/10/17
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:import url="/pages/importcss.jsp"/>

<link href="/css/plugins/datapicker/datepicker3.css" rel="stylesheet">

<c:import url="/pages/importjs.jsp"/>

<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox-title">
                <div class="ibox-tools">
                    <div class="row">
                        <div class="col-sm-8 form-inline" style="text-align: left;">
                            <label class="control-label">门店:</label>
                            <select class="form-control" id="selectShop"
                                    style="width: 120px;">
                                <option value="">全部</option>
                                <c:forEach items="${shopList}" var="shop">
                                    <option value="${shop.code}">${shop.name}</option>
                                </c:forEach>
                            </select>
                            <label class="control-label">宝宝姓名:</label>
                            <input type="text" class="form-control" id="customerName" placeholder="宝宝名字">
                            <button type="button" onclick="funRefresh();" class="btn btn-primary ">查询</button>

                        </div>
                        <div class="col-sm-4">
                            <div class="btn-group">
                                <a onclick="funRefresh();" class="btn btn-primary ">
                                    <span class="fa fa-refresh"></span> 刷新
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="ibox-content table-responsive">
                <table class="table table-hover table-bordered dataTables-example table-responsive" id="tableCustomers"
                       style="font-size: 12px;">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>门店编号</th>
                        <th>会员卡号</th>
                        <th>宝宝姓名</th>
                        <th>性别</th>
                        <th>宝宝生日</th>
                        <th>客户顾问</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
</div>


<div class="modal fade modal-default" id="formEditCustomer">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="formEditTitle">详细信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <input id="editCustomerId" type="hidden">
                        <label for="editShop" class="col-sm-3 control-label">门店</label>
                        <div class="col-sm-5">
                            <select class="form-control" id="editShop">
                                <c:forEach items="${shopList}" var="shop">
                                    <option value="${shop.code}">${shop.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editAdviser" class="col-sm-3 control-label">客户顾问</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editAdviser">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editCardNo" class="col-sm-3 control-label">会员卡号</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editCardNo">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editChildName" class="col-sm-3 control-label">宝宝姓名</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editChildName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="editChildSex" class="col-sm-3 control-label">宝宝性别</label>
                        <div class="col-sm-5">
                            <select class="form-control" id="editChildSex">
                                <option value="1">男</option>
                                <option value="2">女</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editChildBirthday" class="col-sm-3 control-label">宝宝生日</label>
                        <div class="col-sm-5">
                            <div class="input-group date">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                <input id="editChildBirthday" type="text" class="form-control" value="">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editMomName" class="col-sm-3 control-label">妈妈姓名</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editMomName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editMomMobile" class="col-sm-3 control-label">妈妈手机</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editMomMobile">
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                    <%--<label for="editMomEmail" class="col-sm-3 control-label">妈妈Email</label>--%>
                    <%--<div class="col-sm-5">--%>
                    <%--<input type="text" class="form-control" id="editMomEmail">--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label for="editDadName" class="col-sm-3 control-label">爸爸姓名</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editDadName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editDadMobile" class="col-sm-3 control-label">爸爸手机</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editDadMobile">
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                    <%--<label for="editDadEmail" class="col-sm-3 control-label">爸爸Email</label>--%>
                    <%--<div class="col-sm-5">--%>
                    <%--<input type="text" class="form-control" id="editDadEmail">--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label for="editAddress" class="col-sm-3 control-label">家庭地址</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editAddress">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="editRemarks" class="col-sm-3 control-label">备注</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editRemarks">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn  btn-primary" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script src="/js/plugins/datapicker/bootstrap-datepicker.js"></script>


<script src="/javascript/edu/customer.js"></script>
