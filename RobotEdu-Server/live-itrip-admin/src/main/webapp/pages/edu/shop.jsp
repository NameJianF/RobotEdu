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
<c:import url="/pages/importjs.jsp"/>

<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox-title">
                <div class="ibox-tools">
                    <div class="row">
                        <div class="col-sm-8 form-inline" style="text-align: left;">
                            <label class="control-label">省份:</label>
                            <select class="form-control" id="selectProvince" onchange="provinceChangeEvent(event,1);"
                                    style="width: 120px;">
                                <option value="0">全部</option>
                                <c:forEach items="${provinceList}" var="province">
                                    <option value="${province.code}">${province.name}</option>
                                </c:forEach>
                            </select>
                            <label class="control-label">城市:</label>
                            <select class="form-control" id="selectCity" style="width: 120px;">
                                <option value="0">全部</option>
                            </select>
                            <label class="control-label">名称:</label>
                            <input type="text" class="form-control" id="shopName" placeholder="门店名称">
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
                <table class="table table-hover table-bordered dataTables-example table-responsive" id="tableShops"
                       style="font-size: 12px;">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>识别编号</th>
                        <th>名称</th>
                        <th>省份</th>
                        <th>城市</th>
                        <th>地址</th>
                        <th>电话</th>
                        <th>店长</th>
                        <th>手机</th>
                        <th>级别</th>
                        <th>状态</th>
                        <%--<th>创建时间</th>--%>
                        <%--<th>更新时间</th>--%>
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

<div class="modal fade modal-default" id="formEditShop">
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
                        <input id="editShopId" type="hidden">
                        <label for="editShopCode" class="col-sm-3 control-label">识别编号</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editShopCode">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editShopName" class="col-sm-3 control-label">门店名称</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editShopName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="editShopProvince" class="col-sm-3 control-label">省份</label>
                        <div class="col-sm-5">
                            <select class="form-control" id="editShopProvince"
                                    onchange="provinceChangeEvent(event,2);">
                                <option value="0">请选择省份</option>
                                <c:forEach items="${provinceList}" var="province">
                                    <option value="${province.code}">${province.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editShopCity" class="col-sm-3 control-label">城市</label>
                        <div class="col-sm-5">
                            <select class="form-control" id="editShopCity" style="width: 120px;">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="editShopAddress" class="col-sm-3 control-label">地址</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editShopAddress">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editShopTel" class="col-sm-3 control-label">电话</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editShopTel">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editShopManager" class="col-sm-3 control-label">店长</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editShopManager">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editShopMobile" class="col-sm-3 control-label">手机</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editShopMobile">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editShopLevel" class="col-sm-3 control-label">级别</label>
                        <div class="col-sm-5">
                            <select class="form-control" id="editShopLevel">
                                <option value="1">普通</option>
                                <option value="2">VIP</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editShopStatus" class="col-sm-3 control-label">状态</label>
                        <div class="col-sm-5">
                            <select class="form-control" id="editShopStatus">
                                <option value="1">有效</option>
                                <option value="0">无效</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn   btn-primary" data-dismiss="modal">取消</button>
                <button type="button" id="publicBtn" class="btn   btn-primary" onclick="editSaveShopInfo()">确定
                </button>
            </div>
        </div>
    </div>
</div>


<script src="/javascript/edu/shop.js"></script>
