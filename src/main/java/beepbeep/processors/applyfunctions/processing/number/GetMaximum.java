package beepbeep.processors.applyfunctions.processing.number;

import ca.uqac.lif.cep.functions.Cumulate;
import ca.uqac.lif.cep.functions.CumulativeFunction;
import ca.uqac.lif.cep.util.Numbers;

public class GetMaximum extends Cumulate {

    public GetMaximum() {
        super(new CumulativeFunction<>(Numbers.maximum));
    }

}
