package com.godeltech.gbf.factory.impl;

import com.godeltech.gbf.factory.Factory;
import com.godeltech.gbf.localization.LocalMessageSource;
import com.godeltech.gbf.localization.impl.RuMessageSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LocalMessageSourceFactory implements Factory<LocalMessageSource, String> {
    private final RuMessageSource defaultMessageSource;
    private final Map<String, LocalMessageSource> localMessageSourceContext;

    public LocalMessageSourceFactory(List<LocalMessageSource> localMessageSources, RuMessageSource defaultMessageSource) {
        localMessageSourceContext = localMessageSources.stream()
                .collect(Collectors.toMap(LocalMessageSource::getLanguage, Function.identity()));
        this.defaultMessageSource = defaultMessageSource;
    }

    @Override
    public LocalMessageSource get(String language) {
        log.info("Get localMessageSource by language = {}", language);
        return localMessageSourceContext.getOrDefault(language, defaultMessageSource);
    }
}
