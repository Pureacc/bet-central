package org.pureacc.betcentral.application.api;

import org.pureacc.betcentral.vocabulary.DecimalOdds;
import org.pureacc.betcentral.vocabulary.Percentage;

public interface CalculateNoVigOdds {
    Response execute(Request request);

    final class Request {
        private final DecimalOdds oddsA;
        private final DecimalOdds oddsB;

        private Request(Builder builder) {
            oddsA = builder.oddsA;
            oddsB = builder.oddsB;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public DecimalOdds getOddsA() {
            return oddsA;
        }

        public DecimalOdds getOddsB() {
            return oddsB;
        }

        public static final class Builder {
            private DecimalOdds oddsA;
            private DecimalOdds oddsB;

            private Builder() {
            }

            public Builder withOddsA(DecimalOdds oddsA) {
                this.oddsA = oddsA;
                return this;
            }

            public Builder withOddsB(DecimalOdds oddsB) {
                this.oddsB = oddsB;
                return this;
            }

            public Request build() {
                return new Request(this);
            }
        }
    }

    final class Response {
        private final DecimalOdds noVigOddsA;
        private final DecimalOdds noVigOddsB;
        private final Percentage vig;

        private Response(Builder builder) {
            noVigOddsA = builder.noVigOddsA;
            noVigOddsB = builder.noVigOddsB;
            vig = builder.vig;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public DecimalOdds getNoVigOddsA() {
            return noVigOddsA;
        }

        public DecimalOdds getNoVigOddsB() {
            return noVigOddsB;
        }

        public Percentage getVig() {
            return vig;
        }

        public static final class Builder {
            private DecimalOdds noVigOddsA;
            private DecimalOdds noVigOddsB;
            private Percentage vig;

            private Builder() {
            }

            public Builder withNoVigOddsA(DecimalOdds noVigOddsA) {
                this.noVigOddsA = noVigOddsA;
                return this;
            }

            public Builder withNoVigOddsB(DecimalOdds noVigOddsB) {
                this.noVigOddsB = noVigOddsB;
                return this;
            }

            public Builder withVig(Percentage vig) {
                this.vig = vig;
                return this;
            }

            public Response build() {
                return new Response(this);
            }
        }
    }
}
