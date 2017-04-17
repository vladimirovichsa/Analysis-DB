<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<h2> FOOOOOOOOOOOOOOOOOOOTER</h2>

<script userUserType="text/javascript">

    function openConnectionDialog() {
        $.ajax({
            type: "GET",
            contentType: 'application/json',
            url: "/database/userconnection",
            columnType: 'json',
        }).done(function (data) {
            if(data.length > 0){
                var option ;
                for(var i =0;i<data.length;i++) {
                    option += "<option value='"+data[i].id+"'>"+data[i].data_base+"</option>"
                }
                jQuery("#new_repair").html('<div class="row">\
        <form:form method="POST" action="/database" modelAttribute="connectionDB">\
        <div class="col-xs-10 col-sm-7 col-md-8">\
            <select class="form-control col-xs-1" path="connectionDB.id" name="connection"\
        id="connection">\
            <option disabled>--Выберите базу данных--</option>\
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
            title: "Добавление новой заявки",
            width: 550,
            height: 470,
            modal: true,
            resizable: false
        });
    }
</script>