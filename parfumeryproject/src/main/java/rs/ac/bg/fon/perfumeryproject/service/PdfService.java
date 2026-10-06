package rs.ac.bg.fon.perfumeryproject.service;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.perfumeryproject.dto.impl.OrderDto;
import rs.ac.bg.fon.perfumeryproject.dto.impl.OrderItemDto;
import rs.ac.bg.fon.perfumeryproject.entity.impl.Perfume;
import rs.ac.bg.fon.perfumeryproject.repository.impl.PerfumeRepository;

/**
 *
 * @author Milica
 */


@Service
public class PdfService {

    private final PerfumeRepository perfumeRepository;

    public PdfService(PerfumeRepository perfumeRepository) {
        this.perfumeRepository = perfumeRepository;
    }

    public byte[] generateOrderPdf(OrderDto order) throws Exception {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, new BaseColor(26, 26, 46));
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.DARK_GRAY);
        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.DARK_GRAY);
        Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 9, BaseColor.GRAY);

        // naslov
        Paragraph title = new Paragraph("PARFUMERIE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(4);
        document.add(title);

        Paragraph subtitle = new Paragraph("Order Confirmation", 
            FontFactory.getFont(FontFactory.HELVETICA, 13, new BaseColor(201, 168, 76)));
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingAfter(20);
        document.add(subtitle);

        // separator
        document.add(new Paragraph("_____________________________________________\n\n", smallFont));

        // podaci o porudzbini
        Paragraph orderInfo = new Paragraph();
        orderInfo.setFont(normalFont);
        orderInfo.add(new Phrase("Order ID: ", boldFont));
        orderInfo.add(new Phrase("#" + order.getId() + "\n", normalFont));
        orderInfo.add(new Phrase("Status: ", boldFont));
        orderInfo.add(new Phrase(order.getStatus().toString() + "\n", normalFont));
        orderInfo.add(new Phrase("Date: ", boldFont));
        orderInfo.add(new Phrase(
            order.getCreatedAt() != null
                ? order.getCreatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
                : "-",
            normalFont));
        if (order.getNote() != null && !order.getNote().isEmpty()) {
            orderInfo.add(new Phrase("\nNote: ", boldFont));
            orderInfo.add(new Phrase(order.getNote(), normalFont));
        }
        orderInfo.setSpacingAfter(20);
        document.add(orderInfo);

        // tabela stavki
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3f, 1.5f, 1.5f, 1.5f});
        table.setSpacingBefore(10);

        // header tabele
        String[] headers = {"Perfume", "Quantity", "Unit Price", "Total"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
            cell.setBackgroundColor(new BaseColor(26, 26, 46));
            cell.setPadding(8);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        // redovi stavki
        double grandTotal = 0;
        List<OrderItemDto> items = order.getItems();
        if (items != null) {
            for (OrderItemDto item : items) {
                String perfumeName = "Perfume #" + item.getPerfumeId();
                try {
                    Perfume p = perfumeRepository.findById(item.getPerfumeId());
                    perfumeName = p.getName();
                } catch (Exception ignored) {}

                double unitPrice = item.getUnitPrice() != null ? item.getUnitPrice().doubleValue() : 0;
                double total = unitPrice * item.getQuantity();
                grandTotal += total;

                addCell(table, perfumeName, normalFont, Element.ALIGN_LEFT);
                addCell(table, String.valueOf(item.getQuantity()), normalFont, Element.ALIGN_CENTER);
                addCell(table, String.format("%.2f RSD", unitPrice), normalFont, Element.ALIGN_RIGHT);
                addCell(table, String.format("%.2f RSD", total), normalFont, Element.ALIGN_RIGHT);
            }
        }

        document.add(table);

        // ukupno
        document.add(new Paragraph("\n"));
        Paragraph totalPara = new Paragraph();
        totalPara.add(new Phrase("TOTAL: ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13, new BaseColor(26, 26, 46))));
        totalPara.add(new Phrase(String.format("%.2f RSD", grandTotal),
            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13, new BaseColor(201, 168, 76))));
        totalPara.setAlignment(Element.ALIGN_RIGHT);
        totalPara.setSpacingBefore(10);
        document.add(totalPara);

        // footer
        document.add(new Paragraph("\n\n"));
        Paragraph footer = new Paragraph("Thank you for shopping at Parfumerie!\ncontact@parfumerie.com", smallFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        document.close();
        return out.toByteArray();
    }

    private void addCell(PdfPTable table, String text, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(6);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderColor(new BaseColor(220, 220, 220));
        table.addCell(cell);
    }
}