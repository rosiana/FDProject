<%--
  Created by IntelliJ IDEA.
  User: Rosiana
  Date: 7/13/2016
  Time: 2:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>MDS Scatterplot Example</title>
    <style>
        body {
            font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
            font-size: 14px;
        }
        .axis path,
        .axis line {
            fill: none;
            stroke: black;
            shape-rendering: crispEdges;
        }
        text {
            font-size : 12px;
        }
        .axis text {
            font-size: 10px;
        }
        circle {
            stroke: #1f77b4;
            fill : #1f77b4;
        }
    </style>
</head>

<body>
<div id="cities"></div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/numeric/1.2.6/numeric.min.js"></script>
<script src="js/mdsa.js"></script>
</body>
</html>
