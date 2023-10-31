/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.id.syaeful.arnas;

import co.id.syaeful.arnas.dao.GenerateTransactionIdDao;
import java.util.Map;
import org.joget.apps.app.service.AppService;
import org.joget.apps.app.service.AppUtil;
import org.joget.commons.util.LogUtil;
import org.joget.plugin.base.DefaultApplicationPlugin;
import org.joget.workflow.model.WorkflowAssignment;

/**
 *
 * @author Asani
 */
public class GenerateTransactionId extends DefaultApplicationPlugin{
    
    public static String pluginName = "HRDC Custom - Generate Transaction ID";

    @Override
    public Object execute(Map map) {
        // set variable appId
        String appId = "hrdclevy";
        
        // get ID Record
        AppService appService = (AppService) AppUtil.getApplicationContext().getBean("appService");
        WorkflowAssignment workflowAssignment = (WorkflowAssignment) map.get("workflowAssignment");
        
        String recordId = appService.getOriginProcessId(workflowAssignment.getProcessId());
        String appVersion = appService.getPublishedVersion(appId).toString();
        
        //call object
        GenerateTransactionIdDao dao = new GenerateTransactionIdDao();
        
        String resultDao = dao.getDataVar(appId, "counter_transaction_id_current_year", appVersion);
        LogUtil.info(this.getClassName(), resultDao);
        
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
        return getClass().getName();
    }

    @Override
    public String getPropertyOptions() {
        return null;
    }
    
}
