/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;

import javax.swing.JPanel;

/**
 *
 * @author wangsiting
 */
public class AdminRole extends Role {

    public AdminRole() {
    }
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        // 这里应该实例化并返回管理员的工作界面
        // 暂时抛出异常
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

