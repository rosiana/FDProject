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

            var dataPointsGraph1Case = [];
            var dataPointsGraph1Op1 = [];
            var dataPointsGraph1Op2 = [];
            var dataPointsGraph1Op3 = [];
            var dataPointsGraph1Op4 = [];
            var dataPointsGraph1Op5 = [];
            var dataPointsGraph1Outlier = [];
            var dataPointsLFenceGraph1 = [];
            var dataPointsUFenceGraph1 = [];
            var dataPointsGraph2Case = [];
            var dataPointsGraph2Op1 = [];
            var dataPointsGraph2Op2 = [];
            var dataPointsGraph2Op3 = [];
            var dataPointsGraph2Op4 = [];
            var dataPointsGraph2Op5 = [];
            var dataPointsGraph2Outlier = [];
            var dataPointsLFenceGraph2 = [];
            var dataPointsUFenceGraph2 = [];

            var chart1 = new CanvasJS.Chart("chartContainer1",
                    {
                        title: {
                            text: "T1 Periode Mulai - Selesai Lelang"
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
                                fillOpacity:1,
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
                                fillOpacity:.3,
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
                            },
                            {
                                color: "Red",
                                type: "line",
                                toolTipContent: "Upper fence: {y}",
                                dataPoints: []
                            }
                        ]
                    });

            var chart2 = new CanvasJS.Chart("chartContainer2",
                    {
                        title: {
                            text: "T1 Jeda Tahap"
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
                                        fillOpacity:1,
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
                                        fillOpacity:.3,
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
                                    },
                                    {
                                        color: "Red",
                                        type: "line",
                                        toolTipContent: "Upper fence: {y}",
                                        dataPoints: []
                                    }
                                ]
                    });

            // Ajax request for getting JSON data
            //Replace data.php with your JSON API's url

            $.getJSON("json/jabarprov_t1periode.json", function (data) {

                var uf = [];
                data.forEach(function(data){ uf.push(data.outlieratas); });
                var lf = [];
                data.forEach(function(data){ lf.push(data.outlierbawah); });
                var max = Math.max.apply(Math, uf);
                console.log(max);
                var min = Math.min.apply(Math, lf);
                console.log(min);
                var gap = (max - min) / 5;
                var op1 = min + gap;
                var op2 = min + (gap * 2);
                var op3 = min + (gap * 3);
                var op4 = min + (gap * 4);
                for (var i = 0; i < data.length; i++) {
                    if (data[i].id == 22683014 || data[i].id == 22684014 || data[i].id == 23309014 || data[i].id == 23310014 || data[i].id == 23311014 || data[i].id == 23312014 || data[i].id == 23313014 || data[i].id == 23314014 || data[i].id == 23564014 || data[i].id == 23683014 || data[i].id == 23687014 || data[i].id == 24368014 || data[i].id == 24652014 || data[i].id == 24683014 || data[i].id == 25036014 || data[i].id == 25041014 || data[i].id == 25044014 || data[i].id == 25046014 || data[i].id == 25047014 || data[i].id == 25048014) {
                        dataPointsGraph1Case.push({ x: data[i].id, y: data[i].periodelelang, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                    }
                    else {
                        if ((data[i].periodelelang > min) && (data[i].periodelelang < op1)) {
                            dataPointsGraph1Op1.push({ x: data[i].id, y: data[i].periodelelang, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                        if ((data[i].periodelelang >= op1) && (data[i].periodelelang < op2)) {
                            dataPointsGraph1Op2.push({ x: data[i].id, y: data[i].periodelelang, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                        if ((data[i].periodelelang >= op2) && (data[i].periodelelang < op3)) {
                            dataPointsGraph1Op3.push({ x: data[i].id, y: data[i].periodelelang, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                        if ((data[i].periodelelang >= op3) && (data[i].periodelelang < op4)) {
                            dataPointsGraph1Op4.push({ x: data[i].id, y: data[i].periodelelang, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                        if ((data[i].periodelelang >= op4) && (data[i].periodelelang < max)) {
                            dataPointsGraph1Op5.push({ x: data[i].id, y: data[i].periodelelang, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                        if ((data[i].periodelelang >= max) || (data[i].periodelelang <= min)) {
                            dataPointsGraph1Outlier.push({ x: data[i].id, y: data[i].periodelelang, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                    }
                    dataPointsLFenceGraph1.push({ x: data[i].id, y: data[i].outlierbawah });
                    dataPointsUFenceGraph1.push({ x: data[i].id, y: data[i].outlieratas });
                }

                chart1.options.data[0].dataPoints = dataPointsGraph1Case;
                chart1.options.data[1].dataPoints = dataPointsGraph1Op1;
                chart1.options.data[2].dataPoints = dataPointsGraph1Op2;
                chart1.options.data[3].dataPoints = dataPointsGraph1Op3;
                chart1.options.data[4].dataPoints = dataPointsGraph1Op4;
                chart1.options.data[5].dataPoints = dataPointsGraph1Op5;
                chart1.options.data[6].dataPoints = dataPointsGraph1Outlier;
                chart1.options.data[7].dataPoints = dataPointsLFenceGraph1;
                chart1.options.data[8].dataPoints = dataPointsUFenceGraph1;
                chart1.render();
            });

            $.getJSON("json/jabarprov_t1meanselisih.json", function (data) {

                var uf = [];
                data.forEach(function(data){ uf.push(data.outlieratas); });
                console.log(uf);
                var lf = [];
                data.forEach(function(data){ lf.push(data.outlierbawah); });
                console.log(lf);
                var max = Math.max.apply(Math, uf);
                console.log(max);
                var min = Math.min.apply(Math, lf);
                console.log(min);
                var gap = (max - min) / 5;
                var op1 = min + gap;
                var op2 = min + (gap * 2);
                var op3 = min + (gap * 3);
                var op4 = min + (gap * 4);
                for (var i = 0; i < data.length; i++) {
                    if (data[i].id == 22683014 || data[i].id == 22684014 || data[i].id == 23309014 || data[i].id == 23310014 || data[i].id == 23311014 || data[i].id == 23312014 || data[i].id == 23313014 || data[i].id == 23314014 || data[i].id == 23564014 || data[i].id == 23683014 || data[i].id == 23687014 || data[i].id == 24368014 || data[i].id == 24652014 || data[i].id == 24683014 || data[i].id == 25036014 || data[i].id == 25041014 || data[i].id == 25044014 || data[i].id == 25046014 || data[i].id == 25047014 || data[i].id == 25048014) {
                        dataPointsGraph2Case.push({ x: data[i].id, y: data[i].meanselisihtahap, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                    }
                    else {
                        if ((data[i].meanselisihtahap > min) && (data[i].meanselisihtahap < op1)) {
                            dataPointsGraph2Op1.push({ x: data[i].id, y: data[i].meanselisihtahap, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                        if ((data[i].meanselisihtahap >= op1) && (data[i].meanselisihtahap < op2)) {
                            dataPointsGraph2Op2.push({ x: data[i].id, y: data[i].meanselisihtahap, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                        if ((data[i].meanselisihtahap >= op2) && (data[i].meanselisihtahap < op3)) {
                            dataPointsGraph2Op3.push({ x: data[i].id, y: data[i].meanselisihtahap, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                        if ((data[i].meanselisihtahap >= op3) && (data[i].meanselisihtahap < op4)) {
                            dataPointsGraph2Op4.push({ x: data[i].id, y: data[i].meanselisihtahap, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                        if ((data[i].meanselisihtahap >= op4) && (data[i].meanselisihtahap < max)) {
                            dataPointsGraph2Op5.push({ x: data[i].id, y: data[i].meanselisihtahap, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                        if ((data[i].meanselisihtahap >= max) || (data[i].meanselisihtahap <= min)) {
                            dataPointsGraph2Outlier.push({ x: data[i].id, y: data[i].meanselisihtahap, namalelang: data[i].namalelang, agency: data[i].agency, pagu: data[i].pagu, hps: data[i].hps, penawaranmenang: data[i].penawaranmenang, namapemenang: data[i].namapemenang, status: data[i].status, keterangan: data[i].keterangan });
                        }
                    }
                    dataPointsLFenceGraph2.push({ x: data[i].id, y: data[i].outlierbawah });
                    dataPointsUFenceGraph2.push({ x: data[i].id, y: data[i].outlieratas });
                }

                chart2.options.data[0].dataPoints = dataPointsGraph2Case;
                chart2.options.data[1].dataPoints = dataPointsGraph2Op1;
                chart2.options.data[2].dataPoints = dataPointsGraph2Op2;
                chart2.options.data[3].dataPoints = dataPointsGraph2Op3;
                chart2.options.data[4].dataPoints = dataPointsGraph2Op4;
                chart2.options.data[5].dataPoints = dataPointsGraph2Op5;
                chart2.options.data[6].dataPoints = dataPointsGraph2Outlier;
                chart2.options.data[7].dataPoints = dataPointsLFenceGraph2;
                chart2.options.data[8].dataPoints = dataPointsUFenceGraph2;
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