



package examples.java8.beans.boxes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private Integer numberInt;
    private Long numberLong;
    private String name;
    
    private FeatureDto ft;
}