package karazin.yarovoy.db.lab4.blog.entities;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "posts")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;

    @NonNull
    private LocalDateTime publish_date;
    @NonNull
    private String username;
    @NonNull
    private String post_text;



}

