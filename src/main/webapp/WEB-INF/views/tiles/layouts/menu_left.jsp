<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>


<div class="panel-group" id="accordion" style="overflow-x: scroll;">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                Менеджер таблиц
            </h4>
        </div>
    </div>
    <c:if test="${! empty database.getDatabase()}">
        <c:forEach items="${database.getDatabase()}" var="table">
            <div class="panel panel-success">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion"
                           href="#collapse${table.getName()}"
                           onclick="viewTable('${table.getName()}',this);">${table.getName()}</a>
                    </h4>
                </div>
                <c:if test="${! empty table.getAttributes()}">
                    <div id="collapse${table.getName()}" class="panel-collapse collapse">
                        <div class="panel-body">
                            <table class="table">
                                <c:forEach items="${table.getAttributes()}" var="attribute">
                                    <tr>
                                        <td>
                                            <a href="#">${attribute.getName()}|${attribute.getConstraints()}</span></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </c:if>
            </div>
        </c:forEach>

    </c:if>
</div>

<script>
    function viewTable(relationTable, object) {
        $.ajax({
            type: "GET",
            contentType: 'application/json',
            url: "/database/table/" + relationTable,
            columnType: 'json',
        }).done(function (data) {
            if (data.attributes.length > 0) {
                var div = '<div><button class="btn btn-primary" onclick="openResultAnalize(\''+relationTable+'\')">Анализировать</button></div>';
                var table = div + "<table class=\"table table-hover\"><thead><tr></tr></thead>\
                <tbody>\
                </tbody>\
                </table>";
                var th;
                var tr;
                $("#table-content").html(table);
                for (var i = 0; i < data.attributes.length; i++) {
                    th += "<th><a href=\"#\"><span> " + data.attributes[i].name + "</span></a></th>";
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
                        for (var i = 0; i < data.data.length; i++) {
                            tr += "<tr>";
                            for (var j = 0; j < data.data[i].length; j++) {
                                tr += "<td>" + data.data[i][j] + "</td>"
                            }
                            tr += "</tr>";
                        }

                    }
                }
                if (th != null) {
                    $("#table-content table thead tr").html(th);
                }
                if (tr != null) {
                    $("#table-content table tbody").html(tr);
                }

            }
        });
    }

</script>