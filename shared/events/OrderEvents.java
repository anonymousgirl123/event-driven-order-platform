
package shared.events;
public record OrderCreatedV2(String orderId,String item,int quantity,String customerId){}
public record OrderApproved(String orderId){}
public record OrderRejected(String orderId,String reason){}
