package dimas.spring_mvc.utilities;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import dimas.spring_mvc.entities.Transaction;
import jakarta.servlet.http.HttpServletResponse;

public class PdfGenerator {
	public void generate(List<Transaction> transactions, HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		
		Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTiltle.setSize(20);
		
		Paragraph paragraph1 = new Paragraph("Mutasi Rekening", fontTiltle);
		paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(paragraph1);
		
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 3, 3, 3, 3 });
		table.setSpacingBefore(5);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(CMYKColor.BLUE);
		cell.setPadding(5);
		
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(CMYKColor.WHITE);
		cell.setPhrase(new Phrase("Amount", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Type", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("News", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Timestamp", font));
		table.addCell(cell);
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		
		for (Transaction t : transactions) {
			table.addCell("Rp" + t.getAmount().toString());
			table.addCell(t.getType());
			table.addCell(t.getNews());
			table.addCell(t.getTimestamp().format(dateFormat).toString());
		}
		
		document.add(table);
		document.close();
	}
}
