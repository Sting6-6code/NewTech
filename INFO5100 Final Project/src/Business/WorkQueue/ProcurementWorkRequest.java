package Business.WorkQueue;

import java.util.Date;

/**
 * ProcurementWorkRequest for inventory replenishment
 * @author wangsiting
 */
public class ProcurementWorkRequest extends WorkRequest {
    private String productId;
    private String productName;
    private int currentAmount;
    private int requestedAmount;
    private int actualAmount;
    private boolean processed;
    
    public ProcurementWorkRequest() {
        super();
        this.setStatus("Pending");
        this.processed = false;
    }
    
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(int requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public int getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(int actualAmount) {
        this.actualAmount = actualAmount;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
        if (processed) {
            this.setResolveDate(new Date());
        }
    }
    
    @Override
    public String toString() {
        return productName + " (Current: " + currentAmount + 
               ", Requested: " + requestedAmount + ", Status: " + getStatus() + ")";
    }
} 