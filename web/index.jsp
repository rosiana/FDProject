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
    <script type="text/javascript" src="js/canvasjs.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {

            var dataPoints = [];

            var chart = new CanvasJS.Chart("chartContainer",
                    {
                        title: {
                            text: "T2b"
                        },
                        zoomEnabled: true,
                        panEnabled:true,
                        data: [
                            {
                                type: "scatter",
                                toolTipContent: "<strong>{label}</strong><br/><strong>{namalelang}</strong> <br/> Agency: {agency}<br/> Pagu: Rp.{pagu}<br/> HPS: Rp.{hps} <br/> Harga Penawaran: Rp.{penawaranmenang}<br/> Pemenang: {namapemenang}<br/> Status: {status}<br/><br/>{keterangan}",
                                dataPoints: []
                            }]
                    });

            // Ajax request for getting JSON data
            //Replace data.php with your JSON API's url

            $.getJSON("json/t2b.json", function (data) {

                for (var i = 0; i < data.length; i++) {

                    dataPoints.push({ label: data[i].id, y: data[i].menangperhps, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                }

                chart.options.data[0].dataPoints = dataPoints;
                chart.render();
            });
        });
    </script>
</head>
<body>
    <div id="chartContainer" style="width: 100%; height: 300px;"></div>
</body>
</html>