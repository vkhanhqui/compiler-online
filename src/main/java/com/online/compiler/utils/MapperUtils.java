package com.online.compiler.utils;

import com.online.compiler.model.CodeFile;
import com.online.compiler.model.Exercise;
import com.online.compiler.model.dto.CodeFileDTO;
import com.online.compiler.model.dto.ExerciseDTO;
import org.modelmapper.ModelMapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class MapperUtils {

    public static ModelMapper modelMapper = new ModelMapper();

    public static Path toDestinationFile(Path rootLocation, String filename) {
        return rootLocation.resolve(
                Paths.get(filename))
                .normalize().toAbsolutePath();
    }

    public static <S, T> Set<T> mapSet(List<S> source, Class<T> targetClass) {
        return source.stream()
                .map(element -> MapperUtils.modelMapper.map(element, targetClass))
                .collect(Collectors.toSet());
    }

    public static ExerciseDTO toExerciseDTO(Exercise exercise) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Exercise.class, ExerciseDTO.class)
                .setPostConverter(
                        mappingContext -> {
                            Set<UUID> uuidSet = mappingContext.getSource().getCodeFiles().stream()
                                    .map(CodeFile::getId).collect(Collectors.toSet());
                            ExerciseDTO exerciseDTO = mappingContext.getDestination();
                            exerciseDTO.setIdCodeFiles(uuidSet);
                            return exerciseDTO;
                        }
                );
        return modelMapper.map(exercise, ExerciseDTO.class);
    }

    public static CodeFileDTO toCodeFileDTO(CodeFile codeFile) {
        return modelMapper.map(codeFile, CodeFileDTO.class);
    }

}
