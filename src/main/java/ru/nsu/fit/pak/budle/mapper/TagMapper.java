package ru.nsu.fit.pak.budle.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.Tag;
import ru.nsu.fit.pak.budle.dto.TagDto;
import ru.nsu.fit.pak.budle.utils.ImageWorker;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TagMapper {
    private final ImageWorker imageWorker;

    public TagDto modelToSpotTagDto(Tag tag) {
        TagDto tagDto = new TagDto(tag.translateForSpot, tag.assets);
        tagDto.setImage(imageWorker.getImageFromResource(tagDto.getImage()));
        return tagDto;
    }

    public List<TagDto> modelSetToSpotTagDtoList(Set<Tag> tags) {
        return tags.stream()
                .map(this::modelToSpotTagDto)
                .toList();
    }

    public TagDto modelToTagDto(Tag tag) {
        TagDto tagDto = new TagDto(tag.translate, tag.assets);
        tagDto.setImage(imageWorker.getImageFromResource(tagDto.getImage()));
        return tagDto;
    }

    public List<TagDto> modelArrayToTagDtoList(Tag[] tags) {
        return Arrays.stream(tags)
                .map(this::modelToTagDto)
                .toList();
    }

    public Set<Tag> tagDtoSetToModelSet(Set<TagDto> tagDtoSet) {
        return tagDtoSet.stream()
                .map(x -> Tag.parseEnum(x.getName()))
                .collect(Collectors.toSet());
    }

}
