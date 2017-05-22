<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>


<style>
    .word {
        color: blue;
    }

    .word2 {
        color: orange;
    }

    .word3 {
        color: red;
    }

    .word4 {
        color: green;
    }

    .notice {
        background: #f5ff0b;
    }

    .warning {
        background: #ff8f08;
    }

    .critical {
        background: #ff230d;
    }

    .checkbox{
        float: left;
        display: block;
    }
</style>
<div id="table-content" >
    <h2>Выберите таблицу для отображения данных</h2>
</div>
<div id="analize-table-dialog" style="overflow-x: auto;">
    <div id="analize-table-content" ></div>
</div>


<script userUserType="text/javascript">

//    jQuery(document).ready(function($){
//        var $table = $('table'),
//            $header = $('#header-table'),
//            $thead = $('thead');
//        $thead.find('th').each(function(){
//            var $newdiv = $('<div />', {
//                style: 'width:'+ $(this).width()+'px'
//
//            });
//            $newdiv.text($(this).text());
//            $header.append($newdiv);
//        });
//
//        var $viewport = $(window);
//
//        $viewport.scroll(function(){
//            $header.css({
//                left: -$(this).scrollLeft()
//            });
//
//        });
//    });

    function openResultAnalize(relationTable) {
        jQuery("#analize-table-dialog").dialog({
            title: "Результат анализа",
            width: 800,
            height: 600,
            modal: true,
            position : { my: "center top", at: "center top", of: window },
            resizable: false
        });

        $('#analize-table-content').html('<H1>загрузка...</H1>');
        $.ajax({
            userUserType: "GET",
            contentType: 'application/json',
            url: '/database/table/analysis/' + relationTable,
            columnType: 'json',
        }).done(function (data) {
            if (data.attributes.length > 0) {
                var table = "<table class=\"table table-hover\"><thead><tr></tr></thead>\
                <tbody>\
                </tbody>\
                </table>";
                var th;
                var tr;
                $("#analize-table-content").html(table);
                for (var i = 0; i < data.attributes.length; i++) {
                    var checkBoxDisable = "" ;
                    if(data.attributes[i].isForeignKey == true || data.attributes[i].isPrimaryKey == true){
                        checkBoxDisable = "disabled";
                    }
                    th += "<th>" +
                        "<input class='checkbox' type='checkbox' value='"+i+"' "+checkBoxDisable+">" +
                        "<span style='padding-left: 20px; display: block;width:auto;'> " + data.attributes[i].name +" "+
                        "</span>" +
                        "</th>";

                }
                if (data.data.length > 0) {
                    for (var i = 0; i < data.data.length; i++) {
                        /*
                         for (var j = 0; j < data.data[i].length; j++) {
                         if (i == 0 ) {
                         tr += "<tr>";
                         }
                         tr += "<td>" + data.data[i][j] + "</td>";
                         if (i == data.data.length) {
                         tr += "</tr>";
                         }
                         }*/
                        tr += "<tr>";
                        for (var j = 0; j < data.data[i].length; j++) {
                            var style = "";
                            if (data.attributes[j].normal == true) style = "";
                            if (data.attributes[j].notice == true) style = "notice";
                            if (data.attributes[j].warning == true) style = "warning";
                            if (data.attributes[j].critical == true) style = "critical";
                            tr += "<td class='" + style + "'>" + data.data[i][j] + "</td>";
                        }
                        tr += "</tr>";

                    }
                }
                if (th != null) {
                    $("#analize-table-content table thead tr").html(th);
                }
                if (tr != null) {
                    $("#analize-table-content table tbody").html(tr);
                }

            }
        });
    }

    function openRepair(repairId) {
        $.ajax({
            userUserType: "GET",
            contentType: 'application/json',
            url: "/repair/" + repairId,
            columnType: 'json',
            mimeType: 'application/json',
        }).done(function (data) {

            jQuery("#repairDialog").html("<div class=\"col-md-6\" style=\"margin: 8px;\">\n\
<p class=\"form-control-static\"> <b>Номер заявки: </b></p>\n\
<p class=\"form-control-static\"> <b>Цех: </b></p>\n\
<p class=\"form-control-static\"> <b>Оборудование: </b></p>\n\
<p class=\"form-control-static\"> <b>Тип ремонта: </b></p>\n\
<p class=\"form-control-static\"> <b>Дата заявки:</b></p>\n\
<p class=\"form-control-static\"> <b>Ответственный:</b></p>\n\
<p class=\"form-control-static\"> <b>Описание:</b></p>\n\
<p class=\"form-control-static\"> <b>Статус:</b></p>\n\
</div>\n\
\n\
<div id = \"info\" class=\"col-md-5\" style=\"margin: 8px;\" align=\"left\">\n\
<p class=\"form-control-static\" id=\"repair_sheet_id\"></p>\n\
\n\
<p class=\"form-control-static\" id=\"subdivision_id\"></p>\n\
\n\
<p class=\"form-control-static\" id=\"equipment_id\"></p>\n\
\n\
<p class=\"form-control-static\" id=\"type_of_maintenance_id\"></p>\n\
\n\
<p class=\"form-control-static\" id=\"start_date\"></p>\n\
\n\
<p class=\"form-control-static\" id=\"responsible_for_delivery\"></p>\n\
\n\
<p class=\"form-control-static\" id=\"repair_title\"></p>\n\
\n\
<p class=\"form-control-static\" id=\"status\"></p></div>");

            var date = new Date(data.start_date);
            year = date.getFullYear();
            month = date.getMonth() + 1;
            day = date.getDate();
            if (day < 10) {
                day = "0" + day;
            }
            if (month < 10) {
                month = "0" + month;
            }
            var dtade = day + "-" + month + "-" + date.getFullYear();
            desc = data.description;
            repair_id = data.repair_sheet_id;
            status = data.status.status_id;
            start_date = dtade;
            toir_type = data.type_of_maintenance.type_of_maintenance_name;
            toir_id = data.type_of_maintenance.type_of_maintenance_id;
            jQuery("#repair_sheet_id").text(data.sheet_number);
            jQuery("#subdivision_id").text(data.subdivision.subdivision_name);
            jQuery("#equipment_id").text(data.equipment.equipmentName);
            jQuery("#type_of_maintenance_id").text(data.type_of_maintenance.type_of_maintenance_name);
            jQuery("#start_date").text(dtade);
            jQuery("#responsible_for_delivery").text(data.responsibleForDelivery.last_name);
            jQuery("#repair_title").text(desc);
            jQuery("#status").text(data.status.status);

            <security:authorize access="hasRole('ROLE_REPAIR')">
            if (status == 1) {
                jQuery("#repairDialog").append("</br><center><div><p><b>Дата начала работ:</b></p><input id=\"datepicker3\" readonly=\"true\"  tabindex=\"-1\" /><p id =\"errordate\" style=\"color: red\"></p>\n\
<p><b>Комментарий:</b></p><input userUserType=\"text\" id=\"reason\"/><p id =\"errorreason\" style=\"color: red\"></p></br><button onclick=\"confirmRepair(1);\">Перевести в обработку</button>" +
                    "<button onclick=\"rejectRepair(1);\">Отклонить</button></div></center>");
            }

            if (status == 3) {

                jQuery("#repairDialog").append("</br><center><div><p><b>Дата окончания работ:</b></p><input id=\"datepicker4\" readonly=\"true\"  tabindex=\"-1\" /><p id =\"errordate2\" style=\"color: red\"></p>\n\
</br><button onclick=\"confirmRepair(3);\">Заявка выполнена</button>");
            }
            </security:authorize>

            $.datepicker.setDefaults(
                $.extend(
                    {'dateFormat': 'yy-mm-dd'},
                    $.datepicker.regional['ru']
                )
            );
            $("#datepicker3").datepicker({dateFormat: 'yy-mm-dd', minDate: new Date(year, month - 1, day)});
            $("#datepicker4").datepicker({dateFormat: 'yy-mm-dd', minDate: new Date(year, month - 1, day)});

            <security:authorize access="hasRole('ROLE_ADMIN')">
            if (status == 2) {
                jQuery("#repairDialog").append("<center><div style=\"align-content: center;\"><p style=\"align-content: center;\"> <b>Комментарий:</b></p><input id=\"manager_comment\"/>\n\
<p id =\"manager_error\" style=\"color: red\"></p></br><button onclick=\"confirmRepair(2);\">Подтвердить</button><spacer width=\"100\" userUserType=\"block\">" +
                    "<button onclick=\"rejectRepair(2);\">Отправить на пересмотр</button></div></center>");
            }
            if (status == 3 && (toir_id == 3 || toir_id == 4 || toir_id == 5)) {
                jQuery("#repairDialog").append("<center><div style=\"align-content: center;\"><button onclick=\"generateActIn();\">Сформировать акт передачи</button></div></center>");
            }
            if (status == 5 && (toir_id == 3 || toir_id == 4 || toir_id == 5)) {
                jQuery("#repairDialog").append("<center><div style=\"align-content: center;\"><button onclick=\"generateActOut();\">Сформировать акт приемки</button></div></center>");
            }
            </security:authorize>

            jQuery("#repairDialog").dialog({
                title: "Заявка №" + data.sheet_number,
                width: 650,
                height: 570,
                resizable: false,
                modal: true,
                buttons: [
                    {
                        text: 'Закрыть',
                        click: function () {
                            jQuery("#repairDialog").dialog('close');
                        }
                    }
                ]
            });
        });
    }

    function confirmRepair(statusId) {
        if (statusId == 1) {
            $("#errordate").text("");
            $("#errorreason").text("");

            if ($("#datepicker3").val() == "") {
                $("#errordate").text("Укажите дату");
            }
            else {
                var date = $("#datepicker3").val();
                var description = $("#reason").val();
                var status = 2;
                var that = this;
                $.ajax({
                    userUserType: "POST",
                    url: "/repair/" + repair_id,
                    content: "application/json",
                    columnType: "json",
                    data: {
                        date: date,
                        status: status,
                        description: description
                    },
                    success: function (returnData) {
                        alert("Заявка успешно обработана");
                        $(that).dialog("close");
                    }
                });
                refreshContent();
            }
        }
        else if (statusId == 2) {
            $("#manager_error").text("");

            var description = $("#manager_comment").val();
            var status = 3;
            var that = this;
            $.ajax({
                userUserType: "POST",
                url: "/repair/confirm/" + repair_id,
                content: "application/json",
                columnType: "json",
                data: {
                    status: status,
                    description: description
                },
                success: function (returnData) {
                    alert("Заявка подтверждена");
                    $(that).dialog("close");
                }
            });
            refreshContent();
        }
        else if (statusId == 3) {

            $("#errordate2").text("");
            if ($("#datepicker4").val() == "") {
                $("#errordate2").text("Укажите дату");
            }
            else {
                var date = $("#datepicker4").val();
                var description = "";
                var status = 4;
                var that = this;
                $.ajax({
                    userUserType: "POST",
                    url: "/repair/" + repair_id,
                    content: "application/json",
                    columnType: "json",
                    data: {
                        date: date,
                        status: status,
                        description: description
                    },
                    success: function (returnData) {
                        alert("Заявка выполнена");
                        $(that).dialog("close");
                    }
                });
                refreshContent();
            }
        }
    }

    function rejectRepair(statusId) {

        if (statusId == 1) {
            $("#errordate").text("");
            $("#errorreason").text("");

            if ($("#datepicker3").val() == "") {
                $("#errordate").text("Укажите дату");
            }
            else if ($("#reason").val() == "") {
                $("#errorreason").text("Укажите описание");
            }
            else {
                var date = $("#datepicker3").val();
                var description = $("#reason").val();
                var status = 5;
                var that = this;
                $.ajax({
                    userUserType: "POST",
                    url: "/repair/" + repair_id,
                    content: "application/json",
                    columnType: "json",
                    data: {
                        date: date,
                        status: status,
                        description: description
                    },
                    success: function (returnData) {
                        alert("Заявка отклонена");
                        $(that).dialog("close");
                    }
                });
                refreshContent();
            }
        }
        else if (statusId == 2) {
            $("#manager_error").text("");

            if ($("#manager_comment").val() == "") {
                $("#manager_error").text("Укажите причину");
            }
            else {
                var description = $("#manager_comment").val();
                var status = 1;
                var that = this;
                $.ajax({
                    userUserType: "POST",
                    url: "/repair/confirm/" + repair_id,
                    content: "application/json",
                    columnType: "json",
                    data: {
                        status: status,
                        description: description
                    },
                    success: function (returnData) {
                        alert("Заявка отправлена на пересмотр");
                        $(that).dialog("close");
                    }
                });
                refreshContent();
            }
        }
    }

    function generateActIn() {
        $.ajax({
            userUserType: "POST",
            url: "/report/repair/act_in/" + repair_id,
            content: "application/json",
            columnType: "json",
            data: {
                toir_type: toir_type
            },
            success: function (returnData) {
                alert("Акт сформирован");
                $(that).dialog("close");
            }
        });
    }
    function generateActOut() {
        $.ajax({
            userUserType: "POST",
            url: "/report/repair/act_out/" + repair_id,
            content: "application/json",
            columnType: "json",
            data: {
                toir_type: toir_type
            },
            success: function (returnData) {
                alert("Акт сформирован");
                $(that).dialog("close");
            }
        });
    }
    function refreshContent() {
        history.go(0);
    }

    function addRepair() {
        $("#error1").text("");
        $("#error2").text("");
        $("#error3").text("");
        $("#error4").text("");
        $("#error5").text("");
        $("#error6").text("");
        $("#error10").text("");

        if ($("#equipments").val() == "0") {
            $("#error2").text("Укажите оборудование");
        }

        else if ($("#datepicker1").val() == "") {
            $("#error5").text("Укажите дату");
        }
        else if ($("#description").val() == "") {
            $("#error6").text("Укажите описание");
        }
        else {
            var subdivision_id = $("#subdivisions").val();
            var equipment_id = $("#equipments").val();
            var components = $("#components").val();
            var type_of_maintenance_id = $("#maintenanceType").val();
            var start_date = $("#datepicker1").val();
            var description = $("#description").val();

            var that = this;
            $.ajax({
                userUserType: "POST",
                url: "/repair/add",
                content: "application/json",
                columnType: "json",
                data: {
                    subdivision_id: subdivision_id,
                    equipment_id: equipment_id,
                    components: components,
                    type_of_maintenance_id: type_of_maintenance_id,
                    start_date: start_date,
                    description: description
                },
                success: function (returnData) {
                    $(that).dialog("close");
                }
            });
            refreshContent();
        }
    }
</script>
