/**
 * Created by Rosiana on 5/3/2016.
 */

import java.io.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.Scanner;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.*;
 
public class Crawler {

    //mysql cred
    static final String myDriver = "org.gjt.mm.mysql.Driver";  
	static final String myUrl = "jdbc:mysql://localhost/fdproject";
	static final String user = "root";
	static final String pass = "";
    
    //hitung jumlah halaman
    public static int getPageNum(String url) throws IOException {
    	Document doc = Jsoup.connect("http://" + url + "/eproc/lelang/").timeout(50000).get();
    	Elements page = doc.select("div.t-data-grid-pager > a");
		int pagenum;
		int pagesize = page.size();
		System.out.println(pagesize);
		if (page.size() == 22) {
			pagenum = Integer.parseInt((page.get(21)).text());
		}
		else {
			pagenum = page.size() + 1;
		}
    	return pagenum;
    }
    
    //ambil semua kode lelang
    public static String[] getKodeLelang(String url, int pagenum) throws IOException { 
    	int linknum = 0;
    	Document docx = Jsoup.connect("http://" + url + "/eproc/lelang.gridtable.pager/" + pagenum + "?s=5").timeout(50000).get();
    	Elements entrilelanglast = docx.select("tr.horizLineTop");
    	linknum = entrilelanglast.size() + ((pagenum-1)*50);

    	String[] arrlelang = new String[linknum];
		System.out.println("linknum: " + linknum);
    	for (int i = 1; i <= pagenum; i++) {
    		Document doc = Jsoup.connect("http://" + url + "/eproc/lelang.gridtable.pager/" + i + "?s=5").timeout(50000).get();
    		Elements entrilelang = doc.select("tr.horizLineTop");
    		for (int j = 0; j < entrilelang.size(); j++) {
    			Elements linkinfolelang = doc.select("td.pkt_nama > b > a[href]");
				Element link = linkinfolelang.get(j);
				String linkstring = (link.attr("href")).replace("/eproc/lelang/view/", "");
				Elements tahaplelang = doc.select("td.tahap > a");
				Element tahap = tahaplelang.get(j);
				Elements adapemenang = doc.select("td.pkt_nama");
				Element pemenang = adapemenang.get(j);
				//identifikasi tahap
				//lelang selesai, ada pemenang
				if ((((tahap.text()).contains("selesai")) && ((pemenang.text()).contains("Pemenang"))) || (((tahap.text()).contains("Tidak")) && ((pemenang.text()).contains("Pemenang")))) {
					if (i == 1) {
	    				arrlelang[j] = "0 ";
	    			}
	    			else {
	    				arrlelang[((i-1)*50)+j] = "0 ";
	    			}
				}
				else {
					//lelang selesai, tdk ada pemenang
					if ((((tahap.text()).contains("selesai")) && ((pemenang.text()).contains("Pemenang")==false)) || (((tahap.text()).contains("Tidak")) && ((pemenang.text()).contains("Pemenang")==false))) {
						if (i == 1) {
							arrlelang[j] = "1 ";
						}
						else {
							arrlelang[((i-1)*50)+j] = "1 ";
						}
					}
					else {
						//lelang belum selesai, nama penyedia blm diumumkan
						if (((tahap.text()).contains("Pascakualifikasi") || (tahap.text()).contains("Upload Dokumen") || (tahap.text()).contains("Download Dokumen") || (tahap.text()).contains("Penjelasan") || (tahap.text()).contains("Pembukaan")) && ((tahap.text()).contains("Evaluasi")==false)) {
							if (i == 1) {
								arrlelang[j] = "2 ";
							}
							else {
								arrlelang[((i-1)*50)+j] = "2 ";
							}
						}
						else {
							//lelang belum, selesai, ada nama penyedia, masih eval
							if (((tahap.text()).contains("Evaluasi") || (tahap.text()).contains("Pembuktian") || (tahap.text()).contains("Upload Berita")) && ((tahap.text()).contains("Evaluasi")==false)) {
								if (i == 1) {
									arrlelang[j] = "3 ";
								}
								else {
									arrlelang[((i-1)*50)+j] = "3 ";
								}
							}
							//lelang belum selesai setelah eval, ada pemenang
							else {
								if ((tahap.text()).contains("Tidak") == false && (tahap.text()).contains("selesai") == false && (pemenang.text()).contains("Pemenang")) {
									if (i == 1) {
										arrlelang[j] = "4 ";
									}
									else {
										arrlelang[((i-1)*50)+j] = "4 ";
									}
								}
								else {
									//tdk ada jadwal
									if ((tahap.text()).contains("Tidak") == false && (tahap.text()).contains("selesai") == false && (pemenang.text()).contains("Pemenang")==false) {
										if (i == 1) {
											arrlelang[j] = "5 ";
										}
										else {
											arrlelang[((i-1)*50)+j] = "5 ";
										}
									}
								}
							}
						}
					}
				}

				if (i == 1) {
					arrlelang[j] += linkstring;
				}
				else {
					arrlelang[((i-1)*50)+j] += linkstring;
				}
			}
    	}
    	return arrlelang;
    }
    
