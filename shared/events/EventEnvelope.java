
package shared.events;
import java.time.Instant;
public record EventEnvelope<T>(String eventId,String eventType,int version,Instant timestamp,T payload){}
