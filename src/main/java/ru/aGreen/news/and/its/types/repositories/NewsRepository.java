package ru.aGreen.news.and.its.types.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.aGreen.news.and.its.types.models.News;

public interface NewsRepository extends CrudRepository<News, Long> {
}
