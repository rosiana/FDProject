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

            var arrgraphx1 = [];
            var arrgraphy1 = [];
            var arrgraphx2 = [];
            var arrgraphy2 = [];

            var trace1 = {
                x: [],
                y: [],
                type: 'bar',
                //mode: 'markers',
                marker: {
                    color: 'rgb(101, 131, 155)',
                    size: 6
                }
            };

            var trace2 = {
                x: [],
                y: [],
                mode: 'lines',
                marker: {
                    color: '#B86948'
                }
            };

            var data = [trace1, trace2];

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
                    title: 'Harga menang per HPS',
                    tickwidth: 3,
                    tickcolor: 'rgb(83, 94, 126)'
                },
                title: 'Tipe Indikasi 2B - Nilai Harga Menang Per HPS',
                showlegend: false
            };

            d3.json("json/new/v1_t2b.json", function(d) {
                console.log(d);
                for (var i = 0; i < d.length; i++) {
                    arrgraphx1.push(d[i].id);
                    arrgraphy1.push(d[i].menangperhps);
                    arrgraphx2.push(d[i].id);
                    var x = 0;
                    if (d[i].menangperhps >= 1) {
                        x = 0;
                    }
                    else {
                        x = 1 - d[i].menangperhps;
                    }
                    console.log(x);
                    arrgraphy2.push(1);
                }

                trace1.x = arrgraphx1;
                trace1.y = arrgraphy1;
                trace2.x = arrgraphx2;
                trace2.y = arrgraphy2;

                Plotly.newPlot('myDiv', data, layout);
            });

        });
    </script>
</body>
</html>