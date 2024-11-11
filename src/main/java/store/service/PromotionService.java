package store.service;

import java.util.List;
import java.util.stream.Collectors;
import store.dao.PromotionDAO;
import store.domain.entity.Promotion;
import store.dto.PromotionDTO;

public class PromotionService {

    private final PromotionDAO promotionDAO;

    public PromotionService(PromotionDAO promotionDAO) {
        this.promotionDAO = promotionDAO;
    }

    public List<Promotion> findAllPromotionsAsEntities() {
        return findAllPromotions().stream()
                .map(PromotionDTO::toEntity)
                .collect(Collectors.toList());
    }

    private List<PromotionDTO> findAllPromotions() {
        return promotionDAO.findAll().stream()
                .map(PromotionDTO::from)
                .collect(Collectors.toList());
    }
}
