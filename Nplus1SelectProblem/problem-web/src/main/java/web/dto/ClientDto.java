package web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClientDto extends BaseDto {
    private String serialNumber;
    private String name;
    private int age;

}
