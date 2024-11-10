package store.dto;

import store.domain.vo.Name;
import store.domain.vo.Quantity;

public record ProductSelectionDTO(
        Name name,
        Quantity quantity
) {
    public static ProductSelectionDTO of(Name name, Quantity quantity) {
        return new ProductSelectionDTO(name, quantity);
    }
}
