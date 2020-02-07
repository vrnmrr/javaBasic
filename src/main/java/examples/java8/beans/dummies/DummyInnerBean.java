

package examples.java8.beans.dummies;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DummyInnerBean implements Serializable {

    private static final long serialVersionUID = 984535567790896566L;

    private String name;
    private String surname;
    private Long num;
    private boolean bool;
    private DummyInnerBean deepInner;
}