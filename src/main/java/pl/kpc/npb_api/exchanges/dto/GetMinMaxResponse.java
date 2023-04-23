package pl.kpc.npb_api.exchanges.dto;
import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GetMinMaxResponse {
    private Double min;
    private Double max;
}
