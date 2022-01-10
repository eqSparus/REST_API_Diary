package ru.diary.services.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.diary.models.Label;
import ru.diary.models.dto.LabelDto;
import ru.diary.repositories.ILabelRepository;
import ru.diary.repositories.IUserRepository;
import ru.diary.services.IDataService;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class LabelService implements IDataService<LabelDto, Label> {

    ILabelRepository labelRepository;
    IUserRepository userRepository;

    @Autowired
    public LabelService(ILabelRepository labelRepository, IUserRepository userRepository) {
        this.labelRepository = labelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Label create(LabelDto labelDto, String login) {

        var user = userRepository.findUserByEmail(login).orElseThrow(IllegalAccessError::new);

        var label = Label.builder()
                .title(labelDto.getTitle())
                .color(labelDto.getColor())
                .userId(user.getId())
                .build();


        return labelRepository.create(label).orElseThrow(IllegalAccessError::new);
    }

    @Override
    public Label update(LabelDto labelDto, Long id) {

        return labelRepository.update(
                Label.builder()
                        .id(id)
                        .title(labelDto.getTitle())
                        .color(labelDto.getColor())
                        .build()
        ).orElseThrow(IllegalAccessError::new);

    }

    @Override
    public void delete(Long id) {
        labelRepository.delete(id);
    }
}
