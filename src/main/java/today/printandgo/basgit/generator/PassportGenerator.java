package today.printandgo.basgit.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import today.printandgo.basgit.model.User;

public class PassportGenerator {
	
	private String serverRoot = new File("").getAbsolutePath()+"/"; 

	private Font font1 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	private Font font2 = new Font(Font.FontFamily.HELVETICA, 14);

	private void fixMainTable(PdfPTable mainTable, PdfPTable titleTable, PdfPTable infoTable) {

		PdfPCell left = new PdfPCell(titleTable);
		left.setBorder(Rectangle.NO_BORDER);
		left.addElement(titleTable);
		mainTable.addCell(left);

		PdfPCell right = new PdfPCell(infoTable);
		right.setBorder((Rectangle.NO_BORDER));
		right.addElement(infoTable);
		mainTable.addCell(right);

	}

	private void fixTitleTable(PdfPTable titleTable, User u)
			throws IOException, BadElementException, URISyntaxException {

		Font font = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);

		PdfPCell header = new PdfPCell();
		header.setPhrase(new Phrase("Passport", font));
		titleTable.addCell(header).setBorder(Rectangle.NO_BORDER);

		Image img = Image.getInstance(serverRoot + "Photos/" + u.getUsername() + ".jpeg");
		img.scalePercent(30);
		PdfPCell photo = new PdfPCell(img);
		photo.setPaddingTop(50);
		titleTable.addCell(photo).setBorder(Rectangle.NO_BORDER);
	}

	private void fixInfoTable(PdfPTable infoTable, User u) {

		String keyList[] = { "Type", "Country Code", "Passport Number", "Surname", "Name", "Date of Birth",
				"Personal No", "Sex", "Place of Birth", "Date of Issue", "Authority", "Date of Expiry",
				"Holder's Signature" };
		String valueList[] = { "P", u.getCc(), "1", u.getLastname(), u.getFirstname(), u.getDob().toString(), u.getNationalid(),
				u.getSex(), u.getPob(), DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now()),
				"BasGit Authority", DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now().plusMonths(1)),
				"TODO Signature" };

		for (int i = 0; i < keyList.length; i++) {
			PdfPCell cell = new PdfPCell();
			cell.addElement(new Phrase(String.format("%s: ", keyList[i], font1)));
			cell.addElement(new Phrase(String.format("%s", valueList[i], font2)));
			cell.setPaddingTop(20);
			infoTable.addCell(cell).setBorder(Rectangle.NO_BORDER);
		}
	}

	private void fixSecondTable(PdfPTable secondTable, User u, String uuid)
			throws DocumentException, IOException, URISyntaxException {
		// Paragraph p1 = new Paragraph("<P<<<<<UTODOE<<<JOHN<<<<<<<<<<<<<<<<<<",
		// font2);
		// Paragraph p2 = new Paragraph("<<3536472gdk34b3423AF313232 <<<<<<<<<<<<<<",
		// font2);
		// p1.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
		// p2.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
		// PdfPCell mrz = new PdfPCell();
		// mrz.addElement(p1);
		// mrz.addElement(p2);
		// mrz.setPaddingTop(20);
		// secondTable.addCell(mrz)
		// .setBorder(Rectangle.NO_BORDER);

		try {
			generateQRCodeImage(uuid, 256, 256, serverRoot + "QR/"+u.getUsername()+".png");
		} catch (WriterException e) {
			System.out.println("Failed to create QR");
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image img = Image.getInstance(serverRoot + "QR/" + u.getUsername() + ".png");
		PdfPCell qr = new PdfPCell(img);
		qr.setPaddingTop(20);
		secondTable.addCell(qr).setBorder(Rectangle.NO_BORDER);
	}

	private static void generateQRCodeImage(String text, int width, int height, String filePath)
			throws WriterException, IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

		Path path = FileSystems.getDefault().getPath(filePath);
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
	}

	public String generatePDF(User u) {
		try {
			String uuid = PassportGenerator.generateUUID();
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(serverRoot + "Passports/" + u.getUsername() + "_" + uuid + ".pdf"));
			document.open();

			PdfPTable titleTable = new PdfPTable(1);
			PdfPTable infoTable = new PdfPTable(2);
			PdfPTable mainTable = new PdfPTable(2);
			PdfPTable secondTable = new PdfPTable(1);

			fixInfoTable(infoTable, u);
			fixTitleTable(titleTable, u);
			fixMainTable(mainTable, titleTable, infoTable);
			fixSecondTable(secondTable, u, uuid);

			document.add(mainTable);
			document.add(secondTable);

			document.close();
			return uuid;
		} catch (DocumentException | IOException | URISyntaxException e) {
			System.out.println("Failed to create PDF");
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

}
