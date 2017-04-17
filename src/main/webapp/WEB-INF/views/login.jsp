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

    <form name="loginForm" class="form-signin" style="width:30%;" action="<c:url value='j_spring_security_check' />"
          method='POST'>
        <h2 class="form-signin-heading">Пожалуйста, авторизуйтесь</h2>
        <label for="inputEmail" class="sr-only">Ваш логин</label>
        <input userUserType="text" id="inputEmail" class="form-control" name='login' placeholder="Email-адрес" required
               autofocus>
        <label for="inputPassword" class="sr-only">Ваш пароль</label>
        <input userUserType="password" id="inputPassword" class="form-control" name='password' placeholder="Пароль"
               required>

        <button class="submitLogin" name="submit" type="submit">Войти</button>

    </form>
</div>

