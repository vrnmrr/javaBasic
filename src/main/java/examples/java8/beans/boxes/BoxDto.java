

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
public class BoxDto {

    private Integer numberInt;
    private Long numberLong;
    private String name;

    private List<ItemDto> listItems;

    private StorageDto storage;

    private FeatureDto ft;
}