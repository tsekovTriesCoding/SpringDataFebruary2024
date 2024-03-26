package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.dto.UserWithPostExportDto;
import softuni.exam.instagraphlite.models.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("select u from User u " +
            "join u.posts as p group by u.id " +
            "order by size(p) desc, u.id ")
    Optional<List<User>> findAllUsersWithPosts();
}
