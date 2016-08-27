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
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>LPSE Analytics</title>

    <script type="text/javascript" src="https://code.jquery.com/jquery-1.9.1.js"></script>
    <script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js"></script>

    <!-- Bootstrap Styles-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FontAwesome Styles-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
    <!-- Custom Styles-->
    <link href="assets/css/custom-styles.css" rel="stylesheet" />
    <!-- Google Fonts-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
    <link rel="shortcut icon" href="">
</head>
<body>
    <div id="wrapper">
        <nav class="navbar navbar-default top-navbar" role="navigation">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html"><strong>LPSE Analytics</strong></a>
            </div>

            <ul class="nav navbar-top-links navbar-right">
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                        <i class="fa fa-question-circle fa-fw"></i>
                    </a>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
        </nav>
        <!--/. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div id="sideNav" href=""><i class="fa fa-caret-right"></i></div>
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">

                    <li>
                        <a href="#"><i class="fa fa-home fa-fw"></i> Halaman Utama</a>
                    </li>
                    <li>
                        <a href="index1.jsp"><i class="fa fa-bar-chart"></i> Tipe Indikasi 1</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-bar-chart"></i> Tipe Indikasi 2<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="index2a.jsp">Tipe Indikasi 2A</a>
                            </li>
                            <li>
                                <a class="active-menu" href="index2b.jsp">Tipe Indikasi 2B</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="index3.jsp"><i class="fa fa-bar-chart"></i> Tipe Indikasi 3</a>
                    </li>
                    <li>
                        <a href="index4.jsp"><i class="fa fa-bar-chart"></i> Tipe Indikasi 4</a>
                    </li>
                    <li>
                        <a href="index5.jsp"><i class="fa fa-bar-chart"></i> Tipe Indikasi 5</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-question-circle fa-fw"></i> Bantuan</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-info-circle fa-fw"></i> Tentang Kami</a>
                    </li>
                </ul>

            </div>

        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper">
            <div id="page-inner">


                <div class="row">
                    <div class="col-md-12">
                        <div class="page-header">
                            <div id="qm-info"><h1>Tipe Indikasi 2B <small><i class="fa fa-question-circle"></i></small></h1></div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12" id="info-row">
                        <div class="info">
                            Tipe indikasi 2B berdasarkan indikasi kerjasama antarpeserta atau beberapa perusahaan yang dimiliki oknum yang sama; menggunakan perbandingan data HPS dan harga yang dimenangkan (untuk menunjukkan tidak adanya persaingan sehat). <br />
                            <small>Harga Perkiraan Sendiri (HPS) adalah harga yang ditetapkan oleh agency yang membutuhkan barang/jasa sebagai harga patokan atau harga barang/jasa tersebut umumnya di pasaran. HPS ditentukan supaya pengadaan barang/jasa dapat dilaksanakan berdasarkan harga pasar yang wajar. Penyedia tidak dapat memberikan harga penawaran di atas HPS. Jika harga penawaran yang dimenangkan menyamai atau sangat tipis di bawah HPS maka ada kemungkinan penyedia bertindak curang kepada agency, yaitu melakukan perilaku sedemikian rupa (baik dengan campur tangan pegawai ULP atau tidak) sehingga tidak terjadi persaingan bebas. Akibatnya harga maksimum yang mungkin terpaksa dimenangkan.</small>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default" data-num="0">
                            <div class="row header-chart">
                                <div class="col-md-12">
                                    <div class="col-md-5">
                                        <h2>Grafik Harga Penawaran Menang Per HPS</h2>
                                    </div>
                                    <div class="col-md-7">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <select class="form-control lpse-data">
                                                        <option>Semua LPSE</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <select class="form-control year-data">
                                                        <option>Semua Tahun</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <button type="button" class="btn btn-default nextprev" id="prev"><i class="fa fa-angle-left fa-fw arrowleft"></i></button>
                                                    </div>
                                                    <div class="col-md-6" id="count" style="text-align: center;">
                                                        1 / 20
                                                    </div>
                                                    <div class="col-md-3">
                                                        <button type="button" class="btn btn-default nextprev next" id="next"><i class="fa fa-angle-right fa-fw arrowright"></i></button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div id="myDiv" style="width : 1000px; height: 400px;"><!-- Plotly chart will be drawn inside this DIV --></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-4 modal-body-title">Nama lelang</div>
                                    <div class="col-md-8 modal-body-content" id="modalName">Nama lelang</div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4 modal-body-title">LPSE</div>
                                    <div class="col-md-8 modal-body-content" id="modalLPSE">LPSE</div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4 modal-body-title">Tahun</div>
                                    <div class="col-md-8 modal-body-content" id="modalYear">Tahun</div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4 modal-body-title">Pemenang</div>
                                    <div class="col-md-8 modal-body-content" id="modalPemenang">Pemenang</div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4 modal-body-title">Pagu</div>
                                    <div class="col-md-8 modal-body-content" id="modalPagu">Pagu</div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4 modal-body-title">HPS</div>
                                    <div class="col-md-8 modal-body-content" id="modalHPS">HPS</div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4 modal-body-title">Penawaran menang</div>
                                    <div class="col-md-8 modal-body-content" id="modalPenawaran">Penawaran menang</div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4 modal-body-title">Status</div>
                                    <div class="col-md-8 modal-body-content" id="modalStatus">Status</div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary">Lihat selengkapnya</button>
                            </div>
                        </div>
                    </div>
                </div>

                <footer><p>Footer</p>


                </footer>
            </div>
            <!-- /. PAGE INNER  -->
        </div>
        <!-- /. PAGE WRAPPER  -->
    </div>
    <script>

        $(document).ready(function () {

            Plotly.d3.json('http://localhost/jing/getJSONT2b.php', function(rows) {
                function unpack(rows, key) {
                    return rows.map(function(row) { return row[key]; });
                }

                var allLPSE = unpack(rows, 'lpse').reverse(),
                        allYear = unpack(rows, 'tahun').reverse(),
                        allID = unpack(rows, 'id').reverse(),
                        allVal = unpack(rows, 'menangperhps').reverse(),
                        allName = unpack(rows, 'nama').reverse(),
                        allStatus = unpack(rows, 'status').reverse(),
                        allPagu = unpack(rows, 'pagu').reverse(),
                        allHPS = unpack(rows, 'hps').reverse(),
                        allPenawaranMenang = unpack(rows, 'penawaranmenang').reverse(),
                        allPemenang = unpack(rows, 'pemenang').reverse(),
                        listofLPSE = [],
                        currentLPSE,
                        listofYear = [],
                        currentYear,
                        currentVal = [],
                        currentLine = [],
                        currentID = [];

                for (var i = 0; i < allLPSE.length; i++ ){
                    if (listofLPSE.indexOf(allLPSE[i]) === -1 ){
                        listofLPSE.push(allLPSE[i]);
                    }
                }

                for (var i = 0; i < allYear.length; i++ ){
                    if (listofYear.indexOf(allYear[i]) === -1 ){
                        listofYear.push(allYear[i]);
                    }
                }

                function getLPSEandYearData(chosenLPSE, chosenYear) {
                    currentVal = [];
                    currentLine = [];
                    currentID = [];
                    if ( (chosenLPSE === 'Semua LPSE') && (chosenYear === 'Semua Tahun') ) {
                        currentVal = allVal;
                        currentID = allID;
                        for (var i = 0; i < currentVal.length; i++) {
                            currentLine[i] = 1;
                        }
                    }
                    if ( (chosenLPSE === 'Semua LPSE') && (chosenYear !== 'Semua Tahun') ) {
                        for (var i = 0 ; i < allYear.length ; i++){
                            if ( allYear[i] == chosenYear ) {
                                currentVal.push(allVal[i]);
                                currentLine.push(1);
                                currentID.push(allID[i]);
                            }
                        }
                    }
                    if ( (chosenLPSE !== 'Semua LPSE') && (chosenYear === 'Semua Tahun') ) {
                        for (var i = 0 ; i < allLPSE.length ; i++){
                            if ( allLPSE[i] === chosenLPSE ) {
                                currentVal.push(allVal[i]);
                                currentLine.push(1);
                                currentID.push(allID[i]);
                            }
                        }
                    }
                    if ( (chosenLPSE !== 'Semua LPSE') && (chosenYear !== 'Semua Tahun') ) {
                        for (var i = 0 ; i < allLPSE.length ; i++){
                            if ( (allLPSE[i] === chosenLPSE) &&  (allYear[i] == chosenYear) ) {
                                currentVal.push(allVal[i]);
                                currentLine.push(1);
                                currentID.push(allID[i]);
                            }
                        }
                    }
                }

                setPlot('Semua LPSE', 'Semua Tahun');

                function setPlot(chosenLPSE, chosenYear) {
                    getLPSEandYearData(chosenLPSE, chosenYear);

                    var trace1 = {
                        x: currentID,
                        y: currentVal,
                        type: 'scatter',
                        mode: 'markers',
                        marker: {
                            color: 'rgb(101, 131, 155)',
                            size: 6
                        }
                    };

            var trace2 = {
                x: currentID,
                y: currentLine,
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
                            nticks: 10,
                            tickwidth: 3,
                            tickcolor: 'rgb(83, 94, 126)',
                            range: [1, 200]
                        },
                        yaxis: {
                            title: 'Periode (dalam hari)',
                            tickwidth: 3,
                            tickcolor: 'rgb(83, 94, 126)'
                        },
                        //title: 'Tipe Indikasi 1 - Rata-Rata Jeda Tahap Lelang',
                        margin: {
                            l: 70,
                            r: 30,
                            b: 70,
                            t: 50,
                            pad: 4
                        },
                        showlegend: false
                    };

                Plotly.newPlot('myDiv', data, layout, {displayModeBar: true, displaylogo: false});
                }

                var innerContainer = document.querySelector('[data-num="0"'),
                        plotEl = innerContainer.querySelector('.plot'),
                        lpseSelector = innerContainer.querySelector('.lpse-data'),
                        yearSelector = innerContainer.querySelector('.year-data');

                function assignOptions(textArray, selector) {
                    for (var i = 0; i < textArray.length;  i++) {
                        var currentOption = document.createElement('option');
                        currentOption.text = textArray[i];
                        selector.appendChild(currentOption);
                    }
                }

                listofLPSE.sort(function(a, b){return a-b});
                listofYear.sort(function(a, b){return b-a});
                assignOptions(listofLPSE, lpseSelector);
                assignOptions(listofYear, yearSelector);

                function update(){
                    setPlot(lpseSelector.value, yearSelector.value);
                }

                lpseSelector.addEventListener('change', update, false);
                yearSelector.addEventListener('change', update, false);

                var myPlot = document.getElementById('myDiv');

                Number.prototype.format = function(n, x, s, c) {
                    var re = '\\d(?=(\\d{' + (x || 3) + '})+' + (n > 0 ? '\\D' : '$') + ')',
                            num = this.toFixed(Math.max(0, ~~n));

                    return (c ? num.replace('.', c) : num).replace(new RegExp(re, 'g'), '$&' + (s || ','));
                };

                myPlot.on('plotly_click', function(data1){
                    var id, nama, lpse, tahun, pemenang, pagu, hps, penawaran, status = '';
                    for(var i=0; i < data1.points.length; i++){
                        var j = data1.points[i].pointNumber;
                        id = allID[j];
                        nama = allName[j];
                        lpse = allLPSE[j];
                        tahun = allYear[j];
                        pemenang = allPemenang[j];
                        pagu = allPagu[j];
                        hps = allHPS[j];
                        penawaran = allPenawaranMenang[j];
                        status = allStatus[j];
                    }
                    $('#myModalLabel').text("Lelang " + id);

                    $('#modalName').text(nama);
                    $('#modalLPSE').text(lpse);
                    $('#modalYear').text(tahun);
                    $('#modalPemenang').text(pemenang);
                    $('#modalPagu').text("Rp " + pagu.format(2, 3, '.', ','));
                    $('#modalHPS').text("Rp " + hps.format(2, 3, '.', ','));
                    $('#modalPenawaran').text("Rp " + penawaran.format(2, 3, '.', ','));
                    $('#modalStatus').text(status);
                    $('#myModal').modal('show');
                });

                $('#next').click({param: 0}, relay);
                $('#prev').click({param: 1}, relay);

                var start = 1;
                var fin = 200;
                var lastRange;
                var step = Math.floor(allID.length / 200);
                if (allID.length % 200 == 0) {
                    lastRange = (step * 200) - 199;
                    $('#count').text("1 / " + step);
                }
                else {
                    lastRange = (step * 200) + 1;
                    step += 1;
                    $('#count').text("1 / " + step);
                }
                console.log(step);
                console.log(lastRange);

                var tempstep = 1;

                function relay(event) {
                    if ((tempstep > 1) && (event.data.param == 1)) {
                        console.log("prev");
                        start -= 200;
                        fin = start+199;
                        tempstep -= 1;
                        $('#count').text(tempstep + " / " + step);
                    }
                    if ((tempstep < step) && (event.data.param == 0)) {
                        console.log("next");
                        start += 200;
                        fin = start+199;
                        tempstep += 1;
                        $('#count').text(tempstep + " / " + step);
                    }
                    var update = {
                        xaxis: {
                            type: 'category',
                            title: 'ID Lelang',
                            nticks: 10,
                            tickwidth: 3,
                            tickcolor: 'rgb(83, 94, 126)',
                            range: [start, fin]
                        }
                    };
                    Plotly.relayout(myPlot, update);
                }
            });

        });
    </script>
</body>
</html>