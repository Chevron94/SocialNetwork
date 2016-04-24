package network.dto;

import java.util.List;

/**
 * Created by Роман on 23.04.2016.
 */
public class LanguageDto {
    List<Long> languages;
    List<Long> languageLevels;

    public List<Long> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Long> languages) {
        this.languages = languages;
    }

    public List<Long> getLanguageLevels() {
        return languageLevels;
    }

    public void setLanguageLevels(List<Long> languageLevels) {
        this.languageLevels = languageLevels;
    }
}
