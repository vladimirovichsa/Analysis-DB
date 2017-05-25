<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<div align="center">
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <c:if test="${param.logout != null}">
        <div class="alert alert-success">
            <p>Вы успешно вышли из системы.</p>
        </div>
    </c:if>

    <form class="form-horizontal" name="loginForm" class="form-signin" style="width:30%;" action="<c:url value='j_spring_security_check' />"
          method='POST'>
        <h2 class="form-signin-heading">Пожалуйста, авторизуйтесь</h2>
        <div class="form-group row">
            <label class="col-2 col-form-label" for="inputEmail">Логин</label>
            <div class="col-10">
                <input type="text" id="inputEmail" placeholder="Логин" class="form-control" name='login' required autofocus>
            </div>
        </div>
        <div class="form-group">
            <label class="col-2 col-form-label" for="inputPassword">Password</label>
            <div class="col-10">
                <input type="password" id="inputPassword" class="form-control" name='password' placeholder="Пароль" required>
            </div>
        </div>
        <div class="form-group">
            <div class="controls">
                <label class="checkbox">
                    <input type="checkbox"> Remember me
                </label>
                <button type="submit" class="btn">Войти</button>
            </div>
        </div>
    </form>
</div>

