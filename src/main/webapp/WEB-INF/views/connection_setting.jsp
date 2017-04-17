<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<script userUserType="text/javascript">

//    function getUsers() {
//        $.ajax({
//            userUserType: "GET",
//            contentType: 'application/json',
//            url: "/users/all",
//            columnType: 'json',
//            mimeType: 'application/json',
//        }).done(function (data) {
//            jQuery("#result").html("<center></br><button class=\"btn btn-info\"  userUserType=\"button\" onclick=\"openUsers()\">Добавить пользователя</button></br><table class=\"table table-hover\" id=\"params_t\">\n\
//               </br>\
//<thead>\n\
//  <tr><th>ssoid</th><th>Имя</th><th>Фамилия</th><th>Почта</th><th></th></tr>\n\
//  </thead>\n\
//<tbody>\n");
//
//            for (var i = 0; i <= data.length - 1; i++) {
//                $("#params_t").append("<tr><td>" + data[i].id + "</td><td>" + data[i].first_name + "</td><td>" + data[i].last_name + "</td><td><span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span></td></tr>");
//            }
//        });
//    }

    function openItem() {
        jQuery("#new_users").dialog({
                title: "Добавление подключения к базе данных",
                width: 640,
                height: 400,
                resizable: false,
                cache: false,
                modal: true
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
                    <td><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </center>
</div>
<div id="new_users" title="Добавление подключения к базе данных" style="display: none">
    <div class="col-md-5" style="margin-top: 45px;margin-left: 50px">
        <table>
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
                    <td colspan="2"><br/></td>
                </tr>
                <tr>
                    <td><label><spring:message text="Хост "/></label></td>
                    <td><form:input path="host" id="work_hour3"/></td>
                    <p id="error3" style="color: red"></p>
                </tr>
                <tr>
                    <td colspan="2"><br/></td>
                </tr>
                <tr>
                    <td><label><spring:message text="Порт "/></label></td>
                    <td><form:input path="port" id="work_hour1"/></td>
                    <p id="error1" style="color: red"></p>
                </tr>
                <tr>
                    <td colspan="2"><br/></td>
                </tr>
                <tr>
                    <td><label><spring:message text="База данных "/></label></td>
                    <td><form:input path="data_base" id="work_hour4"/></td>
                    <p id="error4" style="color: red"></p>
                </tr>
                <tr>
                    <td colspan="2"><br/></td>
                </tr>
                <tr>
                <td><label><spring:message text="Имя пользователя "/></label></td>
                <td><form:input path="user_name" id="work_hour5"/></td>
                <p id ="error5" style="color: red"></p>
                </tr>
                <tr>
                    <td colspan="2"><br/></td>
                </tr>
                <tr>
                <td><label><spring:message text="Пароль "/></label></td>
                <td><form:input path="password" id="work_hour6" /></td>
                <p id ="error6" style="color: red"></p>
                </tr>
                <tr>
                    <td colspan="2"><br/></td>
                </tr>
                <tr>
                <td><label><spring:message text="Url "/></label></td>
                <td><form:input path="url" id="work_hour7"/></td>
                <p id ="error7" style="color: red"></p>
                </tr>
                <tr>
                    <td colspan="2"><br/></td>
                </tr>
                <tr>
                        <%--<form:select path="type_id" class="form-control"  name="maintenanceType" id="work_hour2">--%>
                        <%--<c:forEach items="${userForm.type}" var="item">--%>
                        <%--<form:option value="${item.getId}">${item.getType}</form:option>--%>
                        <%--</c:forEach>--%>
                        <%--</form:select>--%>
                </tr>
                <tr>
                    <td colspan="2"><br/></td>
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
                <tr>
                    <td colspan="2"><br/></td>
                </tr>
            </form:form>
        </table>
    </div>
</div>