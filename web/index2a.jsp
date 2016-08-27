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
                                <a class="active-menu" href="index2a.jsp">Tipe Indikasi 2A</a>
                            </li>
                            <li>
                                <a href="index2b.jsp">Tipe Indikasi 2B</a>
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
                            <div id="qm-info"><h1>Tipe Indikasi 2A <small><i class="fa fa-question-circle"></i></small></h1></div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12" id="info-row">
                        <div class="info">
                            Tipe indikasi 2A berdasarkan indikasi kerjasama antarpeserta atau beberapa perusahaan yang dimiliki oknum yang sama; menggunakan -  data peserta lelang (untuk menunjukkan kemiripan peserta masing-masing lelang, digambarkan dengan titik-titik lelang yang berdekatan).<br />
                            <small>Sekelompok peserta (atau satu pihak yang memiliki beberapa nama perusahaan) yang bergantian memenangkan lelang merupakan salah satu indikasi terjadinya persaingan lelang yang tidak sehat. Sekelompok peserta ini bisa jadi melakukan multiple bidding untuk menyamarkan harga persaingan sebenarnya sehingga calon peserta lain tidak berani ikut mengajukan penawaran. Pada akhirnya, salah satu dari peserta pelaku multiple bidding tersebut yang akan dimenangkan. In-auction fraud tipe bidding rings juga dilakukan dengan membentuk kelompok peserta.</small>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default" data-num="0">
                            <div class="row header-chart">
                                <div class="col-md-12">
                                    <div class="col-md-5">
                                        <h2>Grafik Keterkaitan Lelang</h2>
                                    </div>
                                    <div class="col-md-7">
                                        <div class="row">
                                            <div class="col-md-4">
                                            </div>
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
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div id="myDiv" style="width : 1000px; height: 1000px;"><!-- Plotly chart will be drawn inside this DIV --></div>
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/numeric/1.2.6/numeric.min.js"></script>
<script src="js/mdsx.js"></script>
<script type="text/javascript" src="js/jquery.tipsy.js"></script>
<link href="css/tipsy.css" rel="stylesheet" type="text/css" />
</body>
</html>
