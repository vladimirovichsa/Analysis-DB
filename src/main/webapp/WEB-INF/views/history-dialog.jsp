<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<div class="row">
    <c:if test="${!empty connectionList}">
        <form:form method="POST" modelAttribute="connectionDB">
            <div class="col-xs-10 col-sm-7 col-md-8">
                <select class="form-control col-xs-1" path="connectionDB.id" name="connection"
                        id="connection">
                    <option disabled>--Выберите базу данных--</option>
                    <c:forEach items="${connectionList}" var="connection">
                        <option value="${connection.id}">${connection.data_base}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-xs-6 col-md-4">
                <button type="submit" class="btn btn-success">
                    <i class="glyphicon glyphicon-ok "></i>
                </button>
            </div>
        </form:form>
    </c:if>
    <c:if test="${empty connectionList}">
        <div class="col-xs-10 col-sm-7 col-md-8">
            <a class="btn btn-primary" href="<c:url value="/connections"/>">Добавить подключение</a>
        </div>
    </c:if>
</div>