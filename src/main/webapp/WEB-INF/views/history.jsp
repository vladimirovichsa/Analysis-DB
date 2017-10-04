<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<div class="row">

    <div class="col-xs-7 col-sm-5 col-md-4">
        <div id="history-user">

            <table class="table hover">
                <tr>
                    <th>Действие</th>
                    <th>Статус</th>
                    <th>Дата</th>
                </tr>
                <c:forEach items="${listHistory}" var="history">
                    <tr>
                        <td>${history.action}</td>
                        <td>${history.status}</td>
                        <td>${history.dateTime}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>