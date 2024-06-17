package com.example.wandukong.repository.miniHome;


import com.example.wandukong.domain.MiniHome.MiniHome;
import com.example.wandukong.domain.MiniHome.MiniHomeLike;
import com.example.wandukong.domain.MiniHome.QMiniHome;
import com.example.wandukong.domain.MiniHome.QMiniHomeLike;
import com.example.wandukong.dto.MiniHome.MiniHomeDto;
import com.example.wandukong.util.GetIpUtil;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Duration;

@RequiredArgsConstructor
public class MiniHomeRepositoryCustomImpl implements MiniHomeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final RedisTemplate<String, String> redisTemplate;
    private final GetIpUtil getIpUtil;

    @Override
    public MiniHomeDto findByUserDo_UserId(Long userId, Long likeUserId) {
        QMiniHome miniHome = QMiniHome.miniHome;
        QMiniHomeLike miniHomeLike = QMiniHomeLike.miniHomeLike;


        MiniHome miniHome1 = jpaQueryFactory.selectFrom(miniHome)
                .where(miniHome.userDo.userId.eq(userId))
                .fetchOne();


        if (miniHome1 == null) {
            throw new EntityNotFoundException("MiniHome not found for userId: " + userId);
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        if (request == null) {
            throw new IllegalStateException("No current HTTP request found");
        }

        String userIp = getIpUtil.getIp(request);
        String userAgent = request.getHeader("User-Agent");

        String visitorkey = "visit:" + miniHome1.getHpId() + ":" + userIp;
        String viewKey = "view:" + miniHome1.getHpId();

        if (!redisTemplate.hasKey(visitorkey)) {
            redisTemplate.opsForValue().set(visitorkey, userAgent, Duration.ofHours(24));
            redisTemplate.opsForValue().increment(viewKey);
            miniHome1.viewCount(miniHome1.getAllVisit() + 1);
        }

        Boolean userLikesMiniHome = jpaQueryFactory.selectFrom(miniHomeLike)
                .where(miniHomeLike.miniHome.eq(miniHome1).and(miniHomeLike.userDo.userId.eq(likeUserId)))
                .fetchFirst() != null;

        Long totalLikeCount = jpaQueryFactory.select(miniHomeLike.count())
                .from(miniHomeLike)
                .where(miniHomeLike.miniHome.eq(miniHome1))
                .fetchOne();


        // Build and return the MiniHomeDto
        MiniHomeDto miniHomeDto = MiniHomeDto.builder()
                .userId(miniHome1.getUserDo().getUserId())
                .hpId(miniHome1.getHpId())
                .statusM(miniHome1.getStatusM())
                .introduction(miniHome1.getIntroduction())
                .playlistId(miniHome1.getPlaylist().getPlaylistId())
                .hpOpen(miniHome1.getHpOpen())
                .allVisit(miniHome1.getAllVisit())
                .hpToday(Integer.parseInt(redisTemplate.opsForValue().get(viewKey)))
                .like(userLikesMiniHome)
                .likeCount(totalLikeCount)
                .build();

        return miniHomeDto;
    }
}