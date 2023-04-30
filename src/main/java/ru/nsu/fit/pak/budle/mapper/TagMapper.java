package ru.nsu.fit.pak.budle.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Tag;
import ru.nsu.fit.pak.budle.dto.request.RequestTagDto;
import ru.nsu.fit.pak.budle.dto.response.ResponseTagDto;
import ru.nsu.fit.pak.budle.utils.ImageWorker;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TagMapper {
    private final ImageWorker imageWorker;

    public ResponseTagDto modelToSpotTagDto(Tag tag) {
        ResponseTagDto tagDto = new ResponseTagDto(tag.translateForSpot, tag.assets);
        tagDto.setImage(imageWorker.getImageFromResource(tagDto.getImage()));
        return tagDto;
    }

    public List<ResponseTagDto> modelSetToSpotTagDtoList(Set<Tag> tags) {
        return tags.stream()
                .map(this::modelToSpotTagDto)
                .toList();
    }

    public ResponseTagDto modelToTagDto(Tag tag) {
        ResponseTagDto tagDto = new ResponseTagDto(tag.translate, tag.assets);
        tagDto.setImage(imageWorker.getImageFromResource(tagDto.getImage()));
        return tagDto;
    }

    public List<ResponseTagDto> modelArrayToTagDtoList(Tag[] tags) {
        return Arrays.stream(tags)
                .map(this::modelToTagDto)
                .toList();
    }

    public Set<Tag> tagDtoSetToModelSet(Set<RequestTagDto> tagDtoSet) {
        return tagDtoSet.stream()
                .map(x -> Tag.parseEnum(x.getName()))
                .collect(Collectors.toSet());
    }

}
