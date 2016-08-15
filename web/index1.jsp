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
                        <a class="active-menu" href="index1.jsp"><i class="fa fa-bar-chart fa-fw"></i> Tipe Indikasi 1</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-bar-chart fa-fw"></i> Tipe Indikasi 2<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="index2a.jsp">Tipe Indikasi 2A</a>
                            </li>
                            <li>
                                <a href="index2b.jsp">Tipe Indikasi 2B</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="index3.jsp"><i class="fa fa-bar-chart fa-fw"></i> Tipe Indikasi 3</a>
                    </li>
                    <li>
                        <a href="index4.jsp"><i class="fa fa-bar-chart fa-fw"></i> Tipe Indikasi 4</a>
                    </li>
                    <li>
                        <a href="index4.jsp"><i class="fa fa-bar-chart fa-fw"></i> Tipe Indikasi 5</a>
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
                            Tipe Indikasi 1 <button id="qm-info"><small><i class="fa fa-question-circle"></i></small></button>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12" id="info-row">
                        <div class="info">
                            Tipe indikasi 1 berdasarkan indikasi tahapan yang tidak dilakukan sebagaimana mestinya; menggunakan data selisih waktu antara masing-masing tahapan lelang dan periode waktu masing-masing tahapan lelang. <br />
                            <small>Selisih waktu atau periode tahapan yang terlalu cepat atau terlalu lambat dapat diambil dua asumsi. Pertama, kegiatan dalam tahapan yang bersangkutan tidak dilakukan sebagaimana mestinya sehingga dilakukan dengan terlalu cepat atau terlalu lambat. Kedua, tahapan tersebut fiktif atau hanya dicatat pada sistem namun tidak benar-benar dilakukan. Kedua asumsi ini bisa jadi merupakan indikasi adanya ‘permainan’ dalam tahapan-tahapan lelang tersebut, yang mengarah pada kemungkinan terjadinya kerjasama antara pegawai Unit Layanan Pengadaan yang melakukan entri tahapan lelang yang bersangkutan dengan calon penyedia.</small>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default" data-num="0">
                            <div class="row header-chart">
                                <div class="col-md-12">
                                    <div class="col-md-6">
                                        <h2>Tipe Indikasi 1 - Periode Lelang</h2>
                                    </div>
                                    <div class="col-md-6">
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
                                                    <div class="col-md-2">
                                                        <button type="button" class="btn btn-default nextprev" id="prev"><i class="fa fa-angle-left fa-fw"></i></button>
                                                    </div>
                                                    <div class="col-md-4 col-md-offset-1" id="count" style="text-align: center;">
                                                        1 / 20
                                                    </div>
                                                    <div class="col-md-2">
                                                        <button type="button" class="btn btn-default nextprev" id="next"><i class="fa fa-angle-right fa-fw"></i></button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div id="myDiv" style="width : 1000px; height: 500px;"><!-- Plotly chart will be drawn inside this DIV --></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default" data-num="1">
                            <div class="row header-chart">
                                <div class="col-md-12">
                                    <div class="col-md-8">
                                        <h2>Tipe Indikasi 1 - Rata-Rata Jeda Tahap Lelang</h2>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <select class="form-control lpse-data">
                                                        <option>Semua LPSE</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <select class="form-control year-data">
                                                        <option>Semua Tahun</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div id="myDiv2" style= "width : 1000px; height: 500px;"><!-- Plotly chart will be drawn inside this DIV --></div>
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

            d3.json('http://localhost:8080/php/getJSONT1Periode.php', function(error,data) {
                if (error) console.log(error);
                console.log(data);

                /*

                function unpack(data, key) {
                    return data.map(function(row) { return row[key]; });
                }

                */

                var allLPSE = (data.lpse).reverse(),
                        allYear = (data.tahun).reverse(),
                        allID = (data.id).reverse(),
                        allVal = (data.periodelelang).reverse(),
                        allName = (data.nama).reverse(),
                        allStatus = (data.status).reverse(),
                        allPagu = (data.pagu).reverse(),
                        allHPS = (data.hps).reverse(),
                        allPenawaranMenang = (data.penawaranmenang).reverse(),
                        allPemenang = (data.pemenang).reverse(),
                        listofLPSE = [],
                        currentLPSE,
                        listofYear = [],
                        currentYear,
                        currentVal = [],
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
                    currentID = [];
                    if ( (chosenLPSE === 'Semua LPSE') && (chosenYear === 'Semua Tahun') ) {
                        currentVal = allVal;
                        currentID = allID;
                    }
                    if ( (chosenLPSE === 'Semua LPSE') && (chosenYear !== 'Semua Tahun') ) {
                        for (var i = 0 ; i < allYear.length ; i++){
                            if ( allYear[i] == chosenYear ) {
                                currentVal.push(allVal[i]);
                                currentID.push(allID[i]);
                            }
                        }
                    }
                    if ( (chosenLPSE !== 'Semua LPSE') && (chosenYear === 'Semua Tahun') ) {
                        for (var i = 0 ; i < allLPSE.length ; i++){
                            if ( allLPSE[i] === chosenLPSE ) {
                                currentVal.push(allVal[i]);
                                currentID.push(allID[i]);
                            }
                        }
                    }
                    if ( (chosenLPSE !== 'Semua LPSE') && (chosenYear !== 'Semua Tahun') ) {
                        for (var i = 0 ; i < allLPSE.length ; i++){
                            if ( (allLPSE[i] === chosenLPSE) &&  (allYear[i] == chosenYear) ) {
                                currentVal.push(allVal[i]);
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
                        type: 'bar',
                        marker: {
                            color: 'rgb(101, 131, 155)',
                            size: 6
                        }
                    };

                    var data1 = [trace1];

                    var layout1 = {
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

                    Plotly.newPlot('myDiv', data1, layout1, {displayModeBar: true, displaylogo: false});

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
                var step = allID.length / 200;
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
                            nticks: 20,
                            tickwidth: 3,
                            tickcolor: 'rgb(83, 94, 126)',
                            range: [start, fin]
                        }
                    };
                    Plotly.relayout(myPlot, update);
                }
            });


            Plotly.d3.json("json/new/v1_t1meanselisih.json", function(rows) {

                function unpack(rows, key) {
                    return rows.map(function(row) { return row[key]; });
                }

                var allLPSE = unpack(rows, 'lpse'),
                        allYear = unpack(rows, 'tahun'),
                        allID = unpack(rows, 'id'),
                        allVal = unpack(rows, 'meanselisihtahap'),
                        listofLPSE = [],
                        currentLPSE,
                        listofYear = [],
                        currentYear,
                        currentVal = [],
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
                    currentID = [];
                    if ( (chosenLPSE === 'Semua LPSE') && (chosenYear === 'Semua Tahun') ) {
                        currentVal = allVal;
                        currentID = allID;
                    }
                    if ( (chosenLPSE === 'Semua LPSE') && (chosenYear !== 'Semua Tahun') ) {
                        for (var i = 0 ; i < allYear.length ; i++){
                            if ( allYear[i] == chosenYear ) {
                                currentVal.push(allVal[i]);
                                currentID.push(allID[i]);
                            }
                        }
                    }
                    if ( (chosenLPSE !== 'Semua LPSE') && (chosenYear === 'Semua Tahun') ) {
                        for (var i = 0 ; i < allLPSE.length ; i++){
                            if ( allLPSE[i] === chosenLPSE ) {
                                currentVal.push(allVal[i]);
                                currentID.push(allID[i]);
                            }
                        }
                    }
                    if ( (chosenLPSE !== 'Semua LPSE') && (chosenYear !== 'Semua Tahun') ) {
                        for (var i = 0 ; i < allLPSE.length ; i++){
                            if ( (allLPSE[i] === chosenLPSE) &&  (allYear[i] == chosenYear) ) {
                                currentVal.push(allVal[i]);
                                currentID.push(allID[i]);
                            }
                        }
                    }
                };

                setPlot('Semua LPSE', 'Semua Tahun');

                function setPlot(chosenLPSE, chosenYear) {
                    getLPSEandYearData(chosenLPSE, chosenYear);

                    var trace2 = {
                        x: currentID,
                        y: currentVal,
                        type: 'bar',
                        marker: {
                            color: 'rgb(101, 131, 155)',
                            size: 6
                        }
                    };

                    var data2 = [trace2];

                    var layout2 = {
                        xaxis: {
                            type: 'category',
                            title: 'ID Lelang',
                            nticks: 20,
                            tickwidth: 3,
                            tickcolor: 'rgb(83, 94, 126)',
                            range: [1, 200]
                        },
                        yaxis: {
                            title: 'Jeda waktu (dalam hari)',
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

                    Plotly.newPlot('myDiv2', data2, layout2, {displayModeBar: true, displaylogo: false});
                };

                var innerContainer = document.querySelector('[data-num="1"'),
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

                assignOptions(listofLPSE, lpseSelector);
                assignOptions(listofYear, yearSelector);

                function update(){
                    setPlot(lpseSelector.value, yearSelector.value);
                }

                lpseSelector.addEventListener('change', update, false);
                yearSelector.addEventListener('change', update, false);

                var myPlot2 = document.getElementById('myDiv2');

                myPlot2.on('plotly_click', function(data){
                    $('#myModal').modal('show');
                });
            });


            $("#qm-info").click(function(){
                $("#info-row").toggle();
            });
        });
    </script>
    <!-- jQuery Js -->
    <script src="assets/js/jquery-1.10.2.js"></script>
    <!-- Bootstrap Js -->
    <script src="assets/js/bootstrap.min.js"></script>
    <!-- Metis Menu Js -->
    <script src="assets/js/jquery.metisMenu.js"></script>

    <!-- Custom Js -->
    <script src="assets/js/custom-scripts.js"></script>
</body>
</html>