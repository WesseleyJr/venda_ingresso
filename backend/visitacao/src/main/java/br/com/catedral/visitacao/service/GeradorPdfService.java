package br.com.catedral.visitacao.service;

import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import br.com.catedral.visitacao.dto.QrCodeDTO;
import br.com.catedral.visitacao.model.Ingresso;
import br.com.catedral.visitacao.repository.IngressoRepository;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeradorPdfService {

    @Autowired
    private IngressoRepository ingressoRepository;

    public byte[] gerarPdfComQrCodes(List<Ingresso> ingressos, List<QrCodeDTO> qrCodes) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        
        for (int i = 0; i < ingressos.size(); i++) {
            Ingresso ingresso = ingressos.get(i);
            QrCodeDTO qrCode = qrCodes.get(i);

            Optional<Ingresso> ingressoOptional = ingressoRepository.findById(ingresso.getId());
            
            if (ingressoOptional.isPresent()) {
                Ingresso ingressoDoBanco = ingressoOptional.get();
                
                if (ingressoDoBanco.getId().equals(qrCode.idIngresso())) {
                    document.add(new Paragraph("Nome: " + ingressoDoBanco.getNomeCompleto()));
                    document.add(new Paragraph("Celular: " + ingressoDoBanco.getCelular()));
                    document.add(new Paragraph("Nome Responsável: " + ingressoDoBanco.getNomeResponsavel()));
                    document.add(new Paragraph("Data: " + ingressoDoBanco.getAgenda().getDataHora().toLocalDate()));
                    document.add(new Paragraph("Hora: " + ingressoDoBanco.getAgenda().getDataHora().toLocalTime()));

                    byte[] qrCodeData = qrCode.dados();
                    Image qrImage = new Image(ImageDataFactory.create(qrCodeData));
                    qrImage.setWidth(100).setHeight(100); 
                    document.add(qrImage);
                } else {
                    document.add(new Paragraph("Erro: O QR Code não corresponde ao ingresso com ID " + ingresso.getId()));
                }
            } else {
                document.add(new Paragraph("Erro: Ingresso com ID " + ingresso.getId() + " não encontrado no banco."));
            }

            if (i < ingressos.size() - 1) {
                document.add(new Paragraph("\n\n"));
            }
        }
        
        document.close();
        
        return byteArrayOutputStream.toByteArray();
    }
}
