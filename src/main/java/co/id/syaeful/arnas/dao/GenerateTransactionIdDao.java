/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.id.syaeful.arnas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.joget.apps.app.service.AppUtil;
import org.joget.commons.util.LogUtil;

/**
 *
 * @author Asani
 */
public class GenerateTransactionIdDao {
    public String getDataVar(String appId, String id, String appVersion) {
        DataSource ds = (DataSource) AppUtil.getApplicationContext().getBean("setupDataSource");
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String result ="";
        
        String query = "SELECT value FROM app_env_variable "
                + "WHERE appId = ? "
                + "AND id = ? "
                + "AND appVersion ?";
        
        try {
            con = ds.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, appId);
            ps.setString(2, id);
            ps.setString(3, appVersion);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                result = rs.getString("value");
            }
        } catch (SQLException ex) {
            LogUtil.error(this.getClass().getName(), ex, "Error : " + ex.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    LogUtil.error(this.getClass().getName(), e, "Error Con : " + e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LogUtil.error(this.getClass().getName(), e, "Error Con : " + e.getMessage());
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LogUtil.error(this.getClass().getName(), e, "Error Con : " + e.getMessage());
                }
            }
        }
        return result;
    }
}
