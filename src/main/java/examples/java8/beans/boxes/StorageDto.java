

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
public class StorageDto {

    private String name;
    private String address;

    private List<BoxDto> listBoxes;

    private FeatureDto ft;

}
