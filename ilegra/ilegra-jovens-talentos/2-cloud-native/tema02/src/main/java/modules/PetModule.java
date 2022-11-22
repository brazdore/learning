package modules;

import com.google.inject.AbstractModule;
import services.OperationService;
import services.PetService;
import services.TupleService;

public class PetModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PetService.class);
        bind(TupleService.class);
        bind(OperationService.class);
    }
}
