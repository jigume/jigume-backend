package site.jigume.domain.goods.service.commend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.goods.repository.GoodsRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GoodsScheduler {

    private final GoodsRepository goodsRepository;

    @Scheduled(cron = "0 0/1 * * * *")
    public void checkGoodsLimitTime() {
        goodsRepository.checkGoodsLimitTime(LocalDateTime.now());
    }
}
