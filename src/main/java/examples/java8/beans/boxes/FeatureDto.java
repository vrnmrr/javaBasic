

package examples.java8.beans.boxes;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeatureDto {

    private Integer numberInt;
    private Long numberLong;
    private String desc;

    private List<FeatureDto> listFeatures;

    public FeatureDto(String desc) {
        super();
        this.desc = desc;
    }

}