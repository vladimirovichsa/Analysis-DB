<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>



  <script>
    $(document).ready(function() {
      $("#datepicker1").datepicker({ dateFormat: 'yy-mm-dd' });
      $("#datepicker2").datepicker({ dateFormat: 'yy-mm-dd' });

    });
  </script>

  <style>
    .error {
      color: red; font-weight: bold;
    }
  </style>

      <div id="result">
    <h3>Список подключений</h3>
    <c:if  test="${!empty connectionList}">
      <table class="data">
        <tr>
          <th>Имя</th>
          <th>Описание</th>
          <th>&nbsp;</th>
        </tr>
        <c:forEach items="${connectionList}" var="connection">
          <tr>
            <td width="100px">${document.document_name}</td>
            <td width="250px">${document.description}</td>
            <td width="20px">
              <span class="glyphicon glyphicon-plus" id="openDoc"></span>

              <a href="/documents/remove/${document.document_id}"
                 onclick="return confirm('Вы действительно хотите удалить текущий документ?')">
                <span class="glyphicon glyphicon-minus"></span></a>
            </td>
          </tr>
        </c:forEach>
      </table>
    </c:if>

    </br>
      <h3>Добавить новый документ</h3>
      <form:form method="post" action="/documents/save" commandName="document" enctype="multipart/form-data">
        <form:errors path="*" cssClass="error"/>
        <table>
          <tr>
            <td><form:label path="document_name">Наименование</form:label></td>
            <td><form:input path="document_name" /></td>
          </tr>
          <tr>
            <td><form:label path="description">Описание</form:label></td>
            <td><form:textarea path="description" /></td>
          </tr>
          <tr>
            <td><form:label path="content">Document</form:label></td>
            <td><input userUserType="file" name="file" id="file"></input></td>
          </tr>
          <tr>
            <td><input userUserType="hidden" name="equipment" path="equipment" value="${cu}">
          </tr>
          <tr>
            <td colspan="2">
              <input userUserType="submit" value="Добавить документ"/>
            </td>
          </tr>
        </table>
      </form:form>

      <br/>
    </div>