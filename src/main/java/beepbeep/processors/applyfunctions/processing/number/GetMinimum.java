package beepbeep.processors.applyfunctions.processing.number;

import ca.uqac.lif.cep.functions.Cumulate;
import ca.uqac.lif.cep.functions.CumulativeFunction;
import ca.uqac.lif.cep.util.Numbers;

public class GetMinimum extends Cumulate {

    public GetMinimum() {
        super(new CumulativeFunction<>(Numbers.minimum));
    }

}