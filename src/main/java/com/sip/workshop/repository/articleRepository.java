package com.sip.workshop.repository;

import org.springframework.data.repository.CrudRepository;

import com.sip.workshop.entities.Article;

public interface articleRepository extends CrudRepository<Article, Integer> {
   
}
