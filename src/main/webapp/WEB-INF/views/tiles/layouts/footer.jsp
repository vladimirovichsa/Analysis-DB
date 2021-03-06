<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>


<script userUserType="text/javascript">

    function openConnectionDialog() {
        $.ajax({
            type: "GET",
            contentType: 'application/json',
            url: "/database/userconnection",
            columnType: 'json',
        }).done(function (data) {
            if(data.length > 0){
                var option = "<option disabled>--Выберите базу данных--</option>";
                for(var i =0;i<data.length;i++) {
                    option += "<option value='"+data[i].id+"'>"+data[i].data_base+"</option>"
                }
                jQuery("#new_repair").html('<div class="row">\
        <form:form method="POST" action="/database" modelAttribute="connectionDB">\
        <div class="col-xs-10 col-sm-7 col-md-8">\
            <select class="form-control col-xs-1" path="connectionDB.id" name="connection"\
        id="connection">\
            '+option+'\
        <option value=""></option></select></div>\
            <div class="col-xs-6 col-md-4">\
            <button type="submit" class="btn btn-success">\
            <i class="glyphicon glyphicon-ok "></i></button></div>\
            </form:form>\
        </div>');
            }else{
                jQuery("#new_repair").html('<div class="row"><div class="col-xs-10 col-sm-7 col-md-8">\
            <a class="btn btn-primary" href="<c:url value="/connections"/>">Добавит подключение</a>\
        </div></div>');
            }

        });
        jQuery("#new_repair").dialog({
            width: 500,
            height: 100,
            modal: true,
            title: "Список подключений",
            resizable: false
        });
    }

    function openHistoryDialog() {
        $.ajax({
            type: "GET",
            contentType: 'application/json',
            url: "/users/allusers",
            columnType: 'json',
        }).done(function (data) {
            if(data.length > 0){
                var option = "<option disabled>--Выберите пользователя--</option>";
                for(var i =0;i<data.length;i++) {
                    option += "<option value='"+data[i].id+"'>"+data[i].first_name+" " + data[i].last_name + " (" +data[i].login + " | " +data[i].type_id.type + ")</option>"
                }
                jQuery("#new_repair").html('<div class="row">\
        <form:form method="POST" action="/users/history" modelAttribute="user">\
        <div class="col-xs-10 col-sm-7 col-md-8">\
            <select class="form-control col-xs-1" path="user.id" name="userid"\
        id="connection">\
            '+option+'\
        <option value=""></option></select></div>\
            <div class="col-xs-6 col-md-4">\
            <button type="submit" class="btn btn-success">Выбрать</button></div>\
            </form:form>\
        </div>');
            }else{
                jQuery("#new_repair").html('<div class="row"><div class="col-xs-10 col-sm-7 col-md-8">\
            <a class="btn btn-primary" href="<c:url value="/users"/>">Добавить пользователя</a>\
        </div></div>');
            }

        });
        jQuery("#new_repair").dialog({
            width: 500,
            height: 100,
            modal: true,
            title: "Список пользователей",
            resizable: false
        });
    }
</script>