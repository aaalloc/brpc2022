package beepbeep.processors.applyfunctions;

import ca.uqac.lif.cep.functions.Cumulate;
import ca.uqac.lif.cep.functions.CumulativeFunction;
import ca.uqac.lif.cep.util.Numbers;

public class GetMaxValue extends Cumulate {

    public GetMaxValue() {
        super(new CumulativeFunction<>(Numbers.maximum));
    }

}
