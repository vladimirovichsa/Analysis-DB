<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<!-- Static navbar -->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button userUserType="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href=""><b>САНР-БД</b></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="<c:url value="/"/>">Главная</a></li>
                <%--<security:authorize access="hasRole('ROLE_ADMIN')">--%>
                    <%--<li><a href="<c:url value="/equipments"/>">Оборудование</a></li>--%>
                <%--</security:authorize>--%>
                <%--<li><a href="<c:url value="/repair"/>">Заявки</a></li>--%>
                <%--<li class="dropdown">--%>
                    <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"--%>
                       <%--aria-expanded="false">План-график<span class="caret"></span></a>--%>
                    <%--<ul class="dropdown-menu">--%>
                        <%--<li><a href="<c:url value="/graphics"/>">Месячный</a></li>--%>
                    <%--</ul>--%>
                <%--</li>--%>
                <security:authorize access="hasRole('ROLE_ADMIN')">
                    <li><a href="<c:url value="/users"/>">Пользователи</a></li>
                </security:authorize>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <%--<security:authorize access="hasRole('ROLE_REPAIR')">--%>
                    <%--<li>--%>
                        <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">--%>
                            <%--<i class="fa fa-bell-o"></i>--%>
                            <%--<span class="glyphicon glyphicon-envelope" style="color: red">${active_req}</span>--%>
                        <%--</a>--%>
                        <%--<ul class="dropdown-menu" style="padding: 15px; ">--%>
                            <%--<li class="header">Поступило новых заявок: <b>${active_req}</b></li>--%>
                            <%--<li>--%>
                                <%--<!-- inner menu: contains the actual data -->--%>
                                <%--<div class="slimScrollDiv">--%>
                                    <%--<ul class="menu">--%>
                                        <%--<li>--%>
                                            <%--<a href="<c:url value="/repair"/>">--%>
                                                <%--<p>перейти к заявкам</p>--%>
                                            <%--</a>--%>
                                        <%--</li>--%>
                                    <%--</ul>--%>
                                <%--</div>--%>
                            <%--</li>--%>
                        <%--</ul>--%>
                    <%--</li>--%>
                <%--</security:authorize>--%>
                <%--<security:authorize access="hasRole('ROLE_ADMIN')">--%>
                    <%--<li>--%>
                        <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">--%>
                            <%--<i class="fa fa-bell-o"></i>--%>
                            <%--<span class="glyphicon glyphicon-envelope" style="color: red">${confirm_req}</span>--%>
                        <%--</a>--%>
                        <%--<ul class="dropdown-menu" style="padding: 15px; ">--%>
                            <%--<li class="header">Заявок на подтверждении: <b>${confirm_req}</b></li>--%>
                            <%--<li>--%>
                                <%--<!-- inner menu: contains the actual data -->--%>
                                <%--<div class="slimScrollDiv">--%>
                                    <%--<ul class="menu">--%>
                                        <%--<li>--%>
                                            <%--<a href="<c:url value="/repair"/>">--%>
                                                <%--<p>перейти к заявкам</p>--%>
                                            <%--</a>--%>
                                        <%--</li>--%>
                                    <%--</ul>--%>
                                <%--</div>--%>
                            <%--</li>--%>
                        <%--</ul>--%>
                    <%--</li>--%>
                <%--</security:authorize>--%>

                <security:authorize access="isAnonymous()">
                    <li><a href="<c:url value="/login"/>">Войти</a></li>
                </security:authorize>
                <security:authorize access="!isAnonymous()">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false"><security:authentication property="principal.username"/><span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="<c:url value="/lk"/>">Настройки</a></li>
                            <li><a href="#" onclick="openConnectionDialog();">Список подключений</a></li>
                            <li><a href="<c:url value="/logout"/>">Выйти</a></li>
                        </ul>
                    </li>
                </security:authorize>
            </ul>
        </div><!--/.nav-collapse -->
    </div><!--/.container-fluid -->
</nav>
<div id="new_repair"  title="Добавление новой заявки" style="display: none"></div>