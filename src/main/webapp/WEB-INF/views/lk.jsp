<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>


<div id="new_users" >
    <div class="col-md-5" style="margin-top: 45px;margin-left: 50px">
        <table>
            <form:form method="POST" modelAttribute="user">
                <security:authorize access="hasRole('ROLE_ADMIN')">
                    <tr>
                        <td><label><spring:message text="Тип пользователя "/></label></td>
                        <td>
                        <select path="userType.type_id" name="userTypeId" class="form-control">
                            <c:forEach items="${userType}" var="type_item" varStatus="i">
                                <%--<c:if test="${type_item.id eq user.type_id}">--%>
                                    <%--<option selected = "selected" items = "${type_item.id}" value="${type_item.id}">${type_item.type}</option>--%>
                                <%--</c:if>--%>
                                <option items = "${type_item.id}" value="${type_item.id}">${type_item.type}</option>
                            </c:forEach>
                        </select></td>
                        <p id="error8" style="color: red"></p>
                    </tr>
                </security:authorize>
                <tr>
                    <td><label><spring:message text="Имя "/></label></td>
                    <td><form:input cssClass="form-control" path="first_name" id="work_hour2" /></td>
                    <p id="error2" style="color: red"></p>
                </tr>
                <tr>
                    <td><label><spring:message text="Фамилия "/></label></td>
                    <td><form:input cssClass="form-control" path="last_name" id="work_hour3"/></td>
                    <p id="error3" style="color: red"></p>
                </tr>
                <tr>
                    <td><label><spring:message text="Логин "/></label></td>
                    <td><form:input cssClass="form-control" path="login" id="work_hour1"/></td>
                    <p id="error1" style="color: red"></p>
                </tr>
                <tr>
                    <td><label><spring:message text="Пароль "/></label></td>
                    <td><form:input cssClass="form-control" path="password" id="work_hour4"/></td>
                    <p id="error4" style="color: red"></p>
                </tr>
                <tr>
                    <td><label><spring:message text="Почта "/></label></td>
                    <td><form:input cssClass="form-control" path="email" id="work_hour5"/></td>
                    <p id ="error5" style="color: red"></p>
                </tr>
                <tr>
                        <%--<form:select path="type_id" class="form-control"  name="maintenanceType" id="work_hour2">--%>
                        <%--<c:forEach items="${userForm.type}" var="item">--%>
                        <%--<form:option value="${item.getId}">${item.getType}</form:option>--%>
                        <%--</c:forEach>--%>
                        <%--</form:select>--%>
                </tr>
                <tr>
                    <td align="center">
                        <input class="btn btn-success" type ="submit" value="<spring:message text="Сохранить"/>"/>
                    </td>
                    <td align="center"></td>
                </tr>
            </form:form>
        </table>
    </div>
</div>