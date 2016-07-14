<%--
  Created by IntelliJ IDEA.
  User: Rosiana
  Date: 7/12/2016
  Time: 11:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>


    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script src="http://d3js.org/d3.v2.js"></script>
    <script src="js/mds.js"></script>



    <style>

        .axis,
        .frame {
            shape-rendering: crispEdges;
        }


        .axis line {
            stroke: #00F;
            stroke-width: 2px;
            fill: none;
            stroke: black;
            shape-rendering: crispEdges;
        }

        .axis path {
            stroke: #00F;
            stroke-width: 2px;
            stroke: black;
            fill: none;
        }

        .axis text {
            font-family: sans-serif;
            font-size: 10px;
        }


        .ghost {
            fill: blue;
            fill-opacity: 0;
        }
        .label {
            font-size: 12px;
            color: blue;
        }
        .datapoint {
            r: 5;
            fill: blue;
            fill-opacity: .5;
        }

        div.tooltip {
            position: absolute;
            text-align: center;
            width: 60px;
            height: 28px;
            padding: 2px;
            font: 12px sans-serif;
            background: lightsteelblue;
            border: 0px;
            border-radius: 8px;
            pointer-events: none;
        }

        #d3plot {
            width: 500px;
            height: 500px;
        }
    </style>

</head>


<body>
<div id="d3plot" class="d3-bubble-output">  </div>
</body>
</html>