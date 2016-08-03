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

            var dataPointsCase = [];
            var dataPoints1 = [];
            var dataPoints2 = [];
            var dataPoints3 = [];
            var dataPoints4 = [];
            var dataPoints5 = [];
            var dataPointsOutlier = [];
            var dataPointsUFence = [];

            var chart = new CanvasJS.Chart("chartContainer",
                    {
                        title: {
                            text: "T5 Periode Masa Sanggah"
                        },
                        zoomEnabled: true,
                        panEnabled:true,
                        axisX: {
                            valueFormatString: "#,.'014'",
                            title: "ID Lelang"
                        },
                        axisY: {
                            gridColor: "#EEEEEE",
                            title: "Periode (dalam hari)"
                        },
                        data:
                                [
                                    {
                                        yValueFormatString: "#,.'014'",
                                        fillOpacity:1,
                                        markerSize: 5,
                                        color: "Black",
                                        type: "scatter",
                                        toolTipContent: "<strong>{x}</strong><br/><strong>{namalelang}</strong> <br/> Agency: {agency}<br/> Pagu: Rp.{pagu}<br/> HPS: Rp.{hps} <br/> Harga Penawaran: Rp.{penawaranmenang}<br/> Pemenang: {namapemenang}<br/> Status: {status}<br/><br/>{keterangan}",
                                        dataPoints: []
                                    },
                                    {
                                        yValueFormatString: "#,.'014'",
                                        fillOpacity:.2,
                                        markerSize: 3,
                                        color: "LightSeaGreen",
                                        type: "scatter",
                                        toolTipContent: "<strong>{x}</strong><br/><strong>{namalelang}</strong> <br/> Agency: {agency}<br/> Pagu: Rp.{pagu}<br/> HPS: Rp.{hps} <br/> Harga Penawaran: Rp.{penawaranmenang}<br/> Pemenang: {namapemenang}<br/> Status: {status}<br/><br/>{keterangan}",
                                        dataPoints: []
                                    },
                                    {
                                        yValueFormatString: "#,.'014'",
                                        fillOpacity:.4,
                                        markerSize: 3,
                                        color: "LightSeaGreen",
                                        type: "scatter",
                                        toolTipContent: "<strong>{x}</strong><br/><strong>{namalelang}</strong> <br/> Agency: {agency}<br/> Pagu: Rp.{pagu}<br/> HPS: Rp.{hps} <br/> Harga Penawaran: Rp.{penawaranmenang}<br/> Pemenang: {namapemenang}<br/> Status: {status}<br/><br/>{keterangan}",
                                        dataPoints: []
                                    },
                                    {
                                        yValueFormatString: "#,.'014'",
                                        fillOpacity:.6,
                                        markerSize: 3,
                                        color: "LightSeaGreen",
                                        type: "scatter",
                                        toolTipContent: "<strong>{x}</strong><br/><strong>{namalelang}</strong> <br/> Agency: {agency}<br/> Pagu: Rp.{pagu}<br/> HPS: Rp.{hps} <br/> Harga Penawaran: Rp.{penawaranmenang}<br/> Pemenang: {namapemenang}<br/> Status: {status}<br/><br/>{keterangan}",
                                        dataPoints: []
                                    },
                                    {
                                        yValueFormatString: "#,.'014'",
                                        fillOpacity:.8,
                                        markerSize: 3,
                                        color: "LightSeaGreen",
                                        type: "scatter",
                                        toolTipContent: "<strong>{x}</strong><br/><strong>{namalelang}</strong> <br/> Agency: {agency}<br/> Pagu: Rp.{pagu}<br/> HPS: Rp.{hps} <br/> Harga Penawaran: Rp.{penawaranmenang}<br/> Pemenang: {namapemenang}<br/> Status: {status}<br/><br/>{keterangan}",
                                        dataPoints: []
                                    },
                                    {
                                        yValueFormatString: "#,.'014'",
                                        fillOpacity:1,
                                        markerSize: 3,
                                        color: "LightSeaGreen",
                                        type: "scatter",
                                        toolTipContent: "<strong>{x}</strong><br/><strong>{namalelang}</strong> <br/> Agency: {agency}<br/> Pagu: Rp.{pagu}<br/> HPS: Rp.{hps} <br/> Harga Penawaran: Rp.{penawaranmenang}<br/> Pemenang: {namapemenang}<br/> Status: {status}<br/><br/>{keterangan}",
                                        dataPoints: []
                                    },
                                    {
                                        yValueFormatString: "#,.'014'",
                                        fillOpacity:.5,
                                        color: "Red",
                                        markerSize: 3,
                                        type: "scatter",
                                        toolTipContent: "<strong>{x}</strong><br/><strong>{namalelang}</strong> <br/> Agency: {agency}<br/> Pagu: Rp.{pagu}<br/> HPS: Rp.{hps} <br/> Harga Penawaran: Rp.{penawaranmenang}<br/> Pemenang: {namapemenang}<br/> Status: {status}<br/><br/>{keterangan}",
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

            $.getJSON("json/jabarprov_t5.json", function (data) {

                var uf = [];
                data.forEach(function(data){ uf.push(data.periodemasasanggah); });
                var max = Math.max.apply(Math, uf);
                console.log(max);
                var lf = [];
                data.forEach(function(data){ lf.push(data.outlier); });
                var min = Math.min.apply(Math, lf);
                console.log(min);
                var gap = (max - min) / 5;
                var op1 = min + gap;
                var op2 = min + (gap * 2);
                var op3 = min + (gap * 3);
                var op4 = min + (gap * 4);
                for (var i = 0; i < data.length; i++) {
                    if (data[i].id == 22683014 || data[i].id == 22684014 || data[i].id == 23309014 || data[i].id == 23310014 || data[i].id == 23311014 || data[i].id == 23312014 || data[i].id == 23313014 || data[i].id == 23314014 || data[i].id == 23564014 || data[i].id == 23683014 || data[i].id == 23687014 || data[i].id == 24368014 || data[i].id == 24652014 || data[i].id == 24683014 || data[i].id == 25036014 || data[i].id == 25041014 || data[i].id == 25044014 || data[i].id == 25046014 || data[i].id == 25047014 || data[i].id == 25048014) {
                        dataPointsCase.push({ x: data[i].id, y: data[i].periodemasasanggah, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                    }
                    else {
                        if ((data[i].periodemasasanggah > min) && (data[i].periodemasasanggah < op1)) {
                            dataPoints1.push({ x: data[i].id, y: data[i].periodemasasanggah, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                        if ((data[i].periodemasasanggah >= op1) && (data[i].periodemasasanggah < op2)) {
                            dataPoints2.push({ x: data[i].id, y: data[i].periodemasasanggah, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                        if ((data[i].periodemasasanggah >= op2) && (data[i].periodemasasanggah < op3)) {
                            dataPoints3.push({ x: data[i].id, y: data[i].periodemasasanggah, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                        if ((data[i].periodemasasanggah >= op3) && (data[i].periodemasasanggah < op4)) {
                            dataPoints4.push({ x: data[i].id, y: data[i].periodemasasanggah, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                        if ((data[i].periodemasasanggah >= op4) && (data[i].periodemasasanggah < max)) {
                            dataPoints5.push({ x: data[i].id, y: data[i].periodemasasanggah, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                        if (data[i].periodemasasanggah <= min) {
                            dataPointsOutlier.push({ x: data[i].id, y: data[i].periodemasasanggah, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                    }
                    dataPointsUFence.push({ x: data[i].id, y: data[i].outlier });
                }

                chart.options.data[0].dataPoints = dataPointsCase;
                chart.options.data[1].dataPoints = dataPoints1;
                chart.options.data[2].dataPoints = dataPoints2;
                chart.options.data[3].dataPoints = dataPoints3;
                chart.options.data[4].dataPoints = dataPoints4;
                chart.options.data[5].dataPoints = dataPoints5;
                chart.options.data[6].dataPoints = dataPointsOutlier;
                chart.options.data[7].dataPoints = dataPointsUFence;
                chart.render();
            });

        });
    </script>
</head>
<body>
    <div id="chartContainer" style="width: 100%; height: 300px;"></div>
</body>
</html>