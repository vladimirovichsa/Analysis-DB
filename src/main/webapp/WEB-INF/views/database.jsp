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

    .warningg {
        background: #ffc628;
    }

    .critical {
        background: #ff7667;
    }

    .checkboxh {
        float: left;
        display: block;
    }

    .glyphicon-refresh-animate {
        -animation: spin .7s infinite linear;
        -webkit-animation: spin2 .7s infinite linear;
    }

    .atomicity {
        background: #4ba9ff;
    }

    .tooltipp {
        position: fixed;
        padding: 10px 20px;
        /* красивости... */

        border: 1px solid #b3c9ce;
        border-radius: 4px;
        text-align: center;
        font: italic 14px/1.3 arial, sans-serif;
        color: #333;
        background: #fff;
        box-shadow: 3px 3px 3px rgba(0, 0, 0, .3);
    }
</style>
<div id="table-content">
    <h2>Выберите таблицу для отображения данных</h2>
</div>
<div id="analize-table-dialog" style="overflow-x: auto;">
    <div id="analize-table-content"></div>
    <div id="create-table-content" style="display: none">
        <div class="form-group row">
            <label for="table-name" class="col-sm-3 col-form-label" id="label-table-name">Название таблицы</label>
            <div class="col-sm-8" id="input-table-name">
                <input type="email" class="form-control" id="table-name" placeholder="Введите имя таблицы">
            </div>
        </div>
        <table class="table table-hover">
            <thead>

            </thead>
            <tbody>

            </tbody>
        </table>
        <div class="row">
            <div class="col-md-5">
                <input class="btn btn-success" style="align-content: center" onclick="createAndGenerateTable();"
                       type="button"
                       value="<spring:message text="OK"/>"/>
            </div>
            <div class="col-md-5">
                <input class="btn btn-warning" style="align-content: center" type="button"
                       onclick="jQuery('#create-table-content').dialog('close');"
                       value="<spring:message text="Отмена" />"/>
            </div>
        </div>
    </div>
    <div id="result-table-analize" style="display: none">
        <div class="form-group row">
            <label for="table-name" class="col-sm-3 col-form-label">Результат анализа</label>
        </div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Операция</th>
                <th>Результат</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
    <div id="res-create-table-content" style="display: none">
        <div class="form-group row">
            <label for="table-name" class="col-sm-3 col-form-label">Результат выполнения операций</label>
        </div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Операция</th>
                <th>Результат</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Создание новой таблицы</td>
                <td id="create_table"><span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span></td>
            </tr>
            <tr>
                <td>Копирование данных</td>
                <td id="copy_data_table"><span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span>
                </td>
            </tr>
            <tr>
                <td>Удаление старых данных</td>
                <td id="delete_old_data_table"><span
                        class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>


