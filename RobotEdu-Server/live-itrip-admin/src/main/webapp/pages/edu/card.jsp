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

<%--<link href="/css/plugins/datapicker/datepicker3.css" rel="stylesheet">--%>

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
                            <label for="cardNo" class="control-label">卡号:</label>
                            <input type="text" class="form-control" id="cardNo" placeholder="卡号">
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
                <table class="table table-hover table-bordered dataTables-example table-responsive" id="tableCards"
                       style="font-size: 12px;">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>门店编号</th>
                        <th>卡号</th>
                        <th>类型</th>
                        <th>总次数</th>
                        <th>已使用次数</th>
                        <th>价格</th>
                        <th>折扣</th>
                        <th>客户顾问</th>
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

<div class="modal fade modal-default" id="formEditCard">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="formEditTitle">会员卡编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <input id="editCardId" type="hidden">
                        <label for="editCardNo" class="col-sm-3 control-label">卡号</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editCardNo">
                        </div>
                    </div>
                    <div class="form-group">
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
                        <label for="editCardType" class="col-sm-3 control-label">卡类型</label>
                        <div class="col-sm-5">
                            <select class="form-control" id="editCardType">
                                <option value="1">次卡</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="editTotalTimes" class="col-sm-3 control-label">总次数</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editTotalTimes">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editUsedTimes" class="col-sm-3 control-label">已使用次数</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editUsedTimes">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editPrice" class="col-sm-3 control-label">价格</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editPrice">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="editDiscount" class="col-sm-3 control-label">折扣</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editDiscount">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="editAdviser" class="col-sm-3 control-label">客户顾问</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="editAdviser">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="editCardFlag" class="col-sm-3 control-label">状态</label>
                        <div class="col-sm-5">
                            <select class="form-control" id="editCardFlag">
                                <option value="1">正常</option>
                                <option value="0">不可用</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn   btn-primary" data-dismiss="modal">取消</button>
                <button type="button" id="publicBtn" class="btn   btn-primary" onclick="editSaveCardInfo()">确定
                </button>
            </div>
        </div>
    </div>
</div>

<%--<script src="/js/plugins/datapicker/bootstrap-datepicker.js"></script>--%>

<script src="/javascript/edu/card.js"></script>
