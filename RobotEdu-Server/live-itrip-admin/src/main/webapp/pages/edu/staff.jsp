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
                                <option value="0">公司总部</option>
                                <c:forEach items="${shopList}" var="shop">
                                    <option value="${shop.code}">${shop.name}</option>
                                </c:forEach>
                            </select>
                            <label class="control-label">姓名:</label>
                            <input type="text" class="form-control" id="staffName" placeholder="姓名">
                            <button type="button" onclick="funRefresh();" class="btn btn-primary ">查询</button>

                        </div>
                        <div class="col-sm-4">
                            <div class="btn-group">
                                <a onclick="funRefresh();" class="btn btn-primary ">
                                    <span class="fa fa-refresh"></span> 刷新
                                </a>
                            </div>
                            <div class="btn-group">
                                <a class="btn btn-primary dropdown-text" onclick="funClickAddRow()">
                                    <i class="fa fa-plus"></i> 新增
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="ibox-content table-responsive">
                <table class="table table-hover table-bordered dataTables-example table-responsive" id="tableStaffs"
                       style="font-size: 12px;">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>门店编号</th>
                        <th>员工编号</th>
                        <th>姓名</th>
                        <th>性别</th>
                        <th>手机</th>
                        <th>员工类型</th>
                        <th>状态</th>
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

<div class="modal fade modal-default" id="formEditStaff">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="formEditTitle">门店编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <input id="editStaffId" type="hidden">
                        <label for="editStaffCode" class="col-sm-3 control-label">员工编号</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editStaffCode">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editStaffName" class="col-sm-3 control-label">员工姓名</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editStaffName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="editStaffSex" class="col-sm-3 control-label">性别</label>
                        <div class="col-sm-5">
                            <select class="form-control" id="editStaffSex">
                                <option value="1">男</option>
                                <option value="2">女</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="editStaffType" class="col-sm-3 control-label">员工类型</label>
                        <div class="col-sm-5">
                            <select class="form-control" id="editStaffType">
                                <option value="0">店长</option>
                                <option value="1">老师</option>
                                <option value="2">客户顾问</option>
                                <option value="3">职员</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="editShop" class="col-sm-3 control-label">门店</label>
                        <div class="col-sm-5">
                            <select class="form-control" id="editShop">
                                <option value="0">总部</option>
                                <c:forEach items="${shopList}" var="shop">
                                    <option value="${shop.code}">${shop.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="editStaffMobile" class="col-sm-3 control-label">手机</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editStaffMobile">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="editStaffBirthday" class="col-sm-3 control-label">生日</label>
                        <div class="col-sm-5">
                            <div class="input-group date">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                <input id="editStaffBirthday" type="text" class="form-control" value="">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editStaffAdsress" class="col-sm-3 control-label">地址</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editStaffAdsress">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="editStaffStatus" class="col-sm-3 control-label">状态</label>
                        <div class="col-sm-5">
                            <select class="form-control" id="editStaffStatus">
                                <option value="1">在职</option>
                                <option value="0">离职</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn   btn-primary" data-dismiss="modal">取消</button>
                <button type="button" id="publicBtn" class="btn   btn-primary" onclick="editSaveStaffInfo()">确定
                </button>
            </div>
        </div>
    </div>
</div>

<script src="/js/plugins/datapicker/bootstrap-datepicker.js"></script>

<script src="/javascript/edu/staff.js"></script>
