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

    .checkboxh {
        float: left;
        display: block;
    }
</style>
<div id="table-content">
    <h2>Выберите таблицу для отображения данных</h2>
</div>
<div id="analize-table-dialog" style="overflow-x: auto;">
    <div id="analize-table-content"></div>
    <div id="create-table-content">
        <div class="form-group row">
            <label for="table-name" class="col-sm-3 col-form-label">Название таблицы</label>
            <div class="col-sm-8">
                <input type="email" class="form-control" id="table-name" placeholder="Введите имя таблицы">
            </div>
        </div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Имя</th>
                <th>Тип</th>
                <th>Not Null</th>
                <th>Auto inc</th>
                <th>Primary key</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>

</div>


<script>
    var relationTableStatic;
    var availableTags = [];

    $(function () {
        $('#analize-table-content').show();

        var countChecked = function () {
            var n = $("#table-table table input:checked").length;
            var disBoll = true;
            if (n != 0) {
                disBoll = false;
            }
            $("#apply").prop('disabled', disBoll);
        };
        countChecked();

        $("#table-content").on("click", ".checkboxh", countChecked);
        $("#column-type").autocomplete({
            source: availableTags
        });

    });

    function openResultAnalize(relationTable) {
        relationTableStatic = relationTable;
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
                    var checkBoxDisable = "";
                    if (data.attributes[i].isForeignKey == true || data.attributes[i].isPrimaryKey == true) {
                        checkBoxDisable = "disabled";
                    }
                    th += "<th>" +
                        "<input class='checkboxh' type='checkbox' value='" + i + "' " + checkBoxDisable + ">" +
                        "<span style='padding-left: 20px; display: block;width:auto;'> " + data.attributes[i].name + " " +
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
            position: {my: "center top", at: "center top", of: window},
            resizable: false,
            buttons: [
                {
                    text: "Далее",
                    icons: {
                        primary: "ui-icon-next"
                    },
                    click: function () {
                        var newTableNmae = $('#table-name').val();
                        var attribute = function () {
                            $('#create-table-content .table tr').each(function (index) {
                                this.ch
                            })
                        }
                    }
                }
            ]
        });
        var checkedColumn = [];
        var bodyTable = $('#create-table-content .table tbody');
        $("#table-table table input:checked").each(function (index) {
            checkedColumn[index] = this.value;
        });
        bodyTable.html('<H1>загрузка...</H1>');
        $.ajax({
            type: "POST",
            url: "/database/getColumnByTableName/" + relationTableStatic,
            content: "application/json",
            dataType: "json",
            data: {
                columns: checkedColumn
            },
            success: function (data) {
                var tr;
                $('#table-name').val('table_name_default');
                if (data.columntype.length > 0) {
                    availableTags = data.columntype;
                }
                if (data.attribute.length > 0) {
                    tr += '<tr>\
                        <td><input class="column-name" type="text" value="id"></td>\
                        <td><input class="column-type" value="INIT(11)"></td>\
                        <td><input class="not-null" type="checkbox" checked></td>\
                        <td><input class="auto-inc" type="checkbox" checked></td>\
                        <td><input class="primary-key" type="checkbox" checked></td>\
                        </tr>';
                    for (var i = 0; i < data.attribute.length; i++) {
                        tr += '<tr>\
                        <td><input class="column-name" type="text" value="' + data.attribute[i].name + '"></td>\
                        <td><input class="column-type" value="' + data.attribute[i].constraints + '"></td>\
                        <td><input class="not-null" type="checkbox" ' + ((data.attribute[i].isNullable == 0) ? 'checked' : '' ) + ' ></td>\
                        <td><input class="auto-inc" type="checkbox" ' + ((data.attribute[i].autoIncrement == true ) ? 'checked' : '' ) + '></td>\
                        <td><input class="primary-key" type="checkbox" ' + ((data.attribute[i].isPrimaryKey == true ) ? 'checked' : '' ) + ' ></td>\
                        </tr>';
                    }
                } else {
                    tr += '<tr>\
                        <td><input class="column-name" type="text" value=""></td>\
                        <td><input class="column-type"></td>\
                        <td><input class="not-null" type="checkbox" ></td>\
                        <td><input class="auto-inc" type="checkbox"></td>\
                        <td><input class="primary-key" type="checkbox"></td>\
                        </tr>';
                }
                bodyTable.html('');
                $('#create-table-content .table').append(tr);
            }
        });

    }

    function createAndGenerateTable(oldTableName, newTableName) {
        $.ajax({
            type: "POST",
            url: "/repair/" + repair_id,
            content: "application/json",
            dataType: "json",
            data: {
                tableName: date,
                rows: status
            },
            success: function (returnData) {
                alert("Заявка успешно обработана");
                $(that).dialog("close");
            }
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

    function addRowInTableForAddRows() {
        var rowTableForAddRows = '<tr id="add-row-table">' +
            '<td><input id="column-name" type="text"></td>' +
            '<td><select id="column-type" class="form-control form-control-sm">' +
            '<option>Small select</option>' +
            '</select></td>' +
            '<td><input id="not-null" type="checkbox"></td>'
        '<td><input id="auto-inc" type="checkbox"></td>'
        '<td><input id="unique" type="checkbox"></td>'
        '<td><input id="primary-key" type="checkbox"></td>' +
        '</tr>';
        $('#create-table-content .table').append(rowTableForAddRows);
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
