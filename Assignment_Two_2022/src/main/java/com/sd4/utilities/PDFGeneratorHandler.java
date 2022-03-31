package com.sd4.utilities;

import java.io.IOException;  
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.sd4.model.Beer;
import com.sd4.repository.BeerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;


@Service
public class PDFGeneratorHandler {

    @Autowired
    private BeerRepository beerRepository;

    public void CreateBeerPDF(HttpServletResponse response, long id) throws IOException, DocumentException{
        
        Beer beer = beerRepository.findById(id).get();

        Document document = new Document(PageSize.A4);
      PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph(beer.getName(), fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        Paragraph paragraph2 = new Paragraph("This is a paragraph.", fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(paragraph);
        document.add(paragraph2);
        document.close();
    }
}
