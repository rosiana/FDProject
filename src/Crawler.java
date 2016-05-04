/**
 * Created by Rosiana on 5/3/2016.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    	Document doc = Jsoup.connect("http://" + url + "/eproc/lelang/").get(); 
    	Elements page = doc.select("div.t-data-grid-pager > a");
    	return page.size() + 1;
    }
    
    //ambil semua kode lelang
    public static String[] getKodeLelang(String url, int pagenum) throws IOException { 
    	int linknum = 0;
    	for (int i = 1; i <= pagenum; i++) {
    		Document doc = Jsoup.connect("http://" + url + "/eproc/lelang.gridtable.pager/" + i + "?s=5").get(); 
    		Elements entrilelang = doc.select("tr.horizLineTop");
    		linknum += entrilelang.size();
    	}
    	String[] arrlelang = new String[linknum];
    	for (int i = 1; i <= pagenum; i++) {
    		Document doc = Jsoup.connect("http://lpse.itb.ac.id/eproc/lelang.gridtable.pager/" + i + "?s=5").get(); 
    		Elements entrilelang = doc.select("tr.horizLineTop");
    		for (int j = 0; j < entrilelang.size(); j++) {
    			Elements linkinfolelang = doc.select("td.pkt_nama > b > a[href]");
				Element link = linkinfolelang.get(j);
				String linkstring = (link.attr("href")).replace("/eproc/lelang/view/", "");
				Elements tahaplelang = doc.select("td.tahap > a");
				Element tahap = tahaplelang.get(j);
				Elements adapemenang = doc.select("td.pkt_nama");
				Element pemenang = adapemenang.get(j);
				//lelang selesai, ada pemenang, dievaluasi (normal)
				if ((tahap.text()).contains("selesai") && (pemenang.text()).contains("Pemenang") && (pemenang.text()).contains("Penawaran")) { 
					if (i == 1) {
	    				arrlelang[j] = "0 ";
	    			}
	    			else {
	    				arrlelang[((i-1)*50)+j] = "0 ";
	    			}
				}
				else {
					//lelang selesai, tidak ada pemenang, dievaluasi
					if ((tahap.text()).contains("selesai") && ((pemenang.text()).contains("Pemenang") == false) && (pemenang.text()).contains("Penawaran")) {
						if (i == 1) {
		    				arrlelang[j] = "1 ";
		    			}
		    			else {
		    				arrlelang[((i-1)*50)+j] = "1 ";
		    			}
					}
					else {
						//lelang selesai, tidak dievaluasi
						if ((tahap.text()).contains("selesai") && ((pemenang.text()).contains("Pemenang") == false) && (pemenang.text()).contains("Penawaran") == false) {
							if (i == 1) {
			    				arrlelang[j] = "2 ";
			    			}
			    			else {
			    				arrlelang[((i-1)*50)+j] = "2 ";
			    			}
						}
						else {
							//lelang belum selesai
							//tahap upload
							if ((tahap.text()).contains("selesai") == false && (tahap.text()).contains("Upload")) {
								if (i == 1) {
									arrlelang[j] = "3 ";
								}
								else {
									arrlelang[((i-1)*50)+j] = "3 ";
								}
							}
							//tahap evaluasi
							else {
								if ((tahap.text()).contains("selesai") == false && (tahap.text()).contains("Evaluasi")) {
									if (i == 1) {
										arrlelang[j] = "4 ";
									}
									else {
										arrlelang[((i-1)*50)+j] = "4 ";
									}
								}
								//tahap pengumuman pemenang dst
								else {
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
    public static String[][] getInfoLelang(String url, String[] arrlelang) throws IOException {
    	String[][] infolelang = new String[arrlelang.length][7];    	
    	for (int i = 0; i < arrlelang.length; i++) {
    		String link = arrlelang[i].substring(2);
    		// int tahap = Integer.parseInt(arrlelang[i].substring(0,1));
    		Document doc = Jsoup.connect("http://" + url + "/eproc/lelang/view/" + link).get(); 
    		Elements baris = doc.select("td.horizLine");
    		Element kode = baris.get(0);
    		Element nama = baris.get(1);
    		Element status = baris.get(3);
    		Element agency = baris.get(4);
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
    		
    		infolelang[i][3] = (agency.text()).replace(",",""); 
    		infolelang[i][4] = (pagu.text().replaceAll("[^\\d,]", "")).replace(",00", "");
    		infolelang[i][5] = (hps.text().replaceAll("[^\\d,]", "")).replace(",00", "");
    		infolelang[i][6] = (peserta.text()).replace(" peserta [Detil...]", ""); 
    	}
    	return infolelang;
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

    public static void dbTulisInfoLelang(String[][] infolelang) throws IOException {
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

			preparedStatement = connect.prepareStatement("insert into  lelang values (?, ?, ?, ?, ?, ?)");

			for (int i = 0; i < infolelang.length; i++) {
				preparedStatement.setInt(1, Integer.parseInt(infolelang[i][0]));
				preparedStatement.setString(2, infolelang[i][1]);
				preparedStatement.setInt(3, Integer.parseInt(infolelang[i][2]));
				preparedStatement.setString(4, infolelang[i][3]);
				long pagu = Long.parseLong(infolelang[i][4].replace(",", ""));
				BigInteger bpagu = BigInteger.valueOf(pagu);
				preparedStatement.setObject(5, bpagu, Types.BIGINT);
				long hps = Long.parseLong(infolelang[i][5].replace(",", ""));
				BigInteger bhps = BigInteger.valueOf(hps);
				preparedStatement.setObject(6, bhps, Types.BIGINT);

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
		Document doc = Jsoup.connect("http://" + url + "/eproc/lelang/tahap/" + kodelelang).get();
		Elements baris = doc.select("tr");
		String[][] tahaplelang = new String[baris.size()-1][12];
		Elements entrimulai = doc.select("td.horizLineSel");
		Elements entrisampai = doc.select("td.horizLine");
		for (int i = 0; i < baris.size()-1; i++) {
			if (i == 0) {
				Element mulai = entrimulai.get(0);
				Element sampai = entrisampai.get(2);

				String datemulai = (mulai.text()).substring(0, mulai.text().length()-6).replace(" ","");
				String timemulai = ((mulai.text()).substring(mulai.text().length()-6).replace(":", "")).replace(" ","");
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

				String datesampai = (sampai.text()).substring(0, sampai.text().length()-6).replace(" ", "");
				String timesampai = ((sampai.text()).substring(sampai.text().length()-6).replace(":", "")).replace(" ", "");
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

				tahaplelang[i][0] = yyyymulai;
				tahaplelang[i][1] = mmmulai;
				tahaplelang[i][2] = ddmulai;
				tahaplelang[i][3] = hhmulai;
				tahaplelang[i][4] = minmulai;
				tahaplelang[i][5] = ssmulai;
				tahaplelang[i][6] = yyyysampai;
				tahaplelang[i][7] = mmsampai;
				tahaplelang[i][8] = ddsampai;
				tahaplelang[i][9] = hhsampai;
				tahaplelang[i][10] = minsampai;
				tahaplelang[i][11] = sssampai;

				System.out.println(tahaplelang[i][0] + "-" + tahaplelang[i][1] + "-" + tahaplelang[i][2] + " " + tahaplelang[i][3] + ":" + tahaplelang[i][4] + ":" + tahaplelang[i][5]);
			}
			else {
				Element mulai = entrimulai.get((i*2));
				Element sampai = entrisampai.get((i*3)+2);

				String datemulai = (mulai.text()).substring(0, mulai.text().length()-6).replace(" ","");
				String timemulai = ((mulai.text()).substring(mulai.text().length()-6).replace(":", "")).replace(" ","");
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

				String datesampai = (sampai.text()).substring(0, sampai.text().length()-6).replace(" ", "");
				String timesampai = ((sampai.text()).substring(sampai.text().length()-6).replace(":", "")).replace(" ", "");
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

				tahaplelang[i][0] = yyyymulai;
				tahaplelang[i][1] = mmmulai;
				tahaplelang[i][2] = ddmulai;
				tahaplelang[i][3] = hhmulai;
				tahaplelang[i][4] = minmulai;
				tahaplelang[i][5] = ssmulai;
				tahaplelang[i][6] = yyyysampai;
				tahaplelang[i][7] = mmsampai;
				tahaplelang[i][8] = ddsampai;
				tahaplelang[i][9] = hhsampai;
				tahaplelang[i][10] = minsampai;
				tahaplelang[i][11] = sssampai;

				System.out.println(tahaplelang[i][0] + "-" + tahaplelang[i][1] + "-" + tahaplelang[i][2] + " " + tahaplelang[i][3] + ":" + tahaplelang[i][4] + ":" + tahaplelang[i][5]);
			}
		}
		return tahaplelang;
	}
    
    //ambil peserta dari semua link
    public static String[][] getPesertaLelang(String url, int kodelelang, int status) throws IOException {
    	Document doc = Jsoup.connect("http://" + url + "/eproc/rekanan/lelangpeserta/" + kodelelang).get(); 
		Elements baris = doc.select("tr");
    	String[][] pesertalelang = new String[baris.size()-1][5];
    	switch (status) {
    		case 0:
    			Elements entripeserta0 = doc.select("td.horizLineTop");
            	for (int i = 0; i < baris.size()-1; i++) {
            		if (i == 0) {
            			Element peserta = entripeserta0.get(1);
                		Element penawaran = entripeserta0.get(4);
                		Element terkoreksi = entripeserta0.get(5);
                		Element pemenang = entripeserta0.get(6);
                		String pesertastring = peserta.text();
                		pesertalelang[i][0] = "" + kodelelang;
                		pesertalelang[i][1] = pesertastring.substring(0, pesertastring.length()-23);
						if (penawaran.text().contains("Rp") == false) {
							pesertalelang[i][2] = "0";
						}
						else {
							pesertalelang[i][2] = (penawaran.text().replaceAll("[^\\d,]", "")).replace(",00", "");
						}
						if (penawaran.text().contains("Rp") == false) {
							pesertalelang[i][3] = "0";
						}
						else {
							pesertalelang[i][3] = (terkoreksi.text().replaceAll("[^\\d,]", "")).replace(",00", "");
						}
                		String bintang = pemenang.html();
                		if (bintang.length() > 10) {
                			pesertalelang[i][4] = "1";
                		}
                		else {
                			pesertalelang[i][4] = "0";
                		}
            		}
            		else {
            			Element peserta = entripeserta0.get((i*8)+1);
                		Element penawaran = entripeserta0.get((i*8)+4);
                		Element terkoreksi = entripeserta0.get((i*8)+5);
                		Element pemenang = entripeserta0.get((i*8)+6);
                		String pesertastring = peserta.text();
                		pesertalelang[i][0] = "" + kodelelang;
                		pesertalelang[i][1] = pesertastring.substring(0, pesertastring.length()-23);
						if (penawaran.text().contains("Rp") == false) {
							pesertalelang[i][2] = "0";
						}
						else {
							pesertalelang[i][2] = (penawaran.text().replaceAll("[^\\d,]", "")).replace(",00", "");
						}
						if (penawaran.text().contains("Rp") == false) {
							pesertalelang[i][3] = "0";
						}
						else {
							pesertalelang[i][3] = (terkoreksi.text().replaceAll("[^\\d,]", "")).replace(",00", "");
						}
                		String bintang = pemenang.html();
                		if (bintang.length() > 10) {
                			pesertalelang[i][4] = "1";
                		}
                		else {
                			pesertalelang[i][4] = "0";
                		}
            		}
            	}
            	break;
    		case 1:
    			Elements entripeserta1 = doc.select("td.horizLineTop");
            	for (int i = 0; i < baris.size()-1; i++) {
            		if (i == 0) {
            			Element peserta = entripeserta1.get(1);
                		String pesertastring = peserta.text();
                		pesertalelang[i][0] = "" + kodelelang;
                		pesertalelang[i][1] = pesertastring.substring(0, pesertastring.length()-23);
                		pesertalelang[i][2] = "0";
                		pesertalelang[i][3] = "0";
                		pesertalelang[i][4] = "0";
            		}
            		else {
            			Element peserta = entripeserta1.get((i*2)+1);
                		String pesertastring = peserta.text();
                		pesertalelang[i][0] = "" + kodelelang;
                		pesertalelang[i][1] = pesertastring.substring(0, pesertastring.length()-23);
                		pesertalelang[i][2] = "0";
                		pesertalelang[i][3] = "0";
                		pesertalelang[i][4] = "0";
            		}
            	}
            	break;
    		case 2:
    			Elements entripeserta2 = doc.select("td.horizLineTop");
            	for (int i = 0; i < baris.size()-1; i++) {
            		if (i == 0) {
            			Element peserta = entripeserta2.get(1);
                		String pesertastring = peserta.text();
                		pesertalelang[i][0] = "" + kodelelang;
                		pesertalelang[i][1] = pesertastring.substring(0, pesertastring.length()-23);
                		pesertalelang[i][2] = "0";
                		pesertalelang[i][3] = "0";
                		pesertalelang[i][4] = "0";
            		}
            		else {
            			Element peserta = entripeserta2.get((i*5)+1);
                		String pesertastring = peserta.text();
                		pesertalelang[i][0] = "" + kodelelang;
                		pesertalelang[i][1] = pesertastring.substring(0, pesertastring.length()-23);
                		pesertalelang[i][2] = "0";
                		pesertalelang[i][3] = "0";
                		pesertalelang[i][4] = "0";
            		}
            	}
            	break;        	
    		case 3:
    			Elements entripeserta3 = doc.select("td.TitleLeft");
            	for (int i = 0; i < entripeserta3.size(); i++) {
            		pesertalelang[i][0] = "" + kodelelang;
            		pesertalelang[i][1] = "Penyedia " + (i+1);
            		pesertalelang[i][2] = "0";
            		pesertalelang[i][3] = "0";
            		pesertalelang[i][4] = "0";
            	}
            	break;
			case 4:
				Elements entripeserta4 = doc.select("td.horizLine");
				for (int i = 0; i < baris.size()-1; i++) {
					Element peserta = entripeserta4.get(i);
					String pesertastring = peserta.text();
					pesertalelang[i][0] = "" + kodelelang;
					pesertalelang[i][1] = pesertastring.substring(0, pesertastring.length());
					pesertalelang[i][2] = "0";
					pesertalelang[i][3] = "0";
					pesertalelang[i][4] = "0";
				}
				break;
			case 5:
				Elements entripeserta5 = doc.select("td.horizLineTop");
				for (int i = 0; i < baris.size()-1; i++) {
					if (i == 0) {
						Element peserta = entripeserta5.get(1);
						String pesertastring = peserta.text();
						pesertalelang[i][0] = "" + kodelelang;
						pesertalelang[i][1] = pesertastring.substring(0, pesertastring.length());
						pesertalelang[i][2] = "0";
						pesertalelang[i][3] = "0";
						pesertalelang[i][4] = "0";
					}
					else {
						Element peserta = entripeserta5.get((i*2)+1);
						String pesertastring = peserta.text();
						pesertalelang[i][0] = "" + kodelelang;
						pesertalelang[i][1] = pesertastring.substring(0, pesertastring.length());
						pesertalelang[i][2] = "0";
						pesertalelang[i][3] = "0";
						pesertalelang[i][4] = "0";
					}
				}
				break;
			default:
            	Elements entripesertad = doc.select("td.horizLineTop");
            	for (int i = 0; i < baris.size()-1; i++) {
            		if (i == 0) {
            			Element peserta = entripesertad.get(1);
                		Element penawaran = entripesertad.get(4);
                		Element terkoreksi = entripesertad.get(5);
                		Element pemenang = entripesertad.get(6);
                		String pesertastring = peserta.text();
                		pesertalelang[i][0] = "" + kodelelang;
                		pesertalelang[i][1] = pesertastring.substring(0, pesertastring.length()-23);
                		pesertalelang[i][2] = penawaran.text();
                		pesertalelang[i][3] = terkoreksi.text();
                		String bintang = pemenang.html();
                		if (bintang.length() > 10) {
                			pesertalelang[i][4] = "1";
                		}
                		else {
                			pesertalelang[i][4] = "0";
                		}
            		}
            		else {
            			Element peserta = entripesertad.get((i*8)+1);
                		Element penawaran = entripesertad.get((i*8)+4);
                		Element terkoreksi = entripesertad.get((i*8)+5);
                		Element pemenang = entripesertad.get((i*8)+6);
                		String pesertastring = peserta.text();
                		pesertalelang[i][0] = "" + kodelelang;
                		pesertalelang[i][1] = pesertastring.substring(0, pesertastring.length()-23);
                		pesertalelang[i][2] = penawaran.text();
                		pesertalelang[i][3] = terkoreksi.text();
                		String bintang = pemenang.html();
                		if (bintang.length() > 10) {
                			pesertalelang[i][4] = "1";
                		}
                		else {
                			pesertalelang[i][4] = "0";
                		}
            		}
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

	public static void dbTulisPesertaLelang(String[][] pesertalelang, int kodelelang, int jumprev) throws IOException {
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

			preparedStatement = connect.prepareStatement("insert into  peserta values (?, ?, ?, ?, ?, ?)");

			for (int i = 0; i < pesertalelang.length; i++) {
				preparedStatement.setInt(1, jumprev + i + 1);
				preparedStatement.setInt(2, kodelelang);
				preparedStatement.setString(3, pesertalelang[i][1]);
				if (pesertalelang[i][2] == "0") {
					preparedStatement.setObject(4, null);
				}
				else {
					long penawaran = Long.parseLong(pesertalelang[i][2].replace(",", ""));
					BigInteger bpenawaran = BigInteger.valueOf(penawaran);
					preparedStatement.setObject(4, bpenawaran, Types.BIGINT);
				}
				if (pesertalelang[i][3] == "0") {
					preparedStatement.setObject(5, null);
				}
				else {
					long terkoreksi = Long.parseLong(pesertalelang[i][3].replace(",", ""));
					BigInteger bterkoreksi = BigInteger.valueOf(terkoreksi);
					preparedStatement.setObject(5, bterkoreksi, Types.BIGINT);
				}
				if (pesertalelang[i][4] == "0"){
					preparedStatement.setObject(6, null);
				}
				else {
					preparedStatement.setInt(6, Integer.parseInt(pesertalelang[i][4]));
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
    
    //ambil jumlah peserta di semua lelang
    public static int[] getAllJumlahPeserta(String[] kodelelang, String[][] infolelang) throws IOException {
    	int[] jumlahpeserta = new int[kodelelang.length];
    	for (int i = 0; i < kodelelang.length; i++) {
    		jumlahpeserta[i] = Integer.parseInt(infolelang[i][6]);
    	}
    	return jumlahpeserta;
    }
    
    //ambil peserta di semua lelang
    public static void getAllPesertaLelang(String url, String[] kodelelang, int[] jumlahpeserta) throws IOException {
		int jumprev = 0;
    	for (int i = 0; i < kodelelang.length; i++) {
    		String[][] temppeserta = new String[jumlahpeserta[i]][5];
    		int lelangnum = Integer.parseInt(kodelelang[i].substring(2));
    		int status = Integer.parseInt(kodelelang[i].substring(0,1));
    		temppeserta = getPesertaLelang(url,lelangnum,status);
			for (int j = 0; j < temppeserta.length; j++) {
				System.out.printf(temppeserta[j][0] + " - ");
				System.out.printf(temppeserta[j][1] + " - ");
				System.out.printf(temppeserta[j][2] + " - ");
				System.out.printf(temppeserta[j][3] + " - ");
				System.out.println(temppeserta[j][4]);
			}
    		//tulisPesertaLelang(temppeserta, lelangnum);
			dbTulisPesertaLelang(temppeserta, lelangnum, jumprev);
			jumprev += temppeserta.length;
    	}
    }

	public static void tulisTahapLelang(String[][] tahaplelang, int kodelelang) throws IOException {
		FileWriter writer = new FileWriter("retrieved/tahap_" + kodelelang + ".csv");
		for (int i = 0; i < tahaplelang.length; i++) {
			writer.append(String.valueOf(kodelelang));
			writer.append(",");
			writer.append(String.valueOf(i + 1));
			writer.append(",");
			writer.append(tahaplelang[i][0]);
			writer.append(",");
			writer.append(tahaplelang[i][1]);
			writer.append(",");
			writer.append(tahaplelang[i][2]);
			writer.append(",");
			writer.append(tahaplelang[i][3]);
			writer.append(",");
			writer.append(tahaplelang[i][4]);
			writer.append(",");
			writer.append(tahaplelang[i][5]);
			writer.append(",");
			writer.append(tahaplelang[i][6]);
			writer.append(",");
			writer.append(tahaplelang[i][7]);
			writer.append(",");
			writer.append(tahaplelang[i][8]);
			writer.append(",");
			writer.append(tahaplelang[i][9]);
			writer.append(",");
			writer.append(tahaplelang[i][10]);
			writer.append(",");
			writer.append(tahaplelang[i][11]);
			writer.append("\n");
		}
		writer.flush();
		writer.close();
	}

	public static void dbTulisTahapLelang(String[][] tahaplelang, int kodelelang, int prevstep) throws IOException {
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

			preparedStatement = connect.prepareStatement("insert into  tahap values (?, ?, ?, ?, ?)");

			for (int i = 0; i < tahaplelang.length; i++) {
				preparedStatement.setInt(1, prevstep + i + 1);
				preparedStatement.setInt(2, kodelelang);
				preparedStatement.setInt(3, i + 1);
				Timestamp tsmulai = Timestamp.valueOf(	tahaplelang[i][0] + "-" +
														tahaplelang[i][1] + "-" +
														tahaplelang[i][2] + " " +
														tahaplelang[i][3] + ":" +
														tahaplelang[i][4] + ":" +
														tahaplelang[i][5] + ".000000000");
				preparedStatement.setTimestamp(4, tsmulai);
				Timestamp tssampai = Timestamp.valueOf(	tahaplelang[i][6] + "-" +
														tahaplelang[i][7] + "-" +
														tahaplelang[i][8] + " " +
														tahaplelang[i][9] + ":" +
														tahaplelang[i][10] + ":" +
														tahaplelang[i][11] + ".000000000");
				preparedStatement.setTimestamp(5, tssampai);

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
			Document doc = Jsoup.connect("http://" + url + "/eproc/lelang/tahap/" + lelangnum).get();
			Elements baris = doc.select("tr");
			jumlahtahap[i] = baris.size() - 1;
		}
		return jumlahtahap;
	}

	//ambil tahap di semua lelang
	public static void getAllTahapLelang(String url, String[] kodelelang, int[] jumlahtahap) throws IOException {
		int prevstep = 0;
		for (int i = 0; i < kodelelang.length; i++) {
			String[][] temptahap = new String[jumlahtahap[i]][12];
			int lelangnum = Integer.parseInt(kodelelang[i].substring(2));
			temptahap = getTahapLelang(url, lelangnum);
			//tulisTahapLelang(temptahap, lelangnum);
			dbTulisTahapLelang(temptahap, lelangnum, prevstep);
			prevstep += temptahap.length;
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

	public static void Crawler() throws IOException {
    	String url = "";
    	Scanner reader = new Scanner(System.in);
    	System.out.println("Enter 1/2/3: ");
    	int choice = reader.nextInt();
    	
    	switch (choice) {
	    	case 1:
	    		url = "lpse.itb.ac.id";
	    		break;
    		case 2:
    			url = "lpse.bandung.go.id";
    			break;
    		case 3:
    			url = "lpse.kominfo.go.id";
    			break;
    		default:
    			url = "lpse.itb.ac.id";
	    		break;
    	}
    	System.out.println(url);
    	
		int pagenum = getPageNum(url);
		String[] kodelelang = getKodeLelang(url,pagenum);
		for (int i = 0; i < kodelelang.length; i++) {
			System.out.println(kodelelang[i]);
		}
		String[][] infolelang = getInfoLelang(url,kodelelang);
		//tulisInfoLelang(infolelang);
		emptyInfoLelang();
		dbTulisInfoLelang(infolelang);
		int[] jumlahpeserta = getAllJumlahPeserta(kodelelang,infolelang);
		int[] jumlahtahap = getAllJumlahTahap(url, kodelelang);
		emptyPesertaLelang();
		getAllPesertaLelang(url,kodelelang,jumlahpeserta);
		emptyTahapLelang();
		getAllTahapLelang(url,kodelelang,jumlahtahap);
		System.out.println("Selesai");
	} 
}