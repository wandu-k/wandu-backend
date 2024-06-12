package com.example.wandukong.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.domain.Album;
import com.example.wandukong.domain.AlbumFile;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.dto.AlbumDto;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.repository.AlbumFileRepository;
import com.example.wandukong.repository.AlbumRepository;
import com.example.wandukong.repository.user.UserRepository;
import com.example.wandukong.util.S3Util;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;
    private final AlbumFileRepository albumFileRepository;
    private final S3Util s3Util;

    @Override
    public void deletePicture(Long pictureId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePicture'");
    }

    @Override
    public ApiResponseDto putPicture(MultipartFile image, AlbumDto pictureDto) {

        // if (pictureDto.getPictureId() == null) {

        // ApiResponseDto apiResponseDto = ApiResponseDto.builder()
        // .message("등록 완료").status(HttpStatus.OK).build();

        // return apiResponseDto;

        // }
        return null;
    }

    // SliceResponseDto<AlbumDto> 일단 급하니까 페이지네이션은 나중에...
    @Override
    public List<AlbumDto> getAlbumList(Long userId) {

        List<Album> list = albumRepository.findAllByUserDo_UserId(userId);

        List<AlbumDto> albumDtos = new ArrayList<>();

        for (Album album : list) {
            AlbumDto albumDto = AlbumDto.builder()
                    .userId(album.getUserDo().getUserId())
                    .albumId(album.getAlbumId())
                    .intro(album.getIntro())
                    .file(s3Util.getUrl(album.getAlbumFile().getFileName()))
                    .build();

            albumDtos.add(albumDto);
        }
        return albumDtos;
    }

    @Override
    public AlbumDto getAlbum(Long albumId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAlbum'");
    }

    @Override
    public void addAlbum(MultipartFile multipartFile, Long userId, AlbumDto albumDto) throws IOException {
        UserDo userDo = userRepository.getReferenceById(userId);

        Album album = new Album(null, albumDto.getIntro(), userDo);

        album = albumRepository.save(album);

        String uuid = UUID.randomUUID().toString();

        String objectKey = "users/album/" + userId + "/" + uuid + "-" + multipartFile.getOriginalFilename();

        s3Util.itemfileUpload(multipartFile, objectKey);

        AlbumFile albumFile = new AlbumFile(album.getAlbumId(), null, objectKey);

        albumFileRepository.save(albumFile);

    }

}
