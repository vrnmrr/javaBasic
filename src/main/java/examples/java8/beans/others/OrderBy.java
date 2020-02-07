


package examples.java8.beans.others;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderBy implements Serializable {

    private static final long serialVersionUID = 4298577961160180327L;

    public enum Sort {
        ASC, DESC
    }

    private String field;
    private Sort sort;
}