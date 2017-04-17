<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>


<div class="content-box-large">


    <%--<div class="panel-heading">--%>
        <%--<div class="panel-title">Заявки</div>--%>
        <%--<security:authorize access="hasRole('ROLE_ADMIN')">--%>
            <%--</br>--%>
            <%--<button class="btn btn-info">Новая заявка</button>--%>
        <%--</security:authorize>--%>
    <%--</div>--%>

    <%--<div id="new_repair" title="Добавление новой заявки" style="display: none">--%>


        <%--<table>--%>
            <%--<tr>--%>
                <%--<td><label><spring:message text="Цех: "/></label></td>--%>
                <%--<td>--%>
                        <%--&lt;%&ndash;<select class="form-control" path="subdivision.subdivision_id" name="subdivisions" id="subdivisions">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<c:forEach items="" var="subdivisions">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<option value=""></option>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</select>&ndash;%&gt;--%>
                    <%--<p id="error1" style="color: red"></p>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td><br/></td>--%>
                <%--<td><br/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td><label><spring:message text="Оборудование: "/></label></td>--%>
                <%--<td>--%>
                        <%--&lt;%&ndash;<select class="form-control" path="equipment.equipmentId" name="equipments" id="equipments" disabled="disabled">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<option value="0">- Выберите оборудование -</option>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</select>&ndash;%&gt;--%>
                    <%--<p id="error2" style="color: red"></p>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td><br/></td>--%>
                <%--<td><br/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td><label><spring:message text="Комплектующие: "/></label></td>--%>
                <%--<td>--%>
                        <%--&lt;%&ndash;<select class="form-control" path="component.componentId" name="components" id="components" disabled="disabled">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<c:forEach items="${components}" var="components">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<option value="${components.componentId}">${components.spare.spare_name}</option>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</select>&ndash;%&gt;--%>
                    <%--<p id="error10" style="color: red"></p>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td><br/></td>--%>
                <%--<td><br/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td><label><spring:message text="Вид ремонта"/></label></td>--%>
                <%--<td>--%>
                        <%--&lt;%&ndash;<select class="form-control" path="type_of_maintenance.type_of_maintenance_id" name="maintenanceType" id="maintenanceType">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<c:forEach items="${listTypeOfMaintenance}" var="maintenance">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<option value="${maintenance.type_of_maintenance_id}">${maintenance.type_of_maintenance_name}</option>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</select>&ndash;%&gt;--%>
                    <%--<p id="error4" style="color: red"></p>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td><br/></td>--%>
                <%--<td><br/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td><label><spring:message text="Дата"/></label></td>--%>
                <%--<td>--%>
                    <%--<p id="error5" style="color: red"></p>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td><br/></td>--%>
                <%--<td><br/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td><label><spring:message text="Описание"/></label></td>--%>
                <%--<td>--%>
                    <%--<p id="error6" style="color: red"></p>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td><br/></td>--%>
                <%--<td><br/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<div style="align-content: center;">--%>
                    <%--<center>--%>
                        <%--<td colspan="2">--%>
                            <%--<input type="button"--%>
                                   <%--value="<spring:message text="Добавить"/>" onclick=""/>--%>
                            <%--<input type="button"--%>
                                   <%--value="<spring:message text="Закрыть"/>" onclick=""/>--%>
                    <%--</center>--%>
                <%--</div>--%>
                <%--</td>--%>
            <%--</tr>--%>
        <%--</table>--%>

    <%--</div>--%>
    <%--<div class="panel-body" id="allrepair">--%>
        <%--<div class="progress">--%>
            <%--<div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow=""--%>
                 <%--aria-valuemin="0" aria-valuemax="100" style="">--%>
                <%--<span>Выполненные()</span>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="progress">--%>
            <%--<div class="progress-bar progress-bar-info progress-bar-striped" role="progressbar" aria-valuenow=""--%>
                 <%--aria-valuemin="0" aria-valuemax="100" style="">--%>
                <%--<span>Новые()</span>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="progress">--%>
            <%--<div class="progress-bar progress-bar-warning progress-bar-striped" role="progressbar" aria-valuenow=""--%>
                 <%--aria-valuemin="0" aria-valuemax="100" style="">--%>
                <%--<span>В обработке()</span>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="progress">--%>
            <%--<div class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" aria-valuenow=""--%>
                 <%--aria-valuemin="0" aria-valuemax="100" style="">--%>
                <%--<span>Отмененные()</span>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>

</div>
<%--<script src="js/vendor/jquery-1.9.1.min.js"></script>--%>
<%--<script src="js/vendor/jquery-1.9.1.min.js"></script>--%>
<%--<script src="js/vendor/bootstrap.min.js"></script>--%>
<%--<script src="js/main.js"></script>--%>

<%--<script src="js/jquery.ba-cond.min.js"></script>--%>
<%--<script src="js/jquery.slitslider.js"></script>--%>