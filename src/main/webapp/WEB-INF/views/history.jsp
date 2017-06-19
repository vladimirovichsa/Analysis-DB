<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<label>Выберите пользователя: </label>
<div class="row">

    <div class="col-xs-7 col-sm-5 col-md-4">

        <select class="form-control col-xs-1" name="user" id="user-select">
            <option disabled>--Выберите пользователя--</option>
            <c:forEach items="${listUser}" var="user">
                <option value="${user.id}">${user.first_name} ${user.last_name} (${user.login} | ${user.type.type} )</option>
            </c:forEach>
        </select>
    </div>
    <div class="col-xs-6 col-sm-4 col-md-3">
        <button type="submit" id="user-select-button" class="btn btn-success">Выбрать</button>
    </div>
</div>
<div id="history-user">

    <table class="table hover">
        <tr>
            <th></th>
        </tr>
<c:forEach items="${listHistory}" var="history">
        <tr>
            <td>${listHistory.action}</td>
            <td>${listHistory.status}</td>
        </tr>
</c:forEach>
    </table>

</div>
