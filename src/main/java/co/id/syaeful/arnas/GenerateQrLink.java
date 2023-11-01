/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.id.syaeful.arnas;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joget.commons.util.LogUtil;
import org.joget.plugin.base.DefaultApplicationPlugin;
import org.joget.plugin.base.PluginWebSupport;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Asani
 */
public class GenerateQrLink extends DefaultApplicationPlugin implements PluginWebSupport{
    
    public static String pluginName = "HRDC test - Generate QR code E-Slip";
    JSONObject json = new JSONObject();
    
    @Override
    public void webService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        String name = request.getParameter("name");
        
        try {
            if("".equals(name) || name == null) {
                json.put("data", name);
            }
        } catch (JSONException e) {
            LogUtil.error(this.getClassName(), e, "Error web service : " + e.getMessage());
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