<script>
    var relationTableStatic;
    var availableTags = [];
    var oldAttribyte;


    $(document).ready(function () {
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
        var checkedAutoInc = function () {
            $('input[name="' + $(this).attr('name') + '"]').removeAttr('checked');
            $(this).prop('checked', true);
        };

        $("body").on("click", ".auto-inc", checkedAutoInc);
        $("body").on("click", ".column-type", function () {
            $('.column-type').keyup(function () {
                var value = $('.column-type').val();
                var par_pattern = /[a-z]+\([0-9]+\)/i;
                var prov = par_pattern.test(value);
                if (!prov) {
                    $(this).css('background-color', 'red');
                } else {
                    $(this).css('background-color', 'white');
                }

            });
        });

        var showingTooltip;

        document.onmouseover = function (e) {
            var target = e.target;

            var tooltip = target.getAttribute('data-tooltip');
            if (!tooltip) return;

            var tooltipElem = document.createElement('div');
            tooltipElem.className = 'tooltipp';
            tooltipElem.innerHTML = tooltip;
            document.body.appendChild(tooltipElem);

            var coords = target.getBoundingClientRect();

            var left = coords.left + (target.offsetWidth - tooltipElem.offsetWidth) / 2;
            if (left < 0) left = 0; // не вылезать за левую границу окна

            var top = coords.top - tooltipElem.offsetHeight - 5;
            if (top < 0) { // не вылезать за верхнюю границу окна
                top = coords.top + target.offsetHeight + 5;
            }

            tooltipElem.style.left = left + 'px';
            tooltipElem.style.top = top + 'px';

            showingTooltip = tooltipElem;
        };

        document.onmouseout = function (e) {

            if (showingTooltip) {
                document.body.removeChild(showingTooltip);
                showingTooltip = null;
            }

        };
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
            var table = "<table class=\"table table-hover\"><thead><tr></tr></thead>\
                <tbody>\
                </tbody>\
                </table>";
            var th;
            if (data.attributes.length > 0) {
                var appendButton = '<button disabled id="apply" class="btn btn-primary" onclick="createTable();">Применить</button>';
                var atomicity = false;
                var primaryKey = false;
                var foreignKey = false;
                var uniqueData = false;
                var tr;
                $('#table-content div').append(appendButton);
                $("#table-table").html(table);
                if (data.data.length > 0) {
                    for (var i = 0; i < data.data.length; i++) {
                        tr += "<tr>";
                        for (var j = 0; j < data.data[i].length; j++) {
                            var style = "";
                            var dataTooltip = "";
                            if (data.attributes[j].isPrimaryKey == true) {
                                primaryKey = true;
                            }
                            if (data.attributes[j].isForeignKey == true) {
                                foreignKey = true;
                            }
                            if (data.attributes[j].atomicity == true) {
                                atomicity = true;
                                for (var k = 0; k < data.attributes[j].atomicityObject.row.length; k++) {
                                    if (data.attributes[j].atomicityObject.row[k] == i) {
                                        style = "atomicity";
                                    }
                                }
                                dataTooltip = "В атрибуте \"" + data.attributes[j].name + "\" </br> имеются не атомарные значения! </br>Рекомендуется исправить этот конфликт, </br>что бы текущее отношение было приведено к нормальной форме №1!";
                            } else if (data.attributes[j].normal == true) {
                                style = "";
                            } else if (data.attributes[j].notice == true) {
                                style = "notice";
                                dataTooltip = "В атрибуте \"" + data.attributes[j].name + "\" </br> меньше 85% уникальных значений, </br> можно не вынесить в отдельную таблицу!";
                            } else if (data.attributes[j].warning == true) {
                                uniqueData = true;
                                style = "warningg";
                                dataTooltip = "В атрибуте \"" + data.attributes[j].name + "\" </br> меньше 30% уникальных значений, </br>желательно вынести в отдельную таблицу!";
                            } else if (data.attributes[j].critical == true) {
                                style = "critical";
                                uniqueData = true;
                                dataTooltip = "В атрибуте \"" + data.attributes[j].name + "\" </br>меньше 15% уникальных значений, </br>настоятельно рекомендуется </br>вынести в отдельную таблицу!";
                            }
                            tr += "<td class='" + style + "' data-tooltip = '" + dataTooltip + "'>" + data.data[i][j] + "</td>";
                        }
                        tr += "</tr>";

                    }
                }
                for (var i = 0; i < data.attributes.length; i++) {
                    var checkBoxDisable = "";
                    if (data.attributes[i].isForeignKey == true || data.attributes[i].isPrimaryKey == true) {
                        checkBoxDisable = "disabled";
                    }
                    var atm = atomicity ? "atm" : "";
                    th += "<th>" +
                        "<input class='checkboxh " + atm + "' type='checkbox' value='" + i + "' " + checkBoxDisable + ">" +
                        "<span style='padding-left: 20px; display: block;width:auto;'> " + data.attributes[i].name + " " +
                        "</span>" +
                        "</th>";

                }
                if (th != null) {
                    $("#table-table table thead tr").html(th);
                }
                if (tr != null) {
                    $("#table-table table tbody").html(tr);
                }
                jQuery("#result-table-analize").dialog({
                    width: 500,
                    height: 400,
                    modal: true,
                    position: {my: "center top", at: "center top", of: window},
                    resizable: false,
                    buttons: [
                        {
                            text: "Ok",
                            icon: "ui-icon-heart",
                            click: function () {
                                $(this).dialog("close");
                            }
                        }
                    ]
                });
                var trAnalize;
                var nf;
                if (atomicity) {
                    nf = "Отсутствует";
                } else {
                    nf = "Нормальная форма №1";
                    if (primaryKey) {
                        nf = "Нормальная форма №2";
                        if (foreignKey) {
                            nf = "Нормальная форма #3";
                        }
                    } else {

                    }
                }
                trAnalize += "<tr><td>Нормальная форма оношения </td><td>" + nf + "</td></tr>";
                trAnalize += atomicity ? "<tr><td>Поиск не атомарных данных </td><td> Найдено</td></tr>" : "<tr><td>Поиск не атомарных данных </td><td>Не найдено</td></tr>";
                trAnalize += primaryKey ? "<tr><td>Поиск первичного ключа </td><td> Найдено</td></tr>" : "<tr><td>Поиск первичного ключа </td><td>Не найдено</td></tr>";
                trAnalize += foreignKey ? "<tr><td>Поиск внешнего ключа </td><td> Найдено</td></tr>" : "<tr><td>Поиск внешнего ключа </td><td>Не найдено</td></tr>";
                trAnalize += uniqueData ? "<tr><td>Поиск не уникальных данных </td><td> Найдено</td></tr>" : "<tr><td>Поиск уникальности данных </td><td>Не найдено</td></tr>";
                $("#result-table-analize table tbody").html(trAnalize);
            }
        });
    }

    function resultCreateDataBase(obj) {
        jQuery("#res-create-table-content").dialog({
            title: "Создание таблицы",
            width: 800,
            height: 600,
            modal: true,
            position: {my: "center top", at: "center top", of: window},
            resizable: false
        });

        $.ajax({
                type: "POST",
                url: "/database/createdatabase",
                contentType: 'application/json',
                columnType: "json",
                data: JSON.stringify(obj),
                success: function (data) {
                    if (data.success != null) {
                        $('#create_table').html('<span class="glyphicon glyphicon-ok-circle "></span>');
                        $.ajax({
                            type: "POST",
                            url: "/database/copydata",
                            contentType: 'application/json',
                            columnType: "json",
                            data: JSON.stringify(obj),
                            success: function (data) {
                                if (data.success != null) {
                                    $('#copy_data_table').html('<span class="glyphicon glyphicon-ok-circle "></span>');
                                    $.ajax({
                                        type: "POST",
                                        url: "/database/deletedata",
                                        contentType: 'application/json',
                                        columnType: "json",
                                        data: JSON.stringify(obj),
                                        success: function (data) {
                                            if (data.success != null) {
                                                $('#delete_old_data_table').html('<span class="glyphicon glyphicon-ok-circle "></span>');
                                                alert("Все изменения выполнены!");
                                            } else {
                                                $('#delete_old_data_table').html('<span class="glyphicon glyphicon-ban-circle "></span>');
                                            }
                                        },
                                        error: function (jqXHR, textStatus, errorThrown) {
                                            $('#copy_data_table').html('<span class="glyphicon glyphicon-ban-circle "></span>');
                                            $('#delete_old_data_table').html('<span class="glyphicon glyphicon-ban-circle "></span>');
                                            alert(jqXHR);
                                        }
                                    });
                                } else {
                                    $('#copy_data_table').html('<span class="glyphicon glyphicon-ban-circle "></span>');
                                    $('#delete_old_data_table').html('<span class="glyphicon glyphicon-ban-circle "></span>');
                                }
                            },
                            error: function (jqXHR, textStatus, errorThrown) {
                                $('#copy_data_table').html('<span class="glyphicon glyphicon-ban-circle "></span>');
                                $('#delete_old_data_table').html('<span class="glyphicon glyphicon-ban-circle "></span>');
                                alert(jqXHR);
                            }
                        });
                    } else {
                        $('#create_table').html('<span class="glyphicon glyphicon-ban-circle "></span>');
                        $('#copy_data_table').html('<span class="glyphicon glyphicon-ban-circle "></span>');
                        $('#delete_old_data_table').html('<span class="glyphicon glyphicon-ban-circle "></span>');
                        alert(data.error);
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    $('#create_table').html('<span class="glyphicon glyphicon-ban-circle "></span>');
                    $('#copy_data_table').html('<span class="glyphicon glyphicon-ban-circle "></span>');
                    $('#delete_old_data_table').html('<span class="glyphicon glyphicon-ban-circle "></span>');
                    alert(jqXHR);
                }
            }
        )
        ;
    }

    function createTable() {
        jQuery("#create-table-content").dialog({
            width: 800,
            height: 600,
            modal: true,
            position: {my: "center top", at: "center top", of: window},
            resizable: false
        });
        var checkedColumn = [];
        var atm = false;
        var labelTableName = $('#create-table-content #label-table-name');
        var inputTableName = $('#create-table-content #input-table-name');
        inputTableName.css("display", "none");

        var bodyTable = $('#create-table-content .table tbody');
        var headTable = $('#create-table-content .table thead');
        $("#table-table table input:checked").each(function (index) {
            checkedColumn[index] = this.value;
            if (this.className.split(" ")[1] == "atm") {
                atm = true;
            }
        });
        bodyTable.html('<H1>Обработка информации...</H1>');
        if (atm) {
            if (confirm("Вы выбрали атрибут в котором содержаться не атомарные значения! Хотите убрать этот конфликт?")) {
                labelTableName.html("Результат выполнения");
                headTable.html("<tr><th>Операция</th><th>Результат</th></tr>");
                bodyTable.html('<tr><td>Разделение на атомарные данные</td><td id="atomicity_table_data"><span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span></td></tr>');
                $.ajax({
                    type: "POST",
                    url: "/database/applyAction/" + relationTableStatic,
                    content: "application/json",
                    dataType: "json",
                    data: {
                        columns: checkedColumn
                    },
                    success: function (data) {
                        if (data.success != null) {
                            $('#atomicity_table_data').html('<span class="glyphicon glyphicon-ok-circle "></span>');
                            atm = false;
                            alert("Все изменения выполнены!");
                        } else {
                            $('#atomicity_table_data').html('<span class="glyphicon glyphicon-ban-circle "></span>');
                        }

                    }
                });
            }
        }
        if (!atm && checkedColumn.length > 1) {
            labelTableName.html("Название таблицы");
            inputTableName.css("display","");
            var head = '<tr>\
            <th>Имя</th>\
            <th>Тип</th>\
            <th>Not Null</th>\
        <th>Auto inc</th>\
        <th>Primary key</th>\
        </tr>';
            headTable.html(head);
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
                        oldAttribyte = data.attribute;
                        tr += '<tr>\
                        <td><input class="column-name" type="text" value="id"></td>\
                        <td><input class="column-type" value="INT(11)"></td>\
                        <td><input class="not-null" type="checkbox" checked></td>\
                        <td><input class="auto-inc" name="one-checked" type="checkbox" checked></td>\
                        <td><input class="primary-key" type="checkbox" checked></td>\
                        </tr>';
                        for (var i = 0; i < data.attribute.length; i++) {
                            tr += '<tr>\
                        <td><input class="column-name" type="text" value="' + data.attribute[i].name + '"></td>\
                        <td><input class="column-type" value="' + data.attribute[i].constraints + '"></td>\
                        <td><input class="not-null" type="checkbox" ' + ((data.attribute[i].isNullable == 0) ? 'checked' : '' ) + ' ></td>\
                        <td><input class="auto-inc" name="one-checked" type="checkbox" ' + ((data.attribute[i].autoIncrement == true ) ? 'checked' : '' ) + '></td>\
                        <td><input class="primary-key" type="checkbox" ' + ((data.attribute[i].isPrimaryKey == true ) ? 'checked' : '' ) + ' ></td>\
                        </tr>';
                        }
                    } else {
                        tr += '<tr>\
                        <td><input class="column-name" type="text" value=""></td>\
                        <td><input class="column-type"></td>\
                        <td><input class="not-null" type="checkbox" ></td>\
                        <td><input class="auto-inc" name="one-checked" type="checkbox"></td>\
                        <td><input class="primary-key" type="checkbox"></td>\
                        </tr>';
                    }

                    bodyTable.html('');
                    $('#create-table-content .table').append(tr);
                }
            });
        }
    }

    function createAndGenerateTable() {
        var newTableName = $('#table-name').val();
        var obj = {};
        var columns = [];
        $('#create-table-content .table tbody tr').each(function (index) {
            var column = {};
            column.name = this.cells[0].firstChild.value;
            column.columnTypeName = this.cells[1].firstChild.value;
            column.isNullable = this.cells[2].firstChild.checked == true ? 0 : 1;
            column.autoIncrement = this.cells[3].firstChild.checked;
            column.isPrimaryKey = this.cells[4].firstChild.checked;
            columns[index] = column;
        });
        obj.tableName = newTableName;
        obj.tableNameOld = relationTableStatic;
        obj.listAttribute = columns;
        obj.listOldAttribute = oldAttribyte;
        $('#create-table-content').dialog("close");
        resultCreateDataBase(obj);

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
