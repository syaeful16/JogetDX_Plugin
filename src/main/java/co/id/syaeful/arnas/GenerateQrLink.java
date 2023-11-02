/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.id.syaeful.arnas;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joget.commons.util.LogUtil;
import org.joget.plugin.base.DefaultApplicationPlugin;
import org.joget.plugin.base.PluginWebSupport;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Asani
 */
public class GenerateQrLink extends DefaultApplicationPlugin implements PluginWebSupport{
    
    public static String pluginName = "HRDC test - Generate QR code E-Slip";
    
    @Override
    public void webService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        PrintWriter out = response.getWriter();
        
        String transactionId = request.getParameter("transaction_id");
        String module = request.getParameter("module");
        
        JSONObject jsono = new JSONObject();
        
        ServletOutputStream outputStream = response.getOutputStream();
        
        try {
            String qrCodeData = "Hello, QR Code!";
            
            Map<EncodeHintType, Object> hintMap = new EnumMap<>(EncodeHintType.class);
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hintMap.put(EncodeHintType.MARGIN, 2);
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            
//            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCodeData, BarcodeFormat.QR_CODE, 220, 220, hintMap);
            
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            
            String base64Image = Base64.getEncoder().encodeToString(byteArray);
            
            if(!"".equals(transactionId) && !"".equals(module)) {
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                response.setContentType("image/jpeg");
                
                jsono.put("data", base64Image);
                
                outputStream.write(imageBytes);
            }
        } catch (JSONException e) {
            LogUtil.error(this.getClassName(), e, "error webservice : " + e.getMessage());
        } catch (WriterException ex) {
            LogUtil.error(this.getClassName(), ex, "error writeEx : " + ex.getMessage());
        } finally {
            // Bersihkan dan tutup output stream
            outputStream.flush();
            outputStream.close();
        }
    }
    
    @Override
    public Object execute(Map map) {
        return null;
    }

    @Override
    public String getName() {
        return pluginName;
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getDescription() {
        return pluginName;
    }

    @Override
    public String getLabel() {
        return pluginName;
    }

    @Override
    public String getClassName() {
        return this.getClass().getName();
    }

    @Override
    public String getPropertyOptions() {
        return null;
    }



    
}
