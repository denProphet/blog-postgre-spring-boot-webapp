package karazin.yarovoy.db.lab4.blog.repository;

import karazin.yarovoy.db.lab4.blog.entities.Post;
import org.springframework.data.repository.CrudRepository;


public interface PostRepository extends CrudRepository<Post,Long> {
}
