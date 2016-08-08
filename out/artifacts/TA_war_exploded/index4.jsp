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
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.9.1.js"></script>
    <script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js"></script>
    <link rel="shortcut icon" href="">
</head>
<body>
    <div id="myDiv" style="width: 1366px; height: 500px;"><!-- Plotly chart will be drawn inside this DIV --></div>
    <script>

        $(document).ready(function () {

            var arrgraphx = [];
            var arrgraphy = [];

            var trace1 = {
                x: [],
                y: [],
                type: 'scatter',
                mode: 'markers',
                marker: {
                    color: 'rgb(101, 131, 155)',
                    size: 6
                }
            };

            var data = [trace1];

            var layout = {
                xaxis: {
                    type: 'category',
                    title: 'ID Lelang',
                    nticks: 20,
                    tickwidth: 3,
                    tickcolor: 'rgb(83, 94, 126)',
                    range: [1, 200]
                },
                yaxis: {
                    title: 'Periode (dalam hari)',
                    tickwidth: 3,
                    tickcolor: 'rgb(83, 94, 126)'
                },
                title: 'Tipe Indikasi 4 - Periode Penetapan Pemenang',
                showlegend: false
            };

            d3.json("json/new/v1_t4.json", function(d) {
                console.log(d);
                for (var i = 0; i < d.length; i++) {
                    arrgraphx.push(d[i].id);
                    arrgraphy.push(d[i].periodepenetapanpemenang);
                }

                trace1.x = arrgraphx;
                trace1.y = arrgraphy;

                Plotly.newPlot('myDiv', data, layout);
            });

        });
    </script>
</body>
</html>