    //ambil info lelang dari semua link
    public static String[][] getInfoLelang(String url, String lpse, String[] arrlelang) throws IOException {
    	String[][] infolelang = new String[arrlelang.length][8];
    	for (int i = 0; i < arrlelang.length; i++) {
    		int adapemenang = Integer.parseInt(arrlelang[i].substring(0,1));
			if (adapemenang == 0 || adapemenang == 4 || adapemenang == 5) {
				String link = arrlelang[i].substring(2);
				Document doc = Jsoup.connect("http://" + url + "/eproc/lelang/view/" + link).timeout(50000).get();
				Elements baris = doc.select("td.horizLine");
				Document doc2 = Jsoup.connect("http://" + url + "/eproc/lelang/pemenang/" + link).timeout(50000).get();
				Elements judul = doc2.select("td.TitleLeft");
				String pemenang = "";
				String penawaran = "";
				if (judul.size() == 10){
					Elements isi = doc2.select("td.horizLine");
					pemenang = (isi.get(6)).text();
					penawaran = (isi.get(9)).text();
				}
				else {
					pemenang = "-";
					penawaran = "-";
				}
				Element kode = baris.get(0);
				Element nama = baris.get(1);
				Element status = baris.get(3);
				Element pagu = baris.get(12);
				Element hps = baris.get(13);
				Element peserta = baris.get(baris.size()-1);
				infolelang[i][0] = kode.text();
				infolelang[i][1] = (nama.text()).replace(",","");
				if (status.text().contains("selesai")) {
					infolelang[i][2] = "0";
				}
				else {
					infolelang[i][2] = "1";
				}
				infolelang[i][3] = (((pagu.text()).replace(",00","").replace(".","")).replace(",",".")).replace("Rp ", "");
				infolelang[i][4] = (((hps.text()).replace(",00","").replace(".","")).replace(",",".")).replace("Rp ", "");
				infolelang[i][5] = (((penawaran).replace(",00","").replace(".","")).replace(",",".")).replace("Rp ", "");
				infolelang[i][6] = pemenang;
				infolelang[i][7] = (peserta.text()).replace(" peserta [Detil...]", "");
				System.out.println(infolelang[i][0] + " - " + infolelang[i][1] + " - " + infolelang[i][2] + " - " + infolelang[i][3] + " - " + infolelang[i][4] + " - " + infolelang[i][5] + " - " + infolelang[i][6] + " - " + infolelang[i][7]);
				dbTulisInfoLelang(lpse, infolelang[i]);
			}
			else {
				String link = arrlelang[i].substring(2);
				Document doc = Jsoup.connect("http://" + url + "/eproc/lelang/view/" + link).timeout(50000).get();
				Elements baris = doc.select("td.horizLine");
				Element kode = baris.get(0);
				Element nama = baris.get(1);
				Element status = baris.get(3);
				Element pagu = baris.get(12);
				Element hps = baris.get(13);
				Element peserta = baris.get(baris.size()-1);
				infolelang[i][0] = kode.text();
				infolelang[i][1] = (nama.text()).replace(",","");
				if (status.text().contains("selesai")) {
					infolelang[i][2] = "0";
				}
				else {
					infolelang[i][2] = "1";
				}
				infolelang[i][3] = (((pagu.text()).replace(",00","").replace(".","")).replace(",",".")).replace("Rp ", "");
				infolelang[i][4] = (((hps.text()).replace(",00","").replace(".","")).replace(",",".")).replace("Rp ", "");
				infolelang[i][5] = "-";
				infolelang[i][6] = "-";
				infolelang[i][7] = (peserta.text()).replace(" peserta [Detil...]", "");
				System.out.println(infolelang[i][0] + " - " + infolelang[i][1] + " - " + infolelang[i][2] + " - " + infolelang[i][3] + " - " + infolelang[i][4] + " - " + infolelang[i][5] + " - " + infolelang[i][6] + " - " + infolelang[i][7]);
				dbTulisInfoLelang(lpse, infolelang[i]);
			}
		}
    	return infolelang;
    }

	//ambil info lelang dari semua link
	public static String[][] getInfoLelangS(String url, String lpse) throws IOException {
		String[][] infolelang = new String[3519][8];
		FileInputStream fstream = new FileInputStream("kodelelang_jogjaprov1");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		//Read File Line By Line
		int i = 0;
		while ((strLine = br.readLine()) != null) {
			int adapemenang = Integer.parseInt(strLine.substring(0,1));
			if (adapemenang == 0 || adapemenang == 4 || adapemenang == 5) {
				String link = strLine.substring(2);
				Document doc = Jsoup.connect("http://" + url + "/eproc/lelang/view/" + link).timeout(50000).get();
				Elements baris = doc.select("td.horizLine");
				Document doc2 = Jsoup.connect("http://" + url + "/eproc/lelang/pemenang/" + link).timeout(50000).get();
				Elements judul = doc2.select("td.TitleLeft");
				String pemenang = "";
				String penawaran = "";
				if (judul.size() == 10){
					Elements isi = doc2.select("td.horizLine");
					pemenang = (isi.get(6)).text();
					penawaran = (isi.get(9)).text();
				}
				else {
					pemenang = "-";
					penawaran = "-";
				}
				Element kode = baris.get(0);
				Element nama = baris.get(1);
				Element status = baris.get(3);
				Element pagu = baris.get(12);
				Element hps = baris.get(13);
				Element peserta = baris.get(baris.size()-1);
				infolelang[i][0] = kode.text();
				infolelang[i][1] = (nama.text()).replace(",","");
				if (status.text().contains("selesai")) {
					infolelang[i][2] = "0";
				}
				else {
					infolelang[i][2] = "1";
				}
				infolelang[i][3] = (((pagu.text()).replace(",00","").replace(".","")).replace(",",".")).replace("Rp ", "");
				infolelang[i][4] = (((hps.text()).replace(",00","").replace(".","")).replace(",",".")).replace("Rp ", "");
				infolelang[i][5] = (((penawaran).replace(",00","").replace(".","")).replace(",",".")).replace("Rp ", "");
				infolelang[i][6] = pemenang;
				infolelang[i][7] = (peserta.text()).replace(" peserta [Detil...]", "");
				System.out.println(infolelang[i][0] + " - " + infolelang[i][1] + " - " + infolelang[i][2] + " - " + infolelang[i][3] + " - " + infolelang[i][4] + " - " + infolelang[i][5] + " - " + infolelang[i][6] + " - " + infolelang[i][7]);
				dbTulisInfoLelang(lpse, infolelang[i]);
			}
			else {
				String link = strLine.substring(2);
				Document doc = Jsoup.connect("http://" + url + "/eproc/lelang/view/" + link).timeout(50000).get();
				Elements baris = doc.select("td.horizLine");
				Element kode = baris.get(0);
				Element nama = baris.get(1);
				Element status = baris.get(3);
				Element pagu = baris.get(12);
				Element hps = baris.get(13);
				Element peserta = baris.get(baris.size()-1);
				infolelang[i][0] = kode.text();
				infolelang[i][1] = (nama.text()).replace(",","");
				if (status.text().contains("selesai")) {
					infolelang[i][2] = "0";
				}
				else {
					infolelang[i][2] = "1";
				}
				infolelang[i][3] = (((pagu.text()).replace(",00","").replace(".","")).replace(",",".")).replace("Rp ", "");
				infolelang[i][4] = (((hps.text()).replace(",00","").replace(".","")).replace(",",".")).replace("Rp ", "");
				infolelang[i][5] = "-";
				infolelang[i][6] = "-";
				infolelang[i][7] = (peserta.text()).replace(" peserta [Detil...]", "");
				System.out.println(infolelang[i][0] + " - " + infolelang[i][1] + " - " + infolelang[i][2] + " - " + infolelang[i][3] + " - " + infolelang[i][4] + " - " + infolelang[i][5] + " - " + infolelang[i][6] + " - " + infolelang[i][7]);
				dbTulisInfoLelang(lpse, infolelang[i]);
			}
			i++;
		}
		return infolelang;
	}

