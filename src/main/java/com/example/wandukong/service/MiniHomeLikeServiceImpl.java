package com.example.wandukong.service;

import com.example.wandukong.domain.MiniHome.MiniHome;
import com.example.wandukong.domain.MiniHome.MiniHomeLike;
import com.example.wandukong.domain.MiniHome.MiniHomeLikePK;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.repository.MiniHomeLikeRepository;
import com.example.wandukong.repository.miniHome.MiniHomeRepository;
import com.example.wandukong.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MiniHomeLikeServiceImpl implements MiniHomeLikeService {

    private final MiniHomeLikeRepository miniHomeLikeRepository;
    private final MiniHomeRepository miniHomeRepository;
    private final UserRepository userRepository;

    @Override
    public void addLike(Long hpId, Long userId) {

        MiniHomeLikePK miniHomeLikePK = new MiniHomeLikePK(hpId, userId);
        MiniHome miniHome = miniHomeRepository.getReferenceById(hpId);
        UserDo userDo = userRepository.getReferenceById(userId);

        MiniHomeLike miniHomeLike = MiniHomeLike.builder()
                .miniHomeLikePK(miniHomeLikePK)
                .miniHome(miniHome)
                .userDo(userDo)
                .build();

        miniHomeLikeRepository.save(miniHomeLike);

    }

    @Override
    public void deleteLike(Long hpId, Long userId) {
        MiniHomeLikePK miniHomeLikePK = new MiniHomeLikePK(hpId, userId);
      
        miniHomeLikeRepository.deleteById(miniHomeLikePK);
    }
}
