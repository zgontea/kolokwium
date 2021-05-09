package edu.iis.mto.oven;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class BakingProgram implements Iterable<ProgramStage> {

    private final int initialTemp;
    private final List<ProgramStage> stage;

    private BakingProgram(Builder builder) {
        this.initialTemp = builder.initialTemp;
        this.stage = builder.stage;
    }

    @Override
    public Iterator<ProgramStage> iterator() {
        return List.copyOf(stage)
                   .iterator();
    }

    public int getInitialTemp() {
        return initialTemp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private int initialTemp;
        private List<ProgramStage> stage = Collections.emptyList();

        private Builder() {}

        public Builder withInitialTemp(int initialTemp) {
            this.initialTemp = initialTemp;
            return this;
        }

        public Builder withStage(List<ProgramStage> stage) {
            this.stage = stage;
            return this;
        }

        public BakingProgram build() {
            return new BakingProgram(this);
        }
    }

}
