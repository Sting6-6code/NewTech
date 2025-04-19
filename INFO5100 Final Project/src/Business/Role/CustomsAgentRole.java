/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Role;

import Business.ConfigureASystem;
import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.CustomsLiaisonOrganization;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import ui.CustomsAgentRole.CustomsLiaisonOfficeHP;

/**
 *
 * @author zhuchenyan
 */
public class CustomsAgentRole extends Role {

    public CustomsAgentRole() {
    }

//    @Override
//    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
//        if (organization == null || !(organization instanceof CustomsLiaisonOrganization)) {
//            // 即使组织不可用，也要传递 userProcessContainer 和其他可用参数
//            return new ui.CustomsAgentRole.CustomsLiaisonOfficeHP(userProcessContainer, account, enterprise, null);
//        } else {
//            return new ui.CustomsAgentRole.CustomsLiaisonOfficeHP(userProcessContainer, account, enterprise, (CustomsLiaisonOrganization) organization);
//        }
//    }
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        System.out.println("Creating work area for CustomsAgentRole");

        CustomsLiaisonOrganization customsOrg = null;

        // 首先尝试从传入的 organization 获取
        if (organization instanceof CustomsLiaisonOrganization) {
            customsOrg = (CustomsLiaisonOrganization) organization;
            System.out.println("Found CustomsLiaisonOrganization from passed organization");
        }

        // 如果没有找到，尝试从企业中查找
        if (customsOrg == null && enterprise != null) {
            for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                if (org instanceof CustomsLiaisonOrganization) {
                    customsOrg = (CustomsLiaisonOrganization) org;
                    System.out.println("Found CustomsLiaisonOrganization from enterprise");
                    break;
                }
            }
        }

        // 如果仍然没有找到，创建一个新的实例并使用物流组织的数据
        if (customsOrg == null) {
            System.out.println("Creating new CustomsLiaisonOrganization");
            customsOrg = new CustomsLiaisonOrganization();

            // 从 ConfigureASystem.logisticsOrg 获取数据
            if (ConfigureASystem.logisticsOrg != null
                    && ConfigureASystem.logisticsOrg.getCustomsDeclarationDirectory() != null) {

                customsOrg.setCustomsDeclarationDirectory(
                        ConfigureASystem.logisticsOrg.getCustomsDeclarationDirectory());
                System.out.println("Using customs declarations from logistics organization");
            }
        }

        return new CustomsLiaisonOfficeHP(userProcessContainer, account, enterprise, customsOrg);
    }

    @Override
    public String toString() {
        return "Customs Liaison Officer";
    }
}
