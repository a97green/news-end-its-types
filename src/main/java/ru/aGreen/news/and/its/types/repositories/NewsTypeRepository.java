package ru.aGreen.news.and.its.types.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.aGreen.news.and.its.types.models.NewsType;

public interface NewsTypeRepository extends CrudRepository<NewsType, Long> {
}
