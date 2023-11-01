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
                + "AND appVersion = ?";
        
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
    
    public boolean setUpdateVar(String value, String appId, String idVar, String appVersion) {
        boolean result = false;
        
        DataSource ds = (DataSource) AppUtil.getApplicationContext().getBean("setupDataSource");
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "UPDATE app_env_variable "
                + "SET value = ? "
                + "WHERE appId = ?"
                + "AND id = ? "
                + "AND appVersion = ?";
        
        try {
            con = ds.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, value);
            ps.setString(2, appId);
            ps.setString(3, idVar);
            ps.setString(4, appVersion);
            rs = ps.executeQuery();
            
            int i = ps.executeUpdate();
            
            if (i > 0) {
                result = true;
                LogUtil.info(this.getClass().getName(), "Reset current counter year Transaction ID");
            } else {
                LogUtil.info(this.getClass().getName(), "Failed update current year");
            }
        } catch (SQLException e) {
            LogUtil.error(this.getClass().getName(), e, "Error : " + e.getMessage());
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
    
    public boolean updateRequestNumber(String recordId, String requestNumber) {
        DataSource ds = (DataSource) AppUtil.getApplicationContext().getBean("setupDataSource");
        Connection con = null;
        PreparedStatement ps = null;
        StringBuilder query = new StringBuilder();
        
        boolean result = false;
        
        query
                .append(" UPDATE ")
                .append(" app_fd_levm_pge_request ")
                .append(" SET ")
                .append(" c_transaction_id=? ")
                .append(" WHERE ")
                .append(" id=? ");

        try{
            con = ds.getConnection();
            ps = con.prepareStatement(query.toString());
            ps.setString(1, requestNumber);
            ps.setString(2, recordId);

            int i = ps.executeUpdate();
            if( i > 0){
                result = true;
            }
        } catch (SQLException e) {
            LogUtil.error(this.getClass().getName(), e, "Error : " + e.getMessage());
        } finally{
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    LogUtil.error(this.getClass().getName(), ex, "Error : " + ex.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    LogUtil.error(this.getClass().getName(), ex, "Error : " + ex.getMessage());
                }
            }
        }
        
        return result;
    }
    
    public String getResetCounterParameter() {
        DataSource ds = (DataSource) AppUtil.getApplicationContext().getBean("setupDataSource");
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuilder query = new StringBuilder();
        
        String result = "MAX";
        
        query
                .append(" SELECT ")
                .append(" c_value ")
                .append(" FROM ")
                .append(" app_fd_levm_stp_general ")
                .append(" WHERE ")
                .append(" id='GS1' ");

        try{
            con = ds.getConnection();
            ps = con.prepareStatement(query.toString());
            rs = ps.executeQuery();
            
            while(rs.next()){
                result = rs.getString("c_value");
            }
        } catch (SQLException e) {
            LogUtil.error(this.getClass().getName(), e, "Error : " + e.getMessage());
        } finally{
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    LogUtil.error(this.getClass().getName(), ex, "Error : " + ex.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    LogUtil.error(this.getClass().getName(), ex, "Error : " + ex.getMessage());
                }
            }
        }
        
        return result;
    }
}
