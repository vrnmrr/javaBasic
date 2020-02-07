


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
public class DummyOuterBean implements Serializable {

    private static final long serialVersionUID = 4709727612490829666L;

    private String name;
    private String surname;
    private Long num;
    private boolean bool;
    private DummyInnerBean innerBean;

};