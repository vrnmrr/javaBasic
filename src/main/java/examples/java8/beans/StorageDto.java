

package examples.java8.beans;

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

}
