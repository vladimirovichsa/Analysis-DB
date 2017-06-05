<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<script type="text/javascript">


    function openItem() {
        jQuery("#new_users").dialog({
                title: "Добавление подключение к базе данных",
                width: 550,
                height: 500,
                resizable: false,
                cache: false,
                modal: true,
            }
        );
    }

    function editRow(connectionID) {
        var tableType = $('#table-type-edit');
        $.ajax({
            type: "GET",
            contentType: 'application/json',
            url: "/connections/connection/"+connectionID,
            columnType: 'json',
        }).done(function (data) {
            $.ajax({
                type: "GET",
                contentType: 'application/json',
                url: "/connection/databasetype",
                columnType: 'json',
            }).done(function (data) {
                var option;
                for (var i = 0; i < data.length; i++) {
                    option += "<option value='" + data[i].tableTypeId + "'>" + data[i].type + "</option>"
                }
                tableType.append(option);
            });
        });
        jQuery("#edit-row").dialog({
                title: "Изменение подключения к базе данных",
                width: 550,
                height: 500,
                resizable: false,
                cache: false,
                modal: true,
                buttons: [
                    {
                        text: "ОК",
                        icons: {
                            primary: "ui-icon-success"
                        },
                        click: function () {
                            var newTableNmae = $('#table-name').val();
                            var attribute = function () {
                                $('#create-table-content .table tr').each(function (index) {
                                    this.ch
                                })
                            }
                        }
                    },
                    {
                        text: "Отмена",
                        icons: {
                            primary: "ui-icon-success"
                        },
                        click: function () {
                            this.close();
                        }
                    }
                ],
            }
        );
    }
</script>

<script>
    $(document).ready(function () {
        $("#datepicker1").datepicker({dateFormat: 'yy-mm-dd'});
        $("#datepicker2").datepicker({dateFormat: 'yy-mm-dd'});

    });
</script>


<%--<div onload="getUsers();"></div>--%>
<%--<center>--%>
<%--<div class="text-center" style="display: table; margin: 0 auto; text-align: center;">--%>
<%--<div class="btn-toolbar" \>--%>
<%--<div class="btn-group">--%>
<%--<button class="btn btn-default"><a href="/users">Пользователи</a></button>--%>
<%--<button class="btn btn-default"><a href="/equipment">Оборудование</a></button>--%>
<%--<button class="btn btn-default"><a href="/spare">Запчасти</a></button>--%>
<%--<button class="btn btn-default"><a href="/subdivision">Цеха</a></button>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</center>--%>
<div id="result">
    <center>
        </br>
        <button class="btn btn-info" userUserType="button" onclick="openItem();">Добавить подключение к БД</button>
        </br>
        <table class="table table-hover" id="params_t">
            <thead>
            <tr>
                <th>Тип базы данных</th>
                <th>Хост</th>
                <th>Порт</th>
                <th>База данных</th>
                <th>Имя пользователя</th>
                <th>Пароль</th>
                <th>URL</th>
                <th>Действия</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${connectionAll}" var="connection">
                <tr>
                    <td>${ connection.table_type_id.type }</td>
                    <td>${ connection.host }</td>
                    <td>${ connection.port}</td>
                    <td> ${ connection.data_base }</td>
                    <td> ${ connection.user_name }</td>
                    <td> ${ connection.password }</td>
                    <td> ${ connection.url }</td>
                    <td>
                        <button onclick="removeRow(${connection.id});"><span class="glyphicon glyphicon-remove"
                                                                             aria-hidden="true"></span></button>
                        |
                        <button onclick="editRow(${connection.id});"><span class="glyphicon glyphicon-edit"
                                                                           aria-hidden="true"></span></button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </center>
</div>
<div id="new_users" title="Добавление подключение к базе данных" style="display: none">
    <div class="col-md-5">
        <table class="table">
            <form:form method="POST" modelAttribute="connectionForm">
                <tr>
                    <td><label><spring:message text="Тип базы данных "/></label></td>
                    <td>
                        <select class="form-control" path="tableType.tableTypeId" name="tableTypeId" id="tableType">
                            <c:forEach items="${tableType}" var="type">
                                <option value="${type.tableTypeId}">${type.type}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <p id="error2" style="color: red"></p>
                </tr>
                <tr>
                    <td><label><spring:message text="Хост "/></label></td>
                    <td><form:input path="host" id="work_hour3"/></td>
                    <p id="error3" style="color: red"></p>
                </tr>
                <tr>
                    <td><label><spring:message text="Порт "/></label></td>
                    <td><form:input path="port" id="work_hour1"/></td>
                    <p id="error1" style="color: red"></p>
                </tr>
                <tr>
                    <td><label><spring:message text="База данных "/></label></td>
                    <td><form:input path="data_base" id="work_hour4"/></td>
                    <p id="error4" style="color: red"></p>
                </tr>
                <tr>
                    <td><label><spring:message text="Имя пользователя "/></label></td>
                    <td><form:input path="user_name" id="work_hour5"/></td>
                    <p id="error5" style="color: red"></p>
                </tr>
                <tr>
                    <td><label><spring:message text="Пароль "/></label></td>
                    <td><form:input path="password" id="work_hour6"/></td>
                    <p id="error6" style="color: red"></p>
                </tr>
                <tr>
                    <td><label><spring:message text="Url "/></label></td>
                    <td><form:input path="url" id="work_hour7"/></td>
                    <p id="error7" style="color: red"></p>
                </tr>
                <tr>
                    <td align="center">
                        <input class="btn btn-success"
                               type="submit"
                               value="<spring:message text="Добавить"/>"/>
                    </td>
                    <td align="center">
                        <input class="btn btn-warning"
                               type="button"
                               onclick="jQuery('#new_users').dialog('close');"
                               value="<spring:message text="Закрыть" />"/>
                    </td>
                </tr>
            </form:form>
        </table>
    </div>
</div>
<div id="edit-row" style="display: none">
    <div class="col-md-5">
        <table class="table">
            <tr>
                <td><label><spring:message text="Тип базы данных "/></label></td>
                <td>
                    <select class="form-control" id="table-type-edit">

                    </select>
                </td>
            </tr>
            <tr>
                <td><label><spring:message text="Хост "/></label></td>
                <td><input id="host-edit"/></td>
            </tr>
            <tr>
                <td><label><spring:message text="Порт "/></label></td>
                <td><input id="port-edit"/></td>
            </tr>
            <tr>
                <td><label><spring:message text="База данных "/></label></td>
                <td><input id="database-edit"/></td>
            </tr>
            <tr>
                <td><label><spring:message text="Имя пользователя "/></label></td>
                <td><input id="username-edit"/></td>
            </tr>
            <tr>
                <td><label><spring:message text="Пароль "/></label></td>
                <td><input id="password-edit"/></td>
            </tr>
            <tr>
                <td><label><spring:message text="Url "/></label></td>
                <td><input id="url-edit"/></td>
            </tr>
        </table>
    </div>
</div>