	public static String[][] getInfoLelangErr(String url, String lpse) throws IOException {
		String[][] infolelang = new String[959][8];
		FileInputStream fstream = new FileInputStream("kodelelang_jogjaprov_err");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		//Read File Line By Line
		int i = 0;
		while ((strLine = br.readLine()) != null) {
			int adapemenang = Integer.parseInt(strLine.substring(0,1));
			String link = strLine.substring(2);
			Document doc2 = Jsoup.connect("http://" + url + "/eproc/lelang/pemenang/" + link).timeout(50000).get();
			Elements baris = doc2.select("td.horizLine");
			String pemenang = "";
			String penawaran = "";
			if (baris.size() == 10){
				pemenang = (baris.get(6)).text();
				penawaran = (baris.get(9)).text();
			}
			else {
				pemenang = "-";
				penawaran = "-";
			}
			Element nama = baris.get(0);
			Element pagu = baris.get(4);
			Element hps = baris.get(5);
			infolelang[i][0] = link;
			infolelang[i][1] = (nama.text()).replace(",","");
			infolelang[i][2] = "0";
			infolelang[i][3] = (((pagu.text()).replace(",00","").replace(".","")).replace(",",".")).replace("Rp ", "");
			infolelang[i][4] = (((hps.text()).replace(",00","").replace(".","")).replace(",",".")).replace("Rp ", "");
			infolelang[i][5] = (((penawaran).replace(",00","").replace(".","")).replace(",",".")).replace("Rp ", "");
			infolelang[i][6] = pemenang;
			int c = baris.size()-10;
			infolelang[i][7] = "" + c;
			System.out.println(infolelang[i][0] + " - " + infolelang[i][1] + " - " + infolelang[i][2] + " - " + infolelang[i][3] + " - " + infolelang[i][4] + " - " + infolelang[i][5] + " - " + infolelang[i][6] + " - " + infolelang[i][7]);
			dbTulisInfoLelang(lpse, infolelang[i]);
			i++;
		}
		return infolelang;
	}

	//ambil info lelang dari semua link
	public static void getAllJumlahPesertaS() throws IOException {
		FileInputStream fstream = new FileInputStream("kodelelang_jogjaprov_nonerr");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		FileWriter writer = new FileWriter("jumlahpeserta_jogjaprov_nonerr");

		//Read File Line By Line
		while ((strLine = br.readLine()) != null) {
			String link = strLine.substring(2);
			Document doc = Jsoup.connect("http://lpse.jogjaprov.go.id/eproc/lelang/view/" + link).timeout(50000).get();
			Elements baris = doc.select("td.horizLine");
			Element peserta = baris.get(baris.size()-2);
			writer.append(peserta.text());
			writer.append("\n");
			System.out.println(peserta.text().replace(" Peserta [Detil...]", ""));
		}

		//Close the input stream
		br.close();
		writer.flush();
		writer.close();
	}

	public static void getAllJumlahPesertaErr() throws IOException {
		FileInputStream fstream = new FileInputStream("kodelelang_jogjaprov_err");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		FileWriter writer = new FileWriter("jumlahpeserta_jogjaprov_err");

		//Read File Line By Line
		while ((strLine = br.readLine()) != null) {
			String link = strLine.substring(2);
			Document doc = Jsoup.connect("http://lpse.jogjaprov.go.id/eproc/lelang/pemenang/" + link).timeout(50000).get();
			Elements baris = doc.select("table.border > tbody > tr");
			int x = baris.size() - 1;
			writer.append("" + x);
			writer.append("\n");
			System.out.println(baris.size());
		}

		//Close the input stream
		br.close();
		writer.flush();
		writer.close();
	}

	//ambil info lelang dari semua link
	public static int[] getAllJumlahPeserta(String url, String[] arrlelang) throws IOException {
		int[] jumlahpeserta = new int[arrlelang.length];
		for (int i = 0; i < arrlelang.length; i++) {
			String link = arrlelang[i].substring(2);
			Document doc = Jsoup.connect("http://" + url + "/eproc/lelang/view/" + link).timeout(50000).get();
			Elements baris = doc.select("td.horizLine");
			Element peserta = baris.get(baris.size()-2);
			jumlahpeserta[i] = Integer.parseInt((peserta.text()).replace(" Peserta [Detil...]", ""));
		}
		return jumlahpeserta;
	}
    
