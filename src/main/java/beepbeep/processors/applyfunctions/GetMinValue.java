package beepbeep.processors.applyfunctions;

import ca.uqac.lif.cep.functions.Cumulate;
import ca.uqac.lif.cep.functions.CumulativeFunction;
import ca.uqac.lif.cep.util.Numbers;

public class GetMinValue extends Cumulate {

    public GetMinValue() {
        super(new CumulativeFunction<>(Numbers.minimum));
    }

}