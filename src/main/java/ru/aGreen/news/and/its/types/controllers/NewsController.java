package ru.aGreen.news.and.its.types.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.aGreen.news.and.its.types.models.News;
import ru.aGreen.news.and.its.types.models.NewsType;
import ru.aGreen.news.and.its.types.repositories.NewsRepository;
import ru.aGreen.news.and.its.types.repositories.NewsTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class NewsController {
    @Autowired
    private NewsTypeRepository newsTypeRepository;
    @Autowired
    private NewsRepository newsRepository;

/*
* CRUD ТИПОВ НОВОСТЕЙ
* */
    //Страница с типами новостей
    @GetMapping("/")
    public String newsType(Model model) {
        Iterable<NewsType> newsTypes = newsTypeRepository.findAll();
        model.addAttribute("newsTypes", newsTypes);
        return "news-type";
    }
    //Редактирование новости
    @GetMapping("/news-type/{id}/edit")
    public String editNewsType(@PathVariable(value = "id") Long id, Model model) {
        NewsType newsTypes = newsTypeRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        model.addAttribute("newsTypes", newsTypes);
        return "news-type-edit";
    }
    //Сохраниение изменений после редактирование новости
    @PostMapping("/news-type/{id}/edit")
    public String saveNewsType(@PathVariable(value = "id") Long id, @RequestParam String name, @RequestParam String color, Model model) {
        NewsType newsType = newsTypeRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        newsType.setName(name);
        newsType.setColor(color);
        newsTypeRepository.save(newsType);
        return "redirect:/";
    }
    //Удаление типа новости
    @PostMapping("/news-type/{id}/remove")
    public String removeNewsType(@PathVariable(value = "id") Long id, Model model) {
        NewsType newsType = newsTypeRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        newsTypeRepository.delete(newsType);
        return "redirect:/";
    }
    //Добавление нового типа новости
    @PostMapping("/news-type/add")
    public String addNewsType(@RequestParam String name, @RequestParam String color, Model model) {
        NewsType newsType = new NewsType(name, color);
        newsTypeRepository.save(newsType);
        return "redirect:/";
    }
    //Получение списка определённого типа новостей
    @GetMapping("/news-type/{id}/list")
    public String newsTypeList(@PathVariable(value = "id") Long id, Model model) {
        NewsType newsType = newsTypeRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        Iterable<News> newsIterable = newsRepository.findAll();
        List<News> newsList = new ArrayList<>();
        for (News news : newsIterable) {
            if (news.getType().equals(newsType)) {
                newsList.add(news);
            }
        }
        model.addAttribute("newsType", newsType);
        model.addAttribute("newsList", newsList);
        return "news-by-type";
    }


/*
* CRUD НОВОСТЕЙ
* */
    //Страница с новостями
    @GetMapping("/news")
    public String news(Model model) {
        Iterable<News> newsList = newsRepository.findAll();
        Iterable<NewsType> newsTypes = newsTypeRepository.findAll();
        model.addAttribute("newsList", newsList);
        model.addAttribute("newsTypes", newsTypes);
        return "news";
    }
    //Подробности новости
    @GetMapping("/news/{id}")
    public String news(@PathVariable(value = "id") Long id, Model model) {
        News news = newsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        model.addAttribute("news", news);
        return "news-details";
    }
    //Редактирование новости
    @GetMapping("/news/{id}/edit")
    public String editNews(@PathVariable(value = "id") Long id, Model model) {
        News news = newsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        Iterable<NewsType> newsTypes = newsTypeRepository.findAll();
        model.addAttribute("news", news);
        model.addAttribute("newsTypes", newsTypes);
        return "news-edit";
    }
    //Сохраниение изменений после редактирование новости
    @PostMapping("/news/{id}/edit")
    public String saveNews(@PathVariable(value = "id") Long id, @RequestParam String heading, @RequestParam String shortDescription, @RequestParam String fullDescription, @RequestParam Long type, Model model) {
        NewsType newsType = newsTypeRepository.findById(type).orElseThrow(() -> new NoSuchElementException(""));
        News news = newsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        news.setHeading(heading);
        news.setShortDescription(shortDescription);
        news.setFullDescription(fullDescription);
        news.setType(newsType);
        newsRepository.save(news);
        return "redirect:/news";
    }
    //Удаление новости
    @PostMapping("/news/{id}/remove")
    public String removeNews(@PathVariable(value = "id") Long id, Model model) {
        News news = newsRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
        newsRepository.delete(news);
        return "redirect:/news";
    }
    //Добавление новой новости
    @PostMapping("/news/add")
    public String addNews(@RequestParam String heading, @RequestParam String shortDescription, @RequestParam String fullDescription, @RequestParam Long type, Model model) {
        NewsType newsType = newsTypeRepository.findById(type).orElseThrow(() -> new NoSuchElementException(""));
        News news = new News(heading, shortDescription, fullDescription, newsType);
        newsRepository.save(news);
        return "redirect:/news";
    }
}