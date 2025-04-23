/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Business.Network.Network;
import Business.Order.Order;
import Business.Organization.Organization;
import Business.Role.AdminRole;
import Business.Role.Role;
import Business.Product.SalesRecordDirectory;
import Business.Order.OrderDirectory;
import Business.Payment.Payment;
import Business.Payment.PaymentDirectory;

import java.util.ArrayList;

/**
 *
 * @author MyPC1
 */
public class EcoSystem extends Organization{
    
    private static EcoSystem business;
    private ArrayList<Network> networkList;
    private SalesRecordDirectory salesRecordDirectory;
    private OrderDirectory orderDirectory;
    private PaymentDirectory paymentDirectory;
    
    public static EcoSystem getInstance(){
        if(business==null){
            business=new EcoSystem();
        }
        return business;
    }
    
    public Network createAndAddNetwork(){
        Network network=new Network();
        networkList.add(network);
        return network;
    }
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roleList=new ArrayList<Role>();
        roleList.add(new AdminRole());
        return roleList;
    }
    private EcoSystem(){
        super(null);
        networkList=new ArrayList<Network>();
        salesRecordDirectory = new SalesRecordDirectory();
        orderDirectory = new OrderDirectory();
        paymentDirectory = new PaymentDirectory();
//        refreshPaymentDirectory();
    }

    public ArrayList<Network> getNetworkList() {
        return networkList;
    }

    public void setNetworkList(ArrayList<Network> networkList) {
        this.networkList = networkList;
    }
    
    public SalesRecordDirectory getSalesRecordDirectory() {
        return salesRecordDirectory;
    }
    
    public void setSalesRecordDirectory(SalesRecordDirectory salesRecordDirectory) {
        this.salesRecordDirectory = salesRecordDirectory;
    }
    
    public OrderDirectory getOrderDirectory() {
        return orderDirectory;
    }
    
    public void setOrderDirectory(OrderDirectory orderDirectory) {
        this.orderDirectory = orderDirectory;
    }
    
    public PaymentDirectory getPaymentDirectory() {
        return paymentDirectory;
    }
    
    public void setPaymentDirectory(PaymentDirectory paymentDirectory) {
        this.paymentDirectory = paymentDirectory;
    }
    
    public boolean checkIfUserIsUnique(String userName){
        if(!this.getUserAccountDirectory().checkIfUsernameIsUnique(userName)){
            return false;
        }
        for(Network network:networkList){
            
        }
        return true;
    }
    
    public void refreshPaymentDirectory() {
//        paymentDirectory.removeAll();
//        for (Order o : orderDirectory.getOrderList()) {
//            Payment p = new Payment(o);
//            paymentDirectory.getPaymentList().add(p);
//        }
    }
}
