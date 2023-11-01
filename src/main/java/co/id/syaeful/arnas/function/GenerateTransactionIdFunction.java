/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.id.syaeful.arnas.function;

import co.id.syaeful.arnas.dao.GenerateTransactionIdDao;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.joget.commons.util.LogUtil;

/**
 *
 * @author Asani
 */
public class GenerateTransactionIdFunction {
    public boolean GenerateTransactionId(String recordId, String appId, String appVersion) {
        boolean result = false;
        
        /*
         * Transaction ID: YYMMXXXXXXX (ex/: 23020000001)
         * 1. Dapetin env variable counter Transaction ID saat ini
         * 2. Dapetin env variable year untuk Transaction ID
         * 3. Dapetin current year
         * 4. Dapetin parameter reset counter
         * 5. Kalo parameter reset counter=YEAR,
         * cek apakah env variable year kurang dari dengan current year,
         * kalo env variable year kurang dari dengan current year,
         * env variable year diupdate dan env variable counter direset
         * 6. Kalo parameter reset counter=MAX,
         * cek apakah env variable counter sudah mencapai 9999999,
         * kalo env variable counter sudah mencapai 9999999,
         * env variable counter direset
         * 7. Format env variable counter menjadi diawali 0
         * 8. Dapetin current year dan current month dengan format YYYYMM
         * 9. Bentuk Transaction ID dengan menggabungkan current year,
         * current month, dan env variable counter
         * 10. Update Transaction ID berdasarkan Record ID
         */
        
        //call object dao class
        GenerateTransactionIdDao dao = new GenerateTransactionIdDao();
        
        // 1. Dapetin env variable counter Transaction ID saat ini
        // 2. Dapetin env variable year untuk Transaction ID
        int envVarYear = Integer.parseInt(dao.getDataVar(appId, "counter_transaction_id_current_year", appVersion));
        int envVarCounter = Integer.parseInt(dao.getDataVar(appId, "counter_transaction_id", appVersion));
        
        // 3. Dapetin current year
        SimpleDateFormat _sdf = new SimpleDateFormat("yyyy");
        int currentYear = Integer.parseInt(_sdf.format(new Date()));
        
        // initialization value 
        int counterTransactionId = 0;
        
        // 4. Dapetin parameter reset counter
        String parameter = dao.getResetCounterParameter();
        
        // 5. Kalo parameter reset counter=YEAR,
        // cek apakah env variable year kurang dari dengan current year,
        // kalo env variable year kurang dari dengan current year,
        // env variable year diupdate dan env variable counter direset
        
        if(parameter.equals("YEAR")) {
            if(envVarYear < currentYear){ //reset condition
                // Reset env variable counter jika sudah ganti tahun
                counterTransactionId = 1;

                // update env variable year 
                dao.setUpdateVar(String.valueOf(currentYear), appId, "counter_transaction_id_current_year", appVersion);
            } else{
                if(envVarCounter == 9999999){
                    counterTransactionId = 1;
                } else{
                    counterTransactionId = envVarCounter + 1;
                }
            }
            
            // update env variable counter
            dao.setUpdateVar(String.valueOf(counterTransactionId), appId, "counter_transaction_id", appVersion);
        } else {
            /*
             * 6. Kalo parameter reset counter=MAX || ELSE,
             * cek apakah env variable counter sudah mencapai 9999999,
             * kalo env variable counter sudah mencapai 9999999,
             * env variable counter direset
            */
            
            if(envVarCounter == 9999999){ //reset condition
                counterTransactionId = 1;
            } else{      
                counterTransactionId = envVarCounter + 1;
            }
            
            dao.setUpdateVar(String.valueOf(counterTransactionId), appId, "counter_transaction_id", appVersion);
        }
        
        /*
            7. Format env variable counter menjadi diawali 0
            Format Sequence Counter
            0 = Prefix
            7 = Jumlah Digit
        */
        String counterFormatted = String.format("%07d", counterTransactionId);
        
        // 8. Dapetin current year dan current month dengan format YYYYMM
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String monthYear = sdf.format(new Date());
        monthYear = monthYear.substring(2);
        
        /*
        9. Bentuk Transaction ID dengan menggabungkan current year,
        current month, dan env variable counter
        */
        String transactionId = monthYear+counterFormatted;
        
        // 10. Update Transaction ID berdasarkan Record ID
        if (dao.updateRequestNumber(recordId, transactionId)) {
            result = true;
        } else {
            LogUtil.info(this.getClass().getName(), "Failed Set Transaction ID : " + transactionId);
        }
        
        return result;
    }
}
