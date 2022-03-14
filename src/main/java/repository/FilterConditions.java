package repository;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
public class FilterConditions {
    private Optional<LocalDate> startDate;
    private Optional<LocalDate> endDate;
    private Optional<String> keyword;
    private Optional<String> destinationName;
    private Optional<Double> minPrice;
    private Optional<Double> maxPrice;
    private boolean available;

    private FilterConditions(Optional<LocalDate> startDate, Optional<LocalDate> endDate, Optional<String> keyword, Optional<String> destinationName, Optional<Double> minPrice, Optional<Double> maxPrice, boolean available) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.keyword = keyword;
        this.destinationName = destinationName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.available = available;
    }

    public static class FilterConditionsBuilder {
        private Optional<LocalDate> startDate = Optional.empty();
        private Optional<LocalDate> endDate = Optional.empty();
        private Optional<String> keyword = Optional.empty();
        private Optional<String> destinationName = Optional.empty();
        private Optional<Double> minPrice = Optional.empty();
        private Optional<Double> maxPrice = Optional.empty();
        private boolean available;

        public FilterConditionsBuilder withStartDate(LocalDate startDate) {
            this.startDate = Optional.of(startDate);
            return this;
        }

        public FilterConditionsBuilder withEndDate(LocalDate endDate) {
            this.endDate = Optional.of(endDate);
            return this;
        }

        public FilterConditionsBuilder withMinPrice(Double minPrice) {
            this.minPrice = Optional.of(minPrice);
            return this;
        }

        public FilterConditionsBuilder withMaxPrice(Double maxPrice) {
            this.maxPrice = Optional.of(maxPrice);
            return this;
        }

        public FilterConditionsBuilder withKeyword(String keyword) {
            this.keyword = Optional.of(keyword);
            return this;
        }

        public FilterConditionsBuilder withDestinationName(String destinationName) {
            this.destinationName = Optional.of(destinationName);
            return this;
        }

        public FilterConditionsBuilder withAvailability(boolean available) {
            this.available = available;
            return this;
        }

        public FilterConditions build() {
            return new FilterConditions(startDate, endDate, keyword, destinationName, minPrice, maxPrice, available);
        }
    }
}
