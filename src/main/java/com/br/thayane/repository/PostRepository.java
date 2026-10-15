package com.br.thayane.repository;

import com.br.thayane.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAtivoTrueOrderByDataPublicacaoDescIdDesc();
    Optional<Post> findByIdAndAtivoTrue(Long id);
}
