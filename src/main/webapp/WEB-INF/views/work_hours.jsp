<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>

<html>
<head>
  <title>ИС ПТОР Оборудование</title>
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>

  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>


  <script userUserType="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.4/jquery.dataTables.min.js"></script>

  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

  <script userUserType="text/javascript" src="charts/sources/jscharts.js"></script>

  <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css">
  <script src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.min.js"></script>

  <script userUserType="text/javascript">
    function getList () {
      $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Свернуть');
      $('.tree li.parent_li > span').on('click', function (e) {
        var children = $(this).parent('li.parent_li').find(' > ul > li');
        if (children.is(":visible")) {
          children.hide('fast');
          $(this).attr('title', 'Развернуть').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
        } else {
          children.show('fast');
          $(this).attr('title', 'Свернуть').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
        }
        e.stopPropagation();
      });
    }

    function SendGet(equipment_id) {
      $.ajax({
        userUserType: "GET",
        contentType: 'application/json',
        url: "/equipments/"+equipment_id,
        columnType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        jQuery("#result").html("          <div class=\"col-md-3\" style=\"margin: 25px;\">\n\
            <p class=\"form-control-static\"> <b> Наименование:</b></p>\n\
            <p class=\"form-control-static\"> <b> Тип оборудования:</b></p>\n\
            <p class=\"form-control-static\"> <b> Местонахождение:</b></p>\n\
            <p class=\"form-control-static\"> <b> Инвентарный номер:</b></p>\n\
            <p class=\"form-control-static\"> <b> Год начала эксплуатации:</b></p>\n\
            <p class=\"form-control-static\"> <b> Изготовитель:</b></p>\n\
            <p class=\"form-control-static\"> <b> Описание:</b></p>\n\
          </div>\n\
\n\
          <div class=\"col-md-5\" style=\"margin: 25px;\" align=\"left\">\n\
            <p class=\"form-control-static\" id=\"eq_name\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_type\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_sub\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_inv\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_year\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_prod\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_desc\"></p>\n\
          </div>");
        jQuery("#eq_name").text(data.equipment_name);
        jQuery("#eq_type").text(data.type_of_equipment.type_of_equipment_name);
        jQuery("#eq_sub").text(data.subdivision.subdivision_name);
        jQuery("#eq_inv").text(data.inventory_number);
        jQuery("#eq_year").text(data.graduation_year);
        jQuery("#eq_prod").text(data.producer_of_equipment);
        jQuery("#eq_desc").text(data.description);

        current_equipment = equipment_id;
        current_equipment_name = data.equipment_name;
        current_equipment_type = data.type_of_equipment.type_of_equipment_id;
      });
    }

    function getTechCard(equipment_id){
      $.ajax({
        userUserType: "GET",
        contentType: 'application/json',
        url: "/cards/equipment/"+equipment_id,
        columnType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        cards = data;
        jQuery("#result").html("<center></br><h5>Оборудование: " + current_equipment_name + "</h5></center><button class=\"btn btn-default\"  userUserType=\"button\" onclick=\"addTechCard()\">Добавить тех.карту</button></br><table class=\"table table-hover\" id=\"params_t\">\n\
<thead>\n\
  <tr><th>Номер тех.карты</th><th>Ответственный</th><th>Дата начала</th><th>Дата окончания</th></tr>\n\
  </thead>\n\
<tbody>\n");
        for(var i =0;i<cards.length;i++) {
          number = i;
          $("#params_t").append("<tr><td onclick=\"openTechCard(cards[number].technological_card_id)\"><span class=\"glyphicon glyphicon-file\"></span></td> <td>" + cards[i].technological_card_number + "</td><td>" + cards[i].responsible_for_delivery.last_name + "</td><td>" + cards[i].start_date + "</td><td>" + cards[i].end_date +"</td></tr>");
        }
      });
    }

    function getSpare(equipment_id){
      $.ajax({
        userUserType: "GET",
        contentType: 'application/json',
        url: "/components/"+equipment_id,
        columnType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        jQuery("#result").html("<center></br><h5>Оборудование: " + current_equipment_name + "</h5></center><table class=\"table table-hover\" id=\"params_t\">\n\
<thead>\n\
  <tr><th>Номенклатура</th><th>Количество</th><th>Производитель</th><th>Описание</th></tr>\n\
  </thead>\n\
<tbody>");
        for(var i =0;i<data.length;i++) {
          components_id = data[i].component_id;
          $("#params_t").append("<tr  onclick='getRepairBySpare(components_id)'><td>" + data[i].spare.spare_name + "</td><td>" + data[i].amount + "</td><td>" + data[i].spare.spare_producer + "</td><td>" + data[i].spare.description + "</td></tr>");

        }
        jQuery("#result").append("<table class=\"table table-hover\" id=\"repair_t\"></table>");
      });
    }

    function getRepairBySpare(component_id){
      $.ajax({
        userUserType: "GET",
        contentType: 'application/json',
        url: "/components/"+component_id +"/equipment/" + current_equipment,
        columnType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        $("#repair_t").html("<table class=\"table table-hover\" id=\"repair_t\">\n\
        <thead>\n\
          <tr><th>Вид ремонта</th><th>Дата</th></tr>\n\
        </thead>\n\
        <tbody>");
        for(var i =0;i<data.length;i++) {
          var date = new Date(data[i].start_date);
          var month = date.getMonth() + 1;
          var day = date.getDate();
          if(day<10){
            day="0"+day;
          }
          if(month<10){
            month ="0" +month;
          }
          var dtade = day + "-" + month + "-" + date.getFullYear();
          $("#repair_t").append("<tr><td>" + data[i].type_of_maintenance.type_of_maintenance_name + "</td><td>" + dtade + "</td></tr>");
        }
      });
    }

    function getParams(type_id){
      $.ajax({
        userUserType: "GET",
        contentType: 'application/json',
        url: "/equipments/parameters/"+type_id,
        columnType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        jQuery("#result").html("<center><h5></br>Оборудование: " + current_equipment_name + "</h5></center><table class=\"table table-hover\" id=\"params_t\">\n\
<thead>\n\
  <tr><th>Характеристика</th><th>Значение</th><th>Единица измерения</th></tr>\n\
  </thead>\n\
<tbody>\n");
        for(var i =0;i<data.length;i++) {
          $("#params_t").append("<tr><td>" + data[i].parameter_name + "</td><td>" + data[i].parameter_value +"</td><td>" + data[i].measure.measure_value + "</td></tr>");
        }
      });
    }

    function workedHours(equipment_id){
      $.ajax({
        userUserType: "GET",
        contentType: 'application/json',
        url: "/working_hours/"+equipment_id,
        columnType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        work_hours = 0;
        if(data.length>=1){
          work_hours = data[data.length-1].value;
        }
        jQuery("#result").html("<center><h5></br>Оборудование: " + current_equipment_name + "</h5></center> <b>Текущая наработка: " + work_hours +  "</b></br></br>\
        <button class=\"btn btn-default\"  userUserType=\"button\" onclick=\"addWorkedHours()\">Добавить наработку</button></br><center>История изменений наработки по текущему образцу оборудования</center><table class=\"table table-hover\" id=\"params_t\"></br></br>\n\
               <div id=\"myfirstchart\" style=\"height: 250px;\"></div> </br>\
<thead>\n\
  <tr><th>Изменил</th><th>Дата изменения</th><th>Значение</th></tr>\n\
  </thead>\n\
<tbody>");

        mas = [];
        for(var i =0;i<=data.length-1;i++) {
          var date = new Date(data[i].date_of_adding);
          var mm = date.getMonth() + 1;
          var dtade = date.getFullYear() + "-" + 0 + mm + "-" + date.getDate();
          var value = data[i].value;
          var t = {year: dtade, value: value};
          mas.push(t);
        }
        console.log(mas);
        new Morris.Line({
          // ID of the element in which to draw the chart.
          element: 'myfirstchart',
          // Chart data records -- each entry in this array corresponds to a point on
          // the chart.
          data: mas,
          // The name of the data record attribute that contains x-values.
          xkey: 'year',
          // A list of names of data record attributes that contain y-values.
          ykeys: ['value'],
          // Labels for the ykeys -- will be displayed when you hover over the
          // chart.
          labels: ['Наработка']
        });
        for(var i =0;i<=data.length-1;i++) {
          var date = new Date(data[i].date_of_adding);
          var mm = date.getMonth() + 1;
          var dtade = date.getFullYear() + "-" + 0 + mm + "-" + date.getDate();
          $("#params_t").append("<tr><td>" + data[i].responsible + "</td><td>" + dtade + "</td><td>" + data[i].value +"</td></tr>");
        }
      });
    }

    function addWorkedHours() {
      jQuery("#new_working_hours").dialog({
                title: "Добавление наработки",
                width:300,
                height: 220,
                resizable:false,
                cache: false,
                modal: true,
                buttons: [
                  {
                    text: 'Добавить',
                    click: function() {
                      var value = parseFloat(document.getElementById("work_hour").value);
                      var that = this;
                      $(that).dialog("close");
                      $("#work_hour").val("0.0");
                      $.ajax({
                        userUserType: "POST",
                        url: "/working_hours/add",
                        content: "application/json",
                        columnType: "json",
                        context: $(this),
                        data: {value: value, equipment_id: current_equipment},
                        success: function (returnData) {
                          $(that).dialog("close");
                          //window.location.reload(true);//$('#container').html(returnData);
                        }
                      });
                      history.go(0);
                    }},
                  {
                    text: 'Закрыть',
                    click: function() {
                      jQuery("#new_working_hours").dialog('close');
                      $("#work_hour").val("0.0");
                    }}
                ]
              }
      );
    }

    function addTechCard() {
      jQuery("#new_tech_card").dialog({
                title: "Добавление технологической карты оборудования",
                width:550,
                height: 470,
                resizable: false,
                cache: false,
                modal: true
              }
      );
    }

    function refreshContent()
    {
      history.go(0);
    }

    function addTechnologicalCard() {
      var type_of_maintenance_id = $("#maintenanceType").val();
      var start_date= $("#datepicker1").val();
      var description = $("#description_tech").val();

      var that = this;
      $.ajax({
        userUserType: "POST",
        url: "/cards/add",
        content: "application/json",
        columnType: "json",
        data: { equipment_id: current_equipment, type_of_maintenance_id : type_of_maintenance_id, start_date : start_date, description : description  },
        success: function(returnData){
          $(that).dialog("close");
        }});
      refreshContent();
    }

    function openTechCard(techCardId){
      $.ajax({
        userUserType: "GET",
        contentType: 'application/json',
        url: "/cards/"+techCardId,
        columnType: 'json',
        mimeType: 'application/json',
      }).done(function( data ) {
        jQuery("#techCardDialog").html("          <div class=\"col-md-4\" style=\"margin: 25px;\">\n\
            <p class=\"form-control-static\"> <b>Номер карты: </b></p>\n\
            <p class=\"form-control-static\"> <b>Оборудование: </b></p>\n\
            <p class=\"form-control-static\"> <b>Тип ремонта: </b></p>\n\
            <p class=\"form-control-static\"> <b>Дата:</b></p>\n\
            <p class=\"form-control-static\"> <b>Ответственный:</b></p>\n\
            <p class=\"form-control-static\"> <b>Описание:</b></p>\n\
          </div>\n\
\n\
          <div id = \"info\" class=\"col-md-3\" style=\"margin: 25px;\" align=\"left\">\n\
            <p class=\"form-control-static\" id=\"tech_card_id\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"equipment_id\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"type_of_maintenance_id\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"start_date\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"responsible_for_delivery\"></p>\n\
            \n\
            <p class=\"form-control-static\" id=\"tech_card_title\"></p>");

        var date = new Date(data.start_date);
        var month = date.getMonth() + 1;
        var day = date.getDate();
        if(day<10){
          day="0"+day;
        }
        if(month<10){
          month ="0" +month;
        }
        var dtade = day + "-" + month + "-" + date.getFullYear();

        desc = data.description;
        jQuery("#tech_card_id").text(data.technological_card_number);
        jQuery("#equipment_id").text(data.equipment.equipment_name);
        jQuery("#type_of_maintenance_id").text(data.type_of_maintenance.type_of_maintenance_name);
        jQuery("#start_date").text(dtade);
        jQuery("#responsible_for_delivery").text(data.responsible_for_delivery.last_name);
        jQuery("#tech_card_title").text(desc);
        jQuery("#techCardDialog").dialog({
          title: "Технологическая карта №"+ data.technological_card_number,
          width:650,
          height: 570,
          resizable:false,
          modal: true,
          buttons: [
            {
              text: 'Закрыть',
              click: function() {
                jQuery("#techCardDialog").dialog('close');
                refreshContent();
              }}
          ]
        });
      });
    }
  </script>

  <script>
    $(document).ready(function() {
      $("#datepicker1").datepicker({ dateFormat: 'yy-mm-dd' });
      $("#datepicker2").datepicker({ dateFormat: 'yy-mm-dd' });

    });
  </script>

</head>
      <div class="text-center">
        <div class="btn-toolbar" \>
          <div class="btn-group">
            <button class="btn btn-default" onclick="SendGet(current_equipment);">Общее</button>
            <button class="btn btn-default" onclick="getParams(current_equipment_type);">Характеристики</button>
            <button class="btn btn-default" onclick="getSpare(current_equipment);">Комплектующие</button>
            <button class="btn btn-default" onclick="workedHours(current_equipment);">Наработка</button>
            <button class="btn btn-default" onclick="getTechCard(current_equipment);">Простой</button>
            <button class="btn btn-default" onclick="getTechCard(current_equipment);">Технологические карты</button>
            <button class="btn btn-default" onclick="getTechCard(current_equipment);">Документы</button>
          </div>
        </div>
      </div>
        <div class="form-group" id="result" align="left"></div>

        <div id="new_working_hours"  title="Добавление наработки">

          <form:form action="working_hours" commandName="workHoursForm" >
            <table></br>
              <tr>
                <td><label><spring:message text="Наработка"/></label></td>
                <td><form:input path="value" id="work_hour" userUserType="number"/></td>
                <td><form:errors path="ssoid" cssClass="error"/></td>
              </tr>
            </table>
          </form:form>
        </div>