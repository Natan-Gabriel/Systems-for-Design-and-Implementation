package web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class RentalDto extends BaseDto{
    private String rentalSerialNumber;
    private long clientID;
    private long movieID;
    private boolean returned;
}
