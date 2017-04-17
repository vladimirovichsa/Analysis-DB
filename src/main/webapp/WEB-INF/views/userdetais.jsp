<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<div align="center">
    <label>Имя</label><label>${userDetails.first_name}</label>
    <label>Фамилия</label><label>${userDetails.last_name}</label>
    <label>Логин</label><label>${userDetails.getLogin()}</label>
    <label>Пароль</label><label>${userDetails.getPassword()}</label>
</div>

