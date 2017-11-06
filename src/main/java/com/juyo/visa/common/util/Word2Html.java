package com.juyo.visa.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import fr.opensagres.xdocreport.core.io.internal.ByteArrayOutputStream;

/**
 * 使用POI作为转换器实现转换功能
 */
public class Word2Html {

	/** 
	 *  xml转html
	 * @param fileName xml地址
	 * @param outputFile html 地址
	 * @throws Exception
	 */
	public void xlsToHtml(String fileName, String outputFile) throws Exception {
		initFolder(outputFile);
		URL url = new URL(fileName);// 生成url对象
		URLConnection urlConnection = url.openConnection();
		//urlConnection.getInputStream();
		InputStream is = urlConnection.getInputStream();
		//Workbook excelBook = WorkbookFactory.create(is);
		HSSFWorkbook excelBook = new HSSFWorkbook(is);

		ExcelToHtmlConverter ethc = new ExcelToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.newDocument());
		ethc.setOutputColumnHeaders(false);
		ethc.setOutputRowNumbers(false);

		ethc.processWorkbook(excelBook);

		Document htmlDocument = ethc.getDocument();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(out);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		out.close();

		String htmlStr = new String(out.toByteArray());

		htmlStr = htmlStr.replace("<h2>Sheet1</h2>", "").replace("<h2>Sheet2</h2>", "").replace("<h2>Sheet3</h2>", "")
				.replace("<h2>Sheet4</h2>", "").replace("<h2>Sheet5</h2>", "");

		writeFile(htmlStr, outputFile);
	}

	/**
	 *  将doc转html
	 * @param sourceFileName doc地址
	 * @param targetFileName html地址
	 * @throws Exception
	 */
	public void docToHtml(String sourceFileName, String targetFileName) throws Exception {
		initFolder(targetFileName);
		String str = System.getProperty("java.io.tmpdir");
		String imagePathStr = str + File.separator;
		/*		String imagePathStr = str + File.separator + "image";
		*/
		// 指定网上的word的文档路径
		URL url = new URL(sourceFileName);// 生成url对象
		URLConnection urlConnection = url.openConnection();
		//urlConnection.getInputStream()
		HWPFDocument wordDocument = new HWPFDocument(urlConnection.getInputStream());
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(document);

		// 保存图片，并返回图片的相对路径
		wordToHtmlConverter.setPicturesManager((content, pictureType, name, width, height) -> {
			try (FileOutputStream out = new FileOutputStream(imagePathStr + name)) {
				out.write(content);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return name;
		});
		wordToHtmlConverter.processDocument(wordDocument);
		Document htmlDocument = wordToHtmlConverter.getDocument();
		// 添加
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(out);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);

		out.close();
		/*调用jsoup格式化*/
		writeFile(new String(out.toByteArray()), targetFileName);

	}

	/**
	 * docx转html
	 * @param sourceFileName docx的位置
	 * @param targetFileName html的位置
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void docxToHtml(String sourceFileName, String targetFileName) throws FileNotFoundException, IOException {
		initFolder(targetFileName);
		String imagePathStr = "E:\\image\\";
		OutputStreamWriter outputStreamWriter = null;
		try {
			XWPFDocument document = new XWPFDocument(new FileInputStream(sourceFileName));
			XHTMLOptions options = XHTMLOptions.create();
			// 存放图片的文件夹
			options.setExtractor(new FileImageExtractor(new File(imagePathStr)));
			// html中图片的路径
			options.URIResolver(new BasicURIResolver("image"));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			outputStreamWriter = new OutputStreamWriter(out, "utf-8");
			XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
			xhtmlConverter.convert(document, outputStreamWriter, options);

			out.close();
			/*调用jsoup格式化*/
			writeFile(new String(out.toByteArray()), targetFileName);

		} finally {
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}
		}
	}

	/**
	 * 使用jsoup将html页面更加格式化
	 * @param content 原html页面
	 * @param path 格式化后的html页面
	 */
	public static void writeFile(String content, String path) {

		FileOutputStream fos = null;
		BufferedWriter bw = null;
		org.jsoup.nodes.Document doc = Jsoup.parse(content);
		content = doc.html();
		// 将所有字体改变成simsum
		Pattern pattern = Pattern.compile("font-family:['\\w|\\s|\u4e00-\u9fa5]+;");
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			content = matcher.replaceAll("font-family:SimSun;");
		} else {
			content = content.replaceAll("</style>", "body{font-family: SimSun;} </style>");
		}
		// 去除nbsp;
		Pattern pattern1 = Pattern.compile("&nbsp;");
		Matcher matcher1 = pattern1.matcher(content);
		content = matcher1.replaceAll(" ");

		// 修改边距[A-Za-z0-9.\\s]表示大写字母小写字母小数点或空格中的任意一个
		Pattern pattern2 = Pattern.compile("margin[a-z\\-]{0,}:[A-Za-z0-9.\\s]+;");
		Matcher matcher2 = pattern2.matcher(content);
		content = matcher2.replaceAll("margin:auto;");
		// System.out.println(content);
		try {
			File file = new File(path);
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
			bw.write(content);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fos != null)
					fos.close();
			} catch (IOException ie) {
			}
		}
	}

	/**
	 * 初始化存放html文件的文件夹
	 * 
	 * @param targetFileName
	 *            html文件的文件名
	 */
	private void initFolder(String targetFileName) {
		File targetFile = new File(targetFileName);
		if (targetFile.exists()) {
			targetFile.delete();
		}
		String targetPathStr = targetFileName.substring(0, targetFileName.lastIndexOf(File.separator));
		File targetPath = new File(targetPathStr);
		// 如果文件夹不存在，则创建
		if (!targetPath.exists()) {
			targetPath.mkdirs();
		}
	}

	public static void main(String[] args) throws Exception {
		Word2Html e = new Word2Html();
		//e.docToHtml("http://oluwc01ms.bkt.clouddn.com/b7dd6d82-db0f-4128-86db-00057128d814.doc",
		//"F:\\11.html");
		//e.docToHtml("http://oluwc01ms.bkt.clouddn.com/b7dd6d82-db0f-4128-86db-00057128d814.doc", "E:\\13.html");
		//		URL url = new URL("http://oluwc01ms.bkt.clouddn.com/image");
		//		File file = new File(url.toURI());
		//		boolean bool = file.mkdir();
		//		System.out.println(bool);
		e.xlsToHtml("D:\\1.xlsx", "D:\\12.html");
	}

}
