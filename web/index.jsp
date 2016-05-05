<%--
  Created by IntelliJ IDEA.
  User: Rosiana
  Date: 5/4/2016
  Time: 3:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <!--Load the AJAX API-->
  <script type="text/javascript" src="js/google.jsapi.js"></script>
  <script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
  <script type="text/javascript">

    // Load the Visualization API and the piechart,table package.
    google.load('visualization', '1', {'packages':['corechart','table']});

    function drawItems(num) {
      var jsonChartData = $.ajax({
        url: "getchartdata.php",
        data: "q="+num,
        dataType:"json",
        async: false
      }).responseText;

      // Create our data table out of JSON data loaded from server.
      var chartdata = new google.visualization.DataTable(jsonChartData);

      var options = {
        title: 'Age vs. Weight comparison',
        hAxis: {title: 'Nomor lelang', minValue: 0, maxValue: 100},
        vAxis: {title: 'Jumlah hari', minValue: 0, maxValue: 15},
        legend: 'none'
      };

      // Instantiate and draw our pie chart, passing in some options.
      var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));
      chart.draw(chartdata, options);
    }

  </script>
</head>
<body>
<form>
  <select name="users" onchange="drawItems(this.value)">
    <?php
    $dbuser="root";
    $dbname="fdproject";
    $dbpass="";
    $dbserver="localhost";
    // Make a MySQL Connection
    $con = mysql_connect($dbserver, $dbuser, $dbpass) or die(mysql_error());
    mysql_select_db($dbname) or die(mysql_error());
    // Create a Query
    $sql_query = "SELECT id, periodelelang FROM t1";
    // Execute query
    $result = mysql_query($sql_query) or die(mysql_error());
    while ($row = mysql_fetch_array($result)){
    echo '<option value='. $row['id'] . '>'. $row['periodelelang'] . '</option>';
    }
    mysql_close($con);
    ?>
  </select>
</form>
<div id="chart_div"></div>
</body>
</html>
