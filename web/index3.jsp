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

            var dataPointsGraph1 = [];
            var dataPointsLFenceGraph1 = [];
            var dataPointsGraph2 = [];
            var dataPointsLFenceGraph2 = [];

            var chart1 = new CanvasJS.Chart("chartContainer1",
                    {
                        title: {
                            text: "T3 Periode Pengumuman Pascakualifikasi"
                        },
                        zoomEnabled: true,
                        panEnabled:true,
                        data:
                        [
                            {
                                fillOpacity:.5,
                                markerSize: 5,
                                type: "scatter",
                                toolTipContent: "<strong>{label}</strong><br/><strong>{namalelang}</strong> <br/> Agency: {agency}<br/> Pagu: Rp.{pagu}<br/> HPS: Rp.{hps} <br/> Harga Penawaran: Rp.{penawaranmenang}<br/> Pemenang: {namapemenang}<br/> Status: {status}<br/><br/>{keterangan}",
                                dataPoints: []
                            },
                            {
                                color: "Red",
                                type: "line",
                                toolTipContent: "Lower fence: {y}",
                                dataPoints: []
                            }
                        ]
                    });

            var chart2 = new CanvasJS.Chart("chartContainer2",
                    {
                        title: {
                            text: "T3 Mulai Lelang - Penetapan Pemenang"
                        },
                        zoomEnabled: true,
                        panEnabled:true,
                        data:
                                [
                                    {
                                        fillOpacity:.5,
                                        markerSize: 5,
                                        type: "scatter",
                                        toolTipContent: "<strong>{label}</strong><br/><strong>{namalelang}</strong> <br/> Agency: {agency}<br/> Pagu: Rp.{pagu}<br/> HPS: Rp.{hps} <br/> Harga Penawaran: Rp.{penawaranmenang}<br/> Pemenang: {namapemenang}<br/> Status: {status}<br/><br/>{keterangan}",
                                        dataPoints: []
                                    },
                                    {
                                        color: "Red",
                                        type: "line",
                                        toolTipContent: "Lower fence: {y}",
                                        dataPoints: []
                                    }
                                ]
                    });

            // Ajax request for getting JSON data
            //Replace data.php with your JSON API's url

            $.getJSON("json/t3periodepasca.json", function (data) {

                for (var i = 0; i < data.length; i++) {

                    dataPointsGraph1.push({ label: data[i].id, y: data[i].periodepengumumanpasca, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                    dataPointsLFenceGraph1.push({ label: data[i].id, y: data[i].outlier });
                }

                chart1.options.data[0].dataPoints = dataPointsGraph1;
                chart1.options.data[1].dataPoints = dataPointsLFenceGraph1;
                chart1.render();
            });

            $.getJSON("json/t3periodepemenang.json", function (data) {

                for (var i = 0; i < data.length; i++) {

                    dataPointsGraph2.push({ label: data[i].id, y: data[i].periodepengumumanpemenang, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                    dataPointsLFenceGraph2.push({ label: data[i].id, y: data[i].outlier });
                }

                chart2.options.data[0].dataPoints = dataPointsGraph2;
                chart2.options.data[1].dataPoints = dataPointsLFenceGraph2;
                chart2.render();
            });

        });
    </script>
</head>
<body>
    <div id="chartContainer1" style="width: 100%; height: 300px;"></div>
    <div id="chartContainer2" style="width: 100%; height: 300px; margin-top: 50px"></div>
</body>
</html>