package br.com.catedral.visitacao.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.common.HybridBinarizer;

import br.com.catedral.visitacao.dto.QrCodeDTO;
import br.com.catedral.visitacao.dto.QrCodeInserirDTO;
import br.com.catedral.visitacao.model.Ingresso;
import br.com.catedral.visitacao.model.QrCode;
import br.com.catedral.visitacao.repository.IngressoRepository;
import br.com.catedral.visitacao.repository.QrCodeRepository;

@Service
public class QrCodeService {

    @Autowired
    private QrCodeRepository qrCodeRepository;

    @Autowired
    private IngressoRepository ingressoRepository;

    public QrCodeDTO inserir(QrCodeInserirDTO qrCodeInserirDTO) {
        Optional<Ingresso> ingressoOptional = ingressoRepository.findById(qrCodeInserirDTO.idIngresso());
        if (ingressoOptional.isEmpty()) {
            return null; 
        }

        Ingresso ingresso = ingressoOptional.get();

        QrCode qrCode = qrCodeInserirDTO.toEntity(new QrCode());
        qrCode.setIngresso(ingresso);

        String dadosQrCode = gerarJsonQrCode(ingresso);

        try {
            byte[] dadosGerados = gerarQrCode(dadosQrCode);
            qrCode.setDados(dadosGerados);
        } catch (Exception e) {
            e.printStackTrace(); 
            return null;
        }

        QrCode qrCodeSalvo = qrCodeRepository.save(qrCode);

        return QrCodeDTO.toDto(qrCodeSalvo);
    }

    private String gerarJsonQrCode(Ingresso ingresso) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> dados = new LinkedHashMap<>();
        dados.put("id", ingresso.getId());

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dados);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public List<QrCodeDTO> buscarTodos() {
        List<QrCode> qrCodes = qrCodeRepository.findAll();
        return qrCodes.stream()
                      .map(QrCodeDTO::toDto)
                      .collect(Collectors.toList());
    }

    public boolean excluir(Long id) {
        Optional<QrCode> qrCodeOptional = qrCodeRepository.findById(id);

        if (qrCodeOptional.isPresent()) {
            qrCodeRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public String lerQrCode(byte[] imagemQrCode) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(imagemQrCode);
        BufferedImage bufferedImage = ImageIO.read(bais);

        QRCodeReader qrCodeReader = new QRCodeReader();
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));

        Result result = qrCodeReader.decode(binaryBitmap);

        return result.getText();
    }

    public byte[] gerarQrCode(String conteudo) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.MARGIN, 1);

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix bitMatrix = writer.encode(conteudo, BarcodeFormat.QR_CODE, 300, 300, hints);

        BufferedImage imagem = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 300; x++) {
            for (int y = 0; y < 300; y++) {
                imagem.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imagem, "PNG", baos);
        return baos.toByteArray();
    }
}