    //tulis info lelang ke file
	/*
    public static void tulisInfoLelang(String[][] infolelang) throws IOException {
    	//csv
    	FileWriter writer = new FileWriter("retrieved/semualelang.csv");
    	for (int i = 0; i < infolelang.length; i++) {
    		writer.append(infolelang[i][0]);
    		writer.append(",");
    		writer.append(infolelang[i][1]);
    		writer.append(",");
    		writer.append(infolelang[i][2]);
    		writer.append(",");
    		writer.append(infolelang[i][3]);
    		writer.append(",");
    		writer.append(infolelang[i][4]);
    		writer.append(",");
    		writer.append(infolelang[i][5]);
    		writer.append(",");
    		writer.append(infolelang[i][6]);
    		writer.append("\n");
    	}
    	writer.flush();
		writer.close();
    }
    */

    public static void dbTulisInfoLelang(String lpse, String[] infolelang) throws IOException {
    	//mysql
        Connection connect = null;
        PreparedStatement preparedStatement = null;
		try {
		    Class.forName(myDriver);
			Properties props = new Properties();
			props.put("user", user);
			props.put("password", pass);
			props.put("autoReconnect", "true");
			connect = DriverManager.getConnection(myUrl, props);

			preparedStatement = connect.prepareStatement("insert into  lelang values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

			for (int i = 0; i < infolelang.length; i++) {
				System.out.println(infolelang[0]);
				preparedStatement.setInt(1, Integer.parseInt(infolelang[0]));
				preparedStatement.setString(2, lpse);
				preparedStatement.setInt(3, 2016);
				preparedStatement.setString(4, infolelang[1]);
				preparedStatement.setInt(5, Integer.parseInt(infolelang[2]));
				float pagu = Float.parseFloat(infolelang[3].replace(",", ""));
				preparedStatement.setFloat(6, pagu);
				float hps = Float.parseFloat(infolelang[4].replace(",", ""));
				preparedStatement.setFloat(7, hps);
				String penawaranstring = infolelang[5];
				if (penawaranstring != "-") {
					float penawaran = Float.parseFloat(penawaranstring.replace(",", ""));
					preparedStatement.setFloat(8, penawaran);
				}
				else {
					preparedStatement.setObject(8, null);
				}
				String pemenang = infolelang[6];
				if (pemenang != "-") {
					preparedStatement.setString(9, pemenang);
				}
				else {
					preparedStatement.setObject(9, null);
				}

				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		}
        catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (preparedStatement != null)
                    connect.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (connect != null)
                    connect.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }

	//ambil tahap dari semua link
	public static String[][] getTahapLelang(String url, int kodelelang) throws IOException {
		Document doc = Jsoup.connect("http://" + url + "/eproc/lelang/tahap/" + kodelelang).timeout(50000).get();
		Elements baris = doc.select("tr");
		String[][] tahaplelang = new String[baris.size()-1][13];
		Elements entrinamatahap = doc.select("td.horizLine");
		Elements entrimulai = doc.select("td.horizLineSel");
		Elements entrisampai = doc.select("td.horizLine");
		for (int i = 0; i < baris.size()-1; i++) {
			if (i == 0) {
				Element namatahap = entrinamatahap.get(1);
				Element mulai = entrimulai.get(0);
				Element sampai = entrisampai.get(2);

				String datemulai = "";
				String timemulai = "";
				String datesampai = "";
				String timesampai = "";
				System.out.println("mulai " + mulai.text());
				System.out.println("sampai " + sampai.text());
				if (mulai.text().contains(":")==false || sampai.text().contains(":")==false) {
					tahaplelang[i][0] = namatahap.text();
					tahaplelang[i][1] = "-";
					tahaplelang[i][7] = "-";
				}
				else {
					datemulai = (mulai.text()).substring(0, mulai.text().length()-6).replace(" ","");
					timemulai = ((mulai.text()).substring(mulai.text().length()-6).replace(":", "")).replace(" ","");
					String yyyymulai = datemulai.substring(datemulai.length()-4);
					String mmmulainum = datemulai.substring(2, datemulai.length()-4);
					String mmmulai = "";
					switch (mmmulainum) {
						case "Januari":
							mmmulai = "01";
							break;
						case "Februari":
							mmmulai = "02";
							break;
						case "Maret":
							mmmulai = "03";
							break;
						case "April":
							mmmulai = "04";
							break;
						case "Mei":
							mmmulai = "05";
							break;
						case "Juni":
							mmmulai = "06";
							break;
						case "Juli":
							mmmulai = "07";
							break;
						case "Agustus":
							mmmulai = "08";
							break;
						case "September":
							mmmulai = "09";
							break;
						case "Oktober":
							mmmulai = "10";
							break;
						case "November":
							mmmulai = "11";
							break;
						case "Desember":
							mmmulai = "12";
							break;
						default:
							mmmulai = "00";
							break;
					}
					String ddmulai = datemulai.substring(0, 2);
					String hhmulai = timemulai.substring(0, 2);
					String minmulai = timemulai.substring(2, 4);
					String ssmulai = "00";

					datesampai = (sampai.text()).substring(0, sampai.text().length()-6).replace(" ", "");
					timesampai = ((sampai.text()).substring(sampai.text().length()-6).replace(":", "")).replace(" ", "");
					String yyyysampai = datesampai.substring(datesampai.length() - 4);
					String mmsampainum = datesampai.substring(2, datesampai.length() - 4);
					String mmsampai = "";
					switch (mmsampainum) {
						case "Januari":
							mmsampai = "01";
							break;
						case "Februari":
							mmsampai = "02";
							break;
						case "Maret":
							mmsampai = "03";
							break;
						case "April":
							mmsampai = "04";
							break;
						case "Mei":
							mmsampai = "05";
							break;
						case "Juni":
							mmsampai = "06";
							break;
						case "Juli":
							mmsampai = "07";
							break;
						case "Agustus":
							mmsampai = "08";
							break;
						case "September":
							mmsampai = "09";
							break;
						case "Oktober":
							mmsampai = "10";
							break;
						case "November":
							mmsampai = "11";
							break;
						case "Desember":
							mmsampai = "12";
							break;
						default:
							mmsampai = "00";
							break;
					}
					String ddsampai = datesampai.substring(0, 2);
					String hhsampai = timesampai.substring(0, 2);
					String minsampai = timesampai.substring(2, 4);
					String sssampai = "00";

					tahaplelang[i][0] = namatahap.text();
					tahaplelang[i][1] = yyyymulai;
					tahaplelang[i][2] = mmmulai;
					tahaplelang[i][3] = ddmulai;
					tahaplelang[i][4] = hhmulai;
					tahaplelang[i][5] = minmulai;
					tahaplelang[i][6] = ssmulai;
					tahaplelang[i][7] = yyyysampai;
					tahaplelang[i][8] = mmsampai;
					tahaplelang[i][9] = ddsampai;
					tahaplelang[i][10] = hhsampai;
					tahaplelang[i][11] = minsampai;
					tahaplelang[i][12] = sssampai;

					System.out.println(tahaplelang[i][0] + "-" + tahaplelang[i][1] + "-" + tahaplelang[i][2] + " " + tahaplelang[i][3] + ":" + tahaplelang[i][4] + ":" + tahaplelang[i][5]);
				}
			}
			else {
				Element namatahap = entrinamatahap.get((i*3)+1);
				Element mulai = entrimulai.get((i*2));
				Element sampai = entrisampai.get((i*3)+2);

				String datemulai = "";
				String timemulai = "";
				String datesampai = "";
				String timesampai = "";
				System.out.println("mulai " + mulai.text());
				System.out.println("sampai " + sampai.text());
				if (mulai.text().contains(":")==false || sampai.text().contains(":")==false) {
					tahaplelang[i][0] = namatahap.text();
					tahaplelang[i][1] = "-";
					tahaplelang[i][7] = "-";
				}
				else {
					datemulai = (mulai.text()).substring(0, mulai.text().length()-6).replace(" ","");
					timemulai = ((mulai.text()).substring(mulai.text().length()-6).replace(":", "")).replace(" ","");
					String yyyymulai = datemulai.substring(datemulai.length()-4);
					String mmmulainum = datemulai.substring(2, datemulai.length()-4);
					String mmmulai = "";
					switch (mmmulainum) {
						case "Januari":
							mmmulai = "01";
							break;
						case "Februari":
							mmmulai = "02";
							break;
						case "Maret":
							mmmulai = "03";
							break;
						case "April":
							mmmulai = "04";
							break;
						case "Mei":
							mmmulai = "05";
							break;
						case "Juni":
							mmmulai = "06";
							break;
						case "Juli":
							mmmulai = "07";
							break;
						case "Agustus":
							mmmulai = "08";
							break;
						case "September":
							mmmulai = "09";
							break;
						case "Oktober":
							mmmulai = "10";
							break;
						case "November":
							mmmulai = "11";
							break;
						case "Desember":
							mmmulai = "12";
							break;
						default:
							mmmulai = "00";
							break;
					}
					String ddmulai = datemulai.substring(0, 2);
					String hhmulai = timemulai.substring(0, 2);
					String minmulai = timemulai.substring(2, 4);
					String ssmulai = "00";

					datesampai = (sampai.text()).substring(0, sampai.text().length()-6).replace(" ", "");
					timesampai = ((sampai.text()).substring(sampai.text().length()-6).replace(":", "")).replace(" ", "");
					String yyyysampai = datesampai.substring(datesampai.length() - 4);
					String mmsampainum = datesampai.substring(2, datesampai.length() - 4);
					String mmsampai = "";
					switch (mmsampainum) {
						case "Januari":
							mmsampai = "01";
							break;
						case "Februari":
							mmsampai = "02";
							break;
						case "Maret":
							mmsampai = "03";
							break;
						case "April":
							mmsampai = "04";
							break;
						case "Mei":
							mmsampai = "05";
							break;
						case "Juni":
							mmsampai = "06";
							break;
						case "Juli":
							mmsampai = "07";
							break;
						case "Agustus":
							mmsampai = "08";
							break;
						case "September":
							mmsampai = "09";
							break;
						case "Oktober":
							mmsampai = "10";
							break;
						case "November":
							mmsampai = "11";
							break;
						case "Desember":
							mmsampai = "12";
							break;
						default:
							mmsampai = "00";
							break;
					}
					String ddsampai = datesampai.substring(0, 2);
					String hhsampai = timesampai.substring(0, 2);
					String minsampai = timesampai.substring(2, 4);
					String sssampai = "00";

					tahaplelang[i][0] = namatahap.text();
					tahaplelang[i][1] = yyyymulai;
					tahaplelang[i][2] = mmmulai;
					tahaplelang[i][3] = ddmulai;
					tahaplelang[i][4] = hhmulai;
					tahaplelang[i][5] = minmulai;
					tahaplelang[i][6] = ssmulai;
					tahaplelang[i][7] = yyyysampai;
					tahaplelang[i][8] = mmsampai;
					tahaplelang[i][9] = ddsampai;
					tahaplelang[i][10] = hhsampai;
					tahaplelang[i][11] = minsampai;
					tahaplelang[i][12] = sssampai;

					System.out.println(tahaplelang[i][0] + "-" + tahaplelang[i][1] + "-" + tahaplelang[i][2] + " " + tahaplelang[i][3] + ":" + tahaplelang[i][4] + ":" + tahaplelang[i][5]);
				}
			}
		}
		return tahaplelang;
	}
    
    //ambil peserta dari semua link
    public static String[] getPesertaLelang(String url, int kodelelang, int status) throws IOException {
    	Document doc = Jsoup.connect("http://" + url + "/eproc/rekanan/lelangpeserta/" + kodelelang).timeout(50000).get();
		Elements baris = doc.select("tr");
    	String[] pesertalelang = new String[baris.size()-1];

		System.out.println("lelang " + kodelelang);
    	switch (status) {
    		case 0:
				Elements baristitle0 = doc.select("th.titleTop");
				int jumlahkolom0 = baristitle0.size();
				Elements entripeserta0 = doc.select(".horizLineTop");
				for (int i = 0; i < baris.size()-1; i++) {
					if (i == 0) {
						Element peserta = entripeserta0.get(1);
						String pesertastring = peserta.text();
						System.out.println(pesertastring);
						pesertalelang[i] = pesertastring.substring(0, pesertastring.length()-23);
					}
					else {
						Element peserta = entripeserta0.get((i*jumlahkolom0)+1);
						String pesertastring = peserta.text();
						pesertalelang[i] = pesertastring.substring(0, pesertastring.length()-23);
					}
				}
				break;
    		case 1:
				Elements baristitle1 = doc.select("th.titleTop");
				int jumlahkolom1 = baristitle1.size();
				Elements entripeserta1 = doc.select(".horizLineTop");
				for (int i = 0; i < baris.size()-1; i++) {
					if (i == 0) {
						Element peserta = entripeserta1.get(1);
						String pesertastring = peserta.text();
						pesertalelang[i] = pesertastring.substring(0, pesertastring.length()-23);
					}
					else {
						Element peserta = entripeserta1.get((i * jumlahkolom1) + 1);
						String pesertastring = peserta.text();
						System.out.println(pesertastring);
						pesertalelang[i] = pesertastring.substring(0, pesertastring.length() - 23);
					}
				}
				break;
    		case 2:
				Elements entripeserta2 = doc.select("td.TitleLeft");
				for (int i = 0; i < entripeserta2.size(); i++) {
					pesertalelang[i] = "Penyedia " + (i+1);
				}
				break;
    		case 3:
				Elements entripeserta3 = doc.select("td.horizLine");
				for (int i = 0; i < baris.size()-1; i++) {
					Element peserta = entripeserta3.get(i);
					String pesertastring = peserta.text();
					pesertalelang[i] = pesertastring.substring(0, pesertastring.length());
				}
				break;
			case 4:
				Elements baristitle4 = doc.select("th.titleTop");
				int jumlahkolom4 = baristitle4.size();
				Elements entripeserta4 = doc.select(".horizLineTop");
				for (int i = 0; i < baris.size()-1; i++) {
					if (i == 0) {
						Element peserta = entripeserta4.get(1);
						String pesertastring = peserta.text();
						pesertalelang[i] = pesertastring.substring(0, pesertastring.length()-23);
					}
					else {
						Element peserta = entripeserta4.get((i*jumlahkolom4)+1);
						String pesertastring = peserta.text();
						pesertalelang[i] = pesertastring.substring(0, pesertastring.length()-23);
					}
				}
				break;
			default:
				Elements entripesertad = doc.select("td.horizLine");
				for (int i = 0; i < baris.size()-1; i++) {
					Element peserta = entripesertad.get(i);
					String pesertastring = peserta.text();
					pesertalelang[i] = pesertastring.substring(0, pesertastring.length());
				}
            	break;
    	}
    	return pesertalelang;
    }
    
    //tulis peserta ke file
    /*
    public static void tulisPesertaLelang(String[][] pesertalelang, int kodelelang) throws IOException {
		FileWriter writer = new FileWriter("retrieved/peserta_" + kodelelang + ".csv");
    	for (int i = 0; i < pesertalelang.length; i++) {
    		String clean = null;
    		writer.append(pesertalelang[i][0]);
    		writer.append(",");
    		clean = pesertalelang[i][1].replace(",","");
    		writer.append(clean);
    		writer.append(",");
    		clean = (((pesertalelang[i][2].replace(".","")).replace("Rp", "")).replace(" ", "")).replace(",00", "");
    		writer.append(clean);
    		writer.append(",");
    		clean = (((pesertalelang[i][3].replace(".","")).replace("Rp", "")).replace(" ", "")).replace(",00", "");
    		writer.append(clean);
    		writer.append(",");
    		clean = pesertalelang[i][4];
    		writer.append(clean);
    		writer.append("\n");
    	}
    	writer.flush();
		writer.close();
    }
    */

	public static void dbTulisPesertaLelang(String[] pesertalelang, int kodelelang) throws IOException {
		//mysql
        Connection connect = null;
        PreparedStatement preparedStatement = null;
		try {
			Class.forName(myDriver);
			Properties props = new Properties();
			props.put("user", user);
			props.put("password", pass);
			props.put("autoReconnect", "true");
			connect = DriverManager.getConnection(myUrl, props);

			preparedStatement = connect.prepareStatement("insert into  peserta values (?, ?)");

			for (int i = 0; i < pesertalelang.length; i++) {
				preparedStatement.setInt(1, kodelelang);
				preparedStatement.setString(2, pesertalelang[i]);
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		}
        catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (preparedStatement != null)
                    connect.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (connect != null)
                    connect.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
	}

	public static void dbTulisPesertaLelangS(String[] pesertalelang, int kodelelang, int jumprev, FileWriter writer) throws IOException {
		for (int i = 0; i < pesertalelang.length; i++) {
			int idpeserta = jumprev + i + 1;
			writer.append("" + idpeserta);
			writer.append(",");
			writer.append("" + kodelelang);
			writer.append(",");
			writer.append("" + pesertalelang[i]);
			writer.append("\n");
		}
	}
    
    //ambil peserta di semua lelang
    public static void getAllPesertaLelang(String url, String[] kodelelang, int[] jumlahpeserta) throws IOException {
		int jumprev = 0;
    	for (int i = 0; i < kodelelang.length; i++) {
    		String[] temppeserta = new String[jumlahpeserta[i]];
    		int lelangnum = Integer.parseInt(kodelelang[i].substring(2));
    		int status = Integer.parseInt(kodelelang[i].substring(0,1));
    		temppeserta = getPesertaLelang(url,lelangnum,status);
    		//tulisPesertaLelang(temppeserta, lelangnum);
			dbTulisPesertaLelang(temppeserta, lelangnum);
			jumprev += temppeserta.length;
    	}
    }

	public static void getAllPesertaLelangS(String url) throws IOException {
		FileInputStream fstream = new FileInputStream("jumlahpeserta_jogjaprov_err");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		FileInputStream fstream2 = new FileInputStream("kodelelang_jogjaprov_err");
		BufferedReader br2 = new BufferedReader(new InputStreamReader(fstream2));

		String strLine;
		String strLine2;

		//Read File Line By Line
		int i = 0;
		while (((strLine = br.readLine()) != null) && ((strLine2 = br2.readLine()) != null)) {
			int jumlahpeserta = Integer.parseInt(strLine);
			String[] temppeserta = new String[jumlahpeserta];
			int lelangnum = Integer.parseInt(strLine2.substring(2));
			int status = Integer.parseInt(strLine2.substring(0,1));
			temppeserta = getPesertaLelang(url,lelangnum,status);
			//tulisPesertaLelang(temppeserta, lelangnum);
			dbTulisPesertaLelang(temppeserta, lelangnum);
			System.out.println(lelangnum);
			i++;
		}

		//Close the input stream
		br.close();
	}

	public static void dbTulisTahapLelang(String[][] tahaplelang, int kodelelang) throws IOException {
		//mysql
        Connection connect = null;
        PreparedStatement preparedStatement = null;
		try {
			Class.forName(myDriver);
			Properties props = new Properties();
			props.put("user", user);
			props.put("password", pass);
			props.put("autoReconnect", "true");
			connect = DriverManager.getConnection(myUrl, props);

			preparedStatement = connect.prepareStatement("insert into  tahap values (?, ?, ?, ?)");

			for (int i = 0; i < tahaplelang.length; i++) {
				preparedStatement.setInt(1, kodelelang);
				preparedStatement.setString(2, tahaplelang[i][0]);
				if (tahaplelang[i][1] == "-") {
					preparedStatement.setObject(3, null);
				}
				else {
					Timestamp tsmulai = Timestamp.valueOf(	tahaplelang[i][1] + "-" +
						tahaplelang[i][2] + "-" +
						tahaplelang[i][3] + " " +
						tahaplelang[i][4] + ":" +
						tahaplelang[i][5] + ":" +
						tahaplelang[i][6] + ".000000000");
					preparedStatement.setTimestamp(3, tsmulai);
				}
				if (tahaplelang[i][7] == "-") {
					preparedStatement.setObject(4, null);
				}
				else {
					Timestamp tssampai = Timestamp.valueOf(	tahaplelang[i][7] + "-" +
							tahaplelang[i][8] + "-" +
							tahaplelang[i][9] + " " +
							tahaplelang[i][10] + ":" +
							tahaplelang[i][11] + ":" +
							tahaplelang[i][12] + ".000000000");
					preparedStatement.setTimestamp(4, tssampai);
				}
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		}
        catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (preparedStatement != null)
                    connect.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (connect != null)
                    connect.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
	}

	public static int[] getAllJumlahTahap(String url, String[] kodelelang) throws IOException {
		int jumlahtahap[] = new int[kodelelang.length];
		for (int i = 0; i < kodelelang.length; i++) {
			int lelangnum = Integer.parseInt(kodelelang[i].substring(2));
			Document doc = Jsoup.connect("http://" + url + "/eproc/lelang/tahap/" + lelangnum).timeout(50000).get();
			Elements baris = doc.select("tr");
			jumlahtahap[i] = baris.size() - 1;
		}
		return jumlahtahap;
	}

	public static int[] getAllJumlahTahapS() throws IOException {
		String[] kodelelang = new String[4852];
		FileInputStream fstream = new FileInputStream("kodelelang_jogjaprov_nonerr");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		FileWriter writer = new FileWriter("jumlahtahap_jogjaprov_nonerr");

		String strLine;

		int i = 0;
		while ((strLine = br.readLine()) != null) {
			kodelelang[i] = strLine;
			i++;
		}

		int jumlahtahap[] = new int[kodelelang.length];
		for (int j = 0; j < kodelelang.length; j++) {
			int lelangnum = Integer.parseInt(kodelelang[j].substring(2));
			Document doc = Jsoup.connect("http://lpse.jogjaprov.go.id/eproc/lelang/tahap/" + lelangnum).timeout(50000).get();
			Elements baris = doc.select("tr");
			jumlahtahap[j] = baris.size() - 1;
			System.out.println(jumlahtahap[j]);
			writer.append("" + jumlahtahap[j]);
			writer.append("\n");
		}
		writer.flush();
		writer.close();
		return jumlahtahap;
	}

	//ambil tahap di semua lelang
	public static void getAllTahapLelang(String url, String[] kodelelang, int[] jumlahtahap) throws IOException {
		for (int i = 0; i < kodelelang.length; i++) {
			String[][] temptahap = new String[jumlahtahap[i]][12];
			int lelangnum = Integer.parseInt(kodelelang[i].substring(2));
			temptahap = getTahapLelang(url, lelangnum);
			//tulisTahapLelang(temptahap, lelangnum);
			dbTulisTahapLelang(temptahap, lelangnum);
		}
	}

	//ambil tahap di semua lelang
	public static void getAllTahapLelangS(String url) throws IOException {
		FileInputStream fstream = new FileInputStream("kodelelang_jogjaprov_nonerr");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		FileInputStream fstream2 = new FileInputStream("jumlahtahap_jogjaprov_nonerr");
		BufferedReader br2 = new BufferedReader(new InputStreamReader(fstream2));

		String strLine;
		String strLine2;

		String[] kodelelang = new String[3216];
		int[] jumlahtahap = new int[3216];

		int i = 0;
		while (((strLine = br.readLine()) != null) && ((strLine2 = br2.readLine()) != null)) {
			kodelelang[i] = strLine;
			jumlahtahap[i] = Integer.parseInt(strLine2);
			i++;
		}

		for (int j = 0; j < kodelelang.length; j++) {
			String[][] temptahap = new String[jumlahtahap[j]][12];
			int lelangnum = Integer.parseInt(kodelelang[j].substring(2));
			temptahap = getTahapLelang(url, lelangnum);
			//tulisTahapLelang(temptahap, lelangnum);
			dbTulisTahapLelang(temptahap, lelangnum);
		}
	}

	public static void emptyInfoLelang() throws IOException {
        Connection connect = null;
        PreparedStatement preparedStatement = null;
		try {
			Class.forName(myDriver);
			Properties props = new Properties();
			props.put("user", user);
			props.put("password", pass);
			props.put("autoReconnect", "true");
			connect = DriverManager.getConnection(myUrl, props);

			preparedStatement = connect.prepareStatement("delete from  lelang");

			preparedStatement.executeUpdate();
		}
        catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (preparedStatement != null)
                    connect.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (connect != null)
                    connect.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
	}

	public static void emptyPesertaLelang() throws IOException {
        Connection connect = null;
        PreparedStatement preparedStatement = null;
		try {
			Class.forName(myDriver);
			Properties props = new Properties();
			props.put("user", user);
			props.put("password", pass);
			props.put("autoReconnect", "true");
			connect = DriverManager.getConnection(myUrl, props);

			preparedStatement = connect.prepareStatement("delete from  peserta");

			preparedStatement.executeUpdate();
		}
        catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (preparedStatement != null)
                    connect.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (connect != null)
                    connect.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
	}

	public static void emptyTahapLelang() throws IOException {
        Connection connect = null;
        PreparedStatement preparedStatement = null;
		try {
			Class.forName(myDriver);
			Properties props = new Properties();
			props.put("user", user);
			props.put("password", pass);
			props.put("autoReconnect", "true");
			connect = DriverManager.getConnection(myUrl, props);

			preparedStatement = connect.prepareStatement("delete from  tahap");

			preparedStatement.executeUpdate();
		}
        catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (preparedStatement != null)
                    connect.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (connect != null)
                    connect.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
	}

	public static String[] filterYear(String[] arrlelang) throws IOException {
		String[] filter = new String[2354];
		int j = 0;
		System.out.println("size arrlelang: " + arrlelang.length);
		loop:
		for (int i = 0; i < arrlelang.length; i++) {
			String link = arrlelang[i].substring(2);
			int linkint = Integer.parseInt(link);
			if (linkint >= 22246014 && linkint <= 25666014) {
				System.out.println(linkint);
				System.out.println(j);
				filter[j] = arrlelang[i];
				j++;
			}
			else {
				if (linkint < 22246014) {
					break loop;
				}
			}
		}
		return filter;
	}

	public static void Crawl() throws IOException {
    	String url = "";
		String lpse = "";
    	Scanner reader = new Scanner(System.in);
    	System.out.println("Enter 1/2/3: ");
    	int choice = reader.nextInt();
    	
    	switch (choice) {
	    	case 1:
	    		url = "lpse.kemenkeu.go.id";
				lpse = "Kementerian Keuangan";
	    		break;
    		case 2:
    			url = "lpse.kemdikbud.go.id";
				lpse = "Kementerian Pendidikan dan Kebudayaan";
    			break;
    		case 3:
    			url = "lpse.jatengprov.go.id";
				lpse = "Provinsi Jawa Tengah";
    			break;
			case 4:
				url = "lpse.sumutprov.go.id";
				lpse = "Provinsi Sumatera Utara";
				break;
    		default:
    			url = "lpse.itb.ac.id";
	    		break;
    	}
    	System.out.println(url);

		int pagenum = getPageNum(url);
		System.out.println(pagenum);

		/*
		String[] kodelelang = getKodeLelang(url,pagenum);

		for (int i = 0; i < kodelelang.length; i++) {
			System.out.println(kodelelang[i]);
		}
		*/

		String[][] infolelang = getInfoLelangS(url,lpse);
		//emptyInfoLelang();
		//System.out.println("DBTulisLelang");
		//getAllJumlahPesertaS();
		//int[] jumlahpeserta = getAllJumlahPeserta(kodelelang);
		//int[] jumlahtahap = getAllJumlahTahapS();
		//emptyPesertaLelang();
		//getAllPesertaLelang(url,kodelelangf,jumlahpeserta);
		//getAllPesertaLelangS(url);
		//emptyTahapLelang();
		//getAllTahapLelangS(url);
		System.out.println("Selesai");
	} 
}