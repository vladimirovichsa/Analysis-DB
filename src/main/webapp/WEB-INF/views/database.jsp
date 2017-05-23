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

    .checkboxh{
        float: left;
        display: block;
    }
</style>
<div id="table-content" >
    <h2>Выберите таблицу для отображения данных</h2>
</div>
<div id="analize-table-dialog" style="overflow-x: auto;">
    <div id="analize-table-content" ></div>
    <div id="create-table-content" ></div>
</div>


<script>

    $(document).ready(function() {
        $( '#analize-table-content' ).show();
        $( '#create-table-content' ).hide();
        var countChecked = function() {
            var n = $("#table-table table input:checked").length;
            var disBoll = true;
            if(n != 0){
                disBoll = false;
            }
            $("#apply").prop('disabled',disBoll);
        };
        countChecked();

        $("#table-content").on("click",".checkboxh", countChecked );
    });

    function openResultAnalize(relationTable) {

        $('#table-table').html('<H1>загрузка...</H1>');
        $.ajax({
            userUserType: "GET",
            contentType: 'application/json',
            url: '/database/table/analysis/' + relationTable,
            columnType: 'json',
        }).done(function (data) {
            if (data.attributes.length > 0) {
                var appendButton = '<button disabled id="apply" class="btn btn-primary" onclick="createTable();">Применить</button>';
                var table = "<table class=\"table table-hover\"><thead><tr></tr></thead>\
                <tbody>\
                </tbody>\
                </table>";
                var th;
                var tr;
                $('#table-content div').append(appendButton);
                $("#table-table").html(table);
                for (var i = 0; i < data.attributes.length; i++) {
                    var checkBoxDisable = "" ;
                    if(data.attributes[i].isForeignKey == true || data.attributes[i].isPrimaryKey == true){
                        checkBoxDisable = "disabled";
                    }
                    th += "<th>" +
                        "<input class='checkboxh' type='checkbox' value='"+i+"' "+checkBoxDisable+">" +
                        "<span style='padding-left: 20px; display: block;width:auto;'> " + data.attributes[i].name +" "+
                        "</span>" +
                        "</th>";

                }
                if (data.data.length > 0) {
                    for (var i = 0; i < data.data.length; i++) {
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
                    $("#table-table table thead tr").html(th);
                }
                if (tr != null) {
                    $("#table-table table tbody").html(tr);
                }
            }
        });
    }

    function createTable() {
        jQuery("#create-table-content").dialog({
            title: "Создание таблицы",
            width: 800,
            height: 600,
            modal: true,
            position : { my: "center top", at: "center top", of: window },
            resizable: false,
            buttons: [
                {
                    text: "Далее",
                    icons: {
                        primary: "ui-icon-next"
                    },
                    click: function() {

                    }
                }
            ]
        });
        $.ajax({
            userUserType: "GET",
            contentType: 'application/json',
            url: "/repair/" + repairId,
            columnType: 'json',
            mimeType: 'application/json',
        }).done(function (data) {

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
