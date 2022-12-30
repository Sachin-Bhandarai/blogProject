package com.mountblue.blog.repository;

import com.mountblue.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    public List<Post> findByAuthor(String author);
    List<Post>findByTitleOrExcerptOrAuthor(String title,String excerpt,String author);

    List<Post> findByTitle(String title);

    List<Post> findByExcerpt(String excerpt);
    List<Post> findByContentContainingIgnoreCase(String infix);
    List<Post> findByContentContaining(String infix);
    List<Post> findByAuthorContaining(String infix);

    List<Post> findByTitleContaining(String infix);
    List<Post> findByAuthorContainingIgnoreCase(String infix);

    List<Post> findByTitleContainingIgnoreCase(String infix);
    @Query("SELECT DISTINCT p.author FROM Post p")
    List<String> getEntireColumn();
//    @Query("select distinct p from Post p join p.tags t where ((t.name IN :tag) and (p.name IN :author) and" +
//            "(published_at between :startDate and :endDate))")
//    List<Post> findByColumn1Column2In(@Param("column1") List<String> tags, @Param("column2") List<String> authors,
//                                              @Param("startDate") LocalDateTime startDate, @Param("endtDate") LocalDateTime endDate);
//
@Query("select distinct p from Post p join p.tags t where (t.name IN (:tag) and p.author IN (:author))")
List<Post> filterByTagAuthorDate(@Param("tag") String[] tags,@Param("author")  String[] authors);

//    @Query(value = "SELECT p.* from parents p inner join children c on c.id=p.childId where TIMESTAMPDIFF(SECOND, p.ts, CURRENT_TIMESTAMP) < :interval", nativeQuery = true)
